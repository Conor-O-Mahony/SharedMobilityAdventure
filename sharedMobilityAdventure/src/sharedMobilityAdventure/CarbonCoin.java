package sharedMobilityAdventure;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CarbonCoin extends Collectable {

    private static final long serialVersionUID = 1675131934479089420L;
    private BufferedImage[] rotationImages; // Array to store rotation images
    private int currentRotationIndex = 0; // Index of the current rotation image
    private GamePanel gamePanel; // Reference to the GamePanel
    private final int NUM_FRAMES = 8;

    public CarbonCoin(String name, GamePanel gamePanel, int playerX, int playerY) {
        super(name);
        this.gamePanel = gamePanel; // Store the reference to the GamePanel
        int[] coordinates = super.dropRandomly(playerX, playerY);
        this.collectabelX = coordinates[0];
        this.collectabelY = coordinates[1];
        loadImage(); // Load rotation images
        startRotation(); // Start the rotation animation
    }

    @Override
    public void loadImage() {
        try {
            rotationImages = new BufferedImage[NUM_FRAMES];
            for (int i = 0; i < NUM_FRAMES; i++) {
                rotationImages[i] = ImageIO.read(new File("images/coins/coin_" + String.format("%02d", i + 1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics g) {
        int adjustedX = collectabelX - (WIDTH / 2);
        int adjustedY = collectabelY - (HEIGHT / 2);
        g.drawImage(rotationImages[currentRotationIndex], adjustedX, adjustedY, WIDTH, HEIGHT, null);
    }

    // Start the rotation animation
    private void startRotation() {
        Thread rotationThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(200); // Adjust rotation speed as needed
                    currentRotationIndex = (currentRotationIndex + 1) % NUM_FRAMES; // Cycle through images
                    gamePanel.repaint(); // Redraw the panel to update the image
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        rotationThread.start();
    }
}
