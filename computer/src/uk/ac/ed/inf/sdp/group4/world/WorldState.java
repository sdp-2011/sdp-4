package uk.ac.ed.inf.sdp.group4.world;

import uk.ac.ed.inf.sdp.group4.WorldStateCommunication.WorldStateResponse;
import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;
import uk.ac.ed.inf.sdp.group4.strategy.RobotColour;

public class WorldState
{
	private long timestamp;

	private Ball ball;
	private Robot blue;
	private Robot yellow;

	public WorldState()
	{
		try
		{
			this.ball = new Ball(0, 0, 0, 0);
			this.blue = new Robot(0, 0, 0, 0, 0, RobotColour.BLUE);
			this.yellow = new Robot(0, 0, 0, 0, 0, RobotColour.YELLOW);
		}
		catch (InvalidAngleException e)
		{
			//log this
		}
	}

	public WorldState(WorldStateResponse response) throws BadWorldStateException
	{
		try
		{
			setTimestamp(response.getTimestamp());
			setBall(new Ball(response.getBall().getPosition().getX(),
			                 response.getBall().getPosition().getY(),
			                 response.getBall().getVelocity().getDirection(),
			                 response.getBall().getVelocity().getMagnitude()));
			setBlue(new Robot(response.getBlue().getPosition().getX(),
			                  response.getBlue().getPosition().getY(),
			                  response.getBlue().getVelocity().getDirection(),
			                  response.getBlue().getVelocity().getMagnitude(),
			                  response.getBlue().getRotation(),
			                  RobotColour.BLUE));
			setYellow(new Robot(response.getYellow().getPosition().getX(),
			                    response.getYellow().getPosition().getY(),
			                    response.getYellow().getVelocity().getDirection(),
			                    response.getYellow().getVelocity().getMagnitude(),
			                    response.getYellow().getRotation(),
			                    RobotColour.YELLOW));
		}
		catch (InvalidAngleException iae)
		{
			throw new BadWorldStateException(iae.getMessage());
		}
	}

	public WorldState(Ball ball, Robot blue, Robot yellow) throws BadWorldStateException
	{
		setTimestamp(0);
		setBall(ball);
		setBlue(blue);
		setYellow(yellow);
	}

	public void setTimestamp(long timestamp)
	{
		this.timestamp = timestamp;
	}

	public long getTimestamp()
	{
		return this.timestamp;
	}

	public void setBall(Ball ball)
	{
		this.ball = ball;
	}

	public Ball getBall()
	{
		return ball;
	}

	public void setBlue(Robot blue)
	{
		this.blue = blue;
	}

	public Robot getBlue()
	{
		return blue;
	}

	public void setYellow(Robot yellow)
	{
		this.yellow = yellow;
	}

	public Robot getYellow()
	{
		return yellow;
	}
}
