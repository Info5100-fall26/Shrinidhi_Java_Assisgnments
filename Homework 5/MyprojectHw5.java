package edu.neu.mgen;
import java.util.ArrayList;


public class MyprojectHw5 
{
    public static void main( String[] args )
    {
       String str = "Oakland";

       //Length of the string
        System.out.println( "Length of the string: " + str.length() ); 
        System.out.println("Character at index 2: " + str.charAt(2));

        //Extract substring land from the string
        String substring = str.substring(3);
        System.out.println("Substring: " + substring);

        //Uppercase conversion
        String upperCase = str.toUpperCase();
        System.out.println("uppercase: " + upperCase);

        //Array and its length
        int[] abc = {1, 2, 3, 4, 5};
        System.out.println("Array length: " +abc.length);
        System.out.println("Last member of the array: " + abc[abc.length -1]);

        //Array lis of cities
        ArrayList<String> cities = new ArrayList<>();
        cities.add("Austin");
        cities.add("Houston");
        cities.add("Oakland");
        cities.add("San Francisco");
        cities.add("Seattle");

        System.out.println("Cities: " +cities);

        //Remove paris from the list
        cities.remove("paries");
        System.out.println("Cities ater removing paris: " +cities);


    





    }
}
