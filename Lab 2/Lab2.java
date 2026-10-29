package edu.neu.mgen;


class Animals {
    protected String speciesName;
    protected double typicalSize;    
    protected double typicalWeight;  
    protected String diet;
    protected boolean predator;

    public Animals(String speciesName, double typicalSize,
                   double typicalWeight, String diet,
                   boolean predator) {
        this.speciesName = speciesName;
        this.typicalSize = typicalSize;
        this.typicalWeight = typicalWeight;
        this.diet = diet;
        this.predator = predator;
    }

    public void displayDetails() {
        System.out.println("Species: " + speciesName);
        System.out.println("Typical Size: " + typicalSize + " m");
        System.out.println("Typical Weight: " + typicalWeight + " kg");
        System.out.println("Diet: " + diet);
        System.out.println("Predator: " + (predator ? "Yes" : "No"));
    }
}

class Bird extends Animals {
    private double wingspan;
    private boolean canFly;
    private String beakType;

    public Bird(String speciesName, double typicalSize,
                double typicalWeight, String diet,
                boolean predator, double wingspan,
                boolean canFly, String beakType) {
        super(speciesName, typicalSize, typicalWeight,
              diet, predator);
        this.wingspan = wingspan;
        this.canFly = canFly;
        this.beakType = beakType;
    }

    @Override
    public void displayDetails() {
        System.out.println("\n--- Bird Details ---");
        super.displayDetails();
        System.out.println("Wingspan: " + wingspan + " m");
        System.out.println("Can Fly: " + (canFly ? "Yes" : "No"));
        System.out.println("Beak Type: " + beakType);
    }
}

class LandAnimal extends Animals {
    private int numberOfLegs;
    private String habitat;
    private String movementType;

    public LandAnimal(String speciesName, double typicalSize,
                      double typicalWeight, String diet,
                      boolean predator, int numberOfLegs,
                      String habitat, String movementType) {
        super(speciesName, typicalSize, typicalWeight,
              diet, predator);
        this.numberOfLegs = numberOfLegs;
        this.habitat = habitat;
        this.movementType = movementType;
    }

    @Override
    public void displayDetails() {
        System.out.println("\n--- Land Animal Details ---");
        super.displayDetails();
        System.out.println("Number of Legs: " + numberOfLegs);
        System.out.println("Habitat: " + habitat);
        System.out.println("Movement: " + movementType);
    }
}

class Fish extends Animals {
    private int numberOfFins;
    private String waterType;
    private String scaleType;

    public Fish(String speciesName, double typicalSize,
                double typicalWeight, String diet,
                boolean predator, int numberOfFins,
                String waterType, String scaleType) {
        super(speciesName, typicalSize, typicalWeight,
              diet, predator);
        this.numberOfFins = numberOfFins;
        this.waterType = waterType;
        this.scaleType = scaleType;
    }

    @Override
    public void displayDetails() {
        System.out.println("\n--- Fish Details ---");
        super.displayDetails();
        System.out.println("Number of Fins: " + numberOfFins);
        System.out.println("Water Type: " + waterType);
        System.out.println("Scale Type: " + scaleType);
    }
}

public class Lab2 {
    public static void main(String[] args) {

        Bird eagle = new Bird(
            "Eagle", 0.9, 6.0, "Carnivore",
            true, 2.1, true, "Hooked"
        );

        LandAnimal elephant = new LandAnimal(
            "Elephant", 3.0, 5000.0, "Herbivore",
            false, 4, "Grassland", "Walking"
        );

        Fish salmon = new Fish(
            "Salmon", 0.8, 5.0, "Omnivore",
            false, 8, "Freshwater and saltwater", "Smooth"
        );

        eagle.displayDetails();
        elephant.displayDetails();
        salmon.displayDetails();
    }
}

