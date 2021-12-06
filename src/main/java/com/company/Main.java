package com.company;

/*import controller.CourseController;
import controller.StudentController;
import repository.CourseFileRepository;
import repository.StudentFileRepository;
import repository.TeacherFileRepository;*/

import exceptions.NullValueException;
import model.Course;
import model.Student;
import model.Teacher;
import repository.CourseJdbcRepository;
import repository.StudentJdbcRepository;
import repository.TeacherJdbcRepository;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, NullValueException {
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
        System.out.println(teacherJdbcRepository.findAll());
    }
}