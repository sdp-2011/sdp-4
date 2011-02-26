package uk.ac.ed.inf.sdp.group4.strategy.tactic;

import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.Robot;

public abstract class Tactic implements ITactic
{
	abstract public void tick(Robot ours, Robot enemy, Ball ball);

	protected Controller controller;

	public Tactic(Controller controller)
	{
		setController(controller);
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
}
