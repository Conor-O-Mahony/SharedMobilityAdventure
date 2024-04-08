package sharedMobilityAdventure;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Player {
    int x; // x-coordinate of the player
    int y; // y-coordinate of the player
    int width; // width of the player
    int height; // height of the player
    int speed = 16; // speed of the player
    BufferedImage image; // image of the player
    Gem gem; // Reference to the Gem
    PopUp popup;
    EndPanel endPanel;
    int score;
    int playerTime;
    public boolean scoreUpdated = false;
 
    public Player(Gem gem, PopUp popup) {
        x = 8*4;
        y = 8*4;        
        width = 16;
        height = 16;
        speed = 16*4;
        this.gem = gem;
        this.popup = popup;
        this.endPanel = endPanel;
        try {
            image = ImageIO.read(new File("images/characters/up.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        playerTime = 200;
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
            timer(10);
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
            timer(10);
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
            timer(10);

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
            timer(10);
        }
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
            System.out.println(score);
        }
        return score;
    }  
    
    public void checkPopUp() {
        if (x == popup.popUpX && y == popup.popUpY) {
            System.out.println("Pop Up");
        }
    } 

    public void timer(int movement) {  	
    	if (playerTime - movement < 0) {

    	      JFrame endFrame = new JFrame();
    	      endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	      endFrame.setResizable(false);
    	      endFrame.setTitle("Shared-Mobility Adventure"); 
    	      
    	      EndPanel endPanel = new EndPanel(endFrame);
    	      endFrame.add(endPanel);
    	 
    	      endFrame.pack();
    	      endFrame.setLocationRelativeTo(null);
    	      endFrame.setVisible(true);
		
    	}
    	else {
    		playerTime -= movement;
    	}
    }

    
    public int getTimer() {  	
    	return playerTime;
    }
    
       
}
