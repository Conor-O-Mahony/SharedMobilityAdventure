package sharedMobilityAdventure;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Player {
	int offset;
    int x; // x-coordinate of the player
    int y; // y-coordinate of the player
    int width; // width of the player
    int height; // height of the player
    int speed = 16; // speed of the player
    int scale; //scale of board
    BufferedImage image; // image of the player
    Gem gem; // Reference to the Gem
    PopUp popup;
    Board board;
    int score;
    int coins;
    int playerTime;
    public boolean scoreUpdated = false;
    private JFrame gameFrame; // Reference to the game frame
    private String username; // Username associated with the game session
    private GamePanel gamePanel;
    
    public Player(GamePanel gamePanel, JFrame gameFrame, String username, Gem gem, PopUp popup, Board board) {
    	this.gamePanel = gamePanel;
    	this.gameFrame = gameFrame;
        this.username = username;    	
    	this.gem = gem;
        this.popup = popup;
        this.board = board;
        this.scale = gamePanel.getScale();
        this.offset = 8 * scale;
        this.x = offset;
        this.y = offset;
        this.width = 16;
        this.height = 16;
        this.speed = 16 * scale;
        this.score = 0;
        this.coins = 1000;
        this.playerTime = 5000;
        this.scoreUpdated = false;
        
        try {
            image = ImageIO.read(new File("images/characters/down.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_UP) {            
            y -= speed;
            try {
                image = ImageIO.read(new File("images/characters/up.png"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            checkScoreIncrease();
            checkPopUp();
            timer(50);
        }
        
        if (key == KeyEvent.VK_RIGHT) {
            x += speed;
            try {
                image = ImageIO.read(new File("images/characters/right.png"));
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            checkScoreIncrease();
            checkPopUp();
            timer(50);
        }
        
        if (key == KeyEvent.VK_DOWN) {
            y += speed;
            try {
                image = ImageIO.read(new File("images/characters/down.png"));
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            checkScoreIncrease();
            checkPopUp();
            timer(50);

        }
        if (key == KeyEvent.VK_LEFT) {
            x -= speed;
            try {
                image = ImageIO.read(new File("images/characters/left.png"));
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            checkScoreIncrease();
            checkPopUp();
            timer(50);
        }
        if (key == KeyEvent.VK_1) {
        	boolean taken = gamePanel.takeTransportRoute(1,x/speed,y/speed);
        	if (taken) {
        		checkScoreIncrease();
                checkPopUp();
                timer(50); //CHANGE
        	}
        }
        if (key == KeyEvent.VK_2) {
        	boolean taken = gamePanel.takeTransportRoute(2,x/speed,y/speed);
        	if (taken) {
        		checkScoreIncrease();
                checkPopUp();
                timer(50); //CHANGE
        	}
        }
    }
    
    public int getCoins() {
    	return coins;
    }
    
    public void setCoins(int cCoins) {
    	coins = cCoins;
    	System.out.println("Carbon Coins updated to: " + coins);
    }
    
    public void adjustCoins(double adjustment) {
        this.coins += adjustment;
        System.out.println("Carbon Coins updated to: " + coins);
    }
    
    public int getX() {
    	return x/speed;
    }
    
    public int getY() {
    	return y/speed;
    }
    
    public void setX(int value) {
    	x = value*speed + offset;
    }
    
    public void setY(int value) {
    	y = value*speed + offset;
    }
    
    public void draw(Graphics g) {
        int adjustedX = x - (width / 2);
        int adjustedY = y - (height / 2);

        g.drawImage(image, adjustedX, adjustedY, width, height, null);
    }

    public int checkScoreIncrease() {
        if (x == gem.x && y == gem.y && !scoreUpdated) {
            score++; // Increase the score
            scoreUpdated = true; // Set the flag to indicate that the score has been updated
            gem.dropRandomly(5);
            System.out.println(score);
        }
        return score;
    }  
    
    public void checkPopUp() {
        if (x == popup.popUpX && y == popup.popUpY) {
//            System.out.println("Pop Up");
        	gamePanel.restartGame();
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

    
    public int getTimer() {  	
    	return playerTime;
    }
    
    public void setTimer(int time) {
    	playerTime = time;
    	System.out.println("Time updated to: " + playerTime);
    }
    
    public void adjustTimer(double adjustment) {
        this.playerTime += adjustment;
        System.out.println("Time updated to: " + playerTime);
    }
    
    public void updateTravel(Route route) {
        TransportTypes type = route.getTransportType();
        int distance = route.getTiles().length; // Calculate the number of tiles in the route

        // Carbon Cost
        double carbonCost = type.calculateCarbonFootprint(distance);
        adjustCoins(-carbonCost); // Adjust carbon coins based on the cost

        // Time Cost
        double timePerTile = type.getSpeed(); // Get adjusted speed considering congestion
        int totalTravelTime = (int) (timePerTile * distance); // Total time taken on route
        adjustTimer(-totalTravelTime); // Subtract this time from the game timer

        System.out.println("Travel Costs - Distance: " + distance + ", Carbon Cost: " + carbonCost + ", Time Cost: " + totalTravelTime + " seconds");
    }

    
    
}
