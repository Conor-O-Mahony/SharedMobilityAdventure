package sharedMobilityAdventure;

import java.awt.Point;

public class Tile {

	private Point coords;
	
	private TransportTypes[] stops;
	
	public Tile(int x, int y) {
		coords = new Point(x,y);
		stops = new TransportTypes[2]; //Say the max amount of stops on a tile is 2 for now
	}
	
	public int getX() {
		return coords.x;
	}
	public int getY() {
		return coords.y;
	}
	
	public void asignTransportType(TransportTypes stop) {
		int index = stops.length;
		stops[index] = stop;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
