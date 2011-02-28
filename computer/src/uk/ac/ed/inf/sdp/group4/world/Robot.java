package uk.ac.ed.inf.sdp.group4.world;

import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;
import uk.ac.ed.inf.sdp.group4.world.WorldObject;
import uk.ac.ed.inf.sdp.group4.strategy.RobotColour;
import uk.ac.ed.inf.sdp.group4.domain.Vector;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import java.lang.Math;

public class Robot extends WorldObject
{
	private double facing;
	private RobotColour colour;

	public Robot(int x, int y, int direction, float speed, double facing, RobotColour colour) throws InvalidAngleException
	{
		setPosition(x, y);
		setVector(direction, speed);
		setFacing(facing);
		setColour(colour);
	}

	public void setFacing(double facing) throws InvalidAngleException
	{
		if (facing < 0 || facing > 359)
		{
			throw new InvalidAngleException(facing);
		}
		else
		{
			this.facing = facing;
		}
	}

	public double getFacing()
	{
		return this.facing;
	}

	public double getRadFacing()
	{
		return Math.toRadians(this.facing);
	}

	public void setColour(RobotColour colour)
	{
		this.colour = colour;
	}

	public RobotColour getColour()
	{
		return this.colour;
	}
	
	// Added for Andrew's strategy system
	public boolean hasBall(Ball ball) throws InvalidAngleException
	{
		Vector ballToBot = Vector.calcVect(this.getPosition(), ball.getPosition());
		double mag = ballToBot.getMagnitude();
		double dir = ballToBot.angleTo(this.getFacing());
		if ((mag < 10) && ((dir < 16) || (dir > -16))){
			return true;
		}
		else return false;
	}
}
