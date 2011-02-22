package uk.ac.ed.inf.sdp.group4.strategy;


import uk.ac.ed.inf.sdp.group4.world.Robot;

/**
 * A heuristic that uses the tile that is closest to the target
 * as the next best tile.
 *
 * @author Kevin Glass
 */
public class RobotHeuristicA implements AStarHeuristic
{
	/**
	 * @see AStarHeuristic#getCost(TileBasedMap, Mover, int, int, int, int)
	 */
	public float getCost(TileBasedMap map, WorldObject robot, int x, int y, int tx, int ty)
	{
		float result = (float)(Math.sqrt(Math.pow((x - tx), 2) + Math.pow((y - ty), 2)));
		return result;
	}

}
