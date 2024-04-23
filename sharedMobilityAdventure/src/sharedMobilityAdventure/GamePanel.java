
package sharedMobilityAdventure;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.awt.FontMetrics;

public class GamePanel extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
	private Player player;
	private Board board;
	// Cache for storing loaded images
	public static Map<String, BufferedImage> imageCache = new HashMap<String, BufferedImage>();
    private Map<String, Color> colorMap = new HashMap<String, Color>();
    private Map<String, String> pinMap = new HashMap<String, String>();


  String username; // Store the username
        
	private transient BufferedImage sidebarImage;
    
  private CarbonCoin[] carbonCoins;
  private int numCarbonCoins = 3;
  
  private Gem[] gems; // Array to store Gems
  private int numGems = 3; // Number of gems to drop

  private PopUp[] popups; // Array to store Popups
  private int numPopups = 3; // Number of popups to drop
  
	int playerTime = 1000;
	int gemScore = 0;
	int coinScore = 100;
	int gameScore = 0;
	int gameRound = 0;
  
  public boolean gemScoreUpdate = true;
  public boolean coinScoreUpdate = true;
  
  public JButton button;
  
  String[] haloNames = {"haloB","haloY","haloG"};
  String[] roadTileNames = {"intersection"};
  static String[] pinNames = {"buspinB","buspinG","buspinY","trainpinB","trainpinG","trainpinY","bikepinB","bikepinG","bikepinY"};
  
  
    public GamePanel(String username){
        this.username = username; // Store the username
	
        addColors();
        addHaloNames();
        
        initGame();
        loadImages();

        this.setFocusable(true);
        focus();
        
        addKeyListener(this);   
        setLayout(null);  //ELSE THE BUTTON WON'T PLACE CORRECTLY
        
        addButton();
    }
    
    private void addColors()
    {
        colorMap.put("bikepinB", Color.BLUE);
        colorMap.put("buspinB", Color.BLUE);
        colorMap.put("trainpinB", Color.BLUE);
        colorMap.put("bikepinY", Color.YELLOW);
        colorMap.put("buspinY", Color.YELLOW);
        colorMap.put("trainpinY", Color.YELLOW);
        colorMap.put("bikepinG", Color.GREEN);
        colorMap.put("buspinG", Color.GREEN);
        colorMap.put("trainpinG", Color.GREEN);
    }
    
    private void addHaloNames()
    {
    	pinMap.put("bikepinB", "haloB");
    	pinMap.put("buspinB", "haloB");
    	pinMap.put("trainpinB", "haloB");
    	pinMap.put("bikepinY", "haloY");
    	pinMap.put("buspinY", "haloY");
    	pinMap.put("trainpinY", "haloY");
    	pinMap.put("bikepinG", "haloG");
    	pinMap.put("buspinG", "haloG");
    	pinMap.put("trainpinG", "haloG");
    }
    
    public void focus() {
    	requestFocus();
    }
	
    public void initGame() {
    	gameRound += 1;
    	board = new Board(Main.DEFAULT_BOARD_SIZE, Main.DEFAULT_BOARD_SIZE);
        player = new Player(this);

        // Get player's initial coordinates
        int playerX = player.getPlayerX();
        int playerY = player.getPlayerY();

        gems = new Gem[numGems]; // Initialize array
        for (int i = 0; i < numGems; i++) {
            gems[i] = new Gem("Diamond", board, this, playerX, playerY); // Pass player's coordinates to the Gem constructor
        }

        carbonCoins = new CarbonCoin[numCarbonCoins];
        for (int i = 0; i < numCarbonCoins; i++) {
            carbonCoins[i] = new CarbonCoin("Carbon Credit", board, this, playerX, playerY); // Pass the GamePanel instance to the CarbonCoin constructor
        }
        startRotation();
                 
        popups = new PopUp[numPopups];
        for (int i = 0; i < numPopups; i++) {
            popups[i] = new PopUp();
        }
        
        JOptionPane.showMessageDialog(null, "Round: " + gameRound + ". Click OK!");
    }
    
    void startRotation() {
    	for (int i = 0; i < numCarbonCoins; i++) {
            carbonCoins[i].startRotation();
        }
    }

    void addButton() {
    	add(createButton(Main.Frame, Main.GAME_WIDTH+15, Main.GAME_HEIGHT-165, "Save Game"));
    }
    
    void loadImages() {

		loadTiles(roadTileNames);
		loadTiles(haloNames);
		loadTiles(pinNames);

		player.loadImages();
		player.setImage("down");
		
		// load images for gem
		for (int i = 0; i < gems.length; i++) {
			gems[i].loadImage();
		}
		
		for (int i=0; i<carbonCoins.length; i++) {
			carbonCoins[i].loadImage();
		  } 

		for (int i=0; i<popups.length; i++) {
			popups[i].loadImage();
		  }
		
		try {        	
        	sidebarImage = ImageIO.read(new File("images/tiles/sidebar.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }  
    }
    
    private void loadTiles(String[] imageNames) {
        for (int i = 0; i < imageNames.length; i++) {
            String imageName = imageNames[i];
            BufferedImage image = null;
            image = getImageFromCache(imageName);
            if (image == null) {
                String source = String.format("images/tiles/%s.png", imageName);
                try {
                    image = ImageIO.read(new File(source));
                    cacheImage(imageName, image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private JButton createButton(JFrame frame, int buttonX, int buttonY, String text) {
        button = new JButton(text);
        setButtonIcon(button, "images/tiles/savegamebuttondefault.png");
        setButtonHoverIcon(button, "images/tiles/savegamebuttonhovered.png");
        int buttonWidth = 226;
        int buttonHeight = 65;
        Rectangle bounds = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
        button.setBounds(bounds);

        addActionListener();
        
        return button;    
    }
    
    void addActionListener() {
    	button.addActionListener(e -> {     
    		CarbonCoin.stopRotation();
            Main.openSaveLoadWindow(this,"save");
        }); 
    }
    
    private void setButtonIcon(JButton button, String imagePath) {
        button.setBorderPainted(false); // Remove button border
        button.setFocusPainted(false); // Remove focus border
        button.setContentAreaFilled(false); // Remove default content fill
        button.setBorder(null); // Remove button border
        ImageIcon icon = new ImageIcon(imagePath);
        button.setIcon(icon);
    }

    private void setButtonHoverIcon(JButton button, String hoverImagePath) {
        button.setRolloverIcon(new ImageIcon(hoverImagePath));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        System.gc();
        
        for (int row = 0; row < Main.DEFAULT_BOARD_SIZE; row++) {
			for (int col = 0; col < Main.DEFAULT_BOARD_SIZE; col++) {		
								
				Route[] routes = board.tiles[row][col].getRoutes();
				
				g.drawImage(getImageFromCache("intersection"), col*Main.TILE_SIZE, row*Main.TILE_SIZE, Main.TILE_SIZE, Main.TILE_SIZE, null);
				
				int extra = (int) Math.round(0.3*Main.TILE_SIZE);
				if (routes[0]!=null) {
					g.drawImage(getImageFromCache(routes[0].getPinName()), col*Main.TILE_SIZE, row*Main.TILE_SIZE, Main.TILE_SIZE*2/3, Main.TILE_SIZE*2/3, null);
				}
				if (routes[1]!=null) {
					g.drawImage(getImageFromCache(routes[1].getPinName()), col*Main.TILE_SIZE + extra, row*Main.TILE_SIZE - extra, Main.TILE_SIZE*2/3, Main.TILE_SIZE*2/3, null);
				}
				//if (routes[2]!=null) {
				//	g.drawImage(routes[2].getPinImage(), col*Main.TILE_SIZE + extra, row*Main.TILE_SIZE - extra, Main.TILE_SIZE*2/3, Main.TILE_SIZE*2/3, null);
				//}
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
        
        for (int i = 0; i < popups.length; i++) {
            PopUp popup = popups[i];
            if (popup.getVisibility()) {
            	popup.draw(g);
            }
        }
               
        //g.drawImage(sidebarImage, Main.GAME_WIDTH, 0, Main.SIDEBAR_WIDTH, Main.GAME_WIDTH, null);
        g.drawImage(sidebarImage,Main.GAME_WIDTH,0,sidebarImage.getWidth(),Main.WINDOW_HEIGHT, null);
        
        paintHalos(g);
        
        // Player
        //g.setColor(Color.BLACK); // Set color to black
        //g.setFont(new Font("Tahoma", Font.BOLD, 16));
        //g.drawString(username, Main.GAME_WIDTH+90, 70);
        
        // Time
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString("" + playerTime, Main.GAME_WIDTH+45, 75);
        
        // Gems
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString("" + checkGemScore(), Main.GAME_WIDTH+40, 165);
        gemScoreUpdate = true;
                     
        // Carbon Coins
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString("" + checkCoinScore(), Main.GAME_WIDTH+175, 75);
        coinScoreUpdate = true;
        
        // Score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString("" + gameScore, Main.GAME_WIDTH+175, 165);
       
    }
    
    public void paintHalos(Graphics g) {
    	int player_x = player.getPlayerXTile();
    	int player_y = player.getPlayerYTile();
    	Tile currentTile = board.tiles[player_y][player_x];
    	Route[] tileRoutes = currentTile.getRoutes();
    	for (int i=0; i<tileRoutes.length; i++) {
    		if (tileRoutes[i]!=null) {
    			String nameOfPin = tileRoutes[i].getPinName();
    			String haloName = pinMap.get(nameOfPin);
    			
    			Tile[] tilesInRoute = tileRoutes[i].getTiles();
    			for (int j=0; j<tilesInRoute.length; j++) {
    				int tile_x = tilesInRoute[j].getX();
    				int tile_y = tilesInRoute[j].getY();
    				
    				g.drawImage(getImageFromCache(haloName), tile_x*Main.TILE_SIZE, tile_y*Main.TILE_SIZE, Main.TILE_SIZE, Main.TILE_SIZE, null);
    			}
    			TransportTypes type = tileRoutes[i].getTransportType();
    			String typeString = type.toString();
    			
    			String text1 = "Press "+(i+1)+" to take ";
    			String text2 = typeString;
    			Font font = new Font("Tahoma", Font.BOLD, 16);
    			FontMetrics fontMetrics = g.getFontMetrics(font);
    			g.setFont(font);
    			
    			//Graphics2D g2d = (Graphics2D) g;
    			
    			int string1Width = fontMetrics.stringWidth(text1);
    			//GradientPaint gradient = new GradientPaint(
    			//	    Main.GAME_WIDTH + 35, 485 + i * 25, Color.BLACK, 
    			//	    Main.GAME_WIDTH + 35 + stringWidth, 485 + i * 25, pinColor); // Adjust the coordinates and width as needed

    				// Apply the gradient paint to the graphics context
    			//g2d.setPaint(gradient);
    				
    			g.setColor(Color.BLACK); // Set color to black
    	        g.drawString(text1, Main.GAME_WIDTH+25, 285 + i*25);

    	        g.setColor(colorMap.get(nameOfPin)); // Set color to black
    	        g.drawString(text2, Main.GAME_WIDTH+25+string1Width, 285 + i*25);
    		}
    	}
    }
    
    private  BufferedImage getImageFromCache(String imageName) {
    	try {
    		BufferedImage cachedImage = imageCache.get(imageName);
    		return cachedImage;
    	} catch (NullPointerException e) {
    		return null;
    	}
    }
    private void cacheImage(String imageName, BufferedImage image) {
    	imageCache.put(imageName, image);
    }
    public void restartGame() {
    	//CLEAR OLD OBJECTS OUT
        player = null;
        for (int i = 0; i < numPopups; i++) {
            popups[i] = null;
        }
        popups = null;
        
        for (int i = 0; i < numGems; i++) {
            gems[i] = null;
        }
        gems = null;

        CarbonCoin.stopRotation();
        for (int i = 0; i < numCarbonCoins; i++) {
            carbonCoins[i] = null;
        }
        carbonCoins = null;
        
        board = null;
        
        System.gc();
        
    	//Initialise new objects
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
    
//    public static void endGame(JFrame gameFrame, String username, int gameRound, int gemScore, int coinScore, int gameScore) {
//        Main.openEndWindow(gameFrame, username, gameRound, gemScore, coinScore, gameScore);
//    }

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
	            gem.playSound();
	        }
	    }

//	    if (allGemsCollected() && allCoinsCollected()) {
	    if (allGemsCollected()) {
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
	            coin.playSound();
	        }
	    }

//	    if (allGemsCollected() && allCoinsCollected()) {
//	        restartGame(); // Restart the game when all gems and coins are collected
//	    }

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

//	private boolean allCoinsCollected() {
//	    for (CarbonCoin coin : carbonCoins) {
//	        if (coin.getVisibility()) {
//	            return false; // If any coin is still visible, return false
//	        }
//	    }
//	    return true; // All coins are collected
//	}

	public void popupIntersection() {
	    for (int i = 0; i < popups.length; i++) {
	    	PopUp popup = popups[i];
	        if (player.getPlayerX() == popup.popupX && player.getPlayerY() == popup.popupY && popup.getVisibility()) {
	        	popup.setVisibility(false);
	        	popup.displayPopup();
	        }
	    }
	}
	
    public void calculateGameScore() {        
    	gameScore += (int) ((0.50 * playerTime) + (0.50 * checkCoinScore()));
    }
       
//    public void checkPopUp() {
//        if (player.getPlayerX() == popup.popUpX && player.getPlayerY() == popup.popUpY) {
//            System.out.println("Pop Up");   	
//    	}
//    } 
       
    public void timer(int movement) {  	
    	if ((playerTime - movement) <= 0) {  
    		CarbonCoin.stopRotation();
    		Main.openEndWindow(username, gameRound, gemScore, coinScore, gameScore);
    	}
    	else {
    		playerTime -= movement;
    	}
    }
 
}
