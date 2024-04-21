package sharedMobilityAdventure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MenuPanel extends JPanel {

	private static final long serialVersionUID = 1881465311138285638L;
	int tile = 16 * 2; // initial tile length / width (16 * 16 pixels)
    int columns = 32;
    int rows = 18;
    int totalWidth = columns * tile; // 640 pixels in length
    int totalHeight = rows * tile; // 440 pixels in height

    JTextField usernameField; // Username field
    BufferedImage backgroundImage; // Background image
    JButton startButton; // Start Game button
    JButton loadButton;

    public MenuPanel() {
        loadBackgroundImage();

        setPreferredSize(new Dimension(totalWidth, totalHeight));
        setLayout(null); // Set layout to null for absolute positioning

        // Add username field
        usernameField = new JTextField();
        usernameField.setBounds(475, 295, 200, 30);
        add(usernameField);

        // Create and add Start Game button
        startButton = new JButton();
        startButton.setBounds(125, 375, 320, 95);
        setButtonIcons(startButton, "images/tiles/playgamebuttonhovered.png", "images/tiles/playgamebuttondefault.png");
        startButton.addActionListener(e -> {
            // Retrieve the username from the text field
            String username = usernameField.getText();
            Main.openGameWindow(username); // Window will be opened with username
        });
        add(startButton);
        
        loadButton = new JButton();
        loadButton.setBounds(550, 375, 320, 95);
        setButtonIcons(loadButton, "images/tiles/loadgamebuttonhovered.png", "images/tiles/loadgamebuttondefault.png");
        loadButton.addActionListener(e -> {       	            
            SaveLoadPanel saveloadPanel = new SaveLoadPanel(this,"load");
            saveloadPanel.setPreferredSize(new Dimension(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT)); //Dimension(totalWidth,totalHeight)
            
            Main.changePanels(saveloadPanel);
        });
        add(loadButton);

        //mouse listener for button animation
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setButtonHoverIcon(startButton, "images/tiles/playgamebuttonhovered.png");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setButtonIcon(startButton, "images/tiles/playgamebuttondefault.png");
            }
        });
        
        loadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setButtonHoverIcon(loadButton, "images/tiles/loadgamebuttonhovered.png");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setButtonIcon(loadButton, "images/tiles/loadgamebuttondefault.png");
            }
        });        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //render background image if none present
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

    }

    private void loadBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(new File("images/tiles/titlebg.png"));
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
