package uk.ac.ed.inf.sdp.group4.strategy.tactic;

import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.Robot;

public abstract class Tactic implements ITactic
{
	abstract public void tick(Robot ours, Robot enemy, Ball ball);

	protected Controller controller;
	protected boolean testing;

	public Tactic(Controller controller, boolean testing)
	{
		setController(controller);
		setTesting(testing);
	}

	public void setTesting(boolean testing)
	{
		this.testing = testing;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	protected void pause(int milliseconds)
	{
		if (!testing)
		{
			try
			{
				Thread.sleep(milliseconds);
			}
			catch (InterruptedException ignored)
			{

			}
		}
	}

}
