package com.company;

import controller.CourseController;
import controller.StudentController;
import controller.TeacherController;
import exceptions.*;
import model.Course;
import model.Student;
import model.Teacher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class View {

    private StudentController studentController;
    private CourseController courseController;
    private TeacherController teacherController;

    public View(StudentController studentController, CourseController courseController, TeacherController teacherController) {
        this.studentController = studentController;
        this.courseController = courseController;
        this.teacherController = teacherController;
    }

    public void sortStudentsByTotalCredits() throws SQLException, IOException, ClassNotFoundException {
        List<Student> students = studentController.sortStudentsByTotalCredits();
        for (Student student : students)
            System.out.println(student);
    }

    public void filterStudentsAttendingCourse() throws SQLException, IOException, ClassNotFoundException, NullValueException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter course id for the filter: ");
        Long courseId = scanner.nextLong();

        List<Student> students = studentController.filterStudentsAttendingCourse(courseId);
        for (Student student : students)
            System.out.println(student);
    }

    public void findOne() throws SQLException, IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student id: ");
        Long studentId = scanner.nextLong();

        try {
            studentController.findOne(studentId);
        } catch (NullValueException e) {
            System.out.println(e);
        }
    }

    public void findAll() throws SQLException, IOException, ClassNotFoundException {
        List<Student> students = studentController.findAll();
        for (Student student : students)
            System.out.println(student);
    }

    public void save() throws SQLException, ClassNotFoundException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student id: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.println("Enter total credits: ");
        int totalCredits = scanner.nextInt();

        Student newStudent = new Student(id, firstName, lastName, totalCredits);
        try {
            studentController.save(newStudent);
        } catch (NullValueException | InvalidCourseException e) {
            System.out.println(e);
        }
    }

    public void delete() throws SQLException, IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student id: ");
        Long id = scanner.nextLong();

        try {
            studentController.delete(id);
        } catch (NullValueException e) {
            System.out.println(e);
        }
    }

    public void update() throws SQLException, IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student id: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.println("Enter total credits: ");
        int totalCredits = scanner.nextInt();

        Student newStudent = new Student(id, firstName, lastName, totalCredits);
        try {
            studentController.update(newStudent);
        } catch (NullValueException e) {
            System.out.println(e);
        }
    }

    public void size() throws SQLException, IOException, ClassNotFoundException {
        System.out.println(studentController.size());
    }

    public void printStudentMenu() {
        System.out.println("1. Sort students by number of total credits");
        System.out.println("2. Filter students attending course");
        System.out.println("3. Find student by id");
        System.out.println("4. Find all students");
        System.out.println("5. Save student");
        System.out.println("6. Delete student");
        System.out.println("7. Update student");
        System.out.println("8. Print the size of the student list");
        System.out.println("9. Exit");
    }

    public void teacherFindOne() throws SQLException, IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter teacher id: ");
        Long teacherId = scanner.nextLong();

        try {
            teacherController.findOne(teacherId);
        } catch (NullValueException e) {
            System.out.println(e);
        }
    }

    public void teacherFindAll() throws SQLException, IOException, ClassNotFoundException {
        List<Teacher> teachers = teacherController.findAll();
        for (Teacher teacher : teachers)
            System.out.println(teacher);
    }

    public void teacherSave() throws SQLException, IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter teacher id: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name: ");
        String lastName = scanner.nextLine();

        Teacher newTeacher = new Teacher(id, firstName, lastName);
        try {
            teacherController.save(newTeacher);
        } catch (NullValueException e) {
            System.out.println(e);
        }
    }

    public void teacherDelete() throws SQLException, IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter teacher id: ");
        Long id = scanner.nextLong();

        try {
            teacherController.delete(id);
        } catch (NullValueException e) {
            System.out.println(e);
        }
    }

    public void teacherUpdate() throws SQLException, IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter teacher id: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name: ");
        String lastName = scanner.nextLine();

        Teacher newTeacher = new Teacher(id, firstName, lastName);
        try {
            teacherController.update(newTeacher);
        } catch (NullValueException e) {
            System.out.println(e);
        }
    }

    public void teacherSize() throws SQLException, IOException, ClassNotFoundException {
        System.out.println(teacherController.size());
    }

    public void printTeacherMenu() {
        System.out.println("1. Find teacher by id");
        System.out.println("2. Find all teachers");
        System.out.println("3. Save teacher");
        System.out.println("4. Delete teacher");
        System.out.println("5. Update teacher");
        System.out.println("6. Print the size of the teacher list");
        System.out.println("7. Exit");
    }

    public void runTeacherMenu() throws InvalidMenuOptionException, SQLException, IOException, ClassNotFoundException {
        boolean done = false;
        while (!done)
            try {
                printTeacherMenu();

                Scanner scanner = new Scanner(System.in);
                System.out.println("Choose option: ");
                int option = scanner.nextInt();

                if (option < 1 || option > 7)
                    throw new InvalidMenuOptionException("Invalid value");
                if (option == 7)
                    done = true;
                if (option == 1)
                    teacherFindOne();
                if (option == 2)
                    teacherFindAll();
                if (option == 3)
                    teacherSave();
                if (option == 4)
                    teacherDelete();
                if (option == 5)
                    teacherUpdate();
                if (option == 6)
                    teacherSize();
            } catch (InvalidMenuOptionException e) {
                System.out.println(e);
            }
    }

    public void runStudentMenu() throws SQLException, IOException, ClassNotFoundException, NullValueException {
        boolean done = false;
        while (!done)
            try {
                printStudentMenu();

                Scanner scanner = new Scanner(System.in);
                System.out.println("Choose option: ");
                int option = scanner.nextInt();

                if (option < 1 || option > 9)
                    throw new InvalidMenuOptionException("Invalid value");
                if (option == 9)
                    done = true;
                if (option == 1)
                    sortStudentsByTotalCredits();
                if (option == 2)
                    filterStudentsAttendingCourse();
                if (option == 3)
                    findOne();
                if (option == 4)
                    findAll();
                if (option == 5)
                    save();
                if (option == 6)
                    delete();
                if (option == 7)
                    update();
                if (option == 8)
                    size();
            } catch (InvalidMenuOptionException e) {
                System.out.println(e);
            }
    }

    public void sortCoursesByStudentsEnrolled() throws SQLException, IOException, ClassNotFoundException {
        List<Course> courses = courseController.sortCoursesByStudentsEnrolled();
        for (Course course : courses)
            System.out.println(course);
    }

    public void filterCoursesWithSpecifiedCredits() throws SQLException, IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of credits for the filter: ");
        int numberCredits = scanner.nextInt();

        List<Course> courses = courseController.filterCoursesWithSpecifiedCredits(numberCredits);
        for (Course course : courses)
            System.out.println(course);
    }

    public void courseFindOne() throws SQLException, IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter course id: ");
        Long courseId = scanner.nextLong();

        try {
            studentController.findOne(courseId);
        } catch (NullValueException e) {
            System.out.println(e);
        }
    }

    public void courseFindAll() throws SQLException, IOException, ClassNotFoundException {
        List<Course> courses = courseController.findAll();
        for (Course course : courses)
            System.out.println(course);
    }

    public void courseSave() throws SQLException, IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter course id: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Enter name: ");
        String name = scanner.nextLine();
        System.out.println("Enter teacher id: ");
        Long teacherId = scanner.nextLong();
        System.out.println("Enter maximum enrollment: ");
        int maxEnrollment = scanner.nextInt();
        System.out.println("Enter the size of the list of enrolled students: ");
        int size = scanner.nextInt();
        List<Long> studentList = new ArrayList<>();

        if (size != 0) {
            System.out.println("Enter students: ");

            Long studentId;

            for (int i = 0; i < size; i++) {
                studentId = scanner.nextLong();
                studentList.add(studentId);
            }
        }

        System.out.println("Enter number of credits: ");
        int credits = scanner.nextInt();

        Course newCourse = new Course(id, name, teacherId, maxEnrollment, credits);
        try {
            courseController.save(newCourse);
        } catch (NullValueException | InvalidStudentException | InvalidTeacherException e) {
            System.out.println(e);
        }
    }

    public void courseDelete() throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter course id: ");
        Long id = scanner.nextLong();

        try {
            courseController.delete(id);
        } catch (NullValueException | IOException e) {
            System.out.println(e);
        }
    }

    public void courseUpdate() throws SQLException, IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student id: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Enter name: ");
        String name = scanner.nextLine();
        System.out.println("Enter teacher id: ");
        Long teacherId = scanner.nextLong();
        System.out.println("Enter maximum enrollment: ");
        int maxEnrollment = scanner.nextInt();
        System.out.println("Enter the size of the list of enrolled students: ");
        int size = scanner.nextInt();
        List<Long> studentList = new ArrayList<>();

        if (size != 0) {
            System.out.println("Enter students: ");

            Long studentId;

            for (int i = 0; i < size; i++) {
                studentId = scanner.nextLong();
                studentList.add(studentId);
            }
        }

        System.out.println("Enter number of credits: ");
        int credits = scanner.nextInt();

        Course newCourse = new Course(id, name, teacherId, maxEnrollment, credits);
        try {
            courseController.update(newCourse);
        } catch (NullValueException e) {
            System.out.println(e);
        }
    }

    public void courseSize() throws SQLException, IOException, ClassNotFoundException {
        System.out.println(courseController.size());
    }

    public void courseRegisterStudentToCourse() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student id: ");
        Long studentId = scanner.nextLong();
        System.out.println("Enter course id: ");
        Long courseId = scanner.nextLong();

        try {
            courseController.registerStudentToCourse(studentId, courseId);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void courseGetStudentsEnrolledInCourse() throws SQLException, IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter course id: ");
        Long courseId = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Enter name: ");
        String name = scanner.nextLine();
        System.out.println("Enter teacher id: ");
        Long teacherId = scanner.nextLong();
        System.out.println("Enter maximum enrollment: ");
        int maxEnrollment = scanner.nextInt();
        System.out.println("Enter number of credits: ");
        int credits = scanner.nextInt();

        System.out.println(courseController.getStudentsEnrolledInCourse(new Course(courseId, name, teacherId, maxEnrollment, credits)));
    }

    public void courseRegisterTeacherToCourse() throws SQLException, InvalidTeacherException, IOException, ClassNotFoundException, NullValueException, InvalidCourseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter teacher id: ");
        Long teacherId = scanner.nextLong();
        System.out.println("Enter course id: ");
        Long courseId = scanner.nextLong();

        try {
            courseController.registerTeacherToCourse(teacherId, courseId);
        } catch (NullValueException | InvalidCourseException | InvalidTeacherException e) {
            System.out.println(e);
        }
    }

    public void printCourseMenu() {
        System.out.println("1. Sort courses by number of students enrolled");
        System.out.println("2. Filter courses with specified number of credits");
        System.out.println("3. Find course by id");
        System.out.println("4. Find all courses");
        System.out.println("5. Save course");
        System.out.println("6. Delete course");
        System.out.println("7. Update course");
        System.out.println("8. Print the size of the course list");
        System.out.println("9. Register student to course");
        System.out.println("10. Get students enrolled in course");
        System.out.println("11. Register teacher to course");
        System.out.println("12. Exit");
    }

    public void runCourseMenu() throws Exception {
        boolean done = false;
        while (!done)
            try {
                printCourseMenu();

                Scanner scanner = new Scanner(System.in);
                System.out.println("Choose option: ");
                int option = scanner.nextInt();

                if (option < 1 || option > 12)
                    throw new InvalidMenuOptionException("Invalid value");
                if (option == 12)
                    done = true;
                if (option == 1)
                    sortCoursesByStudentsEnrolled();
                if (option == 2)
                    filterCoursesWithSpecifiedCredits();
                if (option == 3)
                    courseFindOne();
                if (option == 4)
                    courseFindAll();
                if (option == 5)
                    courseSave();
                if (option == 6)
                    courseDelete();
                if (option == 7)
                    courseUpdate();
                if (option == 8)
                    courseSize();
                if (option == 9)
                    courseRegisterStudentToCourse();
                if (option == 10)
                    courseGetStudentsEnrolledInCourse();
                if (option == 11)
                    courseRegisterTeacherToCourse();
            } catch (InvalidMenuOptionException e) {
                System.out.println(e);
            }
    }


    void runMenu() throws Exception {
        System.out.println("Menu:");
        System.out.println('\t' + "1. Student Menu");
        System.out.println('\t' + "2. Course Menu");
        System.out.println('\t' + "3. Teacher Menu");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose option 1 or 2 or 3: ");
        int option = scanner.nextInt();

        if (option == 1)
            runStudentMenu();
        if (option == 2)
            runCourseMenu();
        else
            runTeacherMenu();
    }
}
