
package sharedMobilityAdventure;

import java.awt.Graphics;
import java.io.Serializable;

public class Gem extends Collectable implements Serializable {

    private static final long serialVersionUID = 3349609681759015759L;
    
    public Gem(String name, Board board, GamePanel gamePanel, int playerX, int playerY) {
        super(name, board);
        loadImage();
        
        // Get initial coordinates using dropRandomly method
        int[] coordinates = super.dropRandomly(playerX, playerY);
        //System.out.println("Gem coordinates after dropRandomly: (" + coordinates[0] + ", " + coordinates[1] + ")");

        // Continue generating new coordinates until the Euclidean distance condition is satisfied
        while (!eucledianDistanceCalculator(coordinates[0], coordinates[1], board)) {
            coordinates = super.dropRandomly(playerX, playerY);
            //System.out.println("Gem coordinates adjusted to satisfy Euclidean distance.");
        }
        
        // Assign the final coordinates that satisfy the Euclidean distance condition
        this.collectabelX = coordinates[0];
        this.collectabelY = coordinates[1];
    }
    
    public void loadImage() {
    	super.cacheImage(name,Main.gemImage);
    	super.loadImage();
    }
    
    @Override
    public void draw(Graphics g) {
        super.draw(g); // Call superclass draw method to draw the gem image and its outline
    }
}
