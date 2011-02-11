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

			System.out.println("GO TOWARDS: " +  Double.toString(angle));
			System.out.println("Robot is facing: " + robot.getFacing());
			System.out.println("Ball is towards: " + route.getDirection());
			System.out.println("Ball is at distance: " + route.getMagnitude());
			System.out.println();

			if (Math.abs(robotVector.getDirection() + 180 - route.getDirection()) < 20)
			{
			
				if (route.getMagnitude() < 30)
				{

					System.out.println("Shoooooot! " + angle);
					controller.shoot();

					try 
					{
						Thread.sleep(200);
					} 
					catch (InterruptedException e)
					{
						System.out.println("Uh oh, spaghettioh...");	
					}

				}
				else if (route.getMagnitude() < 60)
				{

					System.out.println("Run em down!");							
					controller.drivef(5);

					try 
					{
						Thread.sleep(150);
					} 
					catch (InterruptedException e)
					{
						System.out.println("Uh oh, spaghettioh...");	
					}

				} 
				else
				{

					System.out.println("Run em down!");							
					controller.drivef(10);

					try 
					{
						Thread.sleep(200);
					} 
					catch (InterruptedException e)
					{
						System.out.println("Uh oh, spaghettioh...");	
					}

				} 

			}
			else 
			{ 

				System.out.println("Turn ye fecker!");

				int toturn;

				if (angle >= 0)
				{

					System.out.println("Avast! Left be right!");
					toturn = (int)(180 - angle);
					controller.right(toturn);

				} 
				else 				
				{
					
					System.out.println("Right me hearties!");
					toturn = (int)(180 + angle);
					controller.left(toturn);

				}

				try 
				{
					Thread.sleep(2000);
				} 
				catch (InterruptedException e)
				{
					System.out.println("Uh oh, spaghettioh...");	
				}
			}

/*
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
				controller.drivef(Math.abs((int)route.getMagnitude()/2 - 20));
				try
				{
					while(
					if (route.getMagnitude() < 50)
					{
						System.out.println("Shoooooot! " + angle);
						controller.shoot();
					}

					Thread.sleep(500);
				}
				catch (InterruptedException e)
				{
					
				}
			
			}
			*/
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
