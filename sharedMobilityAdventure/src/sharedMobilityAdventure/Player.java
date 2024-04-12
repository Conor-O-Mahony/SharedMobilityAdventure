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
    private int playerX; // x-coordinate of the player
    private int playerY; // y-coordinate of the player
    int width; // width of the player
    int height; // height of the player
    int speed; // speed of the player
    BufferedImage image; // image of the player
    private GamePanel gamePanel;
    
    public Player(GamePanel gamePanel) {
    	this.gamePanel = gamePanel;
        this.offset = 8 * gamePanel.getScale();
        this.playerX = offset;
        this.playerY = offset;
        this.width = 16;
        this.height = 16;
        this.speed = 16 * gamePanel.getScale();
        
        try {
            image = ImageIO.read(new File("images/characters/down.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_UP) {            
        	playerY -= speed;
            try {
                image = ImageIO.read(new File("images/characters/up.png"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            gamePanel.checkGemScore();
            gamePanel.checkCoinScore();
//            gamePanel.checkPopUp();
            gamePanel.timer(10);
        }
        
        if (key == KeyEvent.VK_RIGHT) {
        	playerX += speed;
            try {
                image = ImageIO.read(new File("images/characters/right.png"));
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            gamePanel.checkGemScore();
            gamePanel.checkCoinScore();
            gamePanel.checkPopUp();
            gamePanel.timer(10);
        }
        
        if (key == KeyEvent.VK_DOWN) {
        	playerY += speed;
            try {
                image = ImageIO.read(new File("images/characters/down.png"));
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            gamePanel.checkGemScore();
            gamePanel.checkCoinScore();
            gamePanel.checkPopUp();
            gamePanel.timer(10);
        }
        
        if (key == KeyEvent.VK_LEFT) {
        	playerX -= speed;
            try {
                image = ImageIO.read(new File("images/characters/left.png"));
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            gamePanel.checkGemScore();
            gamePanel.checkCoinScore();
            gamePanel.checkPopUp();
            gamePanel.timer(10);
        }
        
        if (key == KeyEvent.VK_1) {
        	boolean taken = gamePanel.takeTransportRoute(1,playerX/speed, playerY/speed);
        	if (taken) {
        		gamePanel.checkGemScore();
        		gamePanel.checkCoinScore();
        		gamePanel.checkPopUp();
        		gamePanel.timer(10); //CHANGE
        	}
        }
        
        if (key == KeyEvent.VK_2) {
        	boolean taken = gamePanel.takeTransportRoute(2,playerX/speed, playerY/speed);
        	if (taken) {
        		gamePanel.checkGemScore();
        		gamePanel.checkCoinScore();
        		gamePanel.checkPopUp();
        		gamePanel.timer(10); //CHANGE
        	}
        }
    }
        
    public int getPlayerX() {
    	return playerX;
    }
       
    public int getPlayerY() {
    	return playerY;
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
