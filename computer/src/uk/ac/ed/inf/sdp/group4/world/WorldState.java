import uk.ac.ed.inf.sdp.group4.WorldStateCommunication.WorldStateResponse;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.Robot;
import uk.ac.ed.inf.sdp.group4.world.BadWorldStateException;
import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;

class WorldState
{
	private Ball ball;
	private Robot blue;
	private Robot yellow;

	public WorldState(WorldStateResponse response) throws BadWorldStateException
	{
		try
		{
			ball = new Ball(response.getBall().getPosition().getX(),
					response.getBall().getPosition().getY(),
					response.getBall().getVelocity().getDirection(),
					response.getBall().getVelocity().getMagnitude());

			blue = new Robot(response.getBlue().getPosition().getX(),
					response.getBlue().getPosition().getY(),
					response.getBlue().getVelocity().getDirection(),
					response.getBlue().getVelocity().getMagnitude(),
					response.getBlue().getRotation());

			yellow = new Robot(response.getYellow().getPosition().getX(),
					response.getYellow().getPosition().getY(),
					response.getYellow().getVelocity().getDirection(),
					response.getYellow().getVelocity().getMagnitude(),
					response.getYellow().getRotation());

		}
		catch (InvalidAngleException iae)
		{
			throw new BadWorldStateException(iae.getMessage());
		}

	}
}
