package edu.neu.mgen;
import java.util.ArrayList;
import java.util.Arrays;


public class JavaVab1 {
    public static void main(String[] args) {
        int[] X = {1, 2, 3, 4, 5};
        int[] Y = {10, 20, 30, 40, 50};
        int[] Z = new int[5];

        for (int i = 0; i < 5; i++) {
            Z[i] = Math.max(X[i], Y[i]);
        }

        System.out.println("Lab 1 - Part 1: Arrays");
        System.out.println();

        System.out.println("Array x = " + Arrays.toString(X));
        System.out.println();

        System.out.println("Array y = " + Arrays.toString(Y));
        System.out.println();

        System.out.println("Array z = x + y = " + Arrays.toString(Z));

        ArrayList<String> names = new ArrayList<>();

        names.add("Alice");
        names.add("David");
        names.add("Emily");
        names.add("Michael");
        names.add("Sarah");

        ArrayList<String> switchedNames = new ArrayList<>();

        for (String name : names) {

            String switchedName =
                    name.substring(name.length() - 1).toUpperCase()
                    + name.substring(1, name.length() - 1).toLowerCase()
                    + name.substring(0, 1).toLowerCase();

            switchedNames.add(switchedName);
        }

        System.out.println();
        System.out.println("--------------------------------");
        System.out.println();

        System.out.println("Lab 1 - Part 2: ArrayList");
        System.out.println();

        System.out.println("Names = " + names);
        System.out.println();

        System.out.println("Names (switched) = " + switchedNames);
    }
}
    

