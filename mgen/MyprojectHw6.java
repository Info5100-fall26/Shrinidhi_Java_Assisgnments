package edu.neu.mgen;
import java.util.Scanner;

public class MyprojectHw6 {

    public static void main(String[] args) {
        int x = 10;
        int y = 25;

        System.out.println("Maximum: " + Math.max(x, y));
        System.out.println("Minimum: " + Math.min(x, y));
        System.out.println("Square root of x: " + Math.sqrt(x));
        System.out.println("Square root of y: " + Math.sqrt(y));

        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("enter any word: ");
            long startTime = System.nanoTime();
            String word = input.nextLine();
            long endTime = System.nanoTime();

            if (word.isEmpty()) {
                System.out.println("You entered an empty line. Please reenter");
                continue;
            }

            int length = word.length();
            double reactionTime = (endTime - startTime) / 1_000_000.0;

            String classification;
            if (length < 5) {
                classification = "Short";
            } else if (length < 10) {
                classification = "Medium";
            } else {
                classification = "Long";
            }

            System.out.println("Your word is " + word);
            System.out.println("It is a : " + classification + " word");
            System.out.println("The Length of the word is: " + length);
            System.out.println("Your reaction time is: " + reactionTime + "seconds");
            break;
        }

        input.close();
    }
}
