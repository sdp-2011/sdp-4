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
import uk.ac.ed.inf.sdp.group4.controller.ThinController;

public class Launcher implements Runnable
{
	private double X_RATIO;
	private double Y_RATIO;
	private int ROB_X;
	private int ROB_Y;
	private int BALL_SIZE;

	private Pitch pitch;
	private WorldState state;
	private Robot blue;
	private Robot yellow;
	private Ball ball;
	private Component[] components;
	private ThinController controllerOne;
	private ThinController controllerTwo;

	private JFrame frame;

	public Launcher(WorldState state, ThinController controller)
	{
		this.components = components;
		this.state = state;
		this.controllerOne = controller;
		setup();
	}

	public void run()
	{
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

	private void setup()
	{
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
		controllerOne.setBot((SimBot) components[0]);
		controllerTwo = new ThinController();
		controllerTwo.setBot((SimBot) components[1]);

		//set up pitch
		pitch = new Pitch(components);

		//set up display
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 400);
		frame.setVisible(true);
		frame.getContentPane().add(new Situation());
	}

	private void update(int time)
	{
		for (int i = 0; i < components.length; i++)
		{
			components[i].update(time);
		}
		pitch.run();
		
		X_RATIO = frame.getSize().width / 244;
		Y_RATIO = frame.getSize().height / 122;
		ROB_X = (int) (X_RATIO * 20);
		ROB_Y = (int) (Y_RATIO * 18);
		BALL_SIZE = (int) (X_RATIO * 4);
		draw();
	}

	private void draw()
	{
		frame.getContentPane().repaint();
	}


	private class Situation extends JPanel
	{
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			setBackground(Color.green);

			int adjustX = ROB_X / 2;
			int adjustY = ROB_Y / 2;

			g.setColor(Color.pink);
			g.fillRect((int) (0 * X_RATIO), (int) (20 * Y_RATIO), (int) (10 * X_RATIO), (int) (80 * Y_RATIO));
			g.fillRect((int) (256 * X_RATIO), (int) (20 * Y_RATIO), (int) (10 * X_RATIO), (int) (80 * Y_RATIO));

			//draw blue
			g.setColor(Color.blue);

			Position bPos = blue.getPosition();
			g.fillOval((int) (bPos.getX() * X_RATIO - adjustX), (int) (bPos.getY() * Y_RATIO - adjustY),
				ROB_X, ROB_Y);

			//draw Vector
			g.setColor(Color.red);

			double speedX = 10 * Math.cos(Math.toRadians(blue.getVector().getDirection() - 90));
			double speedY = 10 * Math.sin(Math.toRadians(blue.getVector().getDirection() - 90));
			double endX = (bPos.getX() * X_RATIO) + (speedX * X_RATIO);
			double endY = (bPos.getY() * Y_RATIO) + (speedY * Y_RATIO);

			g.drawLine((int) (bPos.getX() * X_RATIO), (int) (bPos.getY() * Y_RATIO), (int) endX, (int) endY);

			//draw yellow
			g.setColor(Color.yellow);

			Position yPos = yellow.getPosition();
			g.fillOval((int) (yPos.getX() * X_RATIO - adjustX), (int) (yPos.getY() * Y_RATIO - adjustY),
				 ROB_X, ROB_Y);

			//draw ball
			g.setColor(Color.red);

			Position ballPos = ball.getPosition();
			g.fillOval((int) (ballPos.getX() * X_RATIO - (4/2)), (int) (ballPos.getY() * Y_RATIO - (4/2)),
				BALL_SIZE, BALL_SIZE);
		}
	}

}
