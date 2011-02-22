package uk.ac.ed.inf.sdp.group4.sim;

import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.domain.Position;
import uk.ac.ed.inf.sdp.group4.domain.Vector;
import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;
import uk.ac.ed.inf.sdp.group4.world.WorldObject;
import java.lang.Math;

public class SimBall extends Component
{
	private Ball ball;
	private double radius;
	private double x;
	private double y;
	private SimBot bot;

	public SimBall(Ball ball)
	{
		this.ball = ball;
		this.radius = 2;
		this.x = ball.getX();
		this.y = ball.getY();
	}

	public void update(int time)
	{
		if (bot == null)
		{
			move(time);
		}

		else
		{
			spin();
		}

		ball.setPosition((int) x, (int) y);
	}

	private void move(int time)
	{
		Vector vector = ball.getVector();

		double speed  = (vector.getMagnitude() / 1000) * time;
		double speedX = speed * Math.cos(Math.toRadians(vector.getDirection() - 90));
		double speedY = speed * Math.sin(Math.toRadians(vector.getDirection() - 90));

		x += speedX;
		y += speedY;
	}

	private void spin()
	{
		double speedX = 10 * Math.cos(Math.toRadians(bot.getObject().getVector().getDirection() - 90));
		double speedY = 10 * Math.sin(Math.toRadians(bot.getObject().getVector().getDirection() - 90));
		double endX = bot.getObject().getX() + speedX;
		double endY = bot.getObject().getY() + speedY;

		x = endX;
		y = endY;
	}

	public double getRadius()
	{
		return radius;	
	}

	public WorldObject getObject()	
	{
		return ball;
	}

	public void grab(SimBot bot)
	{
		this.bot = bot;
		this.bot.grab(this);
	}

	public void lose()
	{
		this.bot = null;
	}
}
