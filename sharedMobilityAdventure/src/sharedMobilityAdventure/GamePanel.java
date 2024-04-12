package sharedMobilityAdventure;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Arrays;

public class GamePanel extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
    private Gem gem;
    private CarbonCoin carboncoin;
	private Player player;
	private PopUp popup;
	private Board board;
    
    public static int DEFAULT_BOARD_SIZE = 8; //i.e 10*10
    private static int SIDEBAR_WIDTH = 256;
    public static int GAME_HEIGHT = 720;
    public static int GAME_WIDTH = 720;
    private static int WINDOW_WIDTH = GAME_HEIGHT + SIDEBAR_WIDTH;
    private static int WINDOW_HEIGHT = GAME_HEIGHT;
    public static int TILE_SIZE = GAME_HEIGHT / DEFAULT_BOARD_SIZE;

    private String username; // Store the username
    private JFrame gameFrame; // Store the game frame  
        
	private BufferedImage[] roadtileArray;
	private BufferedImage[] haloArray;
	private BufferedImage sidebarImage;
    
    private CarbonCoin[] carbonCoins;
    private int numCarbonCoins = 3;
    
	int playerTime = 1000;
	int gemScore = 0;
	int coinScore = 100;
    public boolean gemScoreUpdate = true;
    public boolean coinScoreUpdate = true;

    public GamePanel(JFrame gameFrame, String username){
		
        this.gameFrame = gameFrame; // Store the game frame
        this.username = username; // Store the username
			
		setPreferredSize(new Dimension(GamePanel.WINDOW_WIDTH,GamePanel.WINDOW_HEIGHT)); //Dimension(totalWidth,totalHeight)

        initGame();

        this.setFocusable(true);
        requestFocus();
        addKeyListener(this);            
    }
	
	
    public void initGame() {
		    board = new Board(GamePanel.DEFAULT_BOARD_SIZE, GamePanel.DEFAULT_BOARD_SIZE);
        player = new Player(this);

        gem = new Gem("Diamond");
		
        carbonCoins = new CarbonCoin[numCarbonCoins];

        for (int i = 0; i < numCarbonCoins; i++) {
            carbonCoins[i] = new CarbonCoin("Carbon Credit");
        }
          
        popup = new PopUp();
		
		String[] roadTileNames = {"intersection","bikepin","buspin","trainpin"}; //,"roadBus","roadTrain","roadBike","roadBusTrain","roadBusBike","roadTrainBike"
		roadtileArray = new BufferedImage[roadTileNames.length];
		loadTiles(roadTileNames,roadtileArray);
		
		String[] haloNames = {"halo","halo2"};
		haloArray = new BufferedImage[haloNames.length];
		loadTiles(haloNames,haloArray);             
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
	
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for (int row = 0; row < GamePanel.DEFAULT_BOARD_SIZE; row++) {
			for (int col = 0; col < GamePanel.DEFAULT_BOARD_SIZE; col++) {		
				
				TransportTypes[] routeTypes = board.tiles[row][col].getRouteTypes();
				
				boolean bus=Arrays.stream(routeTypes).anyMatch(TransportTypes.BUS::equals);
				boolean train=Arrays.stream(routeTypes).anyMatch(TransportTypes.TRAIN::equals);
				boolean bike=Arrays.stream(routeTypes).anyMatch(TransportTypes.BICYCLE::equals);
				
				g.drawImage(roadtileArray[0], col*GamePanel.TILE_SIZE, row*GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
				
//	            g.setColor(Color.BLACK);
//	            g.drawRect(col * tile, row * tile, tile, tile);
				
				if (bike==true) {
					g.drawImage(roadtileArray[1], col*GamePanel.TILE_SIZE, row*GamePanel.TILE_SIZE, GamePanel.TILE_SIZE*2/3, GamePanel.TILE_SIZE*2/3, null);
				}
				if (bus==true) {
					int extra = (int) Math.round(0.3*GamePanel.TILE_SIZE);
					g.drawImage(roadtileArray[2], col*GamePanel.TILE_SIZE + extra, row*GamePanel.TILE_SIZE, GamePanel.TILE_SIZE*2/3, GamePanel.TILE_SIZE*2/3, null);
				}
				if (train==true) {
					int extra = (int) Math.round(0.3*GamePanel.TILE_SIZE);
					g.drawImage(roadtileArray[3], col*GamePanel.TILE_SIZE + extra, row*GamePanel.TILE_SIZE - extra, GamePanel.TILE_SIZE*2/3, GamePanel.TILE_SIZE*2/3, null);
				}
			}
        }

        
        player.draw(g);

        if (gem.getVisibility()) {
            gem.draw(g);
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
               
        g.drawImage(sidebarImage, GamePanel.GAME_WIDTH, 0, GamePanel.SIDEBAR_WIDTH, GamePanel.GAME_WIDTH, null);
        
        paintHalos(g);
        
        // Username
        g.setColor(Color.BLACK); // Set color to black
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString(username, GamePanel.GAME_WIDTH+50, 70);
        
        // Time
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString("" + playerTime, GamePanel.GAME_WIDTH+50, 175);
        
        // Gems
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString("" + checkGemScore(), GamePanel.GAME_WIDTH+20, 270);
        gemScoreUpdate = true;
                     
        // Coins
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString("" + checkCoinScore(), GamePanel.GAME_WIDTH+120, 270);
        coinScoreUpdate = true;
        
        // Score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString("1000", GamePanel.GAME_WIDTH+50, 375);
       
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
    				
    				g.drawImage(haloArray[i], tile_x*GamePanel.TILE_SIZE, tile_y*GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
    			}
    			TransportTypes type = tileRoutes[i].getTransportType();
    			String typeString = type.toString();
    			g.setColor(Color.BLACK); // Set color to black
    	        g.setFont(new Font("Tahoma", Font.BOLD, 16));
    	        g.drawString("Press "+(i+1)+" to take "+typeString, GamePanel.GAME_WIDTH+35, 485 + i*25);
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
        if (player.getPlayerX() == gem.collectabelX && player.getPlayerY() == gem.collectabelY && gemScoreUpdate) {
        	gemScore++; // Increase the score
            gemScoreUpdate = false;
            gem.setVisibility(false);
            restartGame();
        }
        return gemScore;
    }  
    
    public int checkCoinScore() {        
        for (int i = 0; i < carbonCoins.length; i++) {
            CarbonCoin coin = carbonCoins[i];
            if (player.getPlayerX() == coin.collectabelX && player.getPlayerY() == coin.collectabelY && coinScoreUpdate) {
                coinScore++; // Increase the score
                coinScoreUpdate = false;
                coin.setVisibility(false);
            }
        }
        return coinScore;
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
