package controller;

import com.sun.java.swing.plaf.motif.MotifCheckBoxMenuItemUI;
import com.sun.org.glassfish.gmbal.Description;
import exceptions.InvalidStudentException;
import exceptions.InvalidTeacherException;
import exceptions.NullValueException;
import model.Course;
import model.Student;
import model.Teacher;
import org.junit.jupiter.api.Test;
import repository.CourseJdbcRepository;
import org.mockito.Mockito;
import repository.EnrolledJdbcRepository;
import repository.StudentJdbcRepository;
import repository.TeacherJdbcRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseControllerTest {

    CourseController courseController;
    CourseJdbcRepository courseJdbcRepo = Mockito.mock(CourseJdbcRepository.class);
    StudentJdbcRepository studentJdbcRepo = Mockito.mock(StudentJdbcRepository.class);
    TeacherJdbcRepository teacherJdbcRepo = Mockito.mock(TeacherJdbcRepository.class);
    EnrolledJdbcRepository enrolledJdbcRepo = Mockito.mock(EnrolledJdbcRepository.class);
    Course course1 = new Course(1141, "Algebra liniara", 1200, 45, 18);
    Course course2 = new Course(653, "Analiza matematica", 1200, 45, 11);
    Course course3 = new Course(807, "Programare distribuita", 120, 88, 8);
    Course course4 = new Course(2051, "Modelare si simulare", 1118, 53, 11);
    Course course5 = new Course(807, "Modelare si simulare", 1200, 53, 11);
    Course course6 = new Course(152, "Modelare si simulare", 1200, 53, 11);
    Course course7 = new Course(113, "Modelare si simulare", 1200, 10, 12);
    Course newCourse2 = new Course(653, "Analiza matematica", 945, 48, 6);

    Teacher teacher = new Teacher(1200, "Mugur", "Acu");
    Student student1 = new Student(1216, "Matei", "Stroia", 11);
    Student student2 = new Student(113, "Luca", "Tompea", 12);
    Connection connection;
    List<Course> courses = new ArrayList<>(Arrays.asList(course1, course3, course2));

    @org.junit.jupiter.api.BeforeEach
    void setUp() throws SQLException, IOException, ClassNotFoundException, NullValueException {
        courseController = new CourseController(courseJdbcRepo, studentJdbcRepo, teacherJdbcRepo, enrolledJdbcRepo);
        Mockito.when(courseJdbcRepo.save(null)).thenThrow(NullValueException.class);
        Mockito.when(courseJdbcRepo.save(course4)).thenThrow(InvalidTeacherException.class);
        Mockito.when(teacherJdbcRepo.findOne(1200L)).thenReturn(teacher);
        Mockito.when(courseJdbcRepo.save(course5)).thenReturn(course5);
        Mockito.when(courseJdbcRepo.save(course6)).thenReturn(null);
        Mockito.when(courseJdbcRepo.openConnection()).thenReturn(connection);
        Mockito.when(courseJdbcRepo.readDataFromDatabase(connection)).thenReturn(courses);
        Mockito.when(enrolledJdbcRepo.getStudentsEnrolledInCourse(course1)).thenReturn(new ArrayList<>(Arrays.asList(1216L)));
        Mockito.when(enrolledJdbcRepo.getStudentsEnrolledInCourse(course3)).thenReturn(new ArrayList<>());
        Mockito.when(enrolledJdbcRepo.getStudentsEnrolledInCourse(course2)).thenReturn(new ArrayList<>(Arrays.asList(1216L, 113L)));
        Mockito.when(courseJdbcRepo.delete(null)).thenThrow(NullValueException.class);
        Mockito.when(courseJdbcRepo.delete(1217L)).thenReturn(null);
        Mockito.when(courseJdbcRepo.findOne(1141L)).thenReturn(course1);
        Mockito.when(courseJdbcRepo.delete(1141L)).thenReturn(course1);
        Mockito.when(courseJdbcRepo.update(newCourse2)).thenReturn(null);
        Mockito.when(courseJdbcRepo.findOne(newCourse2.getId())).thenReturn(newCourse2);
        Mockito.when(courseJdbcRepo.update(course7)).thenReturn(course7);
        Mockito.when(courseJdbcRepo.update(null)).thenThrow(NullValueException.class);
    }

    @org.junit.jupiter.api.Test
    @Description("Should throw a NullValueException because the parameter course is null")
    void save_course_null() {
        assertThrows(NullValueException.class, () -> courseController.save(null));
    }

    @org.junit.jupiter.api.Test
    @Description("Should throw an InvalidTeacherException because there is no teacher in the repository with the specified id")
    void save_course_invalid_teacher() {
        assertThrows(InvalidTeacherException.class, () -> courseController.save(course4));
    }

    @org.junit.jupiter.api.Test
    @Description("Should return the course because the id already exists in the repository")
    void save_course_id_exists() throws SQLException, InvalidTeacherException, IOException, InvalidStudentException, ClassNotFoundException, NullValueException {
        assertEquals(courseController.save(course5), course5);
    }

    @org.junit.jupiter.api.Test
    @Description("Should return null because the course has been saved")
    void save_course_not_exists() throws SQLException, InvalidTeacherException, IOException, InvalidStudentException, ClassNotFoundException, NullValueException {
        assertNull(courseController.save(course6));
    }

    @org.junit.jupiter.api.Test
    @Description("Should sort the courses descending by the number of students enrolled")
    void sortCoursesByStudentsEnrolled() throws IOException, SQLException, ClassNotFoundException {
        List<Course> sortedCourses = courseController.sortCoursesByStudentsEnrolled();
        assertEquals(sortedCourses.get(0).getId(), 653);
        assertEquals(sortedCourses.get(1).getId(), 1141);
        assertEquals(sortedCourses.get(2).getId(), 807);
    }

    @org.junit.jupiter.api.Test
    @Description("Should return the course with id 807 because it's the only one that has 8 credits")
    void filterCoursesWithSpecifiedCredits() throws SQLException, IOException, ClassNotFoundException {
        List<Course> filteredCourses = courseController.filterCoursesWithSpecifiedCredits(8);
        assertEquals(filteredCourses.size(), 1);
        assertEquals(filteredCourses.get(0).getId(), 807);
    }

    @org.junit.jupiter.api.Test
    @Description("Should throw a NullValueException because the parameter course is null")
    void delete_course_null() throws IOException, SQLException, ClassNotFoundException, NullValueException {
        assertThrows(NullValueException.class, () -> courseController.delete(null));
    }

    @org.junit.jupiter.api.Test
    @Description("Should return null because there is no course in the repository with the specified id")
    void delete_course_not_exists() throws IOException, NullValueException, SQLException, ClassNotFoundException {
        assertNull(courseController.delete(1217L));
    }

    @org.junit.jupiter.api.Test
    @Description("Should return the course because it has been removed from the repoList")
    void delete_course_exists() throws IOException, InvalidTeacherException, InvalidStudentException, NullValueException, SQLException, ClassNotFoundException {
        Course course = courseController.findOne(1141L);
        assertEquals(courseController.delete(1141L), course);
    }

    @org.junit.jupiter.api.Test
    @Description("Should return null because the course has been updated")
    void update_course_found() throws SQLException, IOException, ClassNotFoundException, NullValueException {
        assertNull(courseController.update(newCourse2));
        assertEquals(courseController.findOne(newCourse2.getId()), newCourse2);
    }

    @org.junit.jupiter.api.Test
    @Description("Should return the parameter object because there is no course with the same id in the repository")
    void update_course_not_found() throws NullValueException, IOException, SQLException, ClassNotFoundException {
        assertEquals(courseController.update(course7), course7);
    }

    @org.junit.jupiter.api.Test
    @Description("Should throw a NullValueException because the course is null")
    void update_course_null() {
        assertThrows(NullValueException.class, () -> courseController.update(null));
    }

}