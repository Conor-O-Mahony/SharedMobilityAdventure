package sharedMobilityAdventure;

import java.util.Arrays;

public class Route {
	
	Tile[] routeTiles;
	
	int startRow;
	int startCol;
	int finalRow;
	int finalCol;
	TransportTypes type;
	
	public Route(TransportTypes T, Tile[][] boardTiles, int startingRow, int startingCol, int routeSize) {
		routeTiles = new Tile[routeSize];
		routeTiles[0] = boardTiles[startingRow][startingCol]; //Add starting Tile to Route
		int maxX = boardTiles[0].length;
		int maxY = boardTiles.length;
		
		int x = startingCol;
		int y = startingRow;
		
		startRow = y;
		startCol = x;
		
		for (int i=1; i<routeSize; i++) {
			boolean chosen = false;
			while (chosen!=true) {
				int n = Board.getRandomNumber(0,4);
				if (n==0 && x<maxX-2) {
					if (!Arrays.stream(routeTiles).anyMatch(boardTiles[y][x+1]::equals)) { //THE ROUTE SHOULD NOT REVISIT TILES IT HAS ALREADY CROSSED
						x+=1;
						chosen=true;
					}
				} else if (n==1 && x>1) {
					if (!Arrays.stream(routeTiles).anyMatch(boardTiles[y][x-1]::equals)) { //THE ROUTE SHOULD NOT REVISIT TILES IT HAS ALREADY CROSSED
						x-=1;
						chosen=true;
					}
				} else if (n==2 && y<maxY-2) {
					if (!Arrays.stream(routeTiles).anyMatch(boardTiles[y+1][x]::equals)) { //THE ROUTE SHOULD NOT REVISIT TILES IT HAS ALREADY CROSSED
						y+=1;
						chosen=true;
					}
				} else if (n==3 && y>1) {
					if (!Arrays.stream(routeTiles).anyMatch(boardTiles[y-1][x]::equals)) { //THE ROUTE SHOULD NOT REVISIT TILES IT HAS ALREADY CROSSED
						y-=1;
						chosen=true;
					}
				}
				
			}
			routeTiles[i] = boardTiles[y][x];
		}
		//PROBLEM: THIS METHOD CAN RESULT IN MORE THAN MAX NUMBER OF STOPS AT THE LAST TILE.
		//TEMPORARY SOLUTION: RETURN NULL => DISCARD THE ROUTE AND CONTINUE.
		
		if (!boardTiles[y][x].RouteAddable() ) {
			routeTiles = null;
		} else {
			finalRow = y;
			finalCol = x;
			type = T;
		}
	}
	
	public Tile[] getTiles() {
		return routeTiles;
	}
	
	public boolean checkValidity() {
		if (routeTiles!=null) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getFinalRow() {
		return finalRow;
	}
	
	public int getFinalCol() {
		return finalCol;
	}
	
	public int getStartRow() {
		return startRow;
	}
	
	public int getStartCol() {
		return startCol;
	}
	
	public TransportTypes getTransportType() {
		return type;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}