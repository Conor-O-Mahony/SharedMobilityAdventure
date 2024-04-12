package sharedMobilityAdventure;

import java.util.Random;

public class Collectable {
	// Attributes to store the name and value of the collectable
	protected String name;  // Name of the collectable
	protected int collectabelX; // x-coordinate
	protected int collectabelY; // y-coordinate
	protected boolean visible;
	
	// constructor to initialise the name and value of the collectables
	public Collectable(String name) {
		this.name = name;
		this.visible = true;
	}
    
	// Method shared by Gem and CarbonCoin
    public int[] dropRandomly() {
        Random random = new Random();
        // debugging statements
        System.out.println("Dropping collectable.");
        
		int randomNumberX = random.nextInt(GamePanel.DEFAULT_BOARD_SIZE);
        int randomNumberY = random.nextInt(GamePanel.DEFAULT_BOARD_SIZE);
        System.out.println("Random numbers: X=" + randomNumberX + ", Y=" + randomNumberY);
        
        int oddNumberX = randomNumberX * 2 + 1;
        int oddNumberY = randomNumberY * 2 + 1;
        
        collectabelX = GamePanel.TILE_SIZE/2 * oddNumberX;
        collectabelY = GamePanel.TILE_SIZE/2 * oddNumberY;
        System.out.println("Dropped collectable at coordinates: X=" + collectabelX + ", Y=" + collectabelY);
    
        return new int[]{collectabelX, collectabelY};
    
    }

// Method to describe the collectable
	public String getDescription() {
		// Return a string describing the collectable,
		return "Name: " + name;
	}
}
