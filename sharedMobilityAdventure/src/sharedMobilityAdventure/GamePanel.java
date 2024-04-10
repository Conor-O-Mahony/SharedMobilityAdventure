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
	
    private int scale = 4;
    private int tile = 16 * scale;
    private int columns = 64 / scale;
    private int rows = 36 / scale;
    private int totalWidth = columns * tile;
    private int totalHeight = rows * tile;
    private int sidepanelColumns = 3;
    
    private String username; // Store the username
    private JFrame gameFrame; // Store the game frame  
    
    
	private BufferedImage[] roadtileArray;
	private BufferedImage[] dialogTileArray;
	private BufferedImage halo;
	
	private BufferedImage sidebarImage;
		
	public GamePanel(JFrame gameFrame, String username){
		
        this.gameFrame = gameFrame; // Store the game frame
        this.username = username; // Store the username
			
		setPreferredSize(new Dimension(totalWidth,totalHeight));

        initGame();

        this.setFocusable(true);
        requestFocus();
        addKeyListener(this);            
    }
	
    public void initGame() {
		board = new Board(rows, columns);
		carboncoin = new CarbonCoin("Carbon Credit");
        gem = new Gem("Diamond");
        popup = new PopUp();
        player = new Player(this, gameFrame, username, gem, popup, board);
		
		String[] roadTileNames = {"intersection","bikepin","buspin","trainpin"}; //,"roadBus","roadTrain","roadBike","roadBusTrain","roadBusBike","roadTrainBike"
		roadtileArray = new BufferedImage[roadTileNames.length];
		loadTiles(roadTileNames,roadtileArray);
		
		String[] dialogTileNames = {"dialogueTopL","dialogueTop","dialogueTopR","dialogueLeft","dialogueRight","dialogueBottomL","dialogueBottom","dialogueBottomR","dialogueCentre"};
		dialogTileArray = new BufferedImage[dialogTileNames.length];
		loadTiles(dialogTileNames,dialogTileArray);
		
	
		try {
			halo = ImageIO.read(new File("images/tiles/halo.png"));
		} catch (IOException e) {
			e.printStackTrace();
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
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for (int row = 0; row < rows; row++) {
			for (int col = 0; col < (columns - sidepanelColumns); col++) {		
				
				TransportTypes[] routeTypes = board.tiles[row][col].getRouteTypes();
				
				boolean bus=Arrays.stream(routeTypes).anyMatch(TransportTypes.BUS::equals);
				boolean train=Arrays.stream(routeTypes).anyMatch(TransportTypes.TRAIN::equals);
				boolean bike=Arrays.stream(routeTypes).anyMatch(TransportTypes.BICYCLE::equals);
				
				g.drawImage(roadtileArray[0], col*tile, row*tile, tile, tile, null);
				
	            g.setColor(Color.BLACK);
	            g.drawRect(col * tile, row * tile, tile, tile);
				
				if (bike==true) {
					g.drawImage(roadtileArray[1], col*tile, row*tile, tile*2/3, tile*2/3, null);
				}
				if (bus==true) {
					int extra = (int) Math.round(0.3*tile);
					g.drawImage(roadtileArray[2], col*tile + extra, row*tile, tile*2/3, tile*2/3, null);
				}
				if (train==true) {
					int extra = (int) Math.round(0.3*tile);
					g.drawImage(roadtileArray[3], col*tile + extra, row*tile - extra, tile*2/3, tile*2/3, null);
				}
			}
        }
        
        gem.draw(g);
        carboncoin.draw(g);
        popup.draw(g);
        player.draw(g);
        
        try {        	
        	sidebarImage = ImageIO.read(new File("images/tiles/sidebar.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }  
               
        g.drawImage(sidebarImage, 800, 0, 224, totalHeight, null);
        
        paintHalos(g);
        
        // Draw the username
        g.setColor(Color.BLACK); // Set color to black
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString(username, 950, 50);
        
        //Draw the timer (NEEDS FUNCTIONALITY)
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString("" + player.checkScoreIncrease(), 900, 130);
        player.scoreUpdated = false;
        
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString("" + player.getTimer(), 950, 205);
                
        //Draw the coin count (NEEDS FUNCTIONALITY)
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString("1,000", 950, 275);     
        
    }
    
    public int getScale() {
    	return scale;
    }
    
    public void paintHalos(Graphics g) {
    	int player_x = player.getX();
    	int player_y = player.getY();
    	Tile currentTile = board.tiles[player_y][player_x];
    	Route[] tileRoutes = currentTile.getRoutes();
    	for (int i=0; i<tileRoutes.length; i++) {
    		if (tileRoutes[i]!=null) {
    			Tile[] tilesInRoute = tileRoutes[i].getTiles();
    			for (int j=0; j<tilesInRoute.length; j++) {
    				int tile_x = tilesInRoute[j].getX();
    				int tile_y = tilesInRoute[j].getY();
    				
    				g.drawImage(halo, tile_x*tile, tile_y*tile, tile, tile, null);
    			}
    			TransportTypes type = tileRoutes[i].getTransportType();
    			String typeString = type.toString();
    			g.setColor(Color.BLACK); // Set color to black
    	        g.setFont(new Font("Tahoma", Font.BOLD, 16));
    	        g.drawString("Press "+(i+1)+" to take "+typeString, 815, 400 + i*25);
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
				player.setX(new_player_x);
				player.setY(new_player_y);
			} else {
				int new_player_x = routeToTake.getFinalCol();
				int new_player_y = routeToTake.getFinalRow();
				player.setX(new_player_x);
				player.setY(new_player_y);
			}
		} else {
			return false;
		}
		return true;
	}
       
}
