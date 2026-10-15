package edu.neu.mgen;


import java.util.Scanner;

// Abstract parent class
abstract class Vehicle {
    protected String brand;
    protected String model;
    protected int year;
    protected double speed;

    // Constructor
    public Vehicle(String brand, String model, int year, double speed) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.speed = speed;
    }

    // Common method
    public void displayDetails() {
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
        System.out.println("Speed: " + speed + " km/h");
    }

    // Abstract methods implemented by subclasses
    public abstract void start();
    public abstract void stop();
    public abstract void displayType();
}

// Car class
class Car extends Vehicle {
    private int doors;
    private String fuelType;

    public Car(String brand, String model, int year,
               double speed, int doors, String fuelType) {
        super(brand, model, year, speed);
        this.doors = doors;
        this.fuelType = fuelType;
    }

    @Override
    public void start() {
        System.out.println("Car engine started.");
    }

    @Override
    public void stop() {
        System.out.println("Car stopped safely.");
    }

    @Override
    public void displayType() {
        System.out.println("Vehicle Type: Car");
        displayDetails();
        System.out.println("Doors: " + doors);
        System.out.println("Fuel Type: " + fuelType);
    }
}

// Motorbike class
class Motorbike extends Vehicle {
    private int engineCapacity;
    private boolean hasHelmetStorage;

    public Motorbike(String brand, String model, int year,
                     double speed, int engineCapacity,
                     boolean hasHelmetStorage) {
        super(brand, model, year, speed);
        this.engineCapacity = engineCapacity;
        this.hasHelmetStorage = hasHelmetStorage;
    }

    @Override
    public void start() {
        System.out.println("Motorbike engine started.");
    }

    @Override
    public void stop() {
        System.out.println("Motorbike stopped.");
    }

    @Override
    public void displayType() {
        System.out.println("Vehicle Type: Motorbike");
        displayDetails();
        System.out.println("Engine Capacity: " + engineCapacity + " cc");
        System.out.println("Helmet Storage: "
                + (hasHelmetStorage ? "Yes" : "No"));
    }
}

// Aircraft class
class Aircraft extends Vehicle {
    private int passengerCapacity;
    private double altitude;

    public Aircraft(String brand, String model, int year,
                    double speed, int passengerCapacity,
                    double altitude) {
        super(brand, model, year, speed);
        this.passengerCapacity = passengerCapacity;
        this.altitude = altitude;
    }

    @Override
    public void start() {
        System.out.println("Aircraft engines started. Ready for takeoff.");
    }

    @Override
    public void stop() {
        System.out.println("Aircraft landed and engines stopped.");
    }

    @Override
    public void displayType() {
        System.out.println("Vehicle Type: Aircraft");
        displayDetails();
        System.out.println("Passenger Capacity: " + passengerCapacity);
        System.out.println("Altitude: " + altitude + " meters");
    }
}

// Ship class
class Ship extends Vehicle {
    private int passengerCapacity;
    private String shipType;

    public Ship(String brand, String model, int year,
                double speed, int passengerCapacity, String shipType) {
        super(brand, model, year, speed);
        this.passengerCapacity = passengerCapacity;
        this.shipType = shipType;
    }

    @Override
    public void start() {
        System.out.println("Ship engine started. Ready to sail.");
    }

    @Override
    public void stop() {
        System.out.println("Ship stopped at the harbor.");
    }

    @Override
    public void displayType() {
        System.out.println("Vehicle Type: Ship");
        displayDetails();
        System.out.println("Passenger Capacity: " + passengerCapacity);
        System.out.println("Ship Type: " + shipType);
    }
}

// Main class
public class Homework10 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Create objects of all four derived classes
        Vehicle[] vehicles = {
            new Car("Toyota", "Camry", 2024, 120, 4, "Petrol"),
            new Motorbike("Honda", "CB350", 2023, 90, 350, true),
            new Aircraft("Boeing", "737", 2022, 850, 180, 10000),
            new Ship("Carnival", "Cruiser", 2021, 45, 2000, "Cruise Ship")
        };

        int choice;

        do {
            System.out.println("\n=========================");
            System.out.println("1. Display all vehicles");
            System.out.println("2. Start a vehicle");
            System.out.println("3. Stop a vehicle");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Enter a valid number (1-4): ");
                sc.next();
            }
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    for (Vehicle v : vehicles) {
                        System.out.println("\n----------------------");
                        v.displayType();
                    }
                    break;

                case 2:
                    System.out.println("Select vehicle:");
                    System.out.println("1. Car");
                    System.out.println("2. Motorbike");
                    System.out.println("3. Aircraft");
                    System.out.println("4. Ship");
                    System.out.print("Enter vehicle number: ");

                    int startChoice = readVehicleChoice(sc);
                    if (startChoice != -1) {
                        vehicles[startChoice].start();
                    }
                    break;

                case 3:
                    System.out.println("Select vehicle:");
                    System.out.println("1. Car");
                    System.out.println("2. Motorbike");
                    System.out.println("3. Aircraft");
                    System.out.println("4. Ship");
                    System.out.print("Enter vehicle number: ");

                    int stopChoice = readVehicleChoice(sc);
                    if (stopChoice != -1) {
                        vehicles[stopChoice].stop();
                    }
                    break;

                case 4:
                    System.out.println("Exiting Vehicle Management System.");
                    break;

                default:
                    System.out.println("Invalid choice. Please select 1-4.");
            }
        } while (choice != 4);

        sc.close();
    }

    // Validates vehicle selection
    private static int readVehicleChoice(Scanner sc) {
        if (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            sc.next();
            return -1;
        }

        int choice = sc.nextInt();

        if (choice < 1 || choice > 4) {
            System.out.println("Invalid vehicle selection.");
            return -1;
        }

        return choice - 1;
    }
}

