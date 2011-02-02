package uk.ac.ed.inf.sdp.group4.world;

import uk.ac.ed.inf.sdp.group4.domain.Position;
import uk.ac.ed.inf.sdp.group4.domain.Vector;

abstract class WorldObject
{
	private Position position;
	private Vector vector;

	public void setPosition(int x, int y)
	{
		this.position = new Postition(x, y);
	}

	public Position getPosition()
	{
		return this.position;
	}

	public void setVector(int direction, float magnitude)
	{
		this.vector = new Vector(direction, magnitude);
	}

	public Vector getVector()
	{
		return this.vector;
	}
}
