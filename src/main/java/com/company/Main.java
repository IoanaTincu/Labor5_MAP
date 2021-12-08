package com.company;

/*import controller.CourseController;
import controller.StudentController;
import repository.CourseFileRepository;
import repository.StudentFileRepository;
import repository.TeacherFileRepository;*/

import controller.CourseController;
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
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws Exception {
        // write your code here

        /*StudentFileRepository studentFileRepo = new StudentFileRepository("students.json");
        CourseFileRepository courseFileRepo = new CourseFileRepository("courses.json");
        TeacherFileRepository teacherFileRepo = new TeacherFileRepository("teachers.json");

        studentFileRepo.readDataFromFile();
        courseFileRepo.readDataFromFile();
        teacherFileRepo.readDataFromFile();

        StudentController studentController = new StudentController(studentFileRepo, courseFileRepo);
        CourseController courseController = new CourseController(courseFileRepo, studentFileRepo, teacherFileRepo);

        View view = new View(studentController, courseController);
        view.runMenu();*/


        CourseJdbcRepository courseJdbcRepository = new CourseJdbcRepository();
        courseJdbcRepository.save(new Course(45, "Analiza matematica", 1200, 45, 11));
        //System.out.println(courseJdbcRepository.findAll());

        //System.out.println(courseJdbcRepository.findOne(45L));

        StudentJdbcRepository studentJdbcRepository = new StudentJdbcRepository();
        studentJdbcRepository.save(new Student(113, "Luca", "Tompea", 12));
        //System.out.println(studentJdbcRepository.findAll());


        TeacherJdbcRepository teacherJdbcRepository = new TeacherJdbcRepository();
        teacherJdbcRepository.save(new Teacher(334, "Mugur", "Acu"));
        //System.out.println(teacherJdbcRepository.findAll());


        //studentJdbcRepository.save(new Student(9, "Matei", "Stroia", 11));
        //courseController.save(new Course(34, "Programare distribuita", 334, 51, 7));
        //courseController.delete(45L);


        EnrolledJdbcRepository enrolledJdbcRepo = new EnrolledJdbcRepository(studentJdbcRepository, courseJdbcRepository);
        CourseController courseController = new CourseController(courseJdbcRepository, studentJdbcRepository, teacherJdbcRepository, enrolledJdbcRepo);
        courseController.save(new Course(27, "Analiza matematica", 334, 45, 11));
        //enrolledJdbcRepo.registerStudentToCourse(113L, 45L);
        //System.out.println(enrolledJdbcRepo.checkExistenceOfEnrollment(3L, 36L));
        //System.out.println(enrolledJdbcRepo.getStudentsEnrolledInCourse(new Course(45, "Analiza matematica", 1200, 45, 11)));

        //System.out.println(courseJdbcRepository.getCreditsOfCourse(45L));
        //enrolledJdbcRepo.registerStudentToCourse(9L, 45L);
        //System.out.println(courseJdbcRepository.getCreditsOfCourse(34L));
        //enrolledJdbcRepo.registerStudentToCourse(9L, 34L);
        //enrolledJdbcRepo.deleteEnrollment(9L, 45L);
        //studentJdbcRepository.delete(9L);
        //studentJdbcRepository.save(new Student(9, "Matei", "Stroia", 0));

        System.out.println(courseController.filterCoursesWithSpecifiedCredits(11));
    }
}