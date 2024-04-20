package sharedMobilityAdventure;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Collectable implements Serializable {

    // Constants for width and height of collectables
    protected static final int WIDTH = 32;
    protected static final int HEIGHT = 32;

    // Serializable ID
    private static final long serialVersionUID = 2905924766571606302L;

    protected String name;
    protected int collectabelX;
    protected int collectabelY;

    protected boolean visible;
    transient BufferedImage filledImage;

    // Animation frames
    protected transient BufferedImage[] animationFrames;
    // Number of animation frames
    protected static final int NUM_FRAMES = 12; 
    protected int currentFrameIndex = 0; // Index of the current frame

    // Animation speed
    protected static final int ANIMATION_SPEED = 100; // Milliseconds per frame

    // Set to store dropped coordinates
    private static final Set<Integer> droppedCoordinates = new HashSet<>();

    // Minimum distance from player when dropping collectable
    private static final int MIN_DISTANCE_FROM_PLAYER = 3;

    // Cache for storing loaded images
    private static Map<String, BufferedImage> imageCache = new HashMap<>();
    
    // Thread pool for sound playback
    @SuppressWarnings("unused")
	private static final ExecutorService soundThreadPool = Executors.newFixedThreadPool(5); 

    // Constructor
    public Collectable(String name) {
        this.name = name;
        this.visible = true;
    }

    // Method to drop collectable randomly
    public int[] dropRandomly(int playerX, int playerY) {
        Random random = new Random();
        int panelWidth = Main.DEFAULT_BOARD_SIZE;
        int panelHeight = Main.DEFAULT_BOARD_SIZE;
        int maxAttempts = 10;
        int attempts = 0;

        while (attempts < maxAttempts) {
            int randomNumberX = random.nextInt(panelWidth);
            int randomNumberY = random.nextInt(panelHeight);

            int oddNumberX = randomNumberX * 2 + 1;
            int oddNumberY = randomNumberY * 2 + 1;

            collectabelX = Main.TILE_SIZE / 2 * oddNumberX;
            collectabelY = Main.TILE_SIZE / 2 * oddNumberY;

            if (Math.abs(collectabelX - playerX) < MIN_DISTANCE_FROM_PLAYER ||
                    Math.abs(collectabelY - playerY) < MIN_DISTANCE_FROM_PLAYER) {
                attempts++;
                continue;
            }

            int combinedCoordinates = combineCoordinates(collectabelX, collectabelY);
            if (droppedCoordinates.contains(combinedCoordinates)) {
                attempts++;
                System.out.println("Coordinates overlap with previous position. Retrying...");
                continue;
            }

            droppedCoordinates.add(combinedCoordinates);
            System.out.println("Dropped collectable at coordinates: X=" + collectabelX + ", Y=" + collectabelY);
            break;
        }

        return new int[]{collectabelX, collectabelY};
    }

    // Method to combine coordinates into a single integer
    private int combineCoordinates(int x, int y) {
        return x * 1000 + y;
    }

    // Method to get description of collectable
    public String getDescription() {
        return "Name: " + name;
    }
 // Method to play sound of the collectable
    public synchronized void playSound() {
        try {
            // Check if the collectable is a Gem
            if (this instanceof Gem) {
                // Specify the sound file path for gems
                String soundFilePath = "sounds/gem.wav";

                System.out.println("Starting sound playback for " + name + "...");

                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFilePath).getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                
                // Open and start audio playback for this gem
                clip.open(audioInputStream);
                clip.start();

                // Wait for the sound to finish playing
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                        System.out.println("Sound playback for " + name + " completed.");
                    }
                });
            } else {
                System.out.println("No sound playback for " + name + ".");
            }
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
            System.err.println("Error occurred during sound playback for " + name + ": " + e.getMessage());
        }
    }
    // Method to draw the collectable
    public void draw(Graphics g) {
        int adjustedX = collectabelX - (WIDTH / 2);
        int adjustedY = collectabelY - (HEIGHT / 2);

        // Create a copy of the original image
        //BufferedImage filledImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        // Get the graphics object of the filled image
        Graphics gFilled = filledImage.getGraphics();
        gFilled.drawImage(filledImage, 0, 0, null);

        // Fill the image with the desired colour (bright pink)
        for (int y = 0; y < filledImage.getHeight(); y++) {
            for (int x = 0; x < filledImage.getWidth(); x++) {
                // Get the pixel colour at this position
                Color pixelColor = new Color(filledImage.getRGB(x, y), true);

                // If the pixel is not transparent, fill it with the desired color
                if (pixelColor.getAlpha() != 0) {
                    filledImage.setRGB(x, y, new Color(255, 105, 180).getRGB());
                }
            }
        }

        // Find the bounding box of non-transparent pixels
        int minX = filledImage.getWidth();
        int minY = filledImage.getHeight();
        int maxX = -1;
        int maxY = -1;

        for (int y = 0; y < filledImage.getHeight(); y++) {
            for (int x = 0; x < filledImage.getWidth(); x++) {
                // If the pixel is not transparent
                if ((filledImage.getRGB(x, y) >> 24) != 0x00) {
                    minX = Math.min(minX, x);
                    minY = Math.min(minY, y);
                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y);
                }
            }
        }

        // Draw the image onto the graphics context
        g.drawImage(filledImage, adjustedX, adjustedY, WIDTH, HEIGHT, null);

        // Draw white outline around the image edges
        g.setColor(Color.WHITE);
        int outlineThickness = 1; 
        for (int y = minY - outlineThickness; y <= maxY + outlineThickness; y++) {
            for (int x = minX - outlineThickness; x <= maxX + outlineThickness; x++) {
                if (x < 0 || x >= filledImage.getWidth() || y < 0 || y >= filledImage.getHeight() ||
                        (filledImage.getRGB(x, y) >> 24) == 0x00) {
                    for (int dx = -outlineThickness; dx <= outlineThickness; dx++) {
                        for (int dy = -outlineThickness; dy <= outlineThickness; dy++) {
                            if (dx != 0 || dy != 0) {
                                int nx = x + dx;
                                int ny = y + dy;
                                if (nx >= 0 && nx < filledImage.getWidth() && ny >= 0 && ny < filledImage.getHeight() &&
                                        (filledImage.getRGB(nx, ny) >> 24) != 0x00) {
                                    g.fillRect(adjustedX + nx, adjustedY + ny, 1, 1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Getter for visibility
    public boolean getVisibility() {
        return this.visible;
    }

    // Setter for visibility
    public void setVisibility(boolean visibleUpdated) {
        this.visible = visibleUpdated;
    }

    // Method to load the image of the collectable
    public void loadImage() {
    	// check if the image is already cached
    	if (imageCache.containsKey(name)) {
    		// Retrieve the image from the cache
    		filledImage = imageCache.get(name);
    	}
    }
};