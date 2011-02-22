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

	public SimBall(Ball ball)
	{
		this.ball = ball;
		this.radius = 2;
	}

	public void update(int time)
	{

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
