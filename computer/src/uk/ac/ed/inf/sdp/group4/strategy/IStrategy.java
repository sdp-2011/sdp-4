package uk.ac.ed.inf.sdp.group4.strategy;

/**
 * This interface allows strategies to be defined and then loaded by the
 * simulator and robot. This shouldn't really be used as the
 * <code>Strategy</code> abstract class should be used instead.
 *
 * @see	uk.ac.ed.inf.sdp.group4.strategy.Strategy;
 */
public interface IStrategy
{
	/**
	 * Runs the strategy from the start.
	 */
	public void runStrategy();

	/**
	 * One iteration of the strategy.
	 */
	public void tick();
}
