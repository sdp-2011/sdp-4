package uk.ac.ed.inf.sdp.group4.strategy.tactic;

import org.apache.log4j.Logger;
import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;
import uk.ac.ed.inf.sdp.group4.domain.Position;
import uk.ac.ed.inf.sdp.group4.domain.Vector;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.Robot;

public class PenaltySaveTactic extends Tactic
{
	protected static Logger log = Logger.getLogger(PenaltySaveTactic.class);

	private final double BALL_MOVE_THRESHOLD = 5;
	private Position initialBallPosition = null;
	private int state = 1;
	// 0 is south

    Position westGoal = new Position(30, 162);
    Position eastGoal = new Position(525, 162);

	public PenaltySaveTactic(Controller controller)
	{
		this(controller, false);
	}

	public PenaltySaveTactic(Controller controller, boolean testing)
	{
		super(controller, testing);
	}

	@Override
	public void tick(Robot ours, Robot enemy, Ball ball)
	{
	
		Position ballStart = ball.getPosition();

		while (ball.getPosition().distance(ballStart) < 10)
		{
			if (ours.getX() < 162)
			{
				System.out.println("DEFENDING WEST");
			
				if (enemy.getFacing() > 285)
				{
					System.out.println("They are shooting north");

					if (state == 2)
					{
						System.out.println("We should be in the way");
					}
					else
					{
						System.out.println("Move up");
						controller.setSpeed(900);
						controller.driveForward(10);
						state ++;
						pause(120);
					}				
				}
				else if (enemy.getFacing() < 265)
				{			
					System.out.println("They are shooting south");

					if (state == 0)
					{
						System.out.println("We should be in the way");
					}
					else
					{
						System.out.println("Move down");
						controller.setSpeed(900);
						controller.driveBackward(10);
						state --;
						pause(120);
					}			
				}
				else
				{
					System.out.println("They are shooting centrally");
				
					if (state == 1)
					{
						System.out.println("We should be in the way");
					}
					else if (state == 2)
					{
						System.out.println("Move down");
						controller.setSpeed(900);
						controller.driveBackward(10);
						state --;
						pause(120);
					}			
					if (state == 0)
					{
						System.out.println("We should be in the way");
					}
					else
					{
						System.out.println("Move up");
						controller.setSpeed(900);
						controller.driveForward(10);
						state ++;
						pause(120);
					}			
				}
			}
			else
				{
				System.out.println("DEFENDING EAST");
			
				if (enemy.getFacing() < 75)
				{
					System.out.println("They are shooting north");

					if (state == 2)
					{
						System.out.println("We should be in the way");
					}
					else
					{
						System.out.println("Move up");
						controller.setSpeed(900);
						controller.driveForward(10);
						state ++;
						pause(120);
					}				
				}
				else if (enemy.getFacing() > 105)
				{			
					System.out.println("They are shooting south");

					if (state == 0)
					{
						System.out.println("We should be in the way");
					}
					else
					{
						System.out.println("Move down");
						controller.setSpeed(900);
						controller.driveBackward(10);
						state --;
						pause(120);
					}			
				}
				else
				{
					System.out.println("They are shooting centrally");
				
					if (state == 1)
					{
						System.out.println("We should be in the way");
					}
					else if (state == 2)
					{
						System.out.println("Move down");
						controller.setSpeed(900);
						controller.driveBackward(10);
						state --;
						pause(120);
					}			
									if (state == 0)
					{
						System.out.println("We should be in the way");
					}
					else
					{
						System.out.println("Move up");
						controller.setSpeed(900);
						controller.driveForward(10);
						state ++;
						pause(120);
					}			
				}
			}
		}
	}

	private Vector getBallRoute(Robot robot, Ball ball)
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

		log.debug("Ball is towards: " + route.getDirection());
		log.debug("Ball is at distance: " + route.getMagnitude());

		return route;
	}
}

