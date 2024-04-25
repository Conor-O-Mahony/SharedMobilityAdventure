package sharedMobilityAdventure;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Player implements Serializable {

    private static final long serialVersionUID = 325187586807794891L;
    private int offset;
    private int playerX;
    private int playerY;
    private int width;
    private int height;
    private int speed;
    private int tile;
    private int coins;
    static transient BufferedImage image;
    private GamePanel gamePanel; // Added member variable for GamePanel    

    public Player(GamePanel gamePanel) {
        this.gamePanel = gamePanel; // Store the GamePanel instance
        this.offset = Main.TILE_SIZE / 2;
        this.playerX = offset;
        this.playerY = offset;
        this.width = 16;
        this.height = 16;
        this.speed = Main.TILE_SIZE;
        this.tile = Main.TILE_SIZE;
        this.coins = 1000;
        
        loadImage();
        //LOAD IN DOWN AS FIRST IMAGE
    }
    
    void loadImage() {
     image = Main.getImageFromCache("down");
    }
    
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_UP) { 
        	if (playerY-speed > offset-1) {
        		playerY -= speed;
                
        		image = Main.getImageFromCache("up");
        		
		        gamePanel.timer(50);
                gamePanel.checkGemScore();
                gamePanel.checkCoinScore();
                gamePanel.popupIntersection();
               if(gamePanel.allGemsCollected() == true) {
                	gamePanel.restartGame();
                };
        	}
        }
        
        if (key == KeyEvent.VK_RIGHT) {
        	if (playerX+speed < Main.GAME_WIDTH) {
        		playerX += speed;
                
        		image = Main.getImageFromCache("right");
        		
                gamePanel.timer(50);
                gamePanel.checkGemScore();
                gamePanel.checkCoinScore();
                gamePanel.popupIntersection();
               if(gamePanel.allGemsCollected() == true) {
                	gamePanel.restartGame();
                };
        	}
        }
        
        if (key == KeyEvent.VK_DOWN) {
        	if (playerY+speed < Main.GAME_HEIGHT) {
        		playerY += speed;
        		
        		image = Main.getImageFromCache("down");
               
                gamePanel.timer(50);
                gamePanel.checkGemScore();
                gamePanel.checkCoinScore();
                gamePanel.popupIntersection();
               if(gamePanel.allGemsCollected() == true) {

                	gamePanel.restartGame();
                };
        	}
        }
        
        if (key == KeyEvent.VK_LEFT) {
        	if (playerX-speed > offset-1) {
        		playerX -= speed;
        		
        		image = Main.getImageFromCache("left");
        		
                gamePanel.timer(50);
                gamePanel.checkGemScore();
                gamePanel.checkCoinScore();
                gamePanel.popupIntersection();
               if(gamePanel.allGemsCollected() == true) {
                	gamePanel.restartGame();
                };
        	}
        }

	// This was moved to GamePanel, it could probably be kept here either and have it as 1 method - boolean taken = gamePanel.takeTransportRoute(GamePanel.showOption,playerX/speed, playerY/speed);
	
        // if (key == KeyEvent.VK_1) {
        // 	boolean taken = gamePanel.takeTransportRoute(1,playerX/speed, playerY/speed);
        // 	if (taken) {
        // 		gamePanel.timer(50);
        //         gamePanel.checkGemScore();
        //         gamePanel.checkCoinScore();
        //         gamePanel.popupIntersection();
        //        if(gamePanel.allGemsCollected() == true) {
        //         	gamePanel.restartGame();
        //         };
        // 	}
        // }
        
        // if (key == KeyEvent.VK_2) {
        // 	boolean taken = gamePanel.takeTransportRoute(2,playerX/speed, playerY/speed);
        // 	if (taken) {
        // 		gamePanel.timer(50);
        //         gamePanel.checkGemScore();
        //         gamePanel.checkCoinScore();
        //         gamePanel.popupIntersection();
        //        if(gamePanel.allGemsCollected() == true) {
        //         	gamePanel.restartGame();
        //         };
        // 	}
        // }
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
        
    public int getPlayerX() {
    	return playerX;
    }
       
    public int getPlayerY() {
    	return playerY;
    }
    
    public int getPlayerXTile() {
    	return playerX / tile;
    }
       
    public int getPlayerYTile() {
    	return playerY / tile;
    }
     
    public void setPlayerX(int value) {
    	playerX = value*speed + offset;
    }
        
    public void setPlayerY(int value) {
    	playerY = value*speed + offset;
    }
    
    public void draw(Graphics g) {
        int adjustedX = playerX - (width / 2);
        int adjustedY = playerY - (height / 2);

        g.drawImage(image, adjustedX, adjustedY, width, height, null);
    }
}
