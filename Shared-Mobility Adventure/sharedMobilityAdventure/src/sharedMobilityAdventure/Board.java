package sharedMobilityAdventure;

public class Board { //Holds the Tile's
	
	Tile[][] tiles;
	
	public Board(int size) {
		tiles = new Tile[size][size];
		for (int row = 0; row < size; row++) {
			tiles[row] = new Tile[size];
			for (int col = 0; col < size; col++) {
				tiles[row][col] = new Tile(col, row);
			}
		}
	}
	
	public Tile[][] getTiles() {
		return tiles;
	}

	public static void main(String[] args) {
		//Test board creation
		int test_size = 6;
		Board test_board = new Board(test_size);
		for (int row = 0; row<test_size; row++) {
			for (int col = 0; col<test_size; col++) {
				System.out.println("("+test_board.tiles[row][col].getX()+","+test_board.tiles[row][col].getY()+")");
			}
		}

	}

}
