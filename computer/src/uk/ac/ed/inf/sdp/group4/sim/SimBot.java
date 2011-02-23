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
					robot.getVector().setMagnitude(40);
					move(time);
				}
				else if (current.getType() == Action.Type.REVERSE)
				{
					robot.getVector().setMagnitude(-40);
					move(time);
				}
				else if (current.getType() == Action.Type.LEFT)
				{
					turnLeft(time);
				}
				else if (current.getType() == Action.Type.RIGHT)
				{
					turnRight(time);
				}
				else if (current.getType() == Action.Type.SHOOT)
				{
					shoot(time);
				}
			}
		}

		robot.setPosition((int) x, (int) y);
	}

	public void newAction(Action action)
	{
		current = action;
	}

	private void move(int time)
	{
		Vector vector = robot.getVector();

		double speed = (vector.getMagnitude() / 1000) * time;
		double speedX = speed * Math.cos(Math.toRadians(vector.getDirection()));
		double speedY = speed * Math.sin(Math.toRadians(vector.getDirection()));

		x += speedX;
		y += speedY;

		current.addProgress(robot.getPosition().distance(new Position((int) x, (int) y)));
	}

	private void turnLeft(int time)
	{
		Vector vector = robot.getVector();

		double angle = 0.36 * time;

		try
		{
			double direction = vector.getDirection();

			if ((direction - angle) < 0)
			{
				vector.setDirection(360 - (direction - angle));
			}

			else
			{
				vector.setDirection(direction - angle);
			}

			robot.setFacing(robot.getVector().getDirection());
		}

		catch (InvalidAngleException e)
		{
			System.out.println("Invalid angle: left turn");
		}

		current.addProgress(angle);
	}

	private void turnRight(int time)
	{
		Vector vector = robot.getVector();

		double angle = 0.36 * time;

		try
		{
			double direction = vector.getDirection();

			if ((direction + angle) > 360)
			{
				vector.setDirection((direction + angle) - 360);
			}

			else
			{
				vector.setDirection(direction + angle);
			}

			robot.setFacing(robot.getVector().getDirection());
		}

		catch (InvalidAngleException e)
		{
			System.out.println("Invalid angle: right turn");
		}

		current.addProgress(angle);
	}

	public void shoot(int time)
	{

	}
}