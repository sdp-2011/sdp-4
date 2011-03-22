package uk.ac.ed.inf.sdp.group4.strategy;

import uk.ac.ed.inf.sdp.group4.strategy.*;
import uk.ac.ed.inf.sdp.group4.domain.*;
import uk.ac.ed.inf.sdp.group4.controller.*;
import uk.ac.ed.inf.sdp.group4.world.*;


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
		state = StrategyState.PAUSED;
		lastBall = ball.getPosition();
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
			}

			lastBall = ball.getPosition();

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
		super.suspend();
		state = StrategyState.PAUSED;
	}

	public void resume()
	{
		super.resume();
		state = StrategyState.GETTOBALL;
	}
}
