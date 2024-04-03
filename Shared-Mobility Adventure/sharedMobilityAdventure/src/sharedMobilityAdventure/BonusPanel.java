package sharedMobilityAdventure;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
//import java.io.BufferedReader;
import java.io.File;
//import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.Arrays;
//import java.util.Objects;

public class BonusPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int default_board_size = 8; //i.e 10*10
	int sidebarWidth = 360;
	int gameHeight = 720;
	int gameWidth = gameHeight+sidebarWidth;
	int gameTiles = gameHeight / default_board_size;
		
	//private Player player = new Player();
	private String user;
	private Board bonus_board;
	
	private BufferedImage[] roadtileArray;
	private BufferedImage[] dialogTileArray;
	
	public BonusPanel(String username){
		
		bonus_board = new Board(default_board_size,default_board_size);
		
		String[] roadTileNames = {"intersection","bikepin","buspin","trainpin"}; //,"roadBus","roadTrain","roadBike","roadBusTrain","roadBusBike","roadTrainBike"
		roadtileArray = new BufferedImage[roadTileNames.length];
		loadTiles(roadTileNames,roadtileArray);
		
		String[] dialogTileNames = {"dialogueTopL","dialogueTop","dialogueTopR","dialogueLeft","dialogueRight","dialogueBottomL","dialogueBottom","dialogueBottomR","dialogueCentre"};
		dialogTileArray = new BufferedImage[dialogTileNames.length];
		loadTiles(dialogTileNames,dialogTileArray);
		
		setPreferredSize(new Dimension(gameWidth,gameHeight));

        user = username;
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
        
        for (int row = 0; row<default_board_size; row++) {
			for (int col = 0; col<default_board_size; col++) {		
				TransportTypes[] routeTypes = bonus_board.tiles[row][col].getRouteTypes();
				
				boolean bus=Arrays.stream(routeTypes).anyMatch(TransportTypes.BUS::equals);
				boolean train=Arrays.stream(routeTypes).anyMatch(TransportTypes.TRAIN::equals);
				boolean bike=Arrays.stream(routeTypes).anyMatch(TransportTypes.BIKE::equals);
				
				g.drawImage(roadtileArray[0], col*gameTiles, row*gameTiles, gameTiles, gameTiles, null);
				
				if (bike==true) {
					g.drawImage(roadtileArray[1], col*gameTiles, row*gameTiles, gameTiles, gameTiles, null);
				}
				if (bus==true) {
					g.drawImage(roadtileArray[2], col*gameTiles, row*gameTiles, gameTiles, gameTiles, null);
				}
				if (train==true) {
					g.drawImage(roadtileArray[3], col*gameTiles, row*gameTiles, gameTiles, gameTiles, null);
				}
			}
        }
        
        //player.draw(g);
        
        // Draw the username
        g.setColor(Color.RED); // Set color to black
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Player: " + user, 805, 20);
        
        //Draw the timer (NEEDS FUNCTIONALITY)
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Time: " , 805, 120);
        
        //Draw the coin count (NEEDS FUNCTIONALITY)
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Carbon Coins: ", 805, 220);
    }

}
