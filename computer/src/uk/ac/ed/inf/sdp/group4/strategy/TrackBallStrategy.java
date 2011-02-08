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

			try
			{ 
				route = robot.getPosition().calcVectTo(ball.getPosition());
			}
			catch (InvalidAngleException e)
			{
				System.out.println(e.getMessage());
			}
			
			double angle = ((robot.getFacing()- route.getDirection()) + 360) % 360;
			System.out.println("Angle: " + angle);

			if (angle > 10)
			{
				System.out.println("Shiftin' " + angle);
				controller.right((int)angle);
			}
			else
			{	
				System.out.println("FULL STEAM AHEAD! " + angle);
				controller.drivef(10);
			}

			try
			{
				Thread.sleep(3000);
			}
			catch (InterruptedException e)
			{
				
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
