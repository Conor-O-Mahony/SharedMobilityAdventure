package sharedMobilityAdventure;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.Math;

import javax.imageio.ImageIO;

public class Board implements Serializable { //Holds the Tile's

	private static final long serialVersionUID = -6394700480482096940L;

	Tile[][] tiles;
	
	private int max_bus = 3;
	private int max_train = 3;
	private int max_bike = 3;
	private transient BufferedImage[] pinArray;
	private Color[] pinColors = {Color.BLUE,Color.GREEN,Color.YELLOW};
	
	public Board(int rows, int cols) { // Creates a blank Board
		tiles = new Tile[rows][cols];
		for (int row = 0; row < rows; row++) {
			tiles[row] = new Tile[cols];
			for (int col = 0; col < cols; col++) {
				tiles[row][col] = new Tile(col, row);
			}
		}

		loadImages();
		assignRoutesv2(rows, cols); // Assign Routes to the blank Board
		validateRoutes();
	}
	
	public Tile[][] getTiles() {
		return tiles;
	}
	
	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}
	
	public static int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
	
	public void loadImages() {
		String[] pinNames = {"buspinB","buspinG","buspinY","trainpinB","trainpinG","trainpinY","bikepinB","bikepinG","bikepinY"};
		pinArray = new BufferedImage[pinNames.length];
		
        for (int i = 0; i < pinNames.length; i++) {
            String imageName = pinNames[i];
            String source = String.format("images/tiles/%s.png", imageName);
                try {
                	pinArray[i] = ImageIO.read(new File(source));
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
	
	private void assignRoutesv2(int rows, int cols) {
	    int[] no_stations = {0, 0, 0};
	    int[] max_stations = {max_bus, max_train, max_bike};
	    TransportTypes[] transport_types = {TransportTypes.BUS, TransportTypes.TRAIN, TransportTypes.BICYCLE};
	    for (int i = 0; i < no_stations.length; i++) {
	        int j = 0;

	        while (no_stations[i] < max_stations[i]) {

	            Color pinColor = pinColors[j % pinColors.length]; // Define a pin color for the pin
	            int random_col = Main.getRandomNumber(1, cols); // Start from (1,1)
	            int random_row = Main.getRandomNumber(1, rows);
	            if (random_col > 0 && random_row > 0) { // Ensure it starts from (1,1)
	            if (tiles[random_row][random_col].RouteAddable(transport_types[i], pinColor, random_row, random_col)) { // Check Route can be added to the starting tile
	                int route_size = Main.getRandomNumber(Main.MIN_ROUTE_SIZE,Main.MAX_ROUTE_SIZE);
	                Route new_route = new Route(transport_types[i], tiles, pinColor, random_row, random_col, route_size);
	                if (new_route.getTiles() != null) {
	                    tiles[random_row][random_col].asignRouteToTile(new_route); //Assign Route to starting Tile
	                    int finalRow = new_route.getFinalRow();
	                    int finalCol = new_route.getFinalCol();
	                    if (finalRow > 0 && finalCol > 0) {
	                    if (tiles[finalRow][finalCol].RouteAddable(transport_types[i], pinColor, finalRow, finalCol)) { // Check Route can be added to the starting tile
	                        tiles[finalRow][finalCol].asignRouteToTile(new_route); //Assign Route to final Tile
	                        System.out.println("Route " + i + " " + pinColor + transport_types[i] + ": Added from(" + random_row + ", " + random_col + ") to (" + finalRow + ", " + finalCol + ")");
	                        new_route.setPinImage(pinArray[i * max_stations[i] + j]); //THIS WILL FAIL IF YOU CHANGE NO. OF PINS
	                        new_route.setPinColor(pinColors[j % pinColors.length]);
	                        j++;
	                        no_stations[i]++;
	                    } 
	                    
	                    }}
	                
	            }
	            
	        }}
	        
	    }
	}


	public void validateRoutes() {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                Tile tile = tiles[row][col];
                Route[] routes = tile.getRoutes(); //Go Through Cols / Rows and check for routes
                for (int i = 0; i < routes.length; i++) {
                    Route route = routes[i];
                    if (route != null) { 
                        route.updateTravel(); // Update to get cost values for routes
                        if (!route.isValid()) { // Check if the route is valid
                            System.out.println("Removing invalid route at (" + row + ", " + col + ")");
                            tile.removeRoute(route); // Remove the route if it's invalid
                            i--;
                        }
                    }
                }
            }
        }
    }
	

	
	public void reloadPins(int rows, int cols) {
		loadImages();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				int numroutes = tiles[row][col].getNumberOfRoutes();
				if (numroutes>0) {
					Route[] routes = tiles[row][col].getRoutes();
					for (int i=0; i<numroutes; i++) {
						Color pincolor = routes[i].getPinColor();
						TransportTypes transporttype = routes[i].getTransportType();
						int imageIndex = pinIndex(pincolor,transporttype);
						routes[i].setPinImage(pinArray[imageIndex]);
					}
				}
			}
		}
	}
	
	private int pinIndex(Color p, TransportTypes t) {
		int i =0;
		if (t==TransportTypes.TRAIN) {
			i=1;
		} else if (t==TransportTypes.BICYCLE) {
			i=2;
		}
		
		if (p.getRGB() == Color.BLUE.getRGB()) {
			return i*3 + 0;
		}
		if (p.getRGB()==Color.GREEN.getRGB()) {
			return i*3 + 1;
		}
		if (p.getRGB()==Color.YELLOW.getRGB()) {
			return i*3 + 2;
		}
		
		return -1;
	}
	


	
	public static int getMaxRouteSize() {
		return max_route_size;
	}

	public static void main(String[] args) {
	    // Test board creation
	    int test_rows = 10;
	    int test_cols = 20;
	    Board test_board = new Board(test_rows, test_cols);
	    int no_of_stops = 0;
	    int no_bikes = 0;
	    int no_buses = 0;
	    int no_trains = 0;

	    for (int row = 0; row < test_rows; row++) {
	        for (int col = 0; col < test_cols; col++) {

	            Route[] routes = test_board.tiles[row][col].getRoutes();
	            int no_of_routes = test_board.tiles[row][col].getNumberOfRoutes();

	       

	            for (int i = 0; i < no_of_routes; i++) {
	                TransportTypes type = routes[i].getTransportType();

	             
	                if (routes[i].getPinImage() instanceof BufferedImage) {     
		                no_of_stops += 1;
	                } else {
	                   
	                }
	                

	                if (type == TransportTypes.BUS) {
	                    no_buses += 1;
	                } else if (type == TransportTypes.TRAIN) {
	                    no_trains += 1;
	                } else {
	                    no_bikes += 1;
	                }
	            }
	        }
	    }

	    System.out.println("Boardsize=" + test_rows + "*" + test_cols + ", Total Stops=" + no_of_stops + ". Routes: " + (no_of_stops / 2));
	    System.out.println("bike routes: " + no_bikes + ", bus routes: " + no_buses + "train routes: " + no_trains);
	}


}
