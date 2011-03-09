package uk.ac.ed.inf.sdp.group4.domain;

public class Vector
{
	private double direction;
	private double magnitude;

	public Vector(double direction, double magnitude) throws InvalidAngleException
	{
		setDirection(direction);
		setMagnitude(magnitude);
	}

	public void setDirection(double direction) throws InvalidAngleException
	{
		if (direction < 0 || direction >= 360)
		{
			throw new InvalidAngleException(direction);
		}
		else
		{
			this.direction = direction;
		}
	}

	public void setMagnitude(double magnitude)
	{
		this.magnitude = magnitude;
	}

	public double getDirection()
	{
		return this.direction;
	}

	public double getMagnitude()
	{
		return this.magnitude;
	}

	public static Vector calcVect(Position ini, Position dest) throws InvalidAngleException
	{
		double x = dest.getX() - ini.getX();
		double y = dest.getY() - ini.getY();

		double mag = Math.sqrt((x * x) + (y * y));
		double angle = (Math.toDegrees(Math.atan2(y, x)) + 450) % 360;

		return new Vector(angle, mag);
	}

	public double angleFrom(double bearing)
	{
		double from = this.getDirection();
		double angle = from - bearing;

		if (angle > 180)
		{
			angle = -1 * (360 - angle);
		}

		return angle;
	}
}
