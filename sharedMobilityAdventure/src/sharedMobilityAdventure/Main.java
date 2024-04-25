
package sharedMobilityAdventure;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
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
    public static Clip gemClip;
    public static Clip carbonCoinClip;
    public static Clip defaultGameAudioClip;
    public static HashMap<String, BufferedImage> imageCache;
    
    private static final String[] haloNames = {"haloB","haloY","haloG"};
    static final String[] pinNames = {"buspinB","buspinG","buspinY","trainpinB","trainpinG","trainpinY","bikepinB","bikepinG","bikepinY"};
    private static final String[] playerImages = {"down","up","left","right"};
    
    public static BufferedImage sidebarImage;
    public static BufferedImage tileImage;
    public static BufferedImage gemImage;
    public static BufferedImage popupImage;
    
    public static BufferedImage[] coinRotationImages = new BufferedImage[CarbonCoin.NUM_FRAMES];; // Array to store rotation images
    
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		loadImages(haloNames,"images/tiles/");
		loadImages(pinNames,"images/tiles/");
		loadImages(playerImages,"images/characters/");
		
		loadCoinRotationImages();
		
		try {        	
        	sidebarImage = ImageIO.read(new File("images/tiles/sidebar.png"));
        	tileImage = ImageIO.read(new File("images/tiles/intersection.png"));
        	gemImage = ImageIO.read(new File("images/gems/gem.png"));
        	popupImage = ImageIO.read(new File("images/info/info_popup.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		
    	Game(); 
    }
    
    public static void loadSoundClips() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        String gemSoundFilePath = "sounds/Jingle 026.wav";
        String carbonCoinSoundFilePath = "sounds/coin_1.wav";
        String defaultGameAudioFilePath = "sounds/ccs3-pixabay.wav"; 

        // Load gem sound clip
        try (AudioInputStream gemAudioInputStream = AudioSystem.getAudioInputStream(new File(gemSoundFilePath))) {
        	System.out.println("why");
            gemClip = AudioSystem.getClip();
            System.out.println("why not");
            gemClip.open(gemAudioInputStream);

            // Get the FloatControl for the volume
            FloatControl gemGainControl = (FloatControl) gemClip.getControl(FloatControl.Type.MASTER_GAIN);

            // Set the volume level for the gem clip
            float gemVolume = -14.0f; 
            gemGainControl.setValue(gemVolume);
        }

        // Load carbon coin sound clip
        try (AudioInputStream carbonCoinAudioInputStream = AudioSystem.getAudioInputStream(new File(carbonCoinSoundFilePath))) {
            carbonCoinClip = AudioSystem.getClip();
            carbonCoinClip.open(carbonCoinAudioInputStream);

            FloatControl coinGainControl = (FloatControl) carbonCoinClip.getControl(FloatControl.Type.MASTER_GAIN);

            float coinVolume = -4.0f; 
            coinGainControl.setValue(coinVolume);
        }

        // Load the default game audio clip
        try (AudioInputStream defaultGameAudioInputStream = AudioSystem.getAudioInputStream(new File(defaultGameAudioFilePath))) {
            defaultGameAudioClip = AudioSystem.getClip();
            defaultGameAudioClip.open(defaultGameAudioInputStream);

            FloatControl defaultGameAudioGainControl = (FloatControl) defaultGameAudioClip.getControl(FloatControl.Type.MASTER_GAIN);

            float defaultGameAudioVolume = -10.0f; 
            defaultGameAudioGainControl.setValue(defaultGameAudioVolume);
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
	    String[] authorsNames = {"Ryan Davey", "Adam Herdman", "Conor O'Mahony", "Riin Kaljurand", "Calvin van der Riet"};
	    EndPanel endPanel = new EndPanel(username, gameRound, gemScore, coinScore, gameScore, authorsNames);
	    endPanel.setPreferredSize(new Dimension(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT)); //Dimension(totalWidth,totalHeight)
	    
	    changePanels(endPanel);
	}
  
  public static int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
  
  private static void cacheImage(String imageName, BufferedImage image) {
	  imageCache.put(imageName, image);
  }
  
  static BufferedImage getImageFromCache(String imageName) {
  	try {
  		BufferedImage cachedImage = imageCache.get(imageName);
  		return cachedImage;
  	} catch (NullPointerException e) {
  		return null;
  	}
  }
  
  private static void loadImages(String[] imageNames, String directory) {  //directory = images/characters/
  	if (imageCache == null) {
  		imageCache = new HashMap<String, BufferedImage>();
  	}
  	
  	for (int i=0; i<imageNames.length; i++) {
  		if (getImageFromCache(imageNames[i]) == null) {
  			String source = String.format("%s%s.png", directory, imageNames[i]);
  			try {
  				BufferedImage currentimage = ImageIO.read(new File(source));
  				cacheImage(imageNames[i],currentimage);
  			} catch (IOException e) {
                  e.printStackTrace();
              }
  		}
  	}
  }
  
  private static void loadCoinRotationImages() {
	  for (int i = 0; i < CarbonCoin.NUM_FRAMES; i++) {
		  String fileName = String.format("coin_%02d", i + 1);
		  try {
			  coinRotationImages[i] = ImageIO.read(new File("images/coins/" + fileName + ".png"));
		  } catch (IOException e) {
	          e.printStackTrace();
	      }
      } 
  }
  
  
}
