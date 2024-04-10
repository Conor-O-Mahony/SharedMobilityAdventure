package sharedMobilityAdventure;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
// import java.util.Random;

import javax.imageio.ImageIO;

public class Gem extends Collectable {
	// public int x; // x-coordinate
    // public int y; // y-coordinate
    final int width; // width of gem
    final int height; // height of gem
    BufferedImage image; // image of gem
    int score; // Score to keep track of gem

    public Gem(String name) {
    	super(name);
        width = 30;
        height = 30;
        try {
            image = ImageIO.read(new File("images/gems/gem.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        score = 0;
        super.dropRandomly(); // Call dropRandomly from super class
        // debugging statement to confirm that random method wont drop gem and carboncoin to the same location on the gamepanel
        System.out.println("Gem coordinates after dropRandomly(): x=" + x + ", y=" + y);
    }
   
    public void draw(Graphics g) {
        int adjustedX = x - (width / 2);
        int adjustedY = y - (height / 2);

        g.drawImage(image, adjustedX, adjustedY, width, height, null);
    }
}