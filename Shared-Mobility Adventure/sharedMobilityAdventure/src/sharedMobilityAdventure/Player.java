package sharedMobilityAdventure;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {
    int x; // x-coordinate of the player
    int y; // y-coordinate of the player
    int width; // width of the player
    int height; // height of the player
    BufferedImage image; // image of the player
    int speed = 16; // speed of the player
    GamePanel gamePanel; // Reference to the GamePanel
    Gem gem;
    private int score;
    
    public Player(GamePanel gamePanel, Gem gem) {
        x = 0;
        y = 0;        
        width = 16;
        height = 16;
        speed = 16;
        this.gamePanel = gamePanel; // Store the reference to the GamePanel
        this.gem = gem;
        try {
            image = ImageIO.read(new File("images/characters/up.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        score = 0;
        System.out.println("hello");
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_UP) {
            if (!checkCollision(x, y - speed)) {
                y -= speed;
                try {
                    image = ImageIO.read(new File("images/characters/up.png"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                checkScoreIncrease();
            }
        }
        if (key == KeyEvent.VK_RIGHT) {
            if (!checkCollision(x + speed, y)) {
                x += speed;
                try {
                    image = ImageIO.read(new File("images/characters/right.png"));
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                checkScoreIncrease();
            }
        }
        if (key == KeyEvent.VK_DOWN) {
            if (!checkCollision(x, y + speed)) {
                y += speed;
                try {
                    image = ImageIO.read(new File("images/characters/down.png"));
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                checkScoreIncrease();
            }
        }
        if (key == KeyEvent.VK_LEFT) {
            if (!checkCollision(x - speed, y)) {
                x -= speed;
                try {
                    image = ImageIO.read(new File("images/characters/left.png"));
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                checkScoreIncrease();
            }
        }
    }
    
    private boolean checkCollision(int newX, int newY) {
        int tileX = newX / gamePanel.tile;
        int tileY = newY / gamePanel.tile;
        return gamePanel.collisionMap[tileX][tileY]; // Access collisionMap through the GamePanel reference
    }
    
    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    
    
    private void checkScoreIncrease() {
        if (x == gem.x && y == gem.y) {
            score++; // Increase the score
            System.out.println(score);
        }
    }

    
}
