package uk.ac.ed.inf.sdp.group4.domain;

public class Vector
{
	private int direction;
	private float magnitude;

	public Vector(int direction, float magnitude) throws InvalidAngleException
	{
		setDirection(direction);
		setMagnitude(magnitude);
	}

	public void setDirection(int direction) throws InvalidAngleException
	{
		if (direction < 0 || direction > 359)
		{
			throw new InvalidAngleException(direction);
		}
		else
		{
			this.direction = direction;
		}
	}

	public void setMagnitude(float magnitude)
	{
		this.magnitude = magnitude;
	}

	public int getDirection()
	{
		return this.direction;
	}
	
	public float getMagnitude()
	{
		return this.magnitude;
	}
}
