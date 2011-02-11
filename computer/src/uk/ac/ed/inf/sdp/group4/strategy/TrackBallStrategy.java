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

			
			/**
			 * The variable angle can be anywhere from -180 to +180. If it is
			 * positive then it means turn right and inversely if it is
			 * negative then it means turn left.
			 */
			double angle = robotVector.angleTo(route);
			boolean right = (angle < 0) ? false : true;

			System.out.println();
			System.out.println("Robot is facing: " + robot.getFacing());
			System.out.println("Ball is towards: " + route.getDirection());
			System.out.println("Ball is at distance: " + route.getMagnitude());

			// If we are a long turning distance from the ball then we should
			// turn towards it.
			if (Math.abs(angle) > 10)
			{
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
				// If we're close to the ball then we should shoot.
				if (route.getMagnitude() < 50)
				{
					controller.shoot();
				}
				
				// If we're not close then we should drive towards it.
				//
				// The messy distance at the end of the line is required until we get
				// accurate movement.
				controller.drivef(Math.abs((int)route.getMagnitude()/2 - 20));
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
