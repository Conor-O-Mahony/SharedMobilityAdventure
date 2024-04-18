package sharedMobilityAdventure;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;

public class CarbonCoin extends Collectable implements Serializable {

    private static final long serialVersionUID = 1675131934479089420L;
    
    // private GamePanel gamePanel; // Declare a field to store the GamePanel object

    public CarbonCoin(String name, GamePanel gamePanel, int playerX, int playerY) {
        super(name);
        int[] coordinates = super.dropRandomly(playerX, playerY);
        this.collectabelX = coordinates[0];
        this.collectabelY = coordinates[1];
    }

    @Override
    public void loadImage() {
        try {
            image = ImageIO.read(new File("images/coins/coin.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics g) {
        int adjustedX = collectabelX - (WIDTH / 2);
        int adjustedY = collectabelY - (HEIGHT / 2);
        g.drawImage(image, adjustedX, adjustedY, WIDTH, HEIGHT, null);
    }
}
