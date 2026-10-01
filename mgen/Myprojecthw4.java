package edu.neu.mgen;
/*
* Hello world! *
* this is my first java project 
*/
public class Myprojecthw4
{
public static void main( String[] args )
{
//Declare and initialise 2 variables of int long double char abd boolean type
    int number1 =30;
    int number2 = 101;

    long id1 = 12345L;
    long id2 = 123452L;

    Double Salary1 = 50000.50;
    Double Salary2 = 50000.50;

    boolean isStudent = true;
    boolean isWorking = false;

    char firstname = 'S';
    char lastname = 'H';

    //convert int to long
    long ConvertedLong1 = (long) number1;
    long ConvertedLong2 = (long) number2;

    System.out.println("Int Value:" + number1);
    System.out.println("Converted Long Value:" + ConvertedLong1);
    System.out.println("Int Value:" + number2);
    System.out.println("Converted Long Value:" + ConvertedLong2);
    
    //convert long to int
    int ConvertedInt1 = (int) id1;
    int ConvertedInt2 = (int) id2;

    System.out.println("Long Value:" + id1);
    System.out.println("Converted Int Value:" + ConvertedInt1);
    System.out.println("Long Value:" + id2);
    System.out.println("Converted Int Value:" + ConvertedInt2);


//enter values for variables from the terminal
    java.util.Scanner scanner = new java.util.Scanner(System.in);

    System.out.println("Enter Number: ");
    int Number1 = scanner.nextInt();

    System.out.println("Enter Number2");
    int Number2 = scanner.nextInt();


    System.out.println("Number1:" + Number1);
    System.out.println("Number2:" + Number2);

// Arithmatic operations
    int add = Number1 + Number2;
    int subtract = Number1 - Number2;
    int multiply = Number1 * Number2;
    double divide = (double) Number1 / Number2;
    int modulus = Number1 % Number2;

    System.out.println("Addition:" + add);
    System.out.println("Subtraction:" + subtract);
    System.out.println("Multiplication:" + multiply);
    System.out.println("Division:" + divide);
    System.out.println("Modulus:" + modulus);

//Logical operations
    boolean isGreater = Number1 > Number2;
    boolean isLess = Number1 < Number2;
    boolean isEqual = Number1 == Number2;
    boolean isNotEqual = Number1 != Number2;

    System.out.println("Number1 is greater than Number2:" + isGreater);
    System.out.println("Number1 is less than Number2:" + isLess);
    System.out.println("Number1 is equal to Number2:" + isEqual);
    System.out.println("Number1 is not equal to Number2:" + isNotEqual);


}
}

