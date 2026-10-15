package edu.neu.mgen;
import java.util.ArrayList;

class Student {
    int studentID;
    String firstName;
    String lastName;

    Student(int studentID, String firstName, String lastName) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

class EngClass {
    ArrayList<Student> students = new ArrayList<>();

    // Add a student
    void addStudent(Student student) {
        students.add(student);
        System.out.println("Student added: " + student.firstName + " " + student.lastName);
    }

    // Delete a student using student ID
    void deleteStudent(int studentID) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).studentID == studentID) {
                System.out.println("Student deleted: "
                        + students.get(i).firstName + " " + students.get(i).lastName);
                students.remove(i);
                return;
            }
        }
        System.out.println("Student ID not found.");
    }

    // Read (display) the list of students
    void readStudents() {
        if (students.isEmpty()) {
            System.out.println("No students in the class.");
            return;
        }

        System.out.println("\nStudent ID | First Name | Last Name");

        for (Student student : students) {
            System.out.println(student.studentID + " | "
                    + student.firstName + " | "
                    + student.lastName);
        }
    }
}
public class Homework9 {
    public static void main(String[] args) {
        EngClass eng = new EngClass();

        // 1. Add students
        eng.addStudent(new Student(101, "John", "Smith"));
        eng.addStudent(new Student(102, "Alice", "Brown"));
        eng.addStudent(new Student(103, "David", "Wilson"));

        // 2. Read the list of students
        System.out.println("\nInitial student list:");
        eng.readStudents();

        // 3. Delete a student
        System.out.println("\nDeleting student with ID 102:");
        eng.deleteStudent(102);

        // 4. Read the updated list of students
        System.out.println("\nUpdated student list:");
        eng.readStudents();
    }
    
}
