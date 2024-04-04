package sharedMobilityAdventure;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Gem {
    public int x; // x-coordinate
    public int y; // y-coordinate
    final int width; // width of gem
    final int height; // height of gem
    BufferedImage image; // image of gem
    int score; // Score to keep track of gem

    public Gem() {
        width = 16;
        height = 16;
        try {
            image = ImageIO.read(new File("images/gems/gem.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        score = 0;
        dropGemRandomly();
    }

    public void dropGemRandomly() {
            
        Random random = new Random();
        int randomNumberX = random.nextInt(16-1);
        int randomNumberY = random.nextInt(8-1);
    	
        int oddNumberX = randomNumberX * 2 + 1;
        int oddNumberY = randomNumberY * 2 + 1;
        
    	x = 8*4*oddNumberX;
        y = 8*4*oddNumberY;
             
    }
   
    public void draw(Graphics g) {
        int adjustedX = x - (width / 2);
        int adjustedY = y - (height / 2);

        g.drawImage(image, adjustedX, adjustedY, width, height, null);
    }
}