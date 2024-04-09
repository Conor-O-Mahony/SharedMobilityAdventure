package sharedMobilityAdventure;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class PopUp {
    
	public int popUpX; // x-coordinate
	public int popUpY; // y-coordinate
    final int width; // width of popUp
    final int height; // height of popUp
    BufferedImage image; // image of popUp
	
    // List of dialogue strings
    private static String[] dialogueOptions = {
        "Random Fact Number 1",
        "Random Fact Number 2",
        "Random Fact Number 3",
        "Random Fact Number 4"
    };

    public PopUp () {
        width = 16;
        height = 16;
        try {
            image = ImageIO.read(new File("images/tiles/water.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dropPopUp();
    }

    public void dropPopUp() {
            
        Random random = new Random();
        int randomNumberX = random.nextInt(16-1);
        int randomNumberY = random.nextInt(8-1);
    	
        int oddNumberX = randomNumberX * 2 + 1;
        int oddNumberY = randomNumberY * 2 + 1;
        
        popUpX = 8*4*oddNumberX;
        popUpY = 8*4*oddNumberY;
             
    }
   
    public void draw(Graphics g) {
        int adjustedX = popUpX - (width / 2);
        int adjustedY = popUpY - (height / 2);

        g.drawImage(image, adjustedX, adjustedY, width, height, null);
    }
         	
//    	// Create a JFrame (main window)
//        JFrame frame = new JFrame("Random Popup Example");
//        frame.setSize(400, 300); // Set the size of the frame
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application when the frame is closed
//        frame.setLayout(null); // We will use absolute positioning
//        
//        // Create a button to trigger the random popup
//        JButton popupButton = new JButton("Show Random Popup");
//        popupButton.setBounds(100, 100, 200, 30); // Set button position and size
//        frame.add(popupButton); // Add the button to the frame
//        
//        // Add an action listener to the button to show the random popup
//        popupButton.addActionListener(e -> {
//            // Generate a random index to pick a random dialogue from the list
//            int randomIndex = new Random().nextInt(dialogueOptions.length);
//            // Show a JOptionPane with the randomly picked dialogue
//            JOptionPane.showMessageDialog(frame,
//                    dialogueOptions[randomIndex],
//                    "Random Popup",
//                    JOptionPane.INFORMATION_MESSAGE);
//        });
//        
//        // Make the frame visible
//        frame.setVisible(true);
//    }

}
