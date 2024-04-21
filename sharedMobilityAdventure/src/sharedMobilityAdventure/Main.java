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
    public static JFrame Frame;
    
    public static void main(String[] args) {
    	Game(); 
    }
    
  public static void changePanels(JPanel panel) {
	  Frame.setContentPane(panel);
      Frame.revalidate();
  }

  public static void Game() {
	  Frame = new JFrame();
	  Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  Frame.setResizable(false);
	  Frame.setTitle("Shared-Mobility Adventure"); 
      
      MenuPanel menuPanel = new MenuPanel();
      menuPanel.setPreferredSize(new Dimension(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT)); //Dimension(totalWidth,totalHeight)      
      Frame.getContentPane().add(menuPanel);
 
      Frame.pack();
      Frame.setLayout(null);
      Frame.setLocationRelativeTo(null);
      Frame.setVisible(true);    
  }
    
  public static void openGameWindow(String username) {
      GamePanel gamePanel = new GamePanel(username);
      gamePanel.setPreferredSize(new Dimension(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT)); //Dimension(totalWidth,totalHeight)
      
      changePanels(gamePanel);
  }

  public static void openEndWindow(String username, int gameRound, int gemScore, int coinScore, int gameScore) {      
      EndPanel endPanel = new EndPanel(username, gameRound, gemScore, coinScore, gameScore);
      endPanel.setPreferredSize(new Dimension(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT)); //Dimension(totalWidth,totalHeight)
      
      changePanels(endPanel);
  }
  
  
  
  
  
  
  
  
  
  
}
