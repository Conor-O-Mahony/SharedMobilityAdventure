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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


public class EndPanel extends JPanel {

    private static final long serialVersionUID = -8788565932041161917L;
    int tile = 16;
    int columns = 64;
    int rows = 36;
    int totalWidth = columns * tile;
    int totalHeight = rows * tile;

    private String username;
    private int endGameRound;
    private int endGemScore;
    private int endCoinScore;
    private int endGameScore;

    public EndPanel(String username, int gameRound, int gemScore, int coinsCollected, int gameScore) {

        this.username = username;
        this.endGameRound = gameRound;
        this.endGemScore = gemScore;
        this.endCoinScore = coinsCollected;
        this.endGameScore = gameScore;

        setPreferredSize(new Dimension(totalWidth, totalHeight));
        setLayout(null);
        add(usernameTextbox());
        add(statsTextbox());
        add(scoreTextbox());
        //add(endGameTextArea());

        add(createButton()); // Add the button to return to the main panel
    }
    /**
    
    private JTextArea endGameTextArea() {
        JTextArea endGameText = new JTextArea();
        endGameText.setEditable(false);
        endGameText.setBackground(Color.WHITE);
        endGameText.setForeground(new Color(101, 67, 33));
        endGameText.setFont(new Font("Arial", Font.BOLD, 20));
        endGameText.setBorder(null);
        
        // Construct the end game message
        StringBuilder message = new StringBuilder();
        message.append("Game Over. Well done, ").append(username).append("!\n\n");
        message.append("You made it to round: ").append(endGameRound).append("\n");
        message.append("Gems collected: ").append(endGemScore).append("\n");
        message.append("Coins collected: ").append(endCoinScore).append("\n\n");
        message.append("Final score: ").append(endGameScore);
        
        endGameText.setText(message.toString());
        
        // Calculate x-coordinate to center horizontally
        int x = (totalWidth - 400) / 2; 

        // Calculate y-coordinate to place higher on the panel
        int y = 50; // Adjusted y-coordinate to position higher
        
        endGameText.setBounds(x, y, 400, 250); // Centered horizontally and placed higher
        return endGameText;
    }
    **/
    private JTextField usernameTextbox() {
        JTextField endGameText = new JTextField("Game Over. Well done, " + username + "!");
        endGameText.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Calculate x-coordinate to start from the left edge
        int x = 0;

        // Calculate y-coordinate to position higher on the game panel
        int y = 50;
            
        // Calculate width to cover the total width
        int width = totalWidth;

        endGameText.setBounds(x, y, width, 64); // Covers total width from left to right
        endGameText.setEditable(false);
        endGameText.setBackground(new Color(245, 245, 220)); // Beige background color
        endGameText.setForeground(Color.BLACK); // Dark text color
        endGameText.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Add a border with halo effect (Yellow)
        Border border = BorderFactory.createLineBorder(new Color(255, 255, 0), 4); // Yellow color with thickness 4
        Border padding = BorderFactory.createEmptyBorder(8, 12, 8, 12); // Padding of 8 pixels on top, 12 pixels on left, 8 pixels on bottom, and 12 pixels on right
        endGameText.setBorder(BorderFactory.createCompoundBorder(border, padding)); // Combine border and padding
        
        return endGameText;
    }

    private JTextField statsTextbox() {
        JTextField endGameText = new JTextField("You made it to day: " + endGameRound + " | Gems collected: " + endGemScore + " | Coins collected: " + endCoinScore);
        endGameText.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Calculate x-coordinate to start from the left edge
        int x = 0;

        // Calculate y-coordinate to position higher on the game panel
        int y = 130;
        
        // Calculate width to cover the total width
        int width = totalWidth;

        endGameText.setBounds(x, y, width, 64); // Covers total width from left to right
        endGameText.setEditable(false);
        endGameText.setBackground(new Color(240, 240, 240)); // Light gray background color
        endGameText.setForeground(Color.BLACK); // Dark text color
        endGameText.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Add a border with halo effect (Green)
        Border border = BorderFactory.createLineBorder(new Color(0, 255, 0), 4); // Green color with thickness 4
        Border padding = BorderFactory.createEmptyBorder(8, 12, 8, 12); // Padding of 8 pixels on top, 12 pixels on left, 8 pixels on bottom, and 12 pixels on right
        endGameText.setBorder(BorderFactory.createCompoundBorder(border, padding)); // Combine border and padding
        
        return endGameText;
    }

    private JTextField scoreTextbox() {
        JTextField endGameText = new JTextField("Final score: " + endGameScore);
        
        endGameText.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Calculate x-coordinate to start from the left edge
        int x = 0;

        // Calculate y-coordinate to position higher on the game panel
        int y = 210;
        
        // Calculate width to cover the total width
        int width = totalWidth;

        endGameText.setBounds(x, y, width, 64); // Covers total width from left to right
        endGameText.setEditable(false);
        endGameText.setBackground(new Color(220, 220, 255)); // Pale blue background color
        endGameText.setForeground(Color.BLACK); // Dark text color
        endGameText.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Add a border with halo effect (Blue)
        Border border = BorderFactory.createLineBorder(new Color(0, 0, 255), 4); // Blue color with thickness 4
        Border padding = BorderFactory.createEmptyBorder(8, 12, 8, 12); // Padding of 8 pixels on top, 12 pixels on left, 8 pixels on bottom, and 12 pixels on right
        endGameText.setBorder(BorderFactory.createCompoundBorder(border, padding)); // Combine border and padding
        
        return endGameText;
    }


    private JButton createButton() {
        // Load the default and hover images
        ImageIcon defaultIcon = new ImageIcon("images/tiles/menubuttondefault.png");
        ImageIcon hoverIcon = new ImageIcon("images/tiles/menubuttonhovered.png");
        
        // Create the button with the default image
        JButton button = new JButton(defaultIcon);

        // Set the size of the button
        int buttonWidth = defaultIcon.getIconWidth();
        int buttonHeight = defaultIcon.getIconHeight();
        button.setSize(buttonWidth, buttonHeight);

        // Calculate the position of the button to be in the middle of the window
        int buttonX = (Main.WINDOW_WIDTH - buttonWidth) / 2;
        int buttonY = 300;
        // Set the location of the button
        button.setLocation(buttonX, buttonY);

        // Make the button transparent and remove its border
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        // Set hover icon for rollover effect
        button.setRolloverIcon(hoverIcon);

        // Add action listener
        button.addActionListener(e -> {
            Main.openMenuWindow();
        });

        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Get the width and height of the panel
        int width = getWidth();
        int height = getHeight();

        // Load the image
        try {
            BufferedImage image = ImageIO.read(new File("images/tiles/Dublin_1980.png"));

            // Draw the image
            if (image != null) {
                g.drawImage(image, 0, 0, width, height, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set color to transparent for drawing grid lines
        g.setColor(new Color(0, 0, 0, 0));

        // Draw the grid (drawn after the image so it's hidden behind)
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int x = col * tile;
                int y = row * tile;
                g.drawRect(x, y, tile, tile);
            }
        }
    }
}