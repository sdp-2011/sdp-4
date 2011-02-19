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
					moveF(time);
				}
				else if (current.getType() == Action.Type.LEFT)
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

	private void moveF(int time)
	{
		
	}

	private void moveB(int time)
	{

	}

	private void turnLeft(int time)
	{
		
	}

	private void turnRight(int time)
	{

	}

	public void shoot()
	{

	}
}
