package sharedMobilityAdventure;

import javax.sound.sampled.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public class Collectable implements Serializable {

    protected static final int WIDTH = 32; // coin and gem width
    protected static final int HEIGHT = 32; // coin and gem width
    private static final long serialVersionUID = 2905924766571606302L;
    protected String name;
    protected int collectabelX;
    protected int collectabelY;
    protected boolean visible;
    transient BufferedImage image;

    private static final Set<Integer> droppedCoordinates = new HashSet<>();
    private static final int MIN_DISTANCE_FROM_PLAYER = 3;

    public Collectable(String name) {
        this.name = name;
        this.visible = true;
    }

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
    
    private int combineCoordinates(int x, int y) {
        return x * 1000 + y;
    }

    public String getDescription() {
        return "Name: " + name;
    }

    public void playSound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        try {
            String soundFilePath;
            if (this instanceof Gem) {
                soundFilePath = "sounds/gem.wav";
            } else if (this instanceof CarbonCoin) {
                soundFilePath = "sounds/coin.wav";
            } else {
                soundFilePath = "sounds/default_sound.wav";
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFilePath).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            new Thread(() -> {
                try {
                    clip.start();
                    float initialVolume = 1.0f;
                    float volumeStep = initialVolume / 20; // Adjust the volume fade speed
                    while (initialVolume > 0) {
                        initialVolume -= volumeStep;
                        if (initialVolume < 0) initialVolume = 0;
                        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                        float dB = (float) (Math.log(initialVolume) / Math.log(10.0) * 20.0);
                        gainControl.setValue(dB);
                        Thread.sleep(50); // Adjust the fade speed
                    }
                    clip.stop();
                    clip.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics g) {
        int adjustedX = collectabelX - (WIDTH / 2);
        int adjustedY = collectabelY - (HEIGHT / 2);

        // Create a copy of the original image
        BufferedImage filledImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        // Get the graphics object of the filled image
        Graphics gFilled = filledImage.getGraphics();
        gFilled.drawImage(image, 0, 0, null);

        // Fill the image with the desired color (bright pink)
        for (int y = 0; y < filledImage.getHeight(); y++) {
            for (int x = 0; x < filledImage.getWidth(); x++) {
                // Get the pixel color at this position
                Color pixelColor = new Color(image.getRGB(x, y), true);

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
        int outlineThickness = 2; // Adjust the outline thickness as desired
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

    public boolean getVisibility() {
    	return this.visible;
    	}
    
    public void setVisibility(boolean visibleUpdated) {
    	this.visible = visibleUpdated;
    	}

	public void loadImage() {
		
	}
    }
