package uk.ac.ed.inf.sdp.group4.strategy;

import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;
import uk.ac.ed.inf.sdp.group4.domain.Position;
import uk.ac.ed.inf.sdp.group4.domain.Vector;
import uk.ac.ed.inf.sdp.group4.strategy.tactic.PenaltyTakeTactic;
import uk.ac.ed.inf.sdp.group4.strategy.tactic.PenaltySaveTactic;
import uk.ac.ed.inf.sdp.group4.utils.Utils;
import uk.ac.ed.inf.sdp.group4.world.IVisionClient;

public class SimpleStrat extends Strategy
{
	private enum StrategyState
	{
		PAUSED,
		GETTOBALL,
		KICK,
		DEFENCE,
		PENALTYATTACK,
		PENALTYDEFEND
	}

	private StrategyState state;
	private Navigator sulu;
	private Position lastBall;

	public SimpleStrat(IVisionClient client, Controller controller, RobotColour colour)
	{
		// Create the strategy.
		super(client, controller, colour, false);
		
		// Get a world state from the vision.
		super.refresh();

		// Woah there.
		state = StrategyState.PAUSED;

		// Ball should always update at the start.
		lastBall = new Position(100000,100000);

		// Create the navigator. Warp 9.
		sulu = new Navigator(controller, client, colour);
	}

	public void tick()
	{
		// Get a new version of the vision.
		super.refresh();
		
		// OH GREAT SWITCH STATEMENT, WHAT SHOULD WE DO?
		switch (state)
		{
			// Chill.
			case PAUSED:
				return;
			
			// Chase that shit.
			case GETTOBALL:
				//System.out.println("Starting get to ball...");
				if (!lastBall.isNear(ball.getPosition()) || sulu.isIdle())
				{
					System.out.println("Navigating to the ball...");
					sulu.navigateTo(ball.getPosition(), 0);
					lastBall = ball.getPosition();
				}

				//System.out.println("Should we shoot?");
				if (shouldShoot())
				{
					System.out.println("Kicking next time around.");
					changeState(StrategyState.KICK);
				}

				if (shouldDefend())
				{
					System.out.println("Defending the ball...");
					changeState(StrategyState.DEFENCE);
				}
			
				break;
			
			case DEFENCE:
				defenceStrategy();
				break;
			
			// Score goals.
			case KICK:
				System.out.println("Shooting!");
				controller.shoot();
				Utils.pause(1000);
				changeState(StrategyState.GETTOBALL);
				break;
			
			// Score a penalty.
			case PENALTYATTACK:
				System.out.println("Shooting a penalty");
				PenaltyTakeTactic ptt = new PenaltyTakeTactic(controller);
				ptt.tick(robot, enemyRobot, ball);
				changeState(StrategyState.GETTOBALL);
				break;

			// Defend a penalty.
			case PENALTYDEFEND:
				PenaltySaveTactic pst = new PenaltySaveTactic(controller);
				pst.tick(robot, enemyRobot, ball);
				changeState(StrategyState.GETTOBALL);
				break;
		}
	}

	private void changeState(StrategyState state)
	{ 
		System.out.println("Changing state to " + state);
		this.state = state;
	}

	public boolean shouldShoot()
	{
		//System.out.println("Are we facing the ball? " + facingBall());
		//System.out.println("Are we facing enemy goal? " + facingEnemyGoal());
		//System.out.println("Are we near the ball? " + robot.isNear(ball));
		//System.out.println("Is the ball hidden? " +  ball.isHidden());

		return facingEnemyGoal() && ((facingBall() && robot.isNear(ball)) || ball.isHidden());
	}


	public boolean shouldDefend()
	{
		if (currentGoal.equals(eastGoal))
		{
			if (ball.getX() + 20 < robot.getX())
			{
				return true;
			}
		}
		else
		{
			if (ball.getX() + 20 > robot.getX())
			{
				return true;
			}
		}

		return false;
	}

	public void defenceStrategy()
	{
		if (ball.inTopHalf())
		{
			if (currentGoal.equals(eastGoal))
			{
				sulu.navigateTo(new Position(ball.getX() - 40, ball.getY() + 40), 0);
				sulu.addWaypoint(new Position(ball.getX() - 40, ball.getY()), 0);
			}
			else
			{
				sulu.navigateTo(new Position(ball.getX() + 40, ball.getY() + 40), 0);
				sulu.addWaypoint(new Position(ball.getX() + 40, ball.getY()), 0);
			}
		}
		else
		{
			if (currentGoal.equals(eastGoal))
			{
				sulu.navigateTo(new Position(ball.getX() - 40, ball.getY() - 40), 0);
				sulu.addWaypoint(new Position(ball.getX() - 40, ball.getY()), 0);
			}
			else
			{
				sulu.navigateTo(new Position(ball.getX() + 40, ball.getY() - 40), 0);
				sulu.addWaypoint(new Position(ball.getX() + 40, ball.getY()), 0);
			}
		}
	}

	public boolean facingEnemyGoal()
	{
		int angle = (int)robot.getFacing();

		if (currentGoal.equals(eastGoal))
		{
			if ((angle > 30) && (angle < 150))
			{
				return true;
			}
		}
		else
		{
			if ((angle > 210) && (angle < 330))
			{
				return true;
			}
		}

		return false;
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
		
		return Math.abs(robotToBall.angleFrom(robot.getFacing())) < 17;
	}

	public void penaltyDefend()
	{
		changeState(StrategyState.PENALTYDEFEND);
	}

	public void penaltyAttack()
	{
		changeState(StrategyState.PENALTYATTACK);
	}

	public void suspend()
	{
		changeState(StrategyState.PAUSED);
		controller.stop();
		sulu.clearWaypoints();
		super.suspend();
	}

	public void resume()
	{
		System.out.println("State: " + state);
		if (state == StrategyState.PAUSED)
		{
			lastBall = new Position(100000, 100000);
			changeState(StrategyState.GETTOBALL);
		}

		controller.stop();
		super.resume();
	}
}
