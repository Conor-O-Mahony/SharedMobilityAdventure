package sharedMobilityAdventure;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Gem {
	private int x; // x-coordinate of the gem
	private int y; // y-coordinate of the gem
	private final int width; // width of the gem
	private final int height; // height of the gem
	private BufferedImage image; //image of the gem
	private GamePanel gamePanel; // Reference to the GamePanel
	private int score; // Score to keep track of gem
	
	public Gem(GamePanel gamePanel, int x, int y) {
		this.gamePanel = gamePanel; // Store the references to the GamePanel
		this.x = x; // Set gem position
		this.y = y;
		width = 16;
		height = 16;
		try {
			image = ImageIO.read(new File("images/gems/placeholder.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		score = 0;
		setRandomPosition(); // Call setRandomPosition to initialise gem's position randomly
	}
		
	public void draw(Graphics g) {
	    g.drawImage(image, x, y, width, height, null); // Changed y to x
	}
	
	private boolean checkCollision(int newX, int newY) {
        int tileX = newX / gamePanel.tile;
        int tileY = newY / gamePanel.tile;
        return gamePanel.collisionMap[tileX][tileY]; // Access collisionMap through the GamePanel reference
    }
	
    private void setRandomPosition() {
    	int maxX = gamePanel.getWidth() - width;
    	int maxY = gamePanel.getHeight() - height;
    	
    	// Keep generating random positions until a non-colliding position is found
    	do {
    		x = Board.getRandomNumber(0,  maxX);
    		y = Board.getRandomNumber(0,  maxY);
    	} while (checkCollision(x, y));
    } 
    // Getter and setter methods for x, y, width, height, and score
    public int getX() {
        return x;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    // Implement getter and setter methods for y, width, height, and score
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
}

