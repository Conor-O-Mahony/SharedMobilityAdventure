package sharedMobilityAdventure;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class EndPanel extends JPanel {

	private static final long serialVersionUID = -8788565932041161917L;
	int tile = 16;
    int columns = 64;
    int rows = 36;
    // int totalWidth = columns * tile;
    // int totalHeight = rows * tile;
    private BufferedImage backgroundImage; // Background image 
    private int totalWidth = 1024; // Width of the panel
    private int totalHeight = 576; // Height of the panel

    public EndPanel(JFrame endFrame) {
        setPreferredSize(new Dimension(totalWidth, totalHeight));
        setLayout(null);
        loadBackgroundImage(); // Load the background image
        add(createEndGameTextBox()); // Add the "End Game" textbox
        add(createButton(endFrame)); // Add the button to return to the main panel
    }

    private JTextField createEndGameTextBox() {
        JTextField endGameText = new JTextField("Game Over");
        endGameText.setHorizontalAlignment(SwingConstants.CENTER);
        int textWidth = 144;
        int textHeight = 64;
        int textX = (totalWidth - textWidth) / 2; // Centered horizontally
        int textY = 50; // Adjusted y-coordinate to move higher
        endGameText.setBounds(textX, textY, textWidth, textHeight);
        endGameText.setEditable(false);
        endGameText.setBackground(Color.ORANGE);
        endGameText.setForeground(getRainbowColor()); // Rainbow neon color for text
        endGameText.setFont(new Font("Arial", Font.BOLD, 16));
        endGameText.setBorder(null);
        return endGameText;
    }

    private Color getRainbowColor() {
        float hue = (System.currentTimeMillis() % 10000) / 10000f; // Cycling through hue values
        return Color.getHSBColor(hue, 1, 1); // Convert hue to RGB color
    }

    private JButton createButton(JFrame menuFrame) {
        int buttonWidth = 320;
        int buttonHeight = 95;
        int buttonX = (totalWidth - buttonWidth) / 2;
        int buttonY = 150; // Adjusted y-coordinate to move higher

        JButton button = new JButton();
        button.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        setButtonIcons(button, "images/tiles/playgamebuttonhovered.png", "images/tiles/playgamebuttondefault.png");
        button.addActionListener(e -> {
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            currentFrame.dispose();
            Main.Game();
        });

        return button;
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Render background image if available
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            // If background image is not available, fill with black
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void loadBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(new File("images/tiles/Dublin_1980.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //setting button image/icons to correct status of button
    private void setButtonIcons(JButton button, String hoverImagePath, String imagePath) {
        setButtonIcon(button, imagePath);
        setButtonHoverIcon(button, hoverImagePath);
    }

    //Remove the default button border and fills to make images transparent buttons
    private void setButtonIcon(JButton button, String imagePath) {
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(null);
        ImageIcon icon = new ImageIcon(imagePath);
        button.setIcon(icon);
    }

    private void setButtonHoverIcon(JButton button, String hoverImagePath) {
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(null);
        ImageIcon icon = new ImageIcon(hoverImagePath);
        button.setRolloverIcon(icon);
    }
}