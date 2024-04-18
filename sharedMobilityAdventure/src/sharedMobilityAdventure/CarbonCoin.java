package sharedMobilityAdventure;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;

public class CarbonCoin extends Collectable implements Serializable {

    private static final long serialVersionUID = 1675131934479089420L;
    final int width; // width of gem
    final int height; // height of gem
    transient BufferedImage image; // image of gem
    private transient GamePanel gamePanel; // GamePanel instance

    public CarbonCoin(String name, GamePanel gamePanel) {
        super(name);
        this.gamePanel = gamePanel; // Store the GamePanel instance
        width = 32;
        height = 32;
        int[] coordinates = super.dropRandomly();
        this.collectabelX = coordinates[0];
        this.collectabelY = coordinates[1];
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
