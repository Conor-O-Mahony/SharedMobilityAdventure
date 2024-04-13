package sharedMobilityAdventure;
import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

	private static final long serialVersionUID = 329006769552291165L;
    
    JTextField userName; // Username field
    
    public MenuPanel(JFrame menuFrame) {
    	
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setLayout(null); // Set layout to null for absolute positioning
        // Add username field
        userName = new JTextField();
        userName.setBounds(440, 200, 144, 30);
        add(userName);
        
        add(createButton(menuFrame));
        add(createLoadButton(440,400,"Load game"));

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
    
    private JButton createLoadButton(int buttonX, int buttonY, String text) {
        JButton button = new JButton(text);
        int buttonWidth = 16*9;
        int buttonHeight = 16*4;
        Rectangle bounds = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
        button.setBounds(bounds);

        button.addActionListener(e -> {       	
        	//Delete the current frame
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this); // Get the current frame
            currentFrame.dispose(); // Dispose the current EndPanel frame
            
            //Load the save/load frame, pass in the GamePanel as argument
            JFrame saveloadFrame = new JFrame();
            saveloadFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            saveloadFrame.setResizable(false);
            saveloadFrame.setTitle("Save/Load Game"); 
            
            SaveLoadPanel saveloadPanel = new SaveLoadPanel(this,saveloadFrame,"load");
            saveloadPanel.setPreferredSize(new Dimension(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT)); //Dimension(totalWidth,totalHeight)
            saveloadFrame.getContentPane().add(saveloadPanel);
            //saveloadFrame.add(saveloadPanel);
       
            saveloadFrame.pack();
            saveloadFrame.setLocationRelativeTo(null);
            saveloadFrame.setVisible(true);
            
        });  
        return button;     
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
                
        for (int row = 0; row < Main.WINDOW_HEIGHT/Main.DEFAULT_BOARD_SIZE; row++) {
            for (int col = 0; col < Main.WINDOW_WIDTH/Main.DEFAULT_BOARD_SIZE ; col++) {
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