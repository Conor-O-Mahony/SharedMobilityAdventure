package sharedMobilityAdventure;
import java.awt.Dimension;
import javax.swing.*;

public class Main {
	public static final int DEFAULT_BOARD_SIZE = 8; //i.e 10*10
    public static final int SIDEBAR_WIDTH = 256;
    public static final int GAME_HEIGHT = 720;
    public static final int GAME_WIDTH = 720;
    public static final int WINDOW_WIDTH = GAME_HEIGHT + SIDEBAR_WIDTH;
    public static final int WINDOW_HEIGHT = GAME_HEIGHT;
    public static final int TILE_SIZE = GAME_HEIGHT / DEFAULT_BOARD_SIZE;
    
    public static void main(String[] args) {
    	Game(); 
    }

  public static void Game() {
      JFrame menuFrame = new JFrame();
      menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      menuFrame.setResizable(false);
      menuFrame.setTitle("Shared-Mobility Adventure"); 
      
      MenuPanel menuPanel = new MenuPanel(menuFrame, null);
      menuPanel.setPreferredSize(new Dimension(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT)); //Dimension(totalWidth,totalHeight)
	  menuFrame.getContentPane().add(menuPanel);
		
      //menuFrame.add(menuPanel);
 
      menuFrame.pack();
      menuFrame.setLayout(null);
      menuFrame.setLocationRelativeTo(null);
      menuFrame.setVisible(true);    
  }
    
  public static void openGameWindow(MenuPanel menuPanel, JFrame menuFrame, String username) {
	  JFrame gameFrame = new JFrame();
      gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      gameFrame.setResizable(false);
      gameFrame.setTitle("Shared-Mobility Adventure");

      GamePanel gamePanel = new GamePanel(gameFrame, username);
      gamePanel.setPreferredSize(new Dimension(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT)); //Dimension(totalWidth,totalHeight)
      gameFrame.getContentPane().add(gamePanel);
      //gameFrame.add(gamePanel);

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
      endPanel.setPreferredSize(new Dimension(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT)); //Dimension(totalWidth,totalHeight)
      endFrame.getContentPane().add(endPanel);
      //endFrame.add(endPanel);
 
      endFrame.pack();
      endFrame.setLocationRelativeTo(null);
      endFrame.setVisible(true);
      
      gameFrame.dispose();
      
  }
  
  
  
  
  
  
  
  
  
  
}