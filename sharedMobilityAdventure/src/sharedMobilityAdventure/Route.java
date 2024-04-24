package sharedMobilityAdventure;

import java.io.Serializable;
import java.util.Arrays;

public class Route implements Serializable {
    private static final long serialVersionUID = -2354648655745414L;
    private Tile[] routeTiles;
    private double carbonCost;
    private int travelTime;
    private int startRow;
    private int startCol;
    private int finalRow;
    private int finalCol;
    private TransportTypes type;
    private String pinName;
    private boolean isValid = true; // Checks if route is valid i.e. has cost components and valid start / end points
    
    public Route(TransportTypes T, String p, Tile[][] boardTiles, int startingRow, int startingCol, int routeSize) {
    	boolean isValidRoute = startingRow != 0 || startingCol != 0; // Do not want routes starting at (0,0) - Spawn point
    	
    	if (!isValidRoute) { // Double Check if coordinates are zero
    	    System.out.println("Invalid route: Coordinates are zero.");
    	    return;
    	}
            
        this.type = T;
        this.pinName = p;

        routeTiles = new Tile[routeSize];
        routeTiles[0] = boardTiles[startingRow][startingCol]; // Add starting Tile to Route
        int maxX = boardTiles[0].length;
        int maxY = boardTiles.length;

        int x = startingCol;
        int y = startingRow;
        
        startRow = y;
		startCol = x;
		updateTravel();

		int tries = 10;
		
		for (int i=1; i<routeSize; i++) {
			boolean chosen = false;
			tries = 0;
			while (chosen!=true && tries<10) {
				int n = Main.getRandomNumber(0,4);
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
		
		if (!boardTiles[y][x].RouteAddable(this.type, pinName, y, x) || tries==10) {
			// Discard the route if the last tile is not valid or reached max tries
			routeTiles = null;
		} else {
			finalRow = y;
			finalCol = x;
			type = T;
		}
        
    }

    public void updateTravel() {
        if (routeTiles != null && routeTiles.length > 0) { // If theres a route
            int distance = routeTiles.length;
            this.carbonCost = this.type.calculateCarbonFootprint(distance);
            double timePerTile = this.type.getSpeed();
            this.travelTime = (int) (timePerTile * distance); //Ensure its an int for simplicity
//            System.out.println("Updated travel stats: Carbon Cost=" + carbonCost + ", Travel Time=" + travelTime);
        } else {
//            System.out.println("Invalid Route!");
            isValid = false; // Set the route as invalid - Allows to remove it during validation
        }
    }


    public boolean isValid() {
        return isValid;
    }
    
    public double getCarbonCost() {
        return carbonCost;
    }

    public int getTravelTime() {
        return travelTime;
    }

    public Tile[] getTiles() {
        if (routeTiles != null) {
            return routeTiles;
        } else {
            return new Tile[0]; // Return an empty array instead of null to prevent potential NullPointerExceptions
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
		
	public String getPinName() {
		return pinName;
	}
}