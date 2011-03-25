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
		PENALTYDEFEND,
		OWNGOALRISK
	}

	private StrategyState state;
	private Navigator sulu;
	private Position lastBall;
	
	private PenaltySaveTactic pst;

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
		pst = new PenaltySaveTactic(controller);
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
				//System.out.println("Should we shoot?");
				if (shouldShoot())
				{
					System.out.println("Kicking next time around.");
					changeState(StrategyState.KICK);
				}
				
				if (ball.isHidden())
				{
					System.out.println("Oh noes, ball are gon!");
					break;
				}

				if (shouldDefend())
				{
					controller.beserk(false);
					changeState(StrategyState.OWNGOALRISK);
					break;
				}
				//System.out.println("Starting get to ball...");
				else if (!lastBall.isNear(ball.getPosition()) || sulu.isIdle())
				{
					if (isAttacking())
					{
						if (currentGoal.equals(eastGoal))
						{
							sulu.navigateTo(new Position(ball.getX() + 20, ball.getY()), 0);
						}
							
						else
						{
							sulu.navigateTo(new Position(ball.getX() - 20, ball.getY()), 0);
						}
					}
					
					else
					{
						System.out.println("Navigating to the ball...");
						sulu.navigateTo(ball.getPosition(), 0);
						lastBall = ball.getPosition();
					}
				}
			
				break;
			
			// Score goals.
			case KICK:
				System.out.println("Shooting!");
				controller.setSpeed(900);
				controller.shoot();
				pause(500);
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
				if (!pst.isInitialised())
					pst.setInitialBallPosition(ball.getPosition());
				
				pst.tick(robot, enemyRobot, ball);
				
				if (pst.isDone())
				{
					changeState(StrategyState.GETTOBALL);
				}

				break;

			case OWNGOALRISK:
				if (((ball.getPosition().getX() > robot.getPosition().getX()) && 
					(currentGoal.equals(eastGoal))) || ((ball.getPosition().getX() < 
						robot.getPosition().getX()) && (currentGoal.equals(westGoal))))
				{
					System.out.println("We can probably still get it");
					sulu.addWaypoint(ball.getPosition(), 0);
				}

				if (shouldDefend())
				{
					System.out.println("We have problems");
					defenceStrategy();
				}
				else
				{
					controller.beserk(true);
					changeState(StrategyState.GETTOBALL);
				}			
				break;
		}
	}
	
	private void waitPause()
	{
		
		long start = System.currentTimeMillis();
		
		while (System.currentTimeMillis() < start + 50)
		{
			if (shouldShoot())
			{
				System.out.println("Kicking next time around.");
				changeState(StrategyState.KICK);
			}
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
		if ((ball.getPosition().getX() > 475) && (currentGoal.equals(westGoal)))
		{
			System.out.println("We need to defend!");
			return true;
		}	
		else if ((ball.getPosition().getX() > 10) && (ball.getPosition().getX() < 125) && (currentGoal.equals(eastGoal)))
		{
			System.out.println("We need to defend!");
			return true;
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
			}
			else
			{
				sulu.navigateTo(new Position(ball.getX() + 40, ball.getY() + 40), 0);
			}
		}
		else
		{
			if (currentGoal.equals(eastGoal))
			{
				sulu.navigateTo(new Position(ball.getX() - 40, ball.getY() - 40), 0);
			}
			else
			{
				sulu.navigateTo(new Position(ball.getX() + 40, ball.getY() - 40), 0);
			}
		}
	}

	public boolean facingEnemyGoal()
	{
		int angle = (int)robot.getFacing();

		if (currentGoal.equals(eastGoal))
		{
			if ((angle > 20) && (angle < 160))
			{
				return true;
			}
		}
		else
		{
			if ((angle > 200) && (angle < 340))
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
	
	public boolean isAttacking()
	{
		if (currentGoal.equals(eastGoal))
		{
			return robot.getX() < ball.getX();
		}
		
		else
		{
			return robot.getX() > ball.getX();
		}
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
