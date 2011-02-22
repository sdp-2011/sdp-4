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

	public SimBall(Ball ball)
	{
		this.ball = ball;
		this.radius = 2;
		this.x = ball.getX();
		this.y = ball.getY();
	}

	public void update(int time)
	{
		move(time);
		ball.setPosition((int) x, (int) y);
	}

	private void move(int time)
	{
		Vector vector = ball.getVector();

		double speed = (vector.getMagnitude() / 1000) * time;
		double speedX = speed * Math.cos(Math.toRadians(vector.getDirection() - 90));
		double speedY = speed * Math.sin(Math.toRadians(vector.getDirection() - 90));

		x += speedX;
		y += speedY;
	}

	public double getRadius()
	{
		return radius;	
	}

	public WorldObject getObject()	
	{
		return ball;
	}
}
