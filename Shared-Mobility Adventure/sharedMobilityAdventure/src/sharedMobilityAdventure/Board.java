package sharedMobilityAdventure;

import java.lang.Math;

public class Board { //Holds the Tile's
	
	Tile[][] tiles;
	
	private int route_probability = 20; //i.e. 1 in 5 chance of a Route being create at any Tile
	private static int min_route_size = 5;
	private static int max_route_size = 10;
	
	public Board(int rows, int cols) { //Creates a blank Board
		tiles = new Tile[rows][cols];
		for (int row = 0; row < rows; row++) {
			tiles[row] = new Tile[cols];
			for (int col = 0; col < cols; col++) {
				tiles[row][col] = new Tile(col, row);
			}
		}
		assignRoutes(rows, cols); //Assign Routes to the blank Board
	}
	
	public Tile[][] getTiles() {
		return tiles;
	}
	
	public static int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
	
	private static TransportTypes randomTransportType() {
		//First choose a TransportType to add.
		//For now, say we have an equal chance of choosing any transport type.
		int no_of_transport_types = TransportTypes.values().length;
		int transport_type_index = Board.getRandomNumber(1,no_of_transport_types); //Randomly pick a TransportType index. Index 0 is CAR, so ignore
		TransportTypes chosen_transport_type = TransportTypes.values()[transport_type_index];
		return chosen_transport_type;
	}
	
	private void assignRoutes(int rows, int cols) {
		for (int row = 0; row<rows; row++) {
			for (int col = 0; col<cols; col++) { //Iterate through Tile by Tile.
				//Calculate a random number
				int random = Board.getRandomNumber(1,route_probability+1);
				if (random == 5) { //Assign a route, starting at this Tile.
					if (tiles[row][col].RouteAddable()) {
						TransportTypes chosen_transport_type = Board.randomTransportType();
						int route_size = Board.getRandomNumber(Board.min_route_size,Board.max_route_size);
						Route new_route = new Route(chosen_transport_type,tiles,row,col,route_size);
						if (new_route.getTiles() != null) {
							tiles[row][col].asignRouteToTile(new_route); //Assign Route to starting Tile
							int finalRow = new_route.getFinalRow();
							int finalCol = new_route.getFinalCol();
							tiles[finalRow][finalCol].asignRouteToTile(new_route); //Assign Route to final Tile
						}
					}
				}
			}
		}
	}
	
	public static int getMaxRouteSize() {
		return max_route_size;
	}

	public static void main(String[] args) {
		//Test board creation
		int test_rows = 10;
		int test_cols = 20;
		Board test_board = new Board(test_rows,test_cols);
		int no_of_stops = 0;
		int no_bikes = 0;
		int no_buses = 0;
		int no_trains = 0;
		for (int row = 0; row<test_rows; row++) {
			for (int col = 0; col<test_cols; col++) {
				System.out.println("("+test_board.tiles[row][col].getX()+","+test_board.tiles[row][col].getY()+")");
				Route[] routes = test_board.tiles[row][col].getRoutes();
				int no_of_routes = test_board.tiles[row][col].getNumberOfRoutes();
				for (int i=0; i<no_of_routes; i++) {
					TransportTypes type = routes[i].getTransportType();
					System.out.println("Stops exist at above coordinate for: "+type);
					no_of_stops+=1;
					if (type==TransportTypes.BUS) {
						no_buses+=1;
					} else if (type==TransportTypes.TRAIN) {
						no_trains+=1;
					} else {
						no_bikes+=1;
					}
				}
			}
		}
		System.out.println("Stats: Boardsize="+test_rows+"*"+test_cols+", Total No. of Stops="+no_of_stops+" => "+(no_of_stops/2)+" transport routes.");
		System.out.println("Number of bike routes: "+no_bikes/2+", bus routes: "+no_buses/2+" and train routes: "+no_trains/2);
	}

}
