package sharedMobilityAdventure;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {
	int x; // x-coordinate of the player
	int y; // y-coordinate of the player
	int width; // width of the player
	int height; //height of the player
	BufferedImage image; // image of the player
	
	public Player(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		try {
			this.image = ImageIO.read(new File("images/characters/character-1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void draw(Graphics g) {
		g.drawImage(image, x, y, width, height, null);
	}
	// getter and setter methods
}
