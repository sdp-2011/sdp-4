package uk.ac.ed.inf.sdp.group4.strategy;

import uk.ac.ed.inf.sdp.group4.world.Robot;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.VisionClient;
import uk.ac.ed.inf.sdp.group4.world.WorldState;
import uk.ac.ed.inf.sdp.group4.controller.Controller;
import java.lang.Math.*;
import uk.ac.ed.inf.sdp.group4.domain.*;

public class TrackBallStrategy extends Strategy
{

	private Robot robot;
	private Ball ball;

	public TrackBallStrategy(VisionClient client, Controller controller, RobotColour colour)
	{
		super(client, controller, colour);
	}

	public void runStrategy()
	{
		while (true)
		{
			refresh();
			
			Vector route = null;
			Vector robotVector = null;

			try
			{ 
				route = robot.getPosition().calcVectTo(ball.getPosition());
				robotVector = new Vector(robot.getFacing(), 0);
			}
			catch (InvalidAngleException e)
			{
				System.out.println(e.getMessage());
			}

			
			double angle = robotVector.angleTo(route);
			boolean right = (angle < 0) ? false : true;

			System.out.println();
			System.out.println("Robot is facing: " + robot.getFacing());
			System.out.println("Ball is towards: " + route.getDirection());
			System.out.println("Ball is at distance: " + route.getMagnitude());

			if (route.getMagnitude() < 50)
			{
				System.out.println("Shoooooot! " + angle);
				controller.shoot();
			}

			if (Math.abs(angle) > 10)
			{
				System.out.println("Shiftin' " + angle);

				if (right)
				{
					controller.right((int)angle);
				}
				else
				{
					controller.left((int)angle * -1);
				}

				try
				{
					Thread.sleep(3000);
				}
				catch (InterruptedException e)
				{
					
				}
			}
			else
			{	
				System.out.println("FULL STEAM AHEAD! " + angle);
				controller.drivef(Math.abs((int)route.getMagnitude()));
				try
				{
					Thread.sleep(500);
				}
				catch (InterruptedException e)
				{
					
				}
			}

		}
	}

	private void refresh()
	{
		WorldState state = client.getWorldState();

		if (ourColour() == RobotColour.BLUE)
		{
			robot = state.getBlue();
		}
		else
		{
			robot = state.getYellow();
		}

		ball = state.getBall();
	}
}
