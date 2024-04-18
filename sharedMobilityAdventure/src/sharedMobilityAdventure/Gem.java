package sharedMobilityAdventure;

import java.io.Serializable;
<<<<<<< HEAD
=======
import java.awt.Color;
>>>>>>> 7b19ab0 (color for gems)
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
<<<<<<< HEAD

=======
>>>>>>> 7b19ab0 (color for gems)
import javax.imageio.ImageIO;

public class Gem extends Collectable implements Serializable {

    private static final long serialVersionUID = 3349609681759015759L;
    
    // private GamePanel gamePanel; // Declare a field to store the GamePanel object

    public Gem(String name, GamePanel gamePanel, int playerX, int playerY) {
        super(name);
        int[] coordinates = super.dropRandomly(playerX, playerY);
        this.collectabelX = coordinates[0];
        this.collectabelY = coordinates[1];
    }
    public void loadImage() {
        try {
            image = ImageIO.read(new File("images/gems/gem.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
<<<<<<< HEAD

    @Override
    public void draw(Graphics g) {
        super.draw(g); // Call superclass draw method to draw the gem image and its outline
}
=======
    public void draw(Graphics g) {
        int adjustedX = collectabelX - (width / 2);
        int adjustedY = collectabelY - (height / 2);

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
        g.drawImage(filledImage, adjustedX, adjustedY, width, height, null);

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
>>>>>>> 7b19ab0 (color for gems)
}