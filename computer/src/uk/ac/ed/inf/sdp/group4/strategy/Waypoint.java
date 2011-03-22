package uk.ac.ed.inf.sdp.group4.strategy;

import static com.google.common.base.Preconditions.*;
import uk.ac.ed.inf.sdp.group4.domain.Position;

public class Waypoint
{
	private Position position;
	private int angle;
	
	public Waypoint(Position position, int angle)
	{
		setPosition(position);
		setAngle(angle);
	}

	public void setPosition(Position position)
	{
		this.position = checkNotNull(position);
	}

	public Position getPosition() 
	{
		return position;
	}

	public void setAngle(int angle)
	{
		this.angle = checkNotNull(angle);
	}

	public int getAngle()
	{
		return angle;
	}
	
	public int getX()
	{
		return this.position.getX();
	}
	
	public int getY()
	{
		return this.position.getY();
	}
	
}
