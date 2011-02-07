package uk.ac.ed.inf.sdp.group4.world;

import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;

public class Ball extends WorldObject
{
	public Ball(int x, int y, int direction, float speed) throws InvalidAngleException
	{
		setPosition(x, y);
		setVector(direction, speed);
	}
}
