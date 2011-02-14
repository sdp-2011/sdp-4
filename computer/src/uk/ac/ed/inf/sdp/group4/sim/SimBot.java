package uk.ac.ed.inf.sdp.group4.sim;

import uk.ac.ed.inf.sdp.group4.world.Robot;
import uk.ac.ed.inf.sdp.group4.domain.Position;
import uk.ac.ed.inf.sdp.group4.domain.Vector;
import java.lang.Math;

public class SimBot extends Component
{
	private Robot robot;
	private Action current;
	private final double TURN_SPEED = 1;

	public SimBot(Robot robot)
	{
		this.robot = robot;
	}

	public void update(int time)
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
		}
	}

	public void newAction(Action action)
	{
		current = action;
	}

	private void move(int time)
	{
		Vector direction = robot.getVector();
		Position original = robot.getPosition();
		
		double speedX = (direction.getMagnitude() / (time / 1000.0)) * 
			Math.cos(Math.toRadians(direction.getDirection()));
		double speedY = (direction.getMagnitude() / (time / 1000.0)) * 
			Math.sin(Math.toRadians(direction.getDirection()));

		robot.setX((int) (robot.getX() + speedX));
		robot.setY((int) (robot.getY() + speedY));

		current.addProgress(original.distance(robot.getPosition()));
	}

	private void turn(int time)
	{

	}

	public void shoot()
	{

	}
}
