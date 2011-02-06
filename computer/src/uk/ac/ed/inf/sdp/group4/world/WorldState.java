package uk.ac.ed.inf.sdp.group4.world;

import uk.ac.ed.inf.sdp.group4.WorldStateCommunication.WorldStateResponse;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.Robot;
import uk.ac.ed.inf.sdp.group4.world.BadWorldStateException;
import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;
import uk.ac.ed.inf.sdp.group4.strategy.RobotColour;

public class WorldState
{
	private Ball ball;
	private Robot blue;
	private Robot yellow;

	public WorldState(WorldStateResponse response) throws BadWorldStateException
	{
		try
		{
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
