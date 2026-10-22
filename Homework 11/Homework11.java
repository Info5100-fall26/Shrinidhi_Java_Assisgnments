package edu.neu.mgen;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Homework11 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter your age: ");
            int age = sc.nextInt();

            System.out.print("Enter your marks: ");
            double marks = sc.nextDouble();

            System.out.println("Your age is: " + age);
            System.out.println("Your marks are: " + marks);

        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter numbers only.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
    
}
