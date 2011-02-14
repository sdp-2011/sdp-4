package uk.ac.ed.inf.sdp.group4.strategy;

import java.lang.Math.*;


import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.domain.*;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.Robot;
import uk.ac.ed.inf.sdp.group4.world.VisionClient;
import uk.ac.ed.inf.sdp.group4.world.WorldState;

public class TrackBallStrategy extends Strategy
{
	private Robot robot;
	private Ball ball;
        int goalNortX = 0;  //need to get real goal positions
        int goalNorthY = 0;
        int goalSouthX =0;
        int goalSouthYb=0;

	public TrackBallStrategy(VisionClient client, Controller controller, RobotColour colour)
	{
		super(client, controller, colour);
	}

	public void runStrategy()
	{
		log.debug("Starting strategy loop...");
		while (true)
		{
			log.debug("Starting a new cycle...");
			refresh();
			
			Vector route = null;
			Vector robotVector = null;
                        Vector robotGoal = null;

			try
			{ 
				route = robot.getPosition().calcVectTo(ball.getPosition());
				robotVector = new Vector(robot.getFacing(), 0);
                                robotGoal = robot.getPosition().calcVectTo(goalNorthX, goalNorthY);
			}
			catch (InvalidAngleException e)
			{
				log.error(e.getMessage());
			}

			
			/**
			 * The variable angle can be anywhere from -180 to +180. If it is
			 * positive then it means turn right and inversely if it is
			 * negative then it means turn left.
			 */
			double angle = route.angleTo(robot.getFacing());
			boolean right = (angle < 0) ? false : true;

                        double angle2 = robotVector.angleTo(robotGoal);
                        boolean right2 = (angle < 0) ? false : true;

			log.debug("Robot is facing: " + robot.getFacing());
			log.debug("Ball is towards: " + route.getDirection());
			log.debug("Ball is at distance: " + route.getMagnitude());
                        log.debug("Goal is at distance: " + robotGoal.getMagnitude());

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
				// If we're close to the ball and the goal is close  then we should shoot.
				if (route.getMagnitude() < 50 && robotGoal.getMagnitude() < 200)
                                {
                                System.out.println("Shoooooot! " + angle);
                                controller.shoot();
                                }
				
                                // If we're close to the ball and the goal is far  then we should drive with a ball to the goal.
                                if (route.getMagnitude() < 50 && robotGoal.getMagnitude() >= 200)
                                {

                                System.out.println("Driving to the goal! " + angle2);
                                          if (Math.abs(angle2) > 10)
                                          {
                                          System.out.println("Shiftin' " + angle2);

                                                if (right2)
                                                {
                                                controller.right((int)angle2);
                                                 }
                                                else
                                                {
                                                controller.left((int)angle2 * -1);
                                                 }
                                            }
                                          else 
                                          {
                                          System.out.println("drive! ");
                                          controller.drivef(Math.abs((int)RobotGoal.getMagnitude()/2 - 20));
                                          }
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
