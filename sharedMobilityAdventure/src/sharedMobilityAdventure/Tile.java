
package sharedMobilityAdventure;

import java.awt.Point;
import java.io.Serializable;

public class Tile implements Serializable {

	private static final long serialVersionUID = -1656805231857104479L;

	private Point coords;
	
	private Route[] tileRoutes;
	private TransportTypes[] routeTypes;
	private int max_stops_per_tile = 2;
	//private int[] popups; // Change to popup class once created
	private boolean hasGem = false; //Change to Gem class once Gem class created;
	
	public Tile(int x, int y) {
		coords = new Point(x,y);
		tileRoutes = new Route[max_stops_per_tile]; //Say the max amount of stops on a tile is 2 for now
		routeTypes = new TransportTypes[max_stops_per_tile];
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
			TransportTypes stoptype = stop.getTransportType();
			routeTypes[counter] = stoptype;
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
	
	public TransportTypes[] getRouteTypes() {
		return routeTypes;
	}

	public static void main(String[] args) {

	}

}
 