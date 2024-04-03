package sharedMobilityAdventure;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

public class GamePanel extends JPanel implements KeyListener {

    int tile = 16; // initial tile length / width (16 * 16 pixels)
    int columns = 64;
    int rows = 36;
    int totalWidth = columns * tile; // 640 pixels in length
    int totalHeight = rows * tile; // 440 pixels in height
    int arraySize = 10;
    int[][] mapTileNum = new int[columns][rows];
    public boolean[][] collisionMap = new boolean[columns][rows]; // If true, then tile is a collision tile.
    Gem gem;
    Player player;
    
    BufferedImage[] imageArray = new BufferedImage[100];   //increased size for more tile types
    String username;
       
    public GamePanel(String username) { //now inheriting username
        setPreferredSize(new Dimension(totalWidth, totalHeight));

        this.username = username;
        
        try {
        	imageArray[0] = ImageIO.read(new File("images/tiles/dark_grass.png"));
        	imageArray[1] = ImageIO.read(new File("images/tiles/brick_wall.png"));
        	imageArray[2] = ImageIO.read(new File("images/tiles/concrete_road.png"));
        	imageArray[3] = ImageIO.read(new File("images/tiles/sidewalk.png"));
        	imageArray[4] = ImageIO.read(new File("images/tiles/water.png"));
        	imageArray[5] = ImageIO.read(new File("images/tiles/panel.png"));
        	//Dialogue box tiles
        	imageArray[6] = ImageIO.read(new File("images/tiles/dialogueTopL.png"));
        	imageArray[7] = ImageIO.read(new File("images/tiles/dialogueTop.png"));
        	imageArray[8] = ImageIO.read(new File("images/tiles/dialogueTopR.png"));
        	imageArray[9] = ImageIO.read(new File("images/tiles/dialogueLeft.png"));
        	imageArray[10] = ImageIO.read(new File("images/tiles/dialogueRight.png"));
        	imageArray[11] = ImageIO.read(new File("images/tiles/dialogueBottomL.png"));
        	imageArray[12] = ImageIO.read(new File("images/tiles/dialogueBottom.png"));
        	imageArray[13] = ImageIO.read(new File("images/tiles/dialogueBottomR.png"));
        	imageArray[14] = ImageIO.read(new File("images/tiles/dialogueCentre.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }      
        // Initialise gem 
        loadMap();
        gem = new Gem(this);
        player = new Player(this, gem);
        this.setFocusable(true);
        this.requestFocus(); // Ensure the panel has focus to receive key events
        this.addKeyListener(this);
    }
    public void loadMap() {
    	
        File file = new File("maps/map1.txt");

        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {

            int row = 0;
            String line;

            while ((line = br.readLine()) != null && row < rows) {
                String[] numbers = line.split("\t");

                for (int col = 0; col < columns && col < numbers.length; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    if (num == 1) {
                    	collisionMap[col][row] = true;
                    }
                }
                row++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Method to retrieve username from the username field
    	public String getUsername() {
    	return username;
    	}
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
            	int num = mapTileNum[col][row];
                int x = col * tile;
                int y = row * tile;	
            	g.drawImage(imageArray[num], x, y, tile, tile, null);
            }
        }
        gem.draw(g);
        player.draw(g);
        
        // Draw the username
        String username = getUsername();
        g.setColor(Color.RED); // Set color to black
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Player: " + username, 805, 20);
        
        //Draw the timer (NEEDS FUNCTIONALITY)
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Time: " , 805, 120);
        
        //Draw the coin count (NEEDS FUNCTIONALITY)
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Carbon Coins: ", 805, 220);
    }

    
    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed for this implementation
    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e); // Call the keyPressed method in the Player class
        repaint(); // Redraw the panel after key press
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not needed for this implementation
    }
    
    
    
    
    
}

