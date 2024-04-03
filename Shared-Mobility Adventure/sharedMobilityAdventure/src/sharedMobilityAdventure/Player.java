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
	
	public Player() {
		this.width = x * 32;
		this.height = y * 32;
		try {
			this.image = ImageIO.read(new File("images/characters/New Piskel.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void draw(Graphics g) {
		g.drawImage(image, 132, 132, 132, 132, null);
	}
	// getter and setter methods
}
