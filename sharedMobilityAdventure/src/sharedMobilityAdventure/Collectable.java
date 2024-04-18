package sharedMobilityAdventure;

import java.io.Serializable;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public class Collectable implements Serializable {

    private static final long serialVersionUID = 2905924766571606302L;
    // Attributes to store the name and value of the collectable
    protected String name;  // Name of the collectable
    protected int collectabelX; // x-coordinate
    protected int collectabelY; // y-coordinate
    protected boolean visible;

    // Collection to store coordinates of previously dropped collectibles
    private static Set<Integer> droppedCoordinates = new HashSet<>();

    // constructor to initialise the name and value of the collectables
    public Collectable(String name) {
        this.name = name;
        this.visible = true;
    }

    public int[] dropRandomly(int playerX, int playerY) {
        Random random = new Random();
        System.out.println("Dropping collectable.");
        int panelWidth = Main.DEFAULT_BOARD_SIZE;
        int panelHeight = Main.DEFAULT_BOARD_SIZE;
        int maxAttempts = 10;
        int attempts = 0;

        // Define the minimum distance from the player
        int MIN_DISTANCE_FROM_PLAYER = 3; 

        while (attempts < maxAttempts) {
            int randomNumberX = random.nextInt(panelWidth);
            int randomNumberY = random.nextInt(panelHeight);
            System.out.println("Random numbers: X=" + randomNumberX + ", Y=" + randomNumberY);

            int oddNumberX = randomNumberX * 2 + 1;
            int oddNumberY = randomNumberY * 2 + 1;

            collectabelX = Main.TILE_SIZE / 2 * oddNumberX;
            collectabelY = Main.TILE_SIZE / 2 * oddNumberY;
            System.out.println("Dropped collectable at coordinates: X=" + collectabelX + ", Y=" + collectabelY);

            // Check if the new coordinates are too close to the player
            if (Math.abs(collectabelX - playerX) < MIN_DISTANCE_FROM_PLAYER ||
                Math.abs(collectabelY - playerY) < MIN_DISTANCE_FROM_PLAYER) {
                System.out.println("Too close to the player. Retrying...");
                attempts++;
                continue; // Retry dropping in a different location
            }
            
            System.out.println("Collectable dropped further from the player.");

            int combinedCoordinates = combineCoordinates(collectabelX, collectabelY);
            if (droppedCoordinates.contains(combinedCoordinates)) {
                System.out.println("Coordinates overlap with previous position. Retrying...");
                attempts++;
                continue; // Retry dropping in a different location
            }

            droppedCoordinates.add(combinedCoordinates);
            break;
        }

        return new int[]{collectabelX, collectabelY};
    }


    // Utility method to combine x and y coordinates into a single integer for set comparison
    private int combineCoordinates(int x, int y) {
        return x * 1000 + y; // Ensure uniqueness for different x and y values within reasonable bounds
    }

    // Method to describe the collectable
    public String getDescription() {
        // Return a string describing the collectable,
        return "Name: " + name;
    }
}