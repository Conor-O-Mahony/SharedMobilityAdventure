package sharedMobilityAdventure;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class Main {
	public static final int DEFAULT_BOARD_SIZE = 8; //i.e 10*10
    public static final int SIDEBAR_WIDTH = 256;
    public static final int GAME_HEIGHT = 720;
    public static final int GAME_WIDTH = 720;
    public static final int WINDOW_WIDTH = GAME_HEIGHT + SIDEBAR_WIDTH;
    public static final int WINDOW_HEIGHT = GAME_HEIGHT;
    public static final int TILE_SIZE = GAME_HEIGHT / DEFAULT_BOARD_SIZE;
    public static final int MIN_ROUTE_SIZE = 5;
    public static final int MAX_ROUTE_SIZE = 10;
    public static JFrame Frame;
    public static Clip clip;
    
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    	Game(); 
    }
    
  public static void loadSoundClips() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
	  String soundFilePath = "sounds/gem.wav";

	    try (// If the clip is not in the cache, load it and put it in the cache
	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFilePath).getAbsoluteFile())) {
		clip = AudioSystem.getClip();
		  // Open audio input stream and clip for playback
		  clip.open(audioInputStream);
	}
  }
    
  public static void changePanels(JPanel newPanel) {
	  //Discard old panel
	  Frame.getContentPane().removeAll();
	  //System.gc();
	  
	  //Add new panel
	  Frame.setContentPane(newPanel);
      Frame.revalidate();
  }

  public static void Game() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
	  Frame = new JFrame();
	  Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  Frame.setResizable(false);
	  Frame.setTitle("Shared-Mobility Adventure"); 
      
	  MenuPanel menuPanel = new MenuPanel();
      menuPanel.setPreferredSize(new Dimension(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT));
      Frame.setContentPane(menuPanel);
 
      Frame.pack();
      Frame.setLayout(null);
      Frame.setLocationRelativeTo(null);
      Frame.setVisible(true);    
      
      loadSoundClips();
  }
  
  public static void openSaveLoadWindow(JPanel gamePanel, String mode) {
	  SaveLoadPanel saveloadPanel = new SaveLoadPanel(gamePanel,mode);
      saveloadPanel.setPreferredSize(new Dimension(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));

      changePanels(saveloadPanel);
  }
  
  public static void openMenuWindow() {
	  MenuPanel menuPanel = new MenuPanel();
      menuPanel.setPreferredSize(new Dimension(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT)); 
      
      changePanels(menuPanel);
  }
    
  public static void openGameWindow(String username) {
      GamePanel gamePanel = new GamePanel(username);
      gamePanel.setPreferredSize(new Dimension(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT));
      
      changePanels(gamePanel);
  }

  public static void openEndWindow(String username, int gameRound, int gemScore, int coinScore, int gameScore) {      
      EndPanel endPanel = new EndPanel(username, gameRound, gemScore, coinScore, gameScore);
      endPanel.setPreferredSize(new Dimension(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT)); //Dimension(totalWidth,totalHeight)
      
      changePanels(endPanel);
  }
  
  public static int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
  
  
  
  
  
  
  
  
  
  
}
