package sharedMobilityAdventure;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;


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
    private transient BufferedImage image;
    private GamePanel gamePanel; // Added member variable for GamePanel
    

    // Image cache for storing loaded images
    @SuppressWarnings("unused")
	private transient static Map<String, BufferedImage> imageCache = new HashMap<>();


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
    }
    
    public void loadImage() {
    	try {
            image = ImageIO.read(new File("images/characters/down.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_UP) { 
        	if (playerY-speed > offset-1) {
        		playerY -= speed;
                try {
                    image = ImageIO.read(new File("images/characters/up.png"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

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
                try {
                    image = ImageIO.read(new File("images/characters/right.png"));
                } catch (IOException e2) {
                    e2.printStackTrace();
                }

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
                try {
                    image = ImageIO.read(new File("images/characters/down.png"));
                } catch (IOException e3) {
                    e3.printStackTrace();
                }

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
        		try {
                    image = ImageIO.read(new File("images/characters/left.png"));
                } catch (IOException e4) {
                    e4.printStackTrace();
                }

                gamePanel.timer(50);
                gamePanel.checkGemScore();
                gamePanel.checkCoinScore();
                gamePanel.popupIntersection();
               if(gamePanel.allGemsCollected() == true) {

                	gamePanel.restartGame();
                };
        	}
        }
        
        if (key == KeyEvent.VK_1) {
        	boolean taken = gamePanel.takeTransportRoute(1,playerX/speed, playerY/speed);
        	if (taken) {

        		gamePanel.timer(50);
                gamePanel.checkGemScore();
                gamePanel.checkCoinScore();
                gamePanel.popupIntersection();
               if(gamePanel.allGemsCollected() == true) {

                	gamePanel.restartGame();
                };
        	}
        }
        
        if (key == KeyEvent.VK_2) {
        	boolean taken = gamePanel.takeTransportRoute(2,playerX/speed, playerY/speed);
        	if (taken) {

        		gamePanel.timer(50);
                gamePanel.checkGemScore();
                gamePanel.checkCoinScore();
                gamePanel.popupIntersection();
               if(gamePanel.allGemsCollected() == true) {

                	gamePanel.restartGame();
                };
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