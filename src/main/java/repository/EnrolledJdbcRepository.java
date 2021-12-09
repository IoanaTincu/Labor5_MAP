package repository;

import exceptions.InvalidCourseException;
import exceptions.InvalidStudentException;
import exceptions.InvalidTeacherException;
import exceptions.NullValueException;
import model.Course;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrolledJdbcRepository {
    StudentJdbcRepository studentJdbcRepo;
    CourseJdbcRepository courseJdbcRepo;
    TeacherJdbcRepository teacherJdbcRepo;

    public EnrolledJdbcRepository(StudentJdbcRepository studentJdbcRepo, CourseJdbcRepository courseJdbcRepo, TeacherJdbcRepository teacherJdbcRepo) {
        this.studentJdbcRepo = studentJdbcRepo;
        this.courseJdbcRepo = courseJdbcRepo;
        this.teacherJdbcRepo = teacherJdbcRepo;
    }

    /**
     * enrolls a given student in a given course
     *
     * @param studentId the id of the student that will be enrolled in the course
     * @param courseId  the id of the course in which the student is being enrolled
     * @throws NullValueException      if the parameter object is null
     * @throws SQLException            (getConnection) if a database access error occurs or the url is null
     * @throws IOException             (load) if an error occurred when reading from the input stream
     * @throws ClassNotFoundException  (forName) if the class cannot be located
     * @throws InvalidStudentException will be thrown when the id of the student doesn't exist in studentRepository
     * @throws InvalidCourseException  will be thrown when the id of the course doesn't exist in courseRepository
     * @throws Exception               will be thrown when the student is already enrolled in the course
     */
    public void registerStudentToCourse(Long studentId, Long courseId) throws Exception {
        if (studentId == null || courseId == null)
            throw new NullValueException("Invalid entity");

        Connection connection = courseJdbcRepo.openConnection();

        if (studentJdbcRepo.findOne(studentId) == null)
            throw new InvalidStudentException("Invalid student");
        if (courseJdbcRepo.findOne(courseId) == null)
            throw new InvalidCourseException("Invalid course");
        if (checkExistenceOfEnrollment(studentId, courseId))
            throw new Exception("Student already enrolled in course");

        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO studentsCourses VALUES (?, ?)"
        );
        statement.setLong(1, studentId);
        statement.setLong(2, courseId);

        PreparedStatement otherStatement = connection.prepareStatement(
                "UPDATE students " +
                        "SET totalCredits = totalCredits + ? " +
                        "WHERE id = ?"
        );
        otherStatement.setLong(1, courseJdbcRepo.getCreditsOfCourse(courseId));
        otherStatement.setLong(2, studentId);


        otherStatement.executeUpdate();
        otherStatement.close();
        statement.executeUpdate();
        statement.close();
        courseJdbcRepo.closeConnection(connection);
    }

    /**
     * returns true if the student is already enrolled in the course or false otherwise
     *
     * @param studentId the student for which we check enrollment
     * @param courseId  the course for which we check enrollment
     * @return true if the enrollment already exists or false otherwise
     * @throws NullValueException      if the parameter object is null
     * @throws SQLException            (getConnection) if a database access error occurs or the url is null
     * @throws IOException             (load) if an error occurred when reading from the input stream
     * @throws ClassNotFoundException  (forName) if the class cannot be located
     * @throws InvalidStudentException will be thrown when the id of the student doesn't exist in studentRepository
     * @throws InvalidCourseException  will be thrown when the id of the course doesn't exist in courseRepository
     */
    public boolean checkExistenceOfEnrollment(Long studentId, Long courseId) throws NullValueException, SQLException, IOException, ClassNotFoundException, InvalidStudentException, InvalidCourseException {
        if (studentId == null || courseId == null)
            throw new NullValueException("Invalid entity");

        Connection connection = courseJdbcRepo.openConnection();

        if (studentJdbcRepo.findOne(studentId) == null)
            throw new InvalidStudentException("Invalid student");
        if (courseJdbcRepo.findOne(courseId) == null)
            throw new InvalidCourseException("Invalid course");

        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM studentsCourses WHERE (idStudent=? AND idCourse=?)"
        );
        statement.setLong(1, studentId);
        statement.setLong(2, courseId);

        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) {
            statement.close();
            resultSet.close();
            courseJdbcRepo.closeConnection(connection);
            return false;
        }

        statement.close();
        resultSet.close();
        courseJdbcRepo.closeConnection(connection);
        return true;
    }

    /**
     * returns the students who attend the course. The course is given as a parameter
     *
     * @param course for which we want the attending students
     * @return list with all students who attend the course
     * @throws SQLException           (getConnection) if a database access error occurs or the url is null
     * @throws IOException            (load) if an error occurred when reading from the input stream
     * @throws ClassNotFoundException (forName) if the class cannot be located
     */
    public List<Long> getStudentsEnrolledInCourse(Course course) throws SQLException, IOException, ClassNotFoundException {
        List<Long> students = new ArrayList<>();

        Connection connection = courseJdbcRepo.openConnection();

        PreparedStatement statement = connection.prepareStatement(
                "SELECT idStudent " +
                        "FROM ((students S " +
                        "INNER JOIN studentsCourses Sc ON S.id=Sc.idStudent) " +
                        "INNER JOIN courses C ON Sc.idCourse=C.id)" +
                        "WHERE C.id=?");
        statement.setLong(1, course.getId());
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            long id = resultSet.getLong("idStudent");
            students.add(id);
        }

        statement.close();
        courseJdbcRepo.closeConnection(connection);
        return students;
    }

    /**
     * deletes the enrollments of all students for a given course
     *
     * @param courseId the id of the course for which the enrollments will be deleted
     * @throws NullValueException     will be thrown when the received parameter is null
     * @throws SQLException           (getConnection) if a database access error occurs or the url is null
     * @throws IOException            (load) if an error occurred when reading from the input stream
     * @throws ClassNotFoundException (forName) if the class cannot be located
     */
    public void deleteEnrolledStudentsFromCourse(Long courseId) throws NullValueException, SQLException, IOException, ClassNotFoundException {
        if (courseId == null)
            throw new NullValueException("Invalid entity");

        Connection connection = courseJdbcRepo.openConnection();

        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM studentsCourses WHERE idCourse=?"
        );
        statement.setLong(1, courseId);
        statement.execute();

        statement.close();
        courseJdbcRepo.closeConnection(connection);
    }

    /**
     * deletes the enrollments for a given student
     *
     * @param studentId the id of the student for which the enrollments will be deleted
     * @throws SQLException           (getConnection) if a database access error occurs or the url is null
     * @throws IOException            (load) if an error occurred when reading from the input stream
     * @throws ClassNotFoundException (forName) if the class cannot be located
     */
    public void deleteCoursesAttendedByStudent(Long studentId) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = studentJdbcRepo.openConnection();

        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM studentsCourses WHERE idStudent=?"
        );
        statement.setLong(1, studentId);
        statement.execute();

        statement.close();
        studentJdbcRepo.closeConnection(connection);
    }

    /**
     * deletes the enrollment of a given student in a given course
     *
     * @param studentId the id of the student for which the enrollment will be deleted
     * @param courseId  the id of the course for which the enrollment will be deleted
     * @throws SQLException           (getConnection) if a database access error occurs or the url is null
     * @throws IOException            (load) if an error occurred when reading from the input stream
     * @throws ClassNotFoundException (forName) if the class cannot be located
     */
    public void deleteEnrollment(Long studentId, Long courseId) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = courseJdbcRepo.openConnection();

        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM studentsCourses WHERE idStudent = ? AND idCourse = ?"
        );
        statement.setLong(1, studentId);
        statement.setLong(2, courseId);
        statement.executeUpdate();

        statement.close();
        courseJdbcRepo.closeConnection(connection);
    }

    /**
     * sets the teacher id of a course to NULL before the teacher is deleted from the database
     *
     * @param teacherId the id of the student who will be deleted
     * @throws SQLException           (getConnection) if a database access error occurs or the url is null
     * @throws IOException            (load) if an error occurred when reading from the input stream
     * @throws ClassNotFoundException (forName) if the class cannot be located
     */
    public void deleteTeacherFromCourse(Long teacherId) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = courseJdbcRepo.openConnection();

        PreparedStatement statement = connection.prepareStatement(
                "UPDATE courses SET teacherId=NULL WHERE teacherId=?"
        );
        statement.setLong(1, teacherId);
        statement.executeUpdate();

        statement.close();
        courseJdbcRepo.closeConnection(connection);
    }

    /**
     * sets the teacher of a course
     *
     * @param teacherId the id of the teacher who teaches the course
     * @param courseId the id of the course
     * @throws NullValueException      if the parameter object is null
     * @throws SQLException            (getConnection) if a database access error occurs or the url is null
     * @throws IOException             (load) if an error occurred when reading from the input stream
     * @throws ClassNotFoundException  (forName) if the class cannot be located
     * @throws InvalidTeacherException will be thrown when the id of the teacher doesn't exist in studentRepository
     * @throws InvalidCourseException  will be thrown when the id of the course doesn't exist in courseRepository
     */
    public void registerTeacherToCourse(Long teacherId, Long courseId) throws NullValueException, SQLException, IOException, ClassNotFoundException, InvalidTeacherException, InvalidCourseException {
        if (teacherId == null || courseId == null)
            throw new NullValueException("Invalid entity");

        Connection connection = courseJdbcRepo.openConnection();

        if (teacherJdbcRepo.findOne(teacherId) == null)
            throw new InvalidTeacherException("Invalid teacher");
        if (courseJdbcRepo.findOne(courseId) == null)
            throw new InvalidCourseException("Invalid course");

        PreparedStatement statement = connection.prepareStatement(
                "UPDATE courses SET teacherId=? WHERE id=?"
        );
        statement.setLong(1, teacherId);
        statement.setLong(2, courseId);

        statement.executeUpdate();
        statement.close();
        courseJdbcRepo.closeConnection(connection);
    }
}
