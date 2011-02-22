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

import uk.ac.ed.inf.sdp.group4.domain.Position;
import uk.ac.ed.inf.sdp.group4.world.Robot;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.WorldState;
import uk.ac.ed.inf.sdp.group4.world.VisionClient;
import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;
import uk.ac.ed.inf.sdp.group4.strategy.RobotColour;

public class Launcher implements Runnable
{
	//FPS
	final int FPS = 25;
	final int WIDTH = 800;
	final int HEIGHT = 400;
	final int X_RATIO = WIDTH / 244;
	final int Y_RATIO = HEIGHT / 122;
	final int ROB_X = X_RATIO * 20;
	final int ROB_Y = Y_RATIO * 18;
	final int BALL_SIZE = X_RATIO * 4;

	WorldState state;
	Robot blue;
	Robot yellow;
	Ball ball;
	Component[] components;

	int scoreB;
	int scoreY;

	Image iconB;
	Image iconY;

	private JFrame frame;

	public Launcher(WorldState state, Component[] components)
	{
		this.components = components;
		this.state = state;
	}

	public void run()
	{
		loadContent();
		setup();

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

	private void loadContent()
	{
		try
		{
			iconB = ImageIO.read(new File("blue.png"));
			iconY = ImageIO.read(new File("yellow.png"));
		}
		catch (IOException ex)
		{
			System.out.println("You've forgotten an image");
		}
	}

	private void setup()
	{
		Pitch pitch = new Pitch();
		//set up display
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
		frame.getContentPane().add(new Situation(pitch));
		blue = state.getBlue();
		yellow = state.getYellow();
		ball = state.getBall();

		blue.setPosition(30, 60);
	}

	private void update(int time)
	{
		for (int i = 0; i < components.length; i++)
		{
			components[i].update(time);
		}
		draw();
	}

	private void draw()
	{
		frame.getContentPane().repaint();
	}


	private class Situation extends JPanel
	{
		private Pitch pitch;

		public Situation(Pitch pitch)
		{
			this.pitch = pitch;
		}

		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			setBackground(Color.green);

			int adjustX = ROB_X / 2;
			int adjustY = ROB_Y / 2;

			//draw blue
			g.setColor(Color.blue);

			Position bPos = blue.getPosition();
			g.fillOval(bPos.getX() * X_RATIO - adjustX, bPos.getY() * Y_RATIO - adjustY,
				ROB_X, ROB_Y);

			//draw Vector
			g.setColor(Color.red);

			double speedX = 10 * Math.cos(Math.toRadians(blue.getVector().getDirection() - 90));
			double speedY = 10 * Math.sin(Math.toRadians(blue.getVector().getDirection() - 90));
			double endX = (bPos.getX() * X_RATIO) + (speedX * X_RATIO);
			double endY = (bPos.getY() * Y_RATIO) + (speedY * Y_RATIO);

			g.drawLine(bPos.getX() * X_RATIO, bPos.getY() * Y_RATIO, (int) endX, (int) endY);

			//draw yellow
			g.setColor(Color.yellow);

			Position yPos = yellow.getPosition();
			g.fillOval(yPos.getX() * X_RATIO - adjustX, yPos.getY() * Y_RATIO - adjustY,
				 ROB_X, ROB_Y);

			//draw ball
			g.setColor(Color.red);

			Position ballPos = ball.getPosition();
			g.fillOval(ballPos.getX() * X_RATIO - (4/2), ballPos.getY() * Y_RATIO - (4/2),
				BALL_SIZE, BALL_SIZE);
		}
	}

}
