package edu.neu.mgen;

public class Chapyer8Methods {
        public static String[] reverseArray(String[] original) {

        String[] result = new String[original.length];

        for (int i = 0; i < original.length; i++) {

            // Get the elements in reverse order
            String word = original[original.length - 1 - i];

            String reversed = "";

            // Reverse the characters in the word
            for (int j = word.length() - 1; j >= 0; j--) {
                reversed = reversed + word.charAt(j);
            }

            // Capitalize the first letter and make the rest lowercase
            reversed = reversed.substring(0, 1).toUpperCase()
                    + reversed.substring(1).toLowerCase();

            result[i] = reversed;
        }

        return result;
    }

    // Method to print the array
    public static void printArray(String[] array) {

        for (int i = 0; i < array.length; i++) {
            System.out.println("\"" + array[i] + "\"");
        }

        System.out.println("End of the array");
    }

    public static void main(String[] args) {

        // First array
        String[] array1 = {"Anne", "John", "Alex", "Jessica"};

        System.out.println("Original array:");
        printArray(array1);

        System.out.println();
        System.out.println("=========================");
        System.out.println();

        System.out.println("Resultant array:");
        printArray(reverseArray(array1));

        System.out.println();
        System.out.println("=========================");
        System.out.println();

        // Second array
        String[] array2 = {
            "Sun", "Mercury", "Venis",
            "Earth", "Mars", "Jupiter"
        };

        System.out.println("Original array:");
        printArray(array2);

        System.out.println();
        System.out.println("=========================");
        System.out.println();

        System.out.println("Resultant array:");
        printArray(reverseArray(array2));
    }

    
    
}
