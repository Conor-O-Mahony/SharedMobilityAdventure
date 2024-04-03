package sharedMobilityAdventure;
import javax.swing.*;
import java.util.Random;

public class PopUp {
    // List of dialogue strings
    private static String[] dialogueOptions = {
        "Random Fact Number 1",
        "Random Fact Number 2",
        "Random Fact Number 3",
        "Random Fact Number 4"
    };

    public static void main(String[] args) {
        // Create a JFrame (main window)
        JFrame frame = new JFrame("Random Popup Example");
        frame.setSize(400, 300); // Set the size of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application when the frame is closed
        frame.setLayout(null); // We will use absolute positioning
        
        // Create a button to trigger the random popup
        JButton popupButton = new JButton("Show Random Popup");
        popupButton.setBounds(100, 100, 200, 30); // Set button position and size
        frame.add(popupButton); // Add the button to the frame
        
        // Add an action listener to the button to show the random popup
        popupButton.addActionListener(e -> {
            // Generate a random index to pick a random dialogue from the list
            int randomIndex = new Random().nextInt(dialogueOptions.length);
            // Show a JOptionPane with the randomly picked dialogue
            JOptionPane.showMessageDialog(frame,
                    dialogueOptions[randomIndex],
                    "Random Popup",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        
        // Make the frame visible
        frame.setVisible(true);
    }
}
