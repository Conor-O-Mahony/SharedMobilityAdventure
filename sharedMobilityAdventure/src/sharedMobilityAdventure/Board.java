package sharedMobilityAdventure;
import java.io.Serializable;

public class Board implements Serializable { //Holds the Tile's

	private static final long serialVersionUID = -6394700480482096940L;

	Tile[][] tiles;
	
	private int max_bus = 3;
	private int max_train = 3;
	private int max_bike = 3;
	private String[] pinNames = GamePanel.pinNames;
	int[] no_stations;
	int[] max_stations = {max_bus,max_train,max_bike};
	TransportTypes[] transport_types = {TransportTypes.BUS,TransportTypes.TRAIN,TransportTypes.BICYCLE};
	
	public Board(int rows, int cols) { // Creates a blank Board
		no_stations = new int[]{0,0,0};
		
		tiles = new Tile[rows][cols];
		for (int row = 0; row < rows; row++) {
			tiles[row] = new Tile[cols];
			for (int col = 0; col < cols; col++) {
				tiles[row][col] = new Tile(col, row);
			}
		}
		assignRoutesv2(rows, cols); // Assign Routes to the blank Board
		validateRoutes();
	}
	
	public Tile[][] getTiles() {
		return tiles;
	}
	
	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}
	
	private void assignRoutesv2(int rows, int cols) {
	    for (int i = 0; i < no_stations.length; i++) {
	        int j = 0;
	        while (no_stations[i] < max_stations[i]) {
	        	String pinName = pinNames[pinIndex(j,transport_types[i])];
	            int random_col = Main.getRandomNumber(1, cols); // Start from (1,1)
	            int random_row = Main.getRandomNumber(1, rows);
	            if (tiles[random_row][random_col].RouteAddable(transport_types[i], pinName, random_row, random_col)) { // Check Route can be added to the starting tile
	                int route_size = Main.getRandomNumber(Main.MIN_ROUTE_SIZE,Main.MAX_ROUTE_SIZE);
	                Route new_route = new Route(transport_types[i], pinName, tiles,random_row, random_col, route_size);
	                if (new_route.getTiles() != null) {
	                    tiles[random_row][random_col].asignRouteToTile(new_route); //Assign Route to starting Tile
	                    int finalRow = new_route.getFinalRow();
	                    int finalCol = new_route.getFinalCol();
	                    if (finalRow > 0 && finalCol > 0) {
							if (tiles[finalRow][finalCol].RouteAddable(transport_types[i], pinName, finalRow, finalCol)) { // Check Route can be added to the starting tile
								tiles[finalRow][finalCol].asignRouteToTile(new_route); //Assign Route to final Tile
								System.out.println("Route " + i + " " + transport_types[i] + ": Added from(" + random_row + ", " + random_col + ") to (" + finalRow + ", " + finalCol + ")");
								j++;
								no_stations[i]++;
							} 
							
	                    }
					}  
	            }  
	        }  
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

	private int pinIndex(int j, TransportTypes t) {
		int i =0;
		if (t==TransportTypes.TRAIN) {
			i=1;
		} else if (t==TransportTypes.BICYCLE) {
			i=2;
		}
		
		return i*3 + j;
	}
	

	public static void main(String[] args) {
	    // Test board creation
	    int test_rows = 10;
	    int test_cols = 10;
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

	             
	                if (routes[i].getPinName() instanceof String) {     
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