package uk.ac.ed.inf.sdp.group4.sim;

import uk.ac.ed.inf.sdp.group4.domain.Position;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.Robot;

import javax.swing.*;
import java.awt.*;

public class Situation extends JPanel
{
	private static final long serialVersionUID = 101;

	private Robot blue;
	private Robot yellow;
	private Ball ball;

	private boolean blank;

	public Situation(Robot blue, Robot yellow, Ball ball)
	{
		this.blue = blue;
		this.yellow = yellow;
		this.ball = ball;
	}

	public Situation()
	{
		this.blank = true;
	}

	public Situation setup(Robot blue, Robot yellow, Ball ball)
	{
		this.blank = false;

		this.blue = blue;
		this.yellow = yellow;
		this.ball = ball;

		return this;
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		if (!blank)
		{
			double X_RATIO = getSize().width / 244;
			double Y_RATIO = getSize().height / 122;
			int ROB_X = (int) (X_RATIO * 20);
			int ROB_Y = (int) (Y_RATIO * 18);
			int BALL_SIZE = (int) (X_RATIO * 4);

			setBackground(Color.green);

			int adjustX = ROB_X / 2;
			int adjustY = ROB_Y / 2;

			//draw lines
			g.setColor(Color.white);
			g.fillRect((int) (122 * X_RATIO), (int) (0 * Y_RATIO), (int) (1 * X_RATIO), (int) (122 * Y_RATIO));
			g.fillRect((int) (0 * X_RATIO), (int) (121 * Y_RATIO), (int) (244 * X_RATIO), (int) (1 * Y_RATIO));
			g.fillRect((int) (0 * X_RATIO), (int) (0 * Y_RATIO), (int) (244 * X_RATIO), (int) (1 * Y_RATIO));
			g.fillRect((int) (243 * X_RATIO), (int) (0 * Y_RATIO), (int) (1 * X_RATIO), (int) (122 * Y_RATIO));
			g.fillRect((int) (0 * X_RATIO), (int) (0 * Y_RATIO), (int) (1 * X_RATIO), (int) (122 * Y_RATIO));
			

			//draw goals
			g.setColor(Color.pink);
			g.fillRect((int) (0 * X_RATIO), (int) (20 * Y_RATIO), (int) (10 * X_RATIO), (int) (80 * Y_RATIO));
			g.fillRect((int) (234 * X_RATIO), (int) (20 * Y_RATIO), (int) (10 * X_RATIO), (int) (80 * Y_RATIO));			

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

			g.setColor(Color.red);

			speedX = 10 * Math.cos(Math.toRadians(yellow.getVector().getDirection() - 90));
			speedY = 10 * Math.sin(Math.toRadians(yellow.getVector().getDirection() - 90));
			endX = (yPos.getX() * X_RATIO) + (speedX * X_RATIO);
			endY = (yPos.getY() * Y_RATIO) + (speedY * Y_RATIO);

			g.drawLine((int) (yPos.getX() * X_RATIO), (int) (yPos.getY() * Y_RATIO), (int) endX, (int) endY);

			//draw ball
			g.setColor(Color.red);

			Position ballPos = ball.getPosition();
			g.fillOval((int) (ballPos.getX() * X_RATIO - (4/2)), (int) (ballPos.getY() * Y_RATIO - (4/2)),
				BALL_SIZE, BALL_SIZE);
		}	
	}
}
