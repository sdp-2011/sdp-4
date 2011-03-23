package uk.ac.ed.inf.sdp.group4.strategy;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.domain.Position;
import uk.ac.ed.inf.sdp.group4.world.IVisionClient;
import uk.ac.ed.inf.sdp.group4.world.*;

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
	protected Position ownGoal;

	protected Robot robot;
	protected Robot enemyRobot;
	protected Ball ball;

	boolean keepRunning;
	boolean paused;

	public enum Strategies
	{
		TRACKBALL,
		KEYBOARD,
		INTERCEPT,
		SIMPLE
	}

	public enum Goals
	{
		EAST,
		WEST
	}

	public Strategy(IVisionClient client, Controller controller, RobotColour ourColour, boolean testing)
	{
		Logger root = Logger.getRootLogger();
		root.setLevel(Level.OFF);
		this.client = client;
		this.controller = controller;
		this.ourColour = ourColour;
		this.testing = testing;
		this.keepRunning = true;
		this.currentGoal = eastGoal;
		this.ownGoal = westGoal;
		this.paused = true;
	}

	public void run()
	{
		runStrategy();
	}

	public void runStrategy()
	{
		System.out.println("Starting strategy loop...");
		while (keepRunning)
		{
			if (paused) continue;
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
	
	public void suspend()
	{
		paused = true;
	}
	
	public void resume()
	{
		paused = false;
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
			ownGoal = eastGoal;
		}
		else
		{
			currentGoal = eastGoal;
			currentGoal = westGoal;
		}
	}

	public Goals getFacing()
	{
		if (currentGoal.equals(eastGoal))
		{
			return Goals.EAST;
		}

		return Goals.WEST;
	}

	public void stop()
	{
		keepRunning = false;
	}

	protected void refresh()
    {
        WorldState state = client.getWorldState();

        robot = (ourColour() == RobotColour.BLUE) ? state.getBlue() : state.getYellow();
        enemyRobot = (ourColour() == RobotColour.BLUE) ? state.getYellow() : state.getBlue();
        ball = state.getBall();

		//if (ballPos.distance(ball.getPosition()) < 5) {
		//	ballPos = ball.getPosition();
		//}
	
        log.debug("Robot is facing: " + robot.getFacing());
    }

	public static Strategy makeStrat(Strategies strat, IVisionClient client, Controller controller, RobotColour colour)
	{
		Strategy strategy = null;

		if (strat == Strategies.TRACKBALL)
		{
			strategy = new TrackBallStrategy(client, controller, colour);
		}

		else if (strat == Strategies.KEYBOARD)
		{
			strategy = new KeyboardStrategy(null, null, null);
		}

		else if (strat == Strategies.INTERCEPT)
		{
			strategy = new InterceptStrategy(null, null, null);
		}

		else if (strat == Strategies.SIMPLE)
		{
			strategy = new SimpleStrat(client, controller, colour);
		}

		return strategy;
	}

	public abstract void penaltyAttack();
	public abstract void penaltyDefend();
}

