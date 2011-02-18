package uk.ac.ed.inf.sdp.group4.sim;

import uk.ac.ed.inf.sdp.group4.world.Robot;
import uk.ac.ed.inf.sdp.group4.domain.Position;
import uk.ac.ed.inf.sdp.group4.domain.Vector;
import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;
import java.lang.Math;

public class SimBot extends Component
{
	private Robot robot;
	private Action current;
	private final double TURN_SPEED = 1;
	private double x;
	private double y;

	public SimBot(Robot robot)
	{
		this.robot = robot;
		this.x = robot.getX();
		this.y = robot.getY();
	}

	public void update(int time)
	{
		if (current != null)
		{
			if (current.isDone())
			{
				current = null;	
			}

			else
			{
				if (current.getType() == Action.Type.FORWARD)
				{
					move(time);
				}
				else if (current.getType() == Action.Type.TURN)
				{
					turnLeft(time);
				}
			}
		}
	}

	public void newAction(Action action)
	{
		current = action;
	}

	private void move(int time)
	{
		Vector direction = robot.getVector();
		direction.setMagnitude(8);		
	
		Position original = robot.getPosition();
		
		double speedX = (direction.getMagnitude() / (time / 1000.0)) * 
			Math.cos(Math.toRadians(direction.getDirection()));
		double speedY = (direction.getMagnitude() / (time / 1000.0)) * 
			Math.sin(Math.toRadians(direction.getDirection()));

		x = x + speedX;
		y = y + speedY;
		
		robot.setX((int) x);
		robot.setY((int) y);

		current.addProgress(original.distance(robot.getPosition()));
	}

	private void turnLeft(int time)
	{
		Vector direction = robot.getVector();
		double original = direction.getDirection();

		try
		{
			direction.setDirection(direction.getDirection() - (((360 / TURN_SPEED) / 1000) * time));
		}
		catch (InvalidAngleException e)
		{
			//
		}
		current.addProgress(direction.getDirection() - original);
	}

	public void shoot()
	{

	}
}
