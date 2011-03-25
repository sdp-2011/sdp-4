package uk.ac.ed.inf.sdp.group4.strategy.tactic;

import org.apache.log4j.Logger;
import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.Robot;
import java.util.Random;

public class PenaltyTakeTactic extends Tactic
{
	protected static Logger log = Logger.getLogger(PenaltyTakeTactic.class);

	public PenaltyTakeTactic(Controller controller)
	{
		this(controller, false);
	}

	public PenaltyTakeTactic(Controller controller, boolean testing)
	{
		super(controller, testing);
	}

	@Override
	public void tick(Robot ours, Robot enemy, Ball ball)
	{
		boolean eastGoal = ours.getX() > 280;
		
		int modifier = 1;
		if (eastGoal)
			modifier = -1;
		
		// Turn the opposite direction.
		boolean turnedRight = false;
		
		Random rand = new Random();
		int dummy = rand.nextInt(2);
			
		if (dummy == 1)
		{
			turnedRight = true;
			controller.turn(modifier * 28);
		}
		
		else 
		{
			controller.turn(modifier * -28);
		}
		
		pause(700);
		
		if (enemy.inTopHalf())
		{
			if (turnedRight)
			{
				controller.turn(modifier * -50);
				pause(700);
			}
		}
		else
		{
			if (!turnedRight)
			{
				controller.turn(modifier * 50);
				pause(700);
			}
		}

		// Wait for the turn to complete

		// Shooot!
		controller.shoot();
	}
}
