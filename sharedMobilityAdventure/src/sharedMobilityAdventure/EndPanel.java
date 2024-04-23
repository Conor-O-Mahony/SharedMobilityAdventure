package sharedMobilityAdventure;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

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

    public EndPanel(String username, int gameRound, int gemScore, int coinScore, int gameScore) {
    	
        this.username = username;
        this.endGameRound = gameRound;
        this.endGemScore = gemScore;
        this.endCoinScore = coinScore;
        this.endGameScore = gameScore;
        
        setPreferredSize(new Dimension(totalWidth, totalHeight));
        setLayout(null);
        add(usernameTextbox());
        add(statsTextbox());
        add(scoreTextbox());        

        add(createButton()); // Add the button to return to the main panel
    }

    private JTextField usernameTextbox() {

        JTextField endGameText = new JTextField("Game Over. Well done, " + username + "!");    	
        endGameText.setHorizontalAlignment(SwingConstants.CENTER);
        endGameText.setBounds(100, 50, 824, 64);
//        setBounds(x, y, width, height)       
        endGameText.setEditable(false);
        endGameText.setBackground(Color.BLACK);
        endGameText.setForeground(Color.RED);
        endGameText.setFont(new Font("Arial", Font.BOLD, 16));
        endGameText.setBorder(null);
        return endGameText;
    }

    
    private JTextField statsTextbox() {

        JTextField endGameText = new JTextField("You made it to round: " + endGameRound + " | Gems collected: " + endGemScore + " | Coins collected: " + endCoinScore);
    	
        endGameText.setHorizontalAlignment(SwingConstants.CENTER);
        endGameText.setBounds(100, 150, 824, 64);       
        endGameText.setEditable(false);
        endGameText.setBackground(Color.BLACK);
        endGameText.setForeground(Color.RED);
        endGameText.setFont(new Font("Arial", Font.BOLD, 16));
        endGameText.setBorder(null);
        return endGameText;
    }

    
    private JTextField scoreTextbox() {

        JTextField endGameText = new JTextField("Final score: " + endGameScore);
    	
        endGameText.setHorizontalAlignment(SwingConstants.CENTER);
        endGameText.setBounds(100, 250, 824, 64);       
        endGameText.setEditable(false);
        endGameText.setBackground(Color.BLACK);
        endGameText.setForeground(Color.RED);
        endGameText.setFont(new Font("Arial", Font.BOLD, 16));
        endGameText.setBorder(null);
        return endGameText;
    }

  
    
    private JButton createButton() {
        int buttonX = 440; // x-coordinate of the button
        int buttonY = 400; // y-coordinate of the button
        int buttonWidth = 144; // Button width
        int buttonHeight = 64; // Button height

        JButton button = new JButton("Back to Main Panel");
        button.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);

        button.addActionListener(e -> {
            Main.openMenuWindow();  
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