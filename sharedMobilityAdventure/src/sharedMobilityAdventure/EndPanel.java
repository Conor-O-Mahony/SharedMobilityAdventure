package sharedMobilityAdventure;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class EndPanel extends JPanel {

	private static final long serialVersionUID = -8788565932041161917L;
	int tile = 16;
    int columns = 64;
    int rows = 36;
    int totalWidth = columns * tile;
    int totalHeight = rows * tile;

    public EndPanel(JFrame endFrame) {
        setPreferredSize(new Dimension(totalWidth, totalHeight));
        setLayout(null);
        add(createEndGameTextBox()); // Add the "End Game" textbox
        add(createButton(endFrame)); // Add the button to return to the main panel
    }

    private JTextField createEndGameTextBox() {
        JTextField endGameText = new JTextField("Game Over");
        endGameText.setHorizontalAlignment(SwingConstants.CENTER);
        endGameText.setBounds(440, 100, 144, 64);
        endGameText.setEditable(false);
        endGameText.setBackground(Color.BLACK);
        endGameText.setForeground(Color.RED);
        endGameText.setFont(new Font("Arial", Font.BOLD, 16));
        endGameText.setBorder(null);
        return endGameText;
    }

    private JButton createButton(JFrame menuFrame) {
        int buttonX = 440; // x-coordinate of the button
        int buttonY = 300; // y-coordinate of the button
        int buttonWidth = 16 * 9; // Button width
        int buttonHeight = 16 * 4; // Button height

        JButton button = new JButton("Back to Main Panel");
        Rectangle bounds = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
        button.setBounds(bounds);

        button.addActionListener(e -> {
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this); // Get the current frame
            currentFrame.dispose(); // Dispose the current EndPanel frame
            
            Main.Game();


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
