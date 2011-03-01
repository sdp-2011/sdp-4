package uk.ac.ed.inf.sdp.group4.strategy;

import org.apache.log4j.Logger;

import uk.ac.ed.inf.sdp.group4.world.VisionClient;
import uk.ac.ed.inf.sdp.group4.world.IVisionClient;
import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.domain.Position;

public abstract class Strategy implements IStrategy, Runnable
{
	// Logging
	protected static Logger log = Logger.getLogger(Strategy.class);

	// Attributes
	protected IVisionClient client;
	protected Controller controller;
	protected RobotColour ourColour;

	boolean testing;

	private Position westGoal = new Position(30, 162);
	private Position eastGoal = new Position(525, 162);
	protected Position currentGoal;

	boolean keepRunning;

	public enum Strategies
	{
		TRACKBALL
	}

	public enum Goals
	{
		EAST,
		WEST
	}

	public Strategy(IVisionClient client, Controller controller, RobotColour ourColour, boolean testing)
	{
		this.client = client;
		this.controller = controller;
		this.ourColour = ourColour;
		this.testing = testing;
		this.keepRunning = true;
		this.currentGoal = eastGoal;
	}

	public void run()
	{
		runStrategy();
	}

	public void runStrategy()
	{
		log.debug("Starting strategy loop...");
		while (keepRunning)
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

	public void setGoals(int wX, int wY, int eX, int eY)
	{
		eastGoal = new Position(eX, eY);
		westGoal = new Position(wX, wY);
	}

	public void setup(IVisionClient client, Controller controller, RobotColour ourColour, boolean testing)
	{
		this.client = client;
		this.controller = controller;
		this.ourColour = ourColour;
		this.testing = testing;
	}

	public void halfTime()
	{
		if (currentGoal.equals(eastGoal))
		{
			currentGoal = westGoal;
		}
		else
		{
			currentGoal = eastGoal;
		}
	}

	public void stop()
	{
		keepRunning = false;
	}

	public static Strategy makeStrat(Strategies strat)
	{
		Strategy strategy = null;

		if (strat == Strategies.TRACKBALL)
		{
			strategy = new TrackBallStrategy(null, null, null);
		}

		return strategy;
	}

	public abstract void attack();
	public abstract void defend();
}

