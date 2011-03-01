package uk.ac.ed.inf.sdp.group4.strategy;

import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;
import uk.ac.ed.inf.sdp.group4.domain.Position;
import uk.ac.ed.inf.sdp.group4.domain.Vector;
import uk.ac.ed.inf.sdp.group4.strategy.Path.Step;
import uk.ac.ed.inf.sdp.group4.world.IVisionClient;
import uk.ac.ed.inf.sdp.group4.world.Robot;
import uk.ac.ed.inf.sdp.group4.world.Ball;


public class Match extends Strategy
{
	IVisionClient client;
	Controller controller;
	RobotColour robotColour;
	boolean testing;

	public Pitch pitch;
	// Our pathfinders
	private PathFinder pathfinder;
	//private PathFinder trajectory;

	// The path for our robot
	public Path path;

	// Declarations for robots
	private Robot ourRobot;
	private Robot otherBot;
	private Ball ball;

	// estimated goal positions
	Position westGoal = new Position(30, 122);
	Position eastGoal = new Position(445, 122);

	// Boolean for determining if we are in play or not
	float usToBall;
	float themToBall;
	float usToThem;

	public Match(IVisionClient client, Controller controller, RobotColour robotColour, boolean testing)
	{

		super(client, controller, robotColour, testing);
		pitch = new Pitch(client, ourColour());
		ourRobot = pitch.ourRobot;
		otherBot = pitch.theirRobot;
		ball = pitch.ball;
	}
	/*
		public Match(Controller controller, WorldState state)
		{
			super(null, controller, RobotColour.BLUE, false);
		}
	*/
	public void tick()
	{
		pathfinder = new AStarPathFinder(pitch, 5000);
		//trajectory = new TrajectoryFinder(pitch, ourRobot);

		log.debug("Starting tick");
		pitch.repaint();
		// These will probably be useful eventually
		usToBall = (float)(Math.sqrt(Math.pow((ourRobot.getX() - ball.getX()), 2) + Math.pow((ourRobot.getY() - ball.getY()), 2)));
		themToBall = (float)(Math.sqrt(Math.pow((otherBot.getX() - ball.getX()), 2) + Math.pow((ourRobot.getY() - ball.getY()), 2)));
		usToThem = (float)(Math.sqrt(Math.pow((ourRobot.getX() - otherBot.getX()), 2) + Math.pow((ourRobot.getY() - otherBot.getY()), 2)));

		// Check if we have the ball

		try
		{
			while (ourRobot.hasBall(ball) == false)
			{
				// Check if it's worth trying to get to the ball, using ratio of distance between us and opponent
				if ((usToBall / themToBall) > 0)
				{
					path = pathfinder.findPath(ourRobot, ourRobot.getX(), ourRobot.getY(), ball.getX(), ball.getY());
					if (path != null)
					{
						// follow path
						log.debug("Moving to ball");
						Step step = path.getStep(25);
						Position stepPos = new Position(step.getX(), step.getY());
						Position robotPos = new Position(ourRobot.getX(), ourRobot.getY());
						Vector stepBot = Vector.calcVect(robotPos, stepPos);
						double ang = stepBot.angleTo(ourRobot.getFacing());
						if ((ang < 15) && (ang > -15))
						{
							controller.setSpeed(150);
							pause(100);
						}
						else
						{
							controller.stop();
							pause(100);
							controller.turn(ang);
							pause(100);
							controller.setSpeed(150);
							pause(100);
						}

					}
					else
					{
						log.debug("Can't move to ball");
						// can't get too ball, play defensively until we get it
					}
				}
				else
				{
					// Defensive play
				}
			}
		}
		catch (InvalidAngleException e)
		{
			log.error(e.getMessage());
		}
		try
		{
			while (ourRobot.hasBall(ball) == true)
			{
				// Goal-finding and scoring algorithm here
				log.debug("We has ball");
				Vector botToGoal = Vector.calcVect(ourRobot.getPosition(), westGoal);
				double ang = botToGoal.angleTo(ourRobot.getFacing());
				// Can we shoot? If not move towards goal for better shot
				if (ang < 15)
				{
					if (canShoot(ourRobot, ball) == true)
					{
						log.debug("Firing main cannon");
						controller.shoot();
						pause(100);
						controller.stop();
						pause(100);
					}
					else
					{
						path = pathfinder.findPath(ourRobot, ourRobot.getX(), ourRobot.getY(), westGoal.getX(), westGoal.getY());
						if (path != null)
						{
							// follow path
							log.debug("Moving to goal with ball");
							Step step = path.getStep(25);
							Position stepPos = new Position(step.getX(), step.getY());
							Position robotPos = new Position(ourRobot.getX(), ourRobot.getY());
							Vector stepBot = Vector.calcVect(robotPos, stepPos);
							double angTo = stepBot.angleTo(ourRobot.getFacing());
							if ((angTo < 15) && (ang > -15))
							{
								controller.setSpeed(150);
								pause(100);
							}
							else
							{
								controller.stop();
								pause(100);
								controller.turn(angTo);
								pause(100);
								controller.setSpeed(150);
								pause(100);
							}
						}
						else
						{
							log.debug("Can't move to goal :(");
							controller.stop();
							pause(100);
						}
					}
				}
				else
				{
					log.debug("Turning and shooting");
					controller.turn(ang);
					pause(100);
					controller.shoot();
					pause(100);

				}
			}
		}
		catch (InvalidAngleException e)
		{
			log.error(e.getMessage());
		}
	}


	// Checks if shot is blocked
	public boolean canShoot(Robot robot, Ball ball)
	{
		double facing = ourRobot.getFacing();
		boolean blocked = false;
		for (int y = robot.getY(); y < westGoal.getY(); y--)
		{
			int x = (int)(y * Math.tan(facing) + ball.getX() + ball.getY() * Math.tan(facing));
			blocked = pitch.blocked(ball, x, y);
			if (blocked = true)
			{
				break;
			}
		}
		return blocked;


	}

	public void attack()
	{
	}

	public void defend()
	{
	}
}
