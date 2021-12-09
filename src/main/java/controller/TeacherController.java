package controller;

import exceptions.InvalidCourseException;
import exceptions.NullValueException;
import model.Course;
import model.Teacher;
import repository.CourseJdbcRepository;
import repository.EnrolledJdbcRepository;
import repository.TeacherJdbcRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TeacherController {

    private TeacherJdbcRepository teacherJdbcRepo;
    private EnrolledJdbcRepository enrolledJdbcRepo;

    public TeacherController(TeacherJdbcRepository teacherJdbcRepo, EnrolledJdbcRepository enrolledJdbcRepo) {
        this.teacherJdbcRepo = teacherJdbcRepo;
        this.enrolledJdbcRepo = enrolledJdbcRepo;
    }

    public Teacher findOne(Long id) throws NullValueException, SQLException, IOException, ClassNotFoundException {
        return teacherJdbcRepo.findOne(id);
    }

    public List<Teacher> findAll() throws SQLException, IOException, ClassNotFoundException {
        return teacherJdbcRepo.findAll();
    }

    /**
     * saves the parameter object in repoList. Returns the result of method save in PersonRepository<T>
     *
     * @param teacher to be saved
     * @return result of method save in PersonRepository<T>
     * @throws NullValueException     if the parameter object is null
     * @throws IOException            if the file is invalid
     */
    public Teacher save(Teacher teacher) throws NullValueException, IOException, SQLException, ClassNotFoundException {
        if (teacher == null)
            throw new NullValueException("Invalid entity");

        Teacher result = teacherJdbcRepo.save(teacher);
        return result;
    }

    /**
     * deletes the object with the parameter id from repoList. Returns the result of method delete in PersonRepository<T>
     * <p>
     * //@param id of the object to be deleted
     *
     * @return result of method delete in PersonRepository<T>
     * @throws IOException        if the file is invalid
     * @throws NullValueException if the parameter object is null
     */
    public Teacher delete(Long id) throws IOException, NullValueException, SQLException, ClassNotFoundException {
        if (id == null)
            throw new NullValueException("Invalid entity");

        Teacher result = teacherJdbcRepo.findOne(id);
        if (result == null)
            return result;

        enrolledJdbcRepo.deleteTeacherFromCourse(id);
        teacherJdbcRepo.delete(id);
        return result;
    }

    public Teacher update(Teacher teacher) throws IOException, NullValueException, SQLException, ClassNotFoundException {
        return teacherJdbcRepo.update(teacher);
    }

    public int size() throws SQLException, IOException, ClassNotFoundException {
        return teacherJdbcRepo.size();
    }

}
