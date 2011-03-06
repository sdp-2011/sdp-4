package uk.ac.ed.inf.sdp.group4.world;

import uk.ac.ed.inf.sdp.group4.domain.Position;

public class Pitch
{
	// Main Geometry
	public static int TOP = 10;
	public static int BOTTOM = 305;
	public static int LEFT = 8;
	public static int RIGHT = 590;

	// Goals
	public static Position EAST_GOAL_TOP = new Position(RIGHT, 90);
	public static Position EAST_GOAL_BOTTOM = new Position(RIGHT, 234);

	public static Position WEST_GOAL_TOP = new Position(LEFT, 0);
	public static Position WEST_GOAL_BOTTOM = new Position(LEFT, 229);

	// CALCULATED POINTS
	// Center Lines
	public static int CENTER_VERTICAL = ((LEFT + RIGHT) / 2);
	public static int CENTER_HORIZONTAL = ((TOP + BOTTOM) / 2);

	// Center Point
	public static Position CENTER_SPOT = new Position(CENTER_HORIZONTAL,
			CENTER_VERTICAL);

	// Goals
	public static Position EAST_GOAL_CENTER = Position.centerPoint(EAST_GOAL_TOP,
			EAST_GOAL_BOTTOM);

	public static Position WEST_GOAL_CENTER = Position.centerPoint(WEST_GOAL_TOP,
			WEST_GOAL_BOTTOM);

	// Penalty Spots
	public static Position EAST_SPOT = Position.centerPoint(EAST_GOAL_CENTER,
			CENTER_SPOT);
	public static Position WEST_SPOT = Position.centerPoint(WEST_GOAL_CENTER,
			CENTER_SPOT);

}
