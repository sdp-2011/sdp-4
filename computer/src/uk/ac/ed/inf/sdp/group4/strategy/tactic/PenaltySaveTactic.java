package uk.ac.ed.inf.sdp.group4.strategy.tactic;

import org.apache.log4j.Logger;
import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;
import uk.ac.ed.inf.sdp.group4.domain.Position;
import uk.ac.ed.inf.sdp.group4.domain.Vector;
import uk.ac.ed.inf.sdp.group4.utils.Utils;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.Robot;

public class PenaltySaveTactic extends Tactic
{
	protected static Logger log = Logger.getLogger(PenaltySaveTactic.class);

	private enum KeeperPosition
	{
		NORTH,
		CENTER,
		SOUTH
	}
	
	private final double BALL_MOVE_THRESHOLD = 5;
	private Position initialBallPosition = null;
	private KeeperPosition state = KeeperPosition.CENTER;

    private Position westGoal = new Position(30, 162);
    private Position eastGoal = new Position(525, 162);

	private boolean initialised = false;
	private Position ballStart;
	
	private boolean done;

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
		if (ball.getPosition().distance(ballStart) > 10)
		{
			System.out.println("ENDING.");
			this.initialised = false;
			this.done = true;
			return;
		}
		
		System.out.println("OldBallLoc:" + ballStart.getX() + " " + ballStart.getY());
		System.out.println("BallLoc:" +  ball.getPosition().getX() + " " +  ball.getPosition().getY());

		if (enemy.isAimingNorth())
		{
			System.out.println("They are shooting north");

			if (state == KeeperPosition.NORTH)
			{
				System.out.println("We should be in the way");
			}
			else
			{
				moveKeeper(state, KeeperPosition.NORTH);
			}				
		}
		else if (enemy.isAimingSouth())
		{			
			System.out.println("They are shooting south");

			if (state == KeeperPosition.SOUTH)
			{
				System.out.println("We should be in the way");
			}
			else
			{
				moveKeeper(state, KeeperPosition.SOUTH);
			}			
		}
		else
		{
			System.out.println("They are shooting centrally");
		
			if (state == KeeperPosition.CENTER)
			{
				System.out.println("We should be in the way");
			}		
			else
			{
				moveKeeper(state, KeeperPosition.CENTER);
			}			
		}
		
		this.done = false;
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
	
	public boolean isInitialised()
	{
		return this.initialised;
	}
	
	public void setInitialBallPosition(Position position)
	{
		this.ballStart = position;
		this.done = false;
		this.state = KeeperPosition.CENTER;
		this.initialised = true;
	}
	
	private void moveKeeper(KeeperPosition source, KeeperPosition destination)
	{
		switch (source)
		{
			case NORTH:
				if (destination == KeeperPosition.CENTER)
				{
					moveBackward(10);
				}
				else if (destination == KeeperPosition.SOUTH)
				{
					moveBackward(20);
				}
				break;
				
			case CENTER:
				if (destination == KeeperPosition.NORTH)
				{
					moveForward(10);
				}
				else if (destination == KeeperPosition.SOUTH)
				{
					moveBackward(10);
				}
				break;

			case SOUTH:
				if (destination == KeeperPosition.NORTH)
				{
					moveForward(20);
				}
				else if (destination == KeeperPosition.CENTER)
				{
					moveForward(10);
				}
				break;
		}
		
		state = destination;
		Utils.pause(300);
	}
	
	private void moveForward(int distance)
	{
		System.out.println("Move down");
		controller.setSpeed(900);
		controller.driveForward(distance);
	}
	
	private void moveBackward(int distance)
	{
		System.out.println("Move down");
		controller.setSpeed(900);
		controller.driveBackward(distance);
	}
	
	public boolean isDone()
	{
		return this.done;
	}
}

