package sharedMobilityAdventure;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CarbonCoin extends Collectable {
	//public int x; // x-coordinate
    //public int y; // y-coordinate
    final int width; // width of gem
    final int height; // height of gem
    BufferedImage image; // image of gem
    int score; // Score to keep track of gem
    
    public CarbonCoin(String name) {
    	super(name);
    	width = 16;
    	height = 16;
    	try {
    		image = ImageIO.read(new File("images/coins/coin.png"));
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	score = 0;
    	super.dropRandomly(); // Call dropRandomly from super class
    	// debugging statement
        System.out.println("Gem coordinates after dropRandomly(): x=" + x + ", y=" + y);
    }
    public void draw(Graphics g) {
    	int adjustedX = x - (width / 2);
    	int adjustedY = y - (height / 2);
    	
    	g.drawImage(image, adjustedX, adjustedY, width, height, null);
    	}
}
