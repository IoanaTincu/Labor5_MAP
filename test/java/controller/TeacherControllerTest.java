package controller;

import com.sun.org.glassfish.gmbal.Description;
import exceptions.InvalidCourseException;
import exceptions.NullValueException;
import model.Course;
import model.Student;
import model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repository.CourseJdbcRepository;
import repository.EnrolledJdbcRepository;
import repository.StudentJdbcRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*import static org.junit.jupiter.api.Assertions.*;

class StudentControllerTest {

    StudentController studentController;
    CourseJdbcRepository courseJdbcRepo = Mockito.mock(CourseJdbcRepository.class);
    StudentJdbcRepository studentJdbcRepo = Mockito.mock(StudentJdbcRepository.class);
    EnrolledJdbcRepository enrolledJdbcRepo = Mockito.mock(EnrolledJdbcRepository.class);

    Course course1 = new Course(1141, "Algebra liniara", 1200, 45, 18);
    Course course2 = new Course(653, "Analiza matematica", 1200, 45, 11);
    Course course3 = new Course(807, "Programare distribuita", 120, 88, 8);
    Teacher teacher = new Teacher(1200, "Mugur", "Acu");
    Student student1 = new Student(1216, "Matei", "Stroia", 11);
    Student student2 = new Student(113, "Luca", "Tompea", 12);
    Student student3 = new Student(113, "Moise", "Gabriel", 53);
    Student student4 = new Student(930, "Moise", "Gabriel", 53);

    Connection connection;
    List<Student> students = new ArrayList<>(Arrays.asList(student1, student2));

    @BeforeEach
    void setUp() throws SQLException, IOException, ClassNotFoundException, NullValueException {
        studentController = new StudentController(studentJdbcRepo, courseJdbcRepo, enrolledJdbcRepo);
        Mockito.when(studentJdbcRepo.openConnection()).thenReturn(connection);
        Mockito.when(studentJdbcRepo.readDataFromDatabase(connection)).thenReturn(students);
        Mockito.when(courseJdbcRepo.findOne(1141L)).thenReturn(course1);
        Mockito.when(enrolledJdbcRepo.getStudentsEnrolledInCourse(course1)).thenReturn(new ArrayList<>(Arrays.asList(1216L, 113L)));
        Mockito.when(studentJdbcRepo.save(student3)).thenReturn(student3);
        Mockito.when(studentJdbcRepo.save(student4)).thenReturn(null);
        Mockito.when(studentJdbcRepo.delete(null)).thenThrow(NullValueException.class);
        Mockito.when(studentJdbcRepo.delete(1217L)).thenReturn(null);
        Mockito.when(studentJdbcRepo.findOne(1216L)).thenReturn(student1);
        Mockito.when(studentJdbcRepo.delete(1216L)).thenReturn(student1);
    }

    @Test
    void sortStudentsByTotalCredits() throws SQLException, IOException, ClassNotFoundException {
        List<Student> sortedStudents = studentController.sortStudentsByTotalCredits();
        assertEquals(sortedStudents.get(0).getId(), 113);
        assertEquals(sortedStudents.get(1).getId(), 1216);
    }

    @Test
    @Description("Should return the students with id 1216 and 113 because they both attend the course with id 1141")
    void filterStudentsAttendingCourse() throws SQLException, IOException, ClassNotFoundException, NullValueException {
        List<Student> filteredStudents = studentController.filterStudentsAttendingCourse(1141L);
        assertEquals(filteredStudents.size(), 2);
    }

    @Test
    @Description("Should return the student because the id already exists in the repository")
    void save_student_id_exists() throws SQLException, IOException, ClassNotFoundException, NullValueException, InvalidCourseException {
        assertEquals(studentController.save(student3), student3);
    }
    @Test
    @Description("Should return null because the course has been saved")
    void save_student_not_exists() throws SQLException, IOException, ClassNotFoundException, NullValueException, InvalidCourseException {
        assertNull(studentController.save(student4));
    }

    @Test
    @Description("Should throw a NullValueException because the parameter course is null")
    void delete_student_null() throws IOException {
        assertThrows(NullValueException.class, () -> studentController.delete(null));
    }

    @Test
    @Description("Should return null because there is no student in the repository with the specified id")
    void delete_student_not_exists() throws IOException, NullValueException, SQLException, ClassNotFoundException {
        assertEquals(studentController.delete(1217L), null);
    }

    @Test
    @Description("Should return the student because it has been removed from the repoList")
    void delete_student_exists() throws SQLException, IOException, ClassNotFoundException, NullValueException {
        Student student = studentController.findOne(1216L);
        assertEquals(studentController.delete(1216L), student);
    }
}*/