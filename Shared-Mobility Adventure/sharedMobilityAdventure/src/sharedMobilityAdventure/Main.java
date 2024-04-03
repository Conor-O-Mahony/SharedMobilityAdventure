package sharedMobilityAdventure;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

      JFrame menuFrame = new JFrame();
      menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      menuFrame.setResizable(false);
      menuFrame.setTitle("Shared-Mobility Adventure"); 
      // Instantiate player object
      Player player = new Player();
      
      MenuPanel menuPanel = new MenuPanel(menuFrame, player);
      menuFrame.add(menuPanel);
 
      menuFrame.pack();
      menuFrame.setLocationRelativeTo(null);
      menuFrame.setVisible(true); 
      
  }

  public static void openGameWindow(MenuPanel menuPanel, JFrame menuFrame, String username) {  	
  	
	  JFrame gameFrame = new JFrame();
      gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      gameFrame.setResizable(false);
      gameFrame.setTitle("Shared-Mobility Adventure");

      GamePanel gamePanel = new GamePanel(username); //passing username
      gameFrame.add(gamePanel);

      gameFrame.pack();
      gameFrame.setLocationRelativeTo(null);
      gameFrame.setVisible(true);
    	
      menuFrame.dispose();
      
  }
  
  public static void openBonusWindow(MenuPanel menuPanel, JFrame menuFrame, String username) {
	  JFrame gameFrame = new JFrame();
      gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      gameFrame.setResizable(false);
      gameFrame.setTitle("Shared-Mobility Adventure");

      BonusPanel gamePanel = new BonusPanel(username);
      gameFrame.add(gamePanel);

      gameFrame.pack();
      gameFrame.setLocationRelativeTo(null);
      gameFrame.setVisible(true);
    	
      menuFrame.dispose();
  }
}