package sharedMobilityAdventure;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class CarbonCoin extends Collectable implements Serializable {

	private static final long serialVersionUID = 1675131934479089420L;
	// public int x; // x-coordinate
    // public int y; // y-coordinate
    final int width; // width of gem
    final int height; // height of gem
    transient BufferedImage image; // image of gem
    // Color color; // Color of the gem

    public CarbonCoin(String name) {
    	super(name);
        width = 10;
        height = 10;
        //loadImage();

        int[] coordinates = super.dropRandomly(); // Obtain random coordinates from superclass
        this.collectabelX = coordinates[0]; // Set the x-coordinate obtained from dropRandomly
        this.collectabelY = coordinates[1]; // Set the y-coordinate obtained from dropRandomly

        // debugging statement to confirm that random method wont drop gem and carboncoin to the same location on the gamepanel
        System.out.println("Gem coordinates after dropRandomly(): x=" + collectabelX + ", y=" + collectabelY);
    }
    
    public void loadImage() {
    	try {
            image = ImageIO.read(new File("images/coins/coin.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    public void draw(Graphics g) {
        int adjustedX = collectabelX - (width / 2);
        int adjustedY = collectabelY - (height / 2);
        g.drawImage(image, adjustedX, adjustedY, width, height, null);
    }

    public boolean getVisibility() {
        return this.visible;
    }
    
    public void setVisibility(boolean visibleUpdated) {
        this.visible = visibleUpdated;
    }
    
    
    
}
