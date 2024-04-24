
package sharedMobilityAdventure;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Random;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
    static transient BufferedImage filledImage;
    
    private Random random = new Random();
    
    // Animation frames
    protected transient BufferedImage[] animationFrames;
    // Number of animation frames
    protected static final int NUM_FRAMES = 12; 
    protected int currentFrameIndex = 0; // Index of the current frame
	protected Board board;

    // Animation speed
    protected static final int ANIMATION_SPEED = 100; // Milliseconds per frame

    // Set to store dropped coordinates
    private static final Set<Integer> droppedCoordinates = new HashSet<>();

    // Minimum distance from player when dropping collectable
    private static final int MIN_DISTANCE_FROM_PLAYER = 3;

    // Cache for storing loaded images
    protected static transient Map<String, BufferedImage> imageCache = new HashMap<>();
    
    // Constructor
    public Collectable(String name, Board board) {
        this.name = name;
        this.visible = true;
        this.board = board;
    }
    // Clear the array to holds objects coordinates
    public static void clearDroppedCoordinates() {
    	droppedCoordinates.clear();
    }

    // Method to drop collectable randomly
    public int[] dropRandomly(int playerX, int playerY) {
        int panelWidth = Main.DEFAULT_BOARD_SIZE;
        int panelHeight = Main.DEFAULT_BOARD_SIZE;
        int maxAttempts = 10;
        int attempts = 0;

        while (attempts < maxAttempts) {
            int randomNumberX = random.nextInt(panelWidth);
            int randomNumberY = random.nextInt(panelHeight);

            int oddNumberX = randomNumberX * 2 + 1;
            int oddNumberY = randomNumberY * 2 + 1;

            // Generate random coordinates within the panel bounds
            collectabelX = Main.TILE_SIZE / 2 * oddNumberX;
            collectabelY = Main.TILE_SIZE / 2 * oddNumberY;

            // Ensure the generated coordinates are aligned with the tile grid
            if (collectabelX % Main.TILE_SIZE != 0) {
                collectabelX -= (collectabelX % Main.TILE_SIZE);
            }
            if (collectabelY % Main.TILE_SIZE != 0) {
                collectabelY -= (collectabelY % Main.TILE_SIZE);
            }
            // https://stackoverflow.com/questions/6709754/get-the-offset-of-a-string
            // Apply magical offset to position the gem and carboncoin objects in the middle of the tile
            collectabelX = collectabelX + Main.TILE_SIZE / 2;
            collectabelY = collectabelY + Main.TILE_SIZE / 2;

            // Debug statements
            //System.out.println("Generated collectable coordinates: X=" + collectabelX + ", Y=" + collectabelY);
            //System.out.println("Player coordinates: X=" + playerX + ", Y=" + playerY);

            // Check if the generated coordinates are too close to the player
            if (Math.abs(collectabelX - playerX) < MIN_DISTANCE_FROM_PLAYER &&
                    Math.abs(collectabelY - playerY) < MIN_DISTANCE_FROM_PLAYER) {
                //System.out.println("Collectable too close to player. Skipping...");
                attempts++;
                continue;
            }

            // Check if the generated coordinates overlap with existing collectables
            if (isOverlap(collectabelX, collectabelY)) {
                //System.out.println("Overlap detected with existing collectables. Retrying...");
                attempts++;
                continue;
            }

           
            break;
        }
        
        if (attempts == maxAttempts) {
        	boolean fallbackSuccess = false;
        	// fallback method - go through all rows/columns to find one
        	for (int i = 0; i < Main.DEFAULT_BOARD_SIZE - 1; i++) {
        		for (int j = 0; j < Main.DEFAULT_BOARD_SIZE - 1; j++) {
    				collectabelX = i * Main.TILE_SIZE + Main.TILE_SIZE / 2;
    				collectabelY = j * Main.TILE_SIZE + Main.TILE_SIZE / 2;
        			if (!isOverlap(collectabelX, collectabelY)) {
        				//System.out.println("fallback dropping succeeded");
        				fallbackSuccess = true;
        				break;
        			}	
        		}
        		
        		if (fallbackSuccess == true) {
    				break;
    			}
        	}
        	//System.out.println("Max attempts reached");
        }
        
        //noSystem.out.println("attempts" + attempts);
        
        // If no overlap, add the coordinates to droppedCoordinates and break out of the loop
        int combinedCoordinates = combineCoordinates(collectabelX, collectabelY);
        droppedCoordinates.add(combinedCoordinates);
        //System.out.println("Collectable dropped successfully at coordinates: X=" + collectabelX + ", Y=" + collectabelY);
        
        return new int[]{collectabelX, collectabelY};
    }

    public boolean isOverlap(int collectabelX, int collectabelY) {
        // Round up the coordinates to the nearest Main.TILE_SIZE interval
        collectabelX -= (collectabelX % Main.TILE_SIZE);
        collectabelY -= (collectabelY % Main.TILE_SIZE);

        // Calculate combined coordinates
        int combinedCoordinates = combineCoordinates(collectabelX, collectabelY);

        // Check if the combined coordinates are present in droppedCoordinates
        boolean result = droppedCoordinates.contains(combinedCoordinates);

        // Print debug message
        if (result) {
            //System.out.println("Overlap detected at coordinates: X=" + collectabelX + ", Y=" + collectabelY);
        } else {
            //System.out.println("No overlap detected at coordinates: X=" + collectabelX + ", Y=" + collectabelY);
        }

        return result;
    }

    // Method to combine coordinates into a single integer
    protected int combineCoordinates(int x, int y) {
        // Ensure coordinates are aligned with the tile grid
        x -= (x % Main.TILE_SIZE);
        y -= (y % Main.TILE_SIZE);

        return x * 1000 + y;
    }

    // Method to get description of collectable
    public String getDescription() {
        return "Name: " + name;
    }
    // https://stackoverflow.com/questions/49469921/euclidean-distance-between-two-variables
    // Find eucledian distance to calcualte the distance from the transport objects
    public static boolean eucledianDistanceCalculator(int collectabelX, int collectabelY, Board board) {
        final int MIN_DISTANCE_FROM_TRANSPORT = 10; // Define the minimum distance constant locally
        
		// Access the tiles array directly from the Main class
        Tile[][] tiles = board.getTiles();
        
        // Flag to track if the collectable is too close to transportation
        // boolean tooCloseToTransport = false;
        
        // Calculate the distance to the nearest transportation type
        // double nearestTransportDistance = Double.MAX_VALUE;
        //double nearestTransportCarbonFootprint = Double.MAX_VALUE;

        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                Tile tile = tiles[row][col];
                // Check if the tile has any routes
                if (tile.getNumberOfRoutes() > 0) {
                    int tileX = tile.getX() * Main.TILE_SIZE;
                    int tileY = tile.getY() * Main.TILE_SIZE;
                    // Calculate the Euclidean distance between the collectable and the tile
                    double distance = Math.sqrt(Math.pow(collectabelX - (tileX + Main.TILE_SIZE / 2), 2)
                            + Math.pow(collectabelY - (tileY + Main.TILE_SIZE / 2), 2));
                    // Check if the distance is less than the minimum allowed distance
                    if (distance < MIN_DISTANCE_FROM_TRANSPORT) {
                        return false; // Too close to transportation, return false
                    }
                }
            }
        }
        return true; // Distance is acceptable, return true
    }

    // Method to play sound of the collectable
    public void playSound() {
            // Check if the collectable is a Gem
            if (this instanceof Gem) {
                // Specify the sound file path for gems
                // Start playback of the clip
            	
            	if (Main.clip.getFramePosition() != 0) {
            		Main.clip.stop();
            		Main.clip.setFramePosition(0);
            	}
                Main.clip.start();
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
}
