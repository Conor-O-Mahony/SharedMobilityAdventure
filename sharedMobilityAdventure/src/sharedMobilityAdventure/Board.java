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
	}
	
	public Tile[][] getTiles() {
		return tiles;
	}
	
	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}
	
	private void assignRoutesv2(int rows, int cols) {
		for (int i=0; i<no_stations.length; i++) {
			int j=0;
			while (no_stations[i]<max_stations[i]) {
				int random_col = Main.getRandomNumber(0, cols);
				int random_row = Main.getRandomNumber(0, rows);
				if (tiles[random_row][random_col].RouteAddable()) {
					int route_size = Main.getRandomNumber(Main.MIN_ROUTE_SIZE,Main.MAX_ROUTE_SIZE);
					Route new_route = new Route(transport_types[i],tiles,random_row,random_col,route_size);
					if (new_route.getTiles() != null) {
						tiles[random_row][random_col].asignRouteToTile(new_route); //Assign Route to starting Tile
						int finalRow = new_route.getFinalRow();
						int finalCol = new_route.getFinalCol();
						tiles[finalRow][finalCol].asignRouteToTile(new_route); //Assign Route to final Tile
						new_route.setPinName(pinNames[pinIndex(j,transport_types[i])]);  //THIS WILL FAIL IF YOU CHANGE NO. OF PINS
						j++;
						no_stations[i]+=1;
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
					if (routes[i].getPinName() instanceof String) {
						System.out.println("Pin added successfully");
					}
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