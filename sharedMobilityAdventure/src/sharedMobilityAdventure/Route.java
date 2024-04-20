package sharedMobilityAdventure;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Arrays;

public class Route implements Serializable{

	private static final long serialVersionUID = -2354648655745414L;

	private Tile[] routeTiles;
	
	private int startRow;
	private int startCol;
	private int finalRow;
	private int finalCol;
	private TransportTypes type;
	private transient BufferedImage pinImage;
	private Color pinColor;
	
	public Route(TransportTypes T, Tile[][] boardTiles, int startingRow, int startingCol, int routeSize) {
		routeTiles = new Tile[routeSize];
		routeTiles[0] = boardTiles[startingRow][startingCol]; //Add starting Tile to Route
		int maxX = boardTiles[0].length;
		int maxY = boardTiles.length;
		
		int x = startingCol;
		int y = startingRow;
		
		startRow = y;
		startCol = x;
		
		int tries = 10;
		
		for (int i=1; i<routeSize; i++) {
			boolean chosen = false;
			tries = 0;
			while (chosen!=true && tries<10) {
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
				tries+=1;
			}
			if (tries==10) {
				break;
			}
			routeTiles[i] = boardTiles[y][x];
		}
		//PROBLEM: THIS METHOD CAN RESULT IN MORE THAN MAX NUMBER OF STOPS AT THE LAST TILE.
		//TEMPORARY SOLUTION: RETURN NULL => DISCARD THE ROUTE AND CONTINUE.
		
		if (!boardTiles[y][x].RouteAddable() || tries==10) {
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
	
	public void setPinImage(BufferedImage pin) {
		pinImage = pin;
	}
	
	public void setPinColor(Color pin) {
		pinColor = pin;
	}
	
	public BufferedImage getPinImage() {
		return pinImage;
	}
	
	public Color getPinColor() {
		return pinColor;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}