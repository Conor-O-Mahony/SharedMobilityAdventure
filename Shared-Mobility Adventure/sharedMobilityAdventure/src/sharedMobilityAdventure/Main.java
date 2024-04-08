package sharedMobilityAdventure;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
    		Game(); 
  }

  public static void Game() {
      JFrame menuFrame = new JFrame();
      menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      menuFrame.setResizable(false);
      menuFrame.setTitle("Shared-Mobility Adventure"); 
      
      MenuPanel menuPanel = new MenuPanel(menuFrame);
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

      GamePanel gamePanel = new GamePanel(gameFrame, username);
      gameFrame.add(gamePanel);

      gameFrame.pack();
      gameFrame.setLocationRelativeTo(null);
      gameFrame.setVisible(true);
    	
      menuFrame.dispose();
      
  }

  public static void openEndWindow(JFrame gameFrame, String username) {
      JFrame endFrame = new JFrame();
      endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      endFrame.setResizable(false);
      endFrame.setTitle("Shared-Mobility Adventure"); 
      
      EndPanel endPanel = new EndPanel(endFrame);
      endFrame.add(endPanel);
 
      endFrame.pack();
      endFrame.setLocationRelativeTo(null);
      endFrame.setVisible(true);
      
      gameFrame.dispose();
      
  }
  
  
  
  
  
  
  
  
}