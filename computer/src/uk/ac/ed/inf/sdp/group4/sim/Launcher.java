package uk.ac.ed.inf.sdp.group4.sim;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.lang.Runnable;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;

import uk.ac.ed.inf.sdp.group4.domain.Position;
import uk.ac.ed.inf.sdp.group4.world.Robot;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.WorldState;
import uk.ac.ed.inf.sdp.group4.world.VisionClient;
import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;
import uk.ac.ed.inf.sdp.group4.strategy.RobotColour;
import uk.ac.ed.inf.sdp.group4.strategy.Strategy;
import uk.ac.ed.inf.sdp.group4.controller.ThinController;

public class Launcher implements Runnable
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

	private JFrame frame;

	public Launcher(Strategy blueStrat, Strategy yellowStrat)
	{
		this.blueStrat = blueStrat;
		this.yellowStrat = yellowStrat;

		setup(blueStrat, yellowStrat);
	}

	public void run()
	{
		new Thread(blueStrat).start();
		new Thread(yellowStrat).start();

		long t = System.currentTimeMillis();

		while (true)
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
		yellow.setPosition(210, 60);
		ball.setPosition(120, 60);
		
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
		blueStrat.setup(client, controllerOne, RobotColour.BLUE, true);
		yellowStrat.setup(client, controllerTwo, RobotColour.YELLOW, true);

		//set up pitch
		pitch = new Pitch(components);

		//set up display
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 400);
		frame.setVisible(true);
		frame.getContentPane().add(new Situation(frame, blue, yellow, ball));
	}

	private void update(int time)
	{
		for (int i = 0; i < components.length; i++)
		{
			components[i].update(time);
		}
		pitch.run();
		draw();
	}

	private void draw()
	{
		frame.getContentPane().repaint();
	}
}
