package sharedMobilityAdventure;

import java.util.Random;

public class Collectable {
	// Attributes to store the name and value of the collectable
	protected String name;  // Name of the collectable
	protected int x; // x-coordinate
	protected int y; // y-coordinate
	// Final attributes to store the width and height of the collectable
	
	// constructor to initialise the name and value of the collectables
	public Collectable(String name) {
		//this.x = x;
		//this.y = y;
		this.name = name;
		
	}
    // Method shared by Gem and CarbonCoin
    public void dropRandomly() {
        Random random = new Random();
        int randomNumberX = random.nextInt(16-1);
        int randomNumberY = random.nextInt(8-1);
    	
        int oddNumberX = randomNumberX * 2 + 1;
        int oddNumberY = randomNumberY * 2 + 1;
        
        x = 8 * 4 * oddNumberX;
        y = 8 * 4 * oddNumberY;
    }
	// Method to describe the collectable
	public String getDescription() {
		// Return a string describing the collectable,
		return "Name: " + name;
	}

}
