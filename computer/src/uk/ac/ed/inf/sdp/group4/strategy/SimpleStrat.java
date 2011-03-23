package uk.ac.ed.inf.sdp.group4.strategy;

import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;
import uk.ac.ed.inf.sdp.group4.domain.Position;
import uk.ac.ed.inf.sdp.group4.domain.Vector;
import uk.ac.ed.inf.sdp.group4.world.IVisionClient;

public class SimpleStrat extends Strategy
{
	private enum StrategyState
	{
		PAUSED,
		GETTOBALL,
		KICK
	}

	private StrategyState state;
	private Navigator sulu;
	private Position lastBall;

	public SimpleStrat(IVisionClient client, Controller controller, RobotColour colour)
	{
		super(client, controller, colour, false);
		super.refresh();
		state = StrategyState.PAUSED;
		lastBall = new Position(10000000,100000);
		sulu = new Navigator(controller, client, colour);
	}

	public void tick()
	{
		super.refresh();

		if (state == StrategyState.PAUSED)
		{
			return;
		}

		else if (state == StrategyState.GETTOBALL)
		{
			if (!lastBall.isNear(ball.getPosition()))
			{
				sulu.navigateTo(ball.getPosition(), 0);
				lastBall = ball.getPosition();
			}

			if (robot.isNear(ball) && facingBall())
			{
				state = StrategyState.KICK;
			}
		}

		else if (state == StrategyState.KICK)
		{
			controller.shoot();
			state = StrategyState.GETTOBALL;
		}
	}

	public boolean facingBall()
	{
		Vector robotToBall = null;

		try
		{
			robotToBall = Vector.calcVect(robot.getPosition(), ball.getPosition());
		}

		catch (InvalidAngleException e)
		{
			//Ignore
		}
		
		return Math.abs(robotToBall.angleFrom(robot.getFacing())) < 10;
	}

	public void penaltyDefend()
	{
		
	}

	public void penaltyAttack()
	{

	}

	public void suspend()
	{
		state = StrategyState.PAUSED;
		sulu.clearWaypoints();
		super.suspend();
	}

	public void resume()
	{
		state = StrategyState.GETTOBALL;
		sulu.clearWaypoints();
		super.resume();
	}
}
