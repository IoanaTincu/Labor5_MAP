package com.company;

import controller.CourseController;
import controller.StudentController;
import controller.TeacherController;
import model.Course;
import model.Student;
import model.Teacher;
import repository.*;


public class Main {

    public static void main(String[] args) throws Exception {
        // write your code here

        ICrudRepository<Student> studentJdbcRepo = new StudentJdbcRepository();
        ICrudRepository<Course> courseJdbcRepo = new CourseJdbcRepository();
        ICrudRepository<Teacher> teacherJdbcRepo = new TeacherJdbcRepository();
        IJoinTablesRepo enrolledJdbcRepo = new EnrolledJdbcRepository(studentJdbcRepo, courseJdbcRepo, teacherJdbcRepo);

        StudentController studentController = new StudentController(studentJdbcRepo, courseJdbcRepo, enrolledJdbcRepo);
        CourseController courseController = new CourseController(courseJdbcRepo, teacherJdbcRepo, enrolledJdbcRepo);
        TeacherController teacherController = new TeacherController(teacherJdbcRepo, enrolledJdbcRepo);

        View view = new View(studentController, courseController, teacherController);
        view.runMenu();
    }
}