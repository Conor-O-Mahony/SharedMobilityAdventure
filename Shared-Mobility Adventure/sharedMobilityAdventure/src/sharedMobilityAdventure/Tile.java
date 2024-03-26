package sharedMobilityAdventure;

import java.awt.Point;

public class Tile {

	private Point coords;
	
	private Route[] tileRoutes;
	private int max_stops_per_tile = 2;
	private boolean hasGem = false; //Change to Gem class once Gem class created;
	
	public Tile(int x, int y) {
		coords = new Point(x,y);
		tileRoutes = new Route[max_stops_per_tile]; //Say the max amount of stops on a tile is 2 for now
	}
	
	public int getX() {
		return coords.x;
	}
	public int getY() {
		return coords.y;
	}
	
	public boolean hasGem() {
		if (hasGem) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean RouteAddable() {
		int counter = 0;
		for (int i = 0; i < tileRoutes.length; i ++) {
		    if (tileRoutes[i] != null) {
		        counter ++;
		    }
		}

		if (counter < max_stops_per_tile) {
			return true;
		} else {
			return false;
		}
	}
	
	public void asignRouteToTile(Route stop) { //Assume the logic is correct so that no more than max_stops_per_tile can be added. Can add test for this
		int counter = getNumberOfRoutes();
		
		if (counter < max_stops_per_tile) {
			tileRoutes[counter] = stop;
		} else {
			System.out.println("Error: too many stops on this tile.");
		}
	}
	
	public int getNumberOfRoutes() {
		int counter = 0;
		for (int i = 0; i < tileRoutes.length; i ++) {
		    if (tileRoutes[i] != null) {
		    	counter++;
		    }
		}
		return counter;
	}
	
	public Route[] getRoutes() {
		return tileRoutes;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
