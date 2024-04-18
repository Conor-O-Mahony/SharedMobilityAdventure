package sharedMobilityAdventure;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.util.Arrays;

public class GamePanel extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
	private Player player;
	private PopUp popup;
	private Board board;

  private String username; // Store the username
  private JFrame gameFrame; // Store the game frame  
        
	private transient BufferedImage[] roadtileArray;
	private transient BufferedImage[] haloArray;
	private transient BufferedImage sidebarImage;
    
  private CarbonCoin[] carbonCoins;
  private int numCarbonCoins = 3;
  
  private Gem[] gems; //Array to store Gems
  private int numGems = 3; // Number of gems to drop
    
	int playerTime = 1000;
	int gemScore = 0;
	int coinScore = 100;
	int gameScore = 0;
  public boolean gemScoreUpdate = true;
  public boolean coinScoreUpdate = true;
  
    public GamePanel(JFrame gameFrame, String username){
		
        this.gameFrame = gameFrame; // Store the game frame
        this.username = username; // Store the username
        
        // enable doubleBuffering to reduce flickering
        setDoubleBuffered(true);
			
        initGame();

        this.setFocusable(true);
        requestFocus();
        addKeyListener(this);   
        setLayout(null);  //ELSE THE BUTTON WON'T PLACE CORRECTLY
        
        add(createButton(gameFrame, Main.GAME_WIDTH+60, Main.GAME_HEIGHT-80, "Save Game"));
    }
	
    public void initGame() {
        board = new Board(Main.DEFAULT_BOARD_SIZE, Main.DEFAULT_BOARD_SIZE);
        player = new Player(this);

        // Get player's initial coordinates
        int playerX = player.getPlayerX();
        int playerY = player.getPlayerY();

        gems = new Gem[numGems]; // Initialize array
        for (int i = 0; i < numGems; i++) {
            gems[i] = new Gem("Diamond", this, playerX, playerY); // Pass player's coordinates to the Gem constructor
        }

        carbonCoins = new CarbonCoin[numCarbonCoins];
        for (int i = 0; i < numCarbonCoins; i++) {
            carbonCoins[i] = new CarbonCoin("Carbon Credit", this, playerX, playerY); // Pass the GamePanel instance to the CarbonCoin constructor
        }

        popup = new PopUp();
        loadImages();
    }

    
    void loadImages() {
    	String[] roadTileNames = {"intersection","bikepin","buspin","trainpin"}; //,"roadBus","roadTrain","roadBike","roadBusTrain","roadBusBike","roadTrainBike"
		roadtileArray = new BufferedImage[roadTileNames.length];
		loadTiles(roadTileNames,roadtileArray);
		
		String[] haloNames = {"halo","halo2"};
		haloArray = new BufferedImage[haloNames.length];
		loadTiles(haloNames,haloArray);
		
		popup.loadImage();
		player.loadImage();
		
		// load images for gem
		for (int i = 0; i < gems.length; i++) {
			gems[i].loadImage();
		}
		
		for (int i=0; i<carbonCoins.length; i++) {
			carbonCoins[i].loadImage();
		  } 
    }

    private void loadTiles(String[] imageNames, BufferedImage[] imageArray) {
		for (int i=0; i<imageNames.length; i++) {
			String source = String.format("images/tiles/%s.png", imageNames[i]);
		
			try {
				imageArray[i] = ImageIO.read(new File(source));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    
    private JButton createButton(JFrame frame, int buttonX, int buttonY, String text) {
        JButton button = new JButton(text);
        int buttonWidth = 16*9;
        int buttonHeight = 16*4;
        Rectangle bounds = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
        button.setBounds(bounds);

        button.addActionListener(e -> {       	
        	//Delete the current frame
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this); // Get the current frame
            currentFrame.dispose(); // Dispose the current EndPanel frame
            
            //Load the save/load frame, pass in the GamePanel as argument
            JFrame saveloadFrame = new JFrame();
            saveloadFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            saveloadFrame.setResizable(false);
            saveloadFrame.setTitle("Save/Load Game"); 
            
            SaveLoadPanel saveloadPanel = new SaveLoadPanel(this,saveloadFrame,"save");
            saveloadPanel.setPreferredSize(new Dimension(Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT)); //Dimension(totalWidth,totalHeight)
            saveloadFrame.getContentPane().add(saveloadPanel);
            //saveloadFrame.add(saveloadPanel);
       
            saveloadFrame.pack();
            saveloadFrame.setLocationRelativeTo(null);
            saveloadFrame.setVisible(true);
            
        });  
        return button;     
    }
	
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for (int row = 0; row < Main.DEFAULT_BOARD_SIZE; row++) {
			for (int col = 0; col < Main.DEFAULT_BOARD_SIZE; col++) {		
				
				TransportTypes[] routeTypes = board.tiles[row][col].getRouteTypes();
				
				boolean bus=Arrays.stream(routeTypes).anyMatch(TransportTypes.BUS::equals);
				boolean train=Arrays.stream(routeTypes).anyMatch(TransportTypes.TRAIN::equals);
				boolean bike=Arrays.stream(routeTypes).anyMatch(TransportTypes.BICYCLE::equals);
				
				g.drawImage(roadtileArray[0], col*Main.TILE_SIZE, row*Main.TILE_SIZE, Main.TILE_SIZE, Main.TILE_SIZE, null);
				
//	            g.setColor(Color.BLACK);
//	            g.drawRect(col * tile, row * tile, tile, tile);
				
				if (bike==true) {
					g.drawImage(roadtileArray[1], col*Main.TILE_SIZE, row*Main.TILE_SIZE, Main.TILE_SIZE*2/3, Main.TILE_SIZE*2/3, null);
				}
				if (bus==true) {
					int extra = (int) Math.round(0.3*Main.TILE_SIZE);
					g.drawImage(roadtileArray[2], col*Main.TILE_SIZE + extra, row*Main.TILE_SIZE, Main.TILE_SIZE*2/3, Main.TILE_SIZE*2/3, null);
				}
				if (train==true) {
					int extra = (int) Math.round(0.3*Main.TILE_SIZE);
					g.drawImage(roadtileArray[3], col*Main.TILE_SIZE + extra, row*Main.TILE_SIZE - extra, Main.TILE_SIZE*2/3, Main.TILE_SIZE*2/3, null);
				}
			}
        }
        
        player.draw(g);

        for (int i = 0; i < gems.length; i++) {
            Gem gem = gems[i];
            if (gem.getVisibility()) {
                gem.draw(g);
            }
        }
        
        for (int i = 0; i < carbonCoins.length; i++) {
            CarbonCoin coin = carbonCoins[i];
            if (coin.getVisibility()) {
                coin.draw(g);
            }
        }              
        //popup.draw(g);
        
        try {        	
        	sidebarImage = ImageIO.read(new File("images/tiles/sidebar.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }  
               
        //g.drawImage(sidebarImage, Main.GAME_WIDTH, 0, Main.SIDEBAR_WIDTH, Main.GAME_WIDTH, null);
        g.drawImage(sidebarImage,Main.GAME_WIDTH,0,sidebarImage.getWidth(),Main.WINDOW_HEIGHT, null);
        
        paintHalos(g);
        
        // Player
        g.setColor(Color.BLACK); // Set color to black
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString(username, Main.GAME_WIDTH+90, 70);
        
        // Time
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString("" + playerTime, Main.GAME_WIDTH+90, 175);
        
        // Gems
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString("" + checkGemScore(), Main.GAME_WIDTH+40, 270);
        gemScoreUpdate = true;
                     
        // Carbon Coins
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString("" + checkCoinScore(), Main.GAME_WIDTH+160, 270);
        coinScoreUpdate = true;
        
        // Score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString("" + gameScore, Main.GAME_WIDTH+90, 375);
       
    }
    
    public void paintHalos(Graphics g) {
    	int player_x = player.getPlayerXTile();
    	int player_y = player.getPlayerYTile();
    	Tile currentTile = board.tiles[player_y][player_x];
    	Route[] tileRoutes = currentTile.getRoutes();
    	for (int i=0; i<tileRoutes.length; i++) {
    		if (tileRoutes[i]!=null) {
    			Tile[] tilesInRoute = tileRoutes[i].getTiles();
    			for (int j=0; j<tilesInRoute.length; j++) {
    				int tile_x = tilesInRoute[j].getX();
    				int tile_y = tilesInRoute[j].getY();
    				
    				g.drawImage(haloArray[i], tile_x*Main.TILE_SIZE, tile_y*Main.TILE_SIZE, Main.TILE_SIZE, Main.TILE_SIZE, null);
    			}
    			TransportTypes type = tileRoutes[i].getTransportType();
    			String typeString = type.toString();
    			g.setColor(Color.BLACK); // Set color to black
    	        g.setFont(new Font("Tahoma", Font.BOLD, 16));
    	        g.drawString("Press "+(i+1)+" to take "+typeString, Main.GAME_WIDTH+35, 485 + i*25);
    		}
    	}
    }
    
    public void restartGame() {
        initGame();
        repaint();
    } 
 
    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed
    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e); // Call the keyPressed method in the Player class
        repaint(); // Redraw the panel after key press
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not needed
    }
    
    public static void endGame(JFrame gameFrame, String username) {
        Main.openEndWindow(gameFrame, username);
    }

	public boolean takeTransportRoute(int mode, int player_x, int player_y) {
		//TO DO: IMPLEMENT METHODS FOR SUBTRACTING TIME, CARBON COINS, ETC.
		int numberOfRoutes = board.tiles[player_y][player_x].getNumberOfRoutes();
		if (numberOfRoutes>=mode) {
			Route routeToTake = board.tiles[player_y][player_x].getRoutes()[mode-1];
			if (routeToTake.getFinalRow()==player_y && routeToTake.getFinalCol()==player_x) {
				//Player is at the end of the route, move them to the start
				int new_player_x = routeToTake.getStartCol();
				int new_player_y = routeToTake.getStartRow();
				player.setPlayerX(new_player_x);
				player.setPlayerY(new_player_y);
			} else {
				int new_player_x = routeToTake.getFinalCol();
				int new_player_y = routeToTake.getFinalRow();
				player.setPlayerX(new_player_x);
				player.setPlayerY(new_player_y);
			}
			player.updateTravel(routeToTake);
		} else {
			return false;
		}
		return true;
	}
 
	public int checkGemScore() {
	    for (int i = 0; i < gems.length; i++) {
	        Gem gem = gems[i];
	        if (player.getPlayerX() == gem.collectabelX && player.getPlayerY() == gem.collectabelY && gem.getVisibility()) {
	            gemScore++; // Increase the score
	            gem.setVisibility(false);
	            calculateGameScore();
	            try {
	                gem.playSound();
	            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
	                // Handle the exceptions here, such as logging the error or displaying an error message
	                ((Throwable) e).printStackTrace();
	            }
	        }
	    }

	    if (allGemsCollected() && allCoinsCollected()) {
	        restartGame(); // Restart the game when all gems and coins are collected
	    }

	    return gemScore;
	}

	public int checkCoinScore() {
	    for (int i = 0; i < carbonCoins.length; i++) {
	        CarbonCoin coin = carbonCoins[i];
	        if (player.getPlayerX() == coin.collectabelX && player.getPlayerY() == coin.collectabelY && coin.getVisibility()) {
	            coinScore++; // Increase the score
	            coin.setVisibility(false);
	            calculateGameScore();
	            try {
	                coin.playSound();
	            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
	                // Handle the exceptions here, such as logging the error or displaying an error message
	                ((Throwable) e).printStackTrace();
	            }
	        }
	    }

	    if (allGemsCollected() && allCoinsCollected()) {
	        restartGame(); // Restart the game when all gems and coins are collected
	    }

	    return coinScore;
	}


	private boolean allGemsCollected() {
	    for (Gem gem : gems) {
	        if (gem.getVisibility()) {
	            return false; // If any gem is still visible, return false
	        }
	    }
	    return true; // All gems are collected
	}

	private boolean allCoinsCollected() {
	    for (CarbonCoin coin : carbonCoins) {
	        if (coin.getVisibility()) {
	            return false; // If any coin is still visible, return false
	        }
	    }
	    return true; // All coins are collected
	}

    
    public void calculateGameScore() {        
    	gameScore += (int) ((0.50 * playerTime) + (0.50 * checkCoinScore()));
    }
       
    public void checkPopUp() {
        if (player.getPlayerX() == popup.popUpX && player.getPlayerY() == popup.popUpY) {
            System.out.println("Pop Up");   	
    	}
    } 
       
    public void timer(int movement) {  	
    	if ((playerTime - movement) <= 0) {   		
    		Main.openEndWindow(gameFrame, username);
    	}
    	else {
    		playerTime -= movement;
    	}
    }
 
}