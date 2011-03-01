package uk.ac.ed.inf.sdp.group4.strategy.tactic;

import org.apache.log4j.Logger;

import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.Robot;

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
		// Turn the opposite direction.
		if (enemy.inTopHalf())
		{
			controller.turn(-15);
		}
		else
		{
			controller.turn(15);
		}

		// Wait for the turn to complete
		pause(200);

		// Shooot!
		controller.shoot();
	}
}
