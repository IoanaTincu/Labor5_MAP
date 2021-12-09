package com.company;

/*import controller.CourseController;
import controller.StudentController;
import repository.CourseFileRepository;
import repository.StudentFileRepository;
import repository.TeacherFileRepository;*/

import controller.CourseController;
import controller.StudentController;
import controller.TeacherController;
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

        StudentJdbcRepository studentJdbcRepo = new StudentJdbcRepository();
        CourseJdbcRepository courseJdbcRepo = new CourseJdbcRepository();
        TeacherJdbcRepository teacherJdbcRepo = new TeacherJdbcRepository();
        EnrolledJdbcRepository enrolledJdbcRepo = new EnrolledJdbcRepository(studentJdbcRepo, courseJdbcRepo, teacherJdbcRepo);

        StudentController studentController = new StudentController(studentJdbcRepo, courseJdbcRepo, enrolledJdbcRepo);
        CourseController courseController = new CourseController(courseJdbcRepo, studentJdbcRepo, teacherJdbcRepo, enrolledJdbcRepo);
        TeacherController teacherController = new TeacherController(teacherJdbcRepo, enrolledJdbcRepo);

        View view = new View(studentController, courseController, teacherController);
        view.runMenu();


        /*CourseJdbcRepository courseJdbcRepository = new CourseJdbcRepository();
        courseJdbcRepository.save(new Course(45, "Analiza matematica", 1200, 45, 11));
        //System.out.println(courseJdbcRepository.findAll());

        //System.out.println(courseJdbcRepository.findOne(45L));

        StudentJdbcRepository studentJdbcRepository = new StudentJdbcRepository();
        studentJdbcRepository.save(new Student(113, "Luca", "Tompea", 12));
        //System.out.println(studentJdbcRepository.findAll());


        TeacherJdbcRepository teacherJdbcRepository = new TeacherJdbcRepository();
        teacherJdbcRepository.save(new Teacher(334, "Mugur", "Acu"));
        teacherJdbcRepository.save(new Teacher(1200, "Luca", "Tompea"));
        //System.out.println(teacherJdbcRepository.findAll());


        //studentJdbcRepository.save(new Student(9, "Matei", "Stroia", 11));
        //courseController.save(new Course(34, "Programare distribuita", 334, 51, 7));
        //courseController.delete(45L);


        EnrolledJdbcRepository enrolledJdbcRepo = new EnrolledJdbcRepository(studentJdbcRepository, courseJdbcRepository, teacherJdbcRepository);
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

        //System.out.println(courseController.filterCoursesWithSpecifiedCredits(11));

        StudentController studentController = new StudentController(studentJdbcRepository, courseJdbcRepository, enrolledJdbcRepo);
        //System.out.println(studentController.sortStudentsByTotalCredits());
        //System.out.println(studentController.filterStudentsAttendingCourse(45L));
        //System.out.println(courseController.sortCoursesByStudentsEnrolled());

        //studentController.delete(9L);

        TeacherController teacherController = new TeacherController(teacherJdbcRepository, enrolledJdbcRepo);
        teacherController.delete(334L);
        //enrolledJdbcRepo.registerTeacherToCourse(1200L, 27L);
        //enrolledJdbcRepo.deleteTeacherFromCourse(1200L);
        //enrolledJdbcRepo.registerTeacherToCourse(1200L, 27L);
        //enrolledJdbcRepo.registerTeacherToCourse(1200L, 45L);
        //enrolledJdbcRepo.registerTeacherToCourse(334L, 34L);*/
    }
}