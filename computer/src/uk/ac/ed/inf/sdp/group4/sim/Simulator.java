package uk.ac.ed.inf.sdp.group4.sim;

import uk.ac.ed.inf.sdp.group4.controller.ThinController;
import uk.ac.ed.inf.sdp.group4.strategy.RobotColour;
import uk.ac.ed.inf.sdp.group4.strategy.Strategy;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.Robot;
import uk.ac.ed.inf.sdp.group4.world.WorldState;
import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;

public class Simulator implements Runnable
{
	private Pitch pitch;
	private WorldState state;
	private Robot blue;
	private Robot yellow;
	private Ball ball;
	private Component[] components;
	private ThinController controllerOne;
	private ThinController controllerTwo;
	private Strategy blueStrat;
	private Strategy yellowStrat;

	private Situation panel;
	private boolean animate;
	
	private boolean keepRunning;

	public Simulator(Strategy blueStrat, Strategy yellowStrat)
	{
		this.blueStrat = blueStrat;
		this.yellowStrat = yellowStrat;
		this.animate = false;
		this.keepRunning = true;

		setup(blueStrat, yellowStrat);
	}

	public void run()
	{
		new Thread(blueStrat).start();
		new Thread(yellowStrat).start();

		long t = System.currentTimeMillis();

		while (keepRunning)
		{
			long tplus = System.currentTimeMillis();

			if ((tplus - t) >= 40)
			{
				update((int)(tplus - t));
				t = tplus;
			}
		}
	}

	private void setup(Strategy blueStrat, Strategy yellowStrat)
	{
		//create world state
		state = new WorldState();
		FakeVision client = new FakeVision(state);

		//get state objects
		blue = state.getBlue();
		yellow = state.getYellow();
		ball = state.getBall();		

		//set up positions
		blue.setPosition(30, 60);
		yellow.setPosition(210, 20);
		ball.setPosition(210, 80);

		if (blueStrat != null) blueStrat.setGoals(0, 61, 244, 61);
		if (yellowStrat != null) yellowStrat.setGoals(0, 61, 244, 61);
		
		//set up components
		components = new Component[3];
		components[0] = new SimBot(blue);
		components[1] = new SimBot(yellow);
		components[2] = new SimBall(ball);

		//setup controllers
		controllerOne = new ThinController();			
		controllerOne.setBot((SimBot) components[0]);
		controllerTwo = new ThinController();
		controllerTwo.setBot((SimBot) components[1]);

		//setup strategies
		if (blueStrat != null) blueStrat.setup(client, controllerOne, RobotColour.BLUE, true);
		if (yellowStrat != null) yellowStrat.setup(client, controllerTwo, RobotColour.YELLOW, true);
		if (yellowStrat != null) yellowStrat.halfTime();

		//set up pitch
		pitch = new Pitch(components);
	}

	private void update(int time)
	{
		for (int i = 0; i < components.length; i++)
		{
			components[i].update(time);
		}
		pitch.run();
		if (animate) draw();
	}

	private void draw()
	{
		panel.repaint();
	}

	public void setPanel(Situation situation)
	{
		animate = true;
		panel = situation.setup(blue, yellow, ball);
	}

	public void pause()
	{
		blueStrat.suspend();
		yellowStrat.suspend();
	}

	public void resume()
	{
		blueStrat.resume();
		yellowStrat.resume();
	}

	public void stop()
	{
		blueStrat.stop();
		yellowStrat.stop();
		keepRunning = false;
	}
}
