package controller;

import exceptions.InvalidCourseException;
import exceptions.InvalidStudentException;
import exceptions.InvalidTeacherException;
import exceptions.NullValueException;
import model.Course;
import model.Student;
import model.Teacher;
import repository.CourseJdbcRepository;
import repository.EnrolledJdbcRepository;
import repository.StudentJdbcRepository;
import repository.TeacherJdbcRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseController {

    private CourseJdbcRepository courseJdbcRepo;
    private StudentJdbcRepository studentJdbcRepo;
    private TeacherJdbcRepository teacherJdbcRepo;
    private EnrolledJdbcRepository enrolledJdbcRepo;

    public CourseController(CourseJdbcRepository courseJdbcRepo, StudentJdbcRepository studentJdbcRepo, TeacherJdbcRepository teacherJdbcRepo, EnrolledJdbcRepository enrolledJdbcRepo) {
        this.courseJdbcRepo = courseJdbcRepo;
        this.studentJdbcRepo = studentJdbcRepo;
        this.teacherJdbcRepo = teacherJdbcRepo;
        this.enrolledJdbcRepo = enrolledJdbcRepo;
    }

    /**
     * sorts courses descending by the number of enrolled students
     *
     * @return sorted list of students
     */
    public List<Course> sortCoursesByStudentsEnrolled() throws SQLException, IOException, ClassNotFoundException {
        Connection connection = courseJdbcRepo.openConnection();
        List<Course> courses = courseJdbcRepo.readDataFromDatabase(connection).stream()
                .sorted((course, otherCourse) -> {
                    try {
                        return enrolledJdbcRepo.getStudentsEnrolledInCourse(otherCourse).size() - enrolledJdbcRepo.getStudentsEnrolledInCourse(course).size();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    return 0;
                })
                .collect(Collectors.toList());
        courseJdbcRepo.closeConnection(connection);
        return courses;
    }

    /**
     * filters the courses with the specified number of credits
     * <p>
     * //* @param credits number of credits
     *
     * @return list of courses
     */
    public List<Course> filterCoursesWithSpecifiedCredits(int credits) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = courseJdbcRepo.openConnection();

        return courseJdbcRepo.readDataFromDatabase(connection).stream()
                .filter(course -> course.getCredits() == credits)
                .collect(Collectors.toList());
    }

    public Course findOne(Long id) throws NullValueException, SQLException, IOException, ClassNotFoundException {
        return courseJdbcRepo.findOne(id);
    }

    public List<Course> findAll() throws SQLException, IOException, ClassNotFoundException {
        return courseJdbcRepo.findAll();
    }


    /**
     * saves the parameter object in repoList. Returns the result of method save in CourseRepository
     *
     * @param course to be saved
     * @return result of method save in CourseRepository
     * @throws NullValueException      if the parameter object is null
     * @throws IOException             if the file is invalid
     * @throws InvalidTeacherException course has a teacher who doesn't exist in teacherRepolist
     * @throws InvalidStudentException course has a student in studentList who doesn't exist in studentRepoList
     */
    public Course save(Course course) throws NullValueException, InvalidTeacherException, IOException, InvalidStudentException, SQLException, ClassNotFoundException {
        if (course == null)
            throw new NullValueException("Invalid entity");

        Long teacherId = course.getTeacherId();
        if (teacherId != null) {
            Teacher teacher = teacherJdbcRepo.findOne(teacherId);
            if (teacher == null)
                throw new InvalidTeacherException("Invalid teacher");
        }

        Course result = courseJdbcRepo.save(course);
        return result;
    }

    /**
     * deletes the object with the parameter id from repoList. Returns the result of method delete in CourseRepository
     * <p>
     * //@param id of the object to be deleted
     *
     * @return result of method delete in CourseRepository
     * @throws IOException        if the file is invalid
     * @throws NullValueException if the parameter object is null
     */
    public Course delete(Long id) throws NullValueException, IOException, SQLException, ClassNotFoundException {
        if (id == null)
            throw new NullValueException("Invalid entity");

        Course result = courseJdbcRepo.findOne(id);
        if (result == null)
            return result;

        enrolledJdbcRepo.deleteEnrolledStudentsFromCourse(id);
        courseJdbcRepo.delete(id);
        return result;
    }

    public Course update(Course course) throws IOException, NullValueException, SQLException, ClassNotFoundException {
        return courseJdbcRepo.update(course);
    }

    public int size() throws SQLException, IOException, ClassNotFoundException {
        return courseJdbcRepo.size();
    }
}
