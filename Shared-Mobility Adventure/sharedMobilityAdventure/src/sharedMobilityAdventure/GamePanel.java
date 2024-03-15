package sharedMobilityAdventure;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

public class GamePanel extends JPanel {

    int tile = 16*2; // initial tile length / width (16 * 16 pixels)
    int columns = 32;
    int rows = 18;
    int totalWidth = columns * tile; // 640 pixels in length
    int totalHeight = rows * tile; // 440 pixels in height
    int arraySize = 10;
    int[][] mapTileNum = new int[columns][rows];
    
    BufferedImage[] imageArray = new BufferedImage[arraySize];   
     
    public GamePanel() {
        setPreferredSize(new Dimension(totalWidth, totalHeight));
        try {
        	imageArray[0] = ImageIO.read(new File("images/tiles/dark_grass.png"));
        	imageArray[1] = ImageIO.read(new File("images/tiles/brick_wall.png"));
        	imageArray[2] = ImageIO.read(new File("images/tiles/concrete_road.png"));
        	imageArray[3] = ImageIO.read(new File("images/tiles/sidewalk.png"));
        	imageArray[4] = ImageIO.read(new File("images/tiles/water.png"));
        	imageArray[5] = ImageIO.read(new File("images/tiles/panel.png")); 
        } catch (IOException ex) {
            ex.printStackTrace();
        }        
        loadMap();      
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
                }
                row++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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
    }
}
