package sharedMobilityAdventure;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuPanel extends JPanel {

    int tile = 16*2; // initial tile length / width (16 * 16 pixels)
    int columns = 32;
    int rows = 18;
    int totalWidth = columns * tile; // 640 pixels in length
    int totalHeight = rows * tile; // 440 pixels in height
    
    Player player; // Player object
    JTextField usernameField; //Username field
    
    public MenuPanel(JFrame menuFrame, Player player) {
    	this.player = player;
    	
        setPreferredSize(new Dimension(totalWidth, totalHeight));
        setLayout(null); // Set layout to null for absolute positioning
        // Add username field
        usernameField = new JTextField();
        usernameField.setBounds(100, 50, 200, 30);
        add(usernameField);
        
        add(createButton(menuFrame));

    }
    
    private JButton createButton(JFrame menuFrame) {
        int buttonX = 32*14; // X-coordinate of the button
        int buttonY = 32*8; // Y-coordinate of the button
        int buttonWidth = 32 * 4; // Button width
        int buttonHeight = 32 * 2; // Button height
        JButton button = new JButton("Start Game");
        button.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        
        button.addActionListener(e -> {
        	// Retrieve the username from the text field
        	String username = usernameField.getText();
            Main.openGameWindow(this, menuFrame, username); // Open the game window
        });

        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw player
        if (player != null) {
        	player.draw(g);
        	
        }
        
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int x = col * tile;
                int y = row * tile;
                g.setColor(Color.GRAY);
                g.fillRect(x, y, tile, tile);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, tile, tile);
            }
        }
    }
}