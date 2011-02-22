package uk.ac.ed.inf.sdp.group4.strategy;

import java.util.ArrayList;

public class TrajectoryFinder implements PathFinder{

	private TileBasedMap map;
	private Robot robot;
	private ArrayList path = new ArrayList();
	
	public TrajectoryFinder(TileBasedMap map, Robot robot){
		this.map = map;
		this.robot = robot;
	}
	
	public Path findPath(Mover mover, int sx, int sy, int tx, int ty) {
		if (robot.hasBall() == false){
			return null;
		}
		else {
			Path path = new Path();
			float direction = robot.getDirection();
			return path;
		}
	}

	public boolean isValidLocation(Mover mover, int x, int y){
		if (map.blocked(mover, x, y)){
			return false;
		}
		else return true;
	}
	
}
