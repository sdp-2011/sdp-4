package helper;

import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;
import uk.ac.ed.inf.sdp.group4.world.WorldObject;

public class MockWorldObject extends WorldObject
{
	public MockWorldObject(int x, int y, int direction, float magnitude) throws InvalidAngleException
	{
		setPosition(x, y);
		setVector(direction, magnitude);
	}
}
