package sharedMobilityAdventure;

import java.awt.Point;
import java.lang.Math;

public class Route {
	
	private TransportTypes transport;
	private Point start, end;
	private int manhattan;
	
	public Route(TransportTypes T, int startX, int startY, int endX, int endY) {
		if (CheckUniqueness(startX,startY)==0 || CheckUniqueness(endX,endY)==0) {
			transport = T;
			start = new Point(startX,startY);
			end = new Point(endX,endY);
			CalculateManhattan();
			
		}
	}
	
	private int CheckUniqueness(int x, int y) {
		if (/*Route already exists at location*/) {
			return 1;
		} else {
			return 0;
		}
	}
	
	private void GenerateRoute() {
		
	}
	
	private void CalculateManhattan() {
		manhattan = Math.abs(start.x - end.x) + Math.abs(start.y - end.y);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}