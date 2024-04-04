package sharedMobilityAdventure;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Arrays;

public class MainGamePanel extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
    private Gem gem;
	private Player player;
	
    int scale = 4;
    int tile = 16 * scale;
    int columns = 64 / scale;
    int rows = 36 / scale;
    int totalWidth = columns * tile;
    int totalHeight = rows * tile;
    int sidepanelColumns = 3;
		
	private String user;
	private Board board;
	
	private BufferedImage[] roadtileArray;
	private BufferedImage[] dialogTileArray;
	
	BufferedImage sidebarImage;
		
	public MainGamePanel(String username){
		
		board = new Board(rows, columns);
		
		String[] roadTileNames = {"intersection","bikepin","buspin","trainpin"}; //,"roadBus","roadTrain","roadBike","roadBusTrain","roadBusBike","roadTrainBike"
		roadtileArray = new BufferedImage[roadTileNames.length];
		loadTiles(roadTileNames,roadtileArray);
		
		String[] dialogTileNames = {"dialogueTopL","dialogueTop","dialogueTopR","dialogueLeft","dialogueRight","dialogueBottomL","dialogueBottom","dialogueBottomR","dialogueCentre"};
		dialogTileArray = new BufferedImage[dialogTileNames.length];
		loadTiles(dialogTileNames,dialogTileArray);
		
		setPreferredSize(new Dimension(totalWidth,totalHeight));

        user = username;
        
        gem = new Gem();
        player = new Player(gem);

        this.setFocusable(true);
        requestFocus();
        addKeyListener(this);
        
    
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
				boolean bike=Arrays.stream(routeTypes).anyMatch(TransportTypes.BIKE::equals);
				
				g.drawImage(roadtileArray[0], col*tile, row*tile, tile, tile, null);
				
	            g.setColor(Color.BLACK);
	            g.drawRect(col * tile, row * tile, tile, tile);
				
				if (bike==true) {
					g.drawImage(roadtileArray[1], col*tile, row*tile, tile, tile, null);
				}
				if (bus==true) {
					g.drawImage(roadtileArray[2], col*tile, row*tile, tile, tile, null);
				}
				if (train==true) {
					g.drawImage(roadtileArray[3], col*tile, row*tile, tile, tile, null);
				}
			}
        }
        
        gem.draw(g);
        player.draw(g);
        
        try {        	
        	sidebarImage = ImageIO.read(new File("images/tiles/sidebar.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }  
               
        g.drawImage(sidebarImage, 800, 0, 224, totalHeight, null);
        
        // Draw the username
        g.setColor(Color.BLACK); // Set color to black
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString(user, 950, 50);
        
        //Draw the timer (NEEDS FUNCTIONALITY)
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString("" + player.checkScoreIncrease(), 900, 130);
        player.scoreUpdated = false;
        
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString("10:00" , 950, 205);
        
        
        //Draw the coin count (NEEDS FUNCTIONALITY)
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString("1,000", 950, 275);
             
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
        
}
