package uk.ac.ed.inf.sdp.group4.strategy.tactic;

import org.apache.log4j.Logger;

import uk.ac.ed.inf.sdp.group4.domain.Position;
import uk.ac.ed.inf.sdp.group4.domain.Vector;
import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;
import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.Robot;

public class PenaltySaveTactic extends Tactic
{
	protected static Logger log = Logger.getLogger(PenaltySaveTactic.class);

	private final double BALL_MOVE_THRESHOLD = 5;
	private Position initialBallPosition = null;

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
		// If this is the first run through of the strategy then we should
		// store the initial position.
		if(initialBallPosition == null)
		{
			initialBallPosition = ball.getPosition();		
		}

		// Has the ball moved?
		double distance = 0;
		try
		{
			distance = initialBallPosition.calcVectTo(ball.getPosition())
				.getMagnitude();
		}
		catch (InvalidAngleException iae)
		{
			//OH FUCK.
		}

		while(distance < BALL_MOVE_THRESHOLD)
		{
			controller.driveForward(30);
			pause(1000);
			controller.driveBackward(40);
			pause(1000);
		}

		// CHAAAAARGEEEEEEE
		controller.setSpeed(900);
		Vector ballRoute = getBallRoute(ours, ball);
		double ballAngle = ballRoute.angleTo(ours.getFacing());

		while (ballAngle > 10)
		{
			controller.turn((int)ballAngle);
			pause(500);
		}

		controller.driveForward((int)ballRoute.getMagnitude()/2);
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

