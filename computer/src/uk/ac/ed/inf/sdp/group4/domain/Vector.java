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
		if (direction < 0 || direction > 359)
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

		double mag = Math.sqrt((x*x) + (y*y));
		double angle = Math.atan(Math.toRadians(y/x));
		System.out.println(mag);
		System.out.println(angle);
		Vector v = new Vector(angle, mag);
		
		return v;
	}
}
