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
			try
			{
				Thread.sleep(3000);
			}
			catch (InterruptedException e)
			{
				
			}
			
			refresh();
			
			Vector route = null;

			try
			{ 
				route = robot.getPosition().calcVectTo(ball.getPosition());
			}
			catch (InvalidAngleException e)
			{
				System.out.println(e.getMessage());
			}
			
			double robotMinusBall = robot.getFacing()- route.getDirection();
			double ballMinusRobot = route.getDirection() - robot.getFacing();

			if (Math.abs(robotMinusBall) > Math.abs(ballMinusRobot))
			{
					controller.right((int)ballMinusRobot);
			}
			else 
			{
					controller.right((int)robotMinusBall);
			}
			
			controller.drivef(10);
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
