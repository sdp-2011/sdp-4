package uk.ac.ed.inf.sdp.group4.strategy.astar;

import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.Robot;
import uk.ac.ed.inf.sdp.group4.world.WorldObject;

import java.util.ArrayList;

public class TrajectoryFinder
{

	private TileBasedMap map;
	private Robot robot;
	private ArrayList path = new ArrayList();

	public TrajectoryFinder(TileBasedMap map, Robot robot, Ball ball)
	{
		this.map = map;
		this.robot = robot;
	}

	public Path findPath(WorldObject worldObject, int sx, int sy, int tx, int ty)
	{
		Path path = new Path();
		double direction = robot.getFacing();

		return path;

	}

	public boolean isValidLocation(WorldObject worldObject, int x, int y)
	{
		if (map.blocked(robot, x, y))
		{
			return false;
		}
		else
		{
			return true;
		}
	}

}
