package sharedMobilityAdventure;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Gem {
	int x; // x-coordinate
	int y; // y-coordinate
	final int width; // width of the gem
	final int height; // height of the gem
	BufferedImage image; //image of the gem
	GamePanel gamePanel; // Reference to the GamePanel
	int score; // Score to keep track of gem
	
    public Gem(GamePanel gamePanel) {
        this.gamePanel = gamePanel; // Store the references to the GamePanel
        width = 24;
        height = 24;
        try {
            image = ImageIO.read(new File("images/characters/up.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
		score = 0;
		setRandomPosition(); // Call setRandomPosition to initialise gem's position randomly
	}
		
	private boolean checkCollision(int newX, int newY) {
        int tileX = newX / gamePanel.tile;
        int tileY = newY / gamePanel.tile;
        return gamePanel.collisionMap[tileX][tileY]; // Access collisionMap through the GamePanel reference
	}
	
	void setRandomPosition() {
	    int maxX = gamePanel.totalWidth - width; // Adjust maxX to the total width of the playable area
	    int maxY = gamePanel.totalHeight - height; // Adjust maxY to the total height of the playable area
	    
	    // Keep generating random positions until a non-colliding position is found
	    do {
	        // Generate random x and y coordinates within the playable area
	        x = Board.getRandomNumber(0, maxX);
	        y = Board.getRandomNumber(0, maxY);
	    } while (checkCollision(x, y)); // Check if the generated position collides with walls
	    System.out.println(x);
	}
    
	public void draw(Graphics g) {
		if (image != null ) {
			g.drawImage(image, x, y, width, height, null);
		}
    }
}
// collecting score
// 
    /*
    Getter and setter methods for x, y, width, height, and score
    public int getX() {
    return x;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
    	return y;
    }
    
    // Implement getter and setter methods for y, width, height, and score
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
}
*/
