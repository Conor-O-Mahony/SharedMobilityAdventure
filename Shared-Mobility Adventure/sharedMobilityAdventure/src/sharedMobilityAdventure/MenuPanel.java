package sharedMobilityAdventure;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuPanel extends JPanel {

    int tile = 16;
    int columns = 64;
    int rows = 36;
    int totalWidth = columns * tile;
    int totalHeight = rows * tile;
    
    JTextField userName; // Username field
    
    public MenuPanel(JFrame menuFrame) {
    	
        setPreferredSize(new Dimension(totalWidth, totalHeight));
        setLayout(null); // Set layout to null for absolute positioning
        // Add username field
        userName = new JTextField();
        userName.setBounds(440, 200, 144, 30);
        add(userName);
        
        add(createButton(menuFrame));
        
        System.out.println("hello");

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
                
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int x = col * tile;
                int y = row * tile;
                g.setColor(Color.BLACK);
                g.fillRect(x, y, tile, tile);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, tile, tile);
            }
        }
    }
}