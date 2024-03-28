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
    BufferedImage image; // image of the gem
    GamePanel gamePanel; // Reference to the GamePanel
    int score; // Score to keep track of gem

    public Gem(GamePanel gamePanel) {
        width = 16;
        height = 16;
        this.gamePanel = gamePanel; // Store the references to the GamePanel
        try {
            image = ImageIO.read(new File("images/gems/gem.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        score = 0;
        dropGemRandomly();
    }

    public void dropGemRandomly() {
        // Keep generating random coordinates until a position with no collision is found
        while (true) {
            // Generate random coordinates for the gem
            int randomX = Board.getRandomNumber(0, 1024); // Using Board's getRandomNumber method
            int randomY = Board.getRandomNumber(0, 576); // Using Board's getRandomNumber method
            int c = 16;
       
            int randomC = (randomX / c) * c;
            int randomA = (randomY / c) * c;

            // Check for collision at the generated coordinates
            if (!checkCollision(randomC, randomA)) {
                // No collision found, set the gem coordinates and exit the loop
                x = randomC;
                y = randomA;
                break;
            }
        }
    }
    private boolean checkCollision(int newX, int newY) {
        int tileX = newX / gamePanel.tile;
        int tileY = newY / gamePanel.tile;
        return gamePanel.collisionMap[tileX][tileY]; // Access collisionMap through the GamePanel reference
    }
    public void draw(Graphics g) {
    if (image != null) {
        g.drawImage(image, x, y, width, height, null); 
    }

}
}
// Getter and setter methods for x, y, width, height

// collecting score
// */
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
