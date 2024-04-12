package sharedMobilityAdventure;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuPanel extends JPanel {

    private int DEFAULT_BOARD_SIZE_X = 8;
    private int DEFAULT_BOARD_SIZE_Y = 12;
    private int SIDEBAR_WIDTH = 256;
    private int GAME_WIDTH = 720;
    private int GAME_HEIGHT = 720;
    private int WINDOW_WIDTH = GAME_HEIGHT + SIDEBAR_WIDTH; // 976
    private int WINDOW_HEIGHT = GAME_HEIGHT;
    private int TILE_SIZE = GAME_HEIGHT / DEFAULT_BOARD_SIZE_X;
    
    JTextField userName; // Username field
    
    public MenuPanel(JFrame menuFrame) {
    	
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setLayout(null); // Set layout to null for absolute positioning
        // Add username field
        userName = new JTextField();
        userName.setBounds(440, 200, 144, 30);
        add(userName);
        
        add(createButton(menuFrame));

    }
    
    private JButton createButton(JFrame menuFrame) {
        int buttonX = 440; // x-coordinate of the button
        int buttonY = 300; // y-coordinate of the button
        int buttonWidth = 16 * 9; // Button width
        int buttonHeight = 16 * 4; // Button height

        JButton button = new JButton("Play Game");

        // Create a Rectangle object representing the bounds of the button
        Rectangle bounds = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);

        button.setBounds(bounds);

        button.addActionListener(e -> {
            // Retrieve the user's name from the text field
            String username = userName.getText();
            Main.openGameWindow(this, menuFrame, username);
        });

        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
                
        for (int row = 0; row < DEFAULT_BOARD_SIZE_X; row++) {
            for (int col = 0; col < DEFAULT_BOARD_SIZE_Y ; col++) {
                int x = col * TILE_SIZE;
                int y = row * TILE_SIZE;
                g.setColor(Color.BLACK);
                g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, TILE_SIZE, TILE_SIZE);
            }
        }
    }
}