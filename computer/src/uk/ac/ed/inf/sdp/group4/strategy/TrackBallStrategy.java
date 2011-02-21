package uk.ac.ed.inf.sdp.group4.strategy;

import java.lang.Math.*;


import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.domain.*;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.Robot;
import uk.ac.ed.inf.sdp.group4.world.VisionClient;
import uk.ac.ed.inf.sdp.group4.world.IVisionClient;
import uk.ac.ed.inf.sdp.group4.world.WorldState;

public class TrackBallStrategy extends Strategy
{
	private Robot robot;
	private Robot enemyRobot;
	private Ball ball;
	private WorldState state;

	Position westGoal = new Position(30, 162);
	Position eastGoal = new Position(525, 162);

	public TrackBallStrategy(IVisionClient client, Controller controller, RobotColour colour)
	{
		this(client, controller, colour, false);
	}

	public TrackBallStrategy(IVisionClient client, Controller controller, RobotColour colour, boolean testing)
	{
		super(client, controller, colour, testing);
	}

	public TrackBallStrategy(Controller controller, WorldState state)
	{
		super(null, controller, RobotColour.BLUE, false);
		this.state = state;
	}

	@Override
	public void tick()
	{
		log.debug("=================================================");
		log.debug("Starting a new cycle...");

		// Get the world state.
		refresh();

		/**
		 * The angle variables can be anywhere from -180 to +180.
		 *
		 * If it is positive then it means turn right and inversely if it is
		 * negative then it means turn left.
		 */
		Vector ballRoute = getBallRoute();
		double ballAngle = ballRoute.angleTo(robot.getFacing());

		Vector goalRoute = getGoalRoute();
		double goalAngle = goalRoute.angleTo(robot.getFacing());

		// If we are a long turning distance from the ball then we should
		// turn towards it.
		if (Math.abs(ballAngle) > 15)
		{
			controller.turn(ballAngle);
			pause(1000);
		}
		else
		{
			// If we're close to the ball and the goal is close then we should shoot.
			if (ballRoute.getMagnitude() < 30 && goalRoute.getMagnitude() < 200 && Math.abs(goalAngle) < 20)
			{
				controller.shoot();
			}
			// If we're close to the ball and the goal is far then we should
			// drive with a ball to the goal.
			else if (ballRoute.getMagnitude() < 30 && goalRoute.getMagnitude() >= 200)
			{
				log.debug("Driving to the goal!");
				if (Math.abs(goalAngle) > 15)
				{
					controller.setSpeed(100);
					controller.turn(goalAngle);
					pause(1000);
				}
				else
				{
					controller.stop();
					controller.setSpeed(300);
					controller.driveForward((int)goalRoute.getMagnitude() / 8);
					pause(1000);
				}
			}

			// If we're not close then we should drive towards it.
			//
			// The messy distance at the end of the line is required until we get
			// accurate movement.
			if (ballRoute.getMagnitude() > 40)
			{
				controller.stop();
				controller.setSpeed(900);
				controller.driveForward((int)ballRoute.getMagnitude() / 8);
				pause(1000);
			}
			else
			{
				controller.stop();
				controller.setSpeed(100);
				controller.driveForward((int)ballRoute.getMagnitude() / 8);
				pause(1300);
			}
		}
	}


	private void refresh()
	{
		if (client != null)
		{
			WorldState state = client.getWorldState();
		}

		robot = (ourColour() == RobotColour.BLUE) ? state.getBlue() : state.getYellow();
		enemyRobot = (ourColour() == RobotColour.BLUE) ? state.getYellow() : state.getBlue();
		ball = state.getBall();

		log.debug("Robot is facing: " + robot.getFacing());
	}

	private Vector getGoalRoute()
	{
		Vector goalRoute = null;

		try
		{
			goalRoute = robot.getPosition().calcVectTo(eastGoal);
		}
		catch (InvalidAngleException e)
		{
			log.error(e.getMessage());
		}

		log.debug("    Goal is towards: " + goalRoute.getDirection());
		log.debug("Goal is at distance: " + goalRoute.getMagnitude());

		return goalRoute;
	}

	private Vector getBallRoute()
	{
		Vector route = null;

		try
		{
			route = robot.getPosition().calcVectTo(ball.getPosition());
		}
		catch (InvalidAngleException e)
		{
			log.error(e.getMessage());
		}

		log.debug("    Ball is towards: " + route.getDirection());
		log.debug("Ball is at distance: " + route.getMagnitude());

		return route;
	}
}

