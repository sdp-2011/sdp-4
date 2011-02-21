package uk.ac.ed.inf.sdp.group4.strategy;

import org.apache.log4j.Logger;

import uk.ac.ed.inf.sdp.group4.world.VisionClient;
import uk.ac.ed.inf.sdp.group4.world.IVisionClient;
import uk.ac.ed.inf.sdp.group4.controller.Controller;

public abstract class Strategy implements IStrategy
{
	// Logging
	protected static Logger log = Logger.getLogger(Strategy.class);

	// Attributes
	protected IVisionClient client;
	protected Controller controller;
	protected RobotColour ourColour;

	boolean testing;

	public Strategy(IVisionClient client, Controller controller, RobotColour ourColour, boolean testing)
	{
		this.client = client;
		this.controller = controller;
		this.ourColour = ourColour;
		this.testing = testing;
	}

	public void runStrategy()
	{
		log.debug("Starting strategy loop...");
		while (true)
		{
			tick();
		}
	}

	public abstract void tick();

	public IVisionClient getVisionClient()
	{
		return this.client;
	}

	public Controller getController()
	{
		return this.controller;
	}

	public RobotColour ourColour()
	{
		return this.ourColour;
	}

	public RobotColour enemyColour()
	{
		if (ourColour().equals(RobotColour.BLUE))
		{
			return RobotColour.YELLOW;
		}
		else
		{
			return RobotColour.BLUE;
		}
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

