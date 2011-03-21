package uk.ac.ed.inf.sdp.group4.strategy.tactic;

import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.Robot;

public interface ITactic
{
	public void tick(Robot ours, Robot enemy, Ball ball);
}
