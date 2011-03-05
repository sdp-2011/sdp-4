package uk.ac.ed.inf.sdp.group4.world;

import uk.ac.ed.inf.sdp.group4.domain.Position;

public class Pitch
{
    // Main Geometry
    public static int TOP = 0;
    public static int BOTTOM = 0;
    public static int LEFT = 0;
    public static int RIGHT = 0;


    // Penalty Spots
    public static Position EAST_SPOT = new Position(0, 0);
    public static Position WEST_SPOT = new Position(0, 0);

    // Goals
    public static Position EAST_GOAL_TOP = new Position(0, 0);
    public static Position EAST_GOAL_BOTTOM = new Position(0, 0);

    public static Position WEST_GOAL_TOP = new Position(0, 0);
    public static Position WEST_GOAL_BOTTOM = new Position(0, 0);

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

}
