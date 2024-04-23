
package sharedMobilityAdventure;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

public class CarbonCoin extends Collectable {

    private static final long serialVersionUID = 1675131934479089420L;
    private transient BufferedImage[] rotationImages; // Array to store rotation images
    private int currentRotationIndex = 0; // Index of the current rotation image
    private GamePanel gamePanel; // Reference to the GamePanel
    private final int NUM_FRAMES = 8;
    private static ExecutorService executor;

    public CarbonCoin(String name, Board board, GamePanel gamePanel, int playerX, int playerY) {
        super(name, board);
        this.gamePanel = gamePanel; // Store the reference to the GamePanel
        
        // Get initial coordinates using dropRandomly method
        int[] coordinates = super.dropRandomly(playerX, playerY);
        
        // Continue generating new coordinates until the Euclidean distance condition is satisfied
        while (!checkEuclideanDistance(coordinates[0], coordinates[1], board)) {
            coordinates = super.dropRandomly(playerX, playerY);
        }
        // Assign the final coordinates that satisfy the Euclidean distance condition
        this.collectabelX = coordinates[0];
        this.collectabelY = coordinates[1];
        
        loadImage();
    }
    @Override
    public void loadImage() {
        try {
            rotationImages = new BufferedImage[NUM_FRAMES];
            for (int i = 0; i < NUM_FRAMES; i++) {
            	String fileName = String.format("coin_%02d", i + 1);
            	if (imageCache.containsKey(fileName)) {
            		rotationImages[i] = imageCache.get(fileName);
            	} else {
            		rotationImages[i] = ImageIO.read(new File("images/coins/" + fileName + ".png"));
            	}
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
    void startRotation() {    	
    	if (executor == null || executor.isShutdown() || executor.isTerminated()) {
    		executor = Executors.newFixedThreadPool(3);
        }
    	
    	executor.submit(new Runnable() {
            @Override public void run() 
            {
                   while(!Thread.currentThread().isInterrupted()) {
                	   try {
                           Thread.sleep(200); // Adjust rotation speed as needed
                           currentRotationIndex = (currentRotationIndex + 1) % NUM_FRAMES; // Cycle through images
                           gamePanel.repaint(); // Redraw the panel to update the image
                       } catch (InterruptedException e) {
                    	   Thread.currentThread().interrupt();
                           //return; // Exit the task if interrupted
                       }
                   }

             }
	    });
	    }
    
    static void stopRotation() {
        executor.shutdownNow();
    }
}
