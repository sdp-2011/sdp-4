import uk.ac.ed.inf.sdp.group4.controller.Controller;

public class TestController extends Controller
{
	int command;
	int argument;

	public void reset()
	{
		this.command = -1;
		this.argument = -1;
	}

	public void driveForward(int distance)
	{
		sendCommand(0, distance);
	}

	public void driveBackward(int distance)
	{
		sendCommand(1, distance);
	}

	public void shoot()
	{
		sendCommand(2, 0);
	}

	public void beserk(boolean val)
	{
		if (val)
		{
			sendCommand(98, 1);
		}
		else
		{
			sendCommand(98, 0);
		}
	}

	public void turn(double angle)
	{
		if (angle >= 0)
		{
			turnLeft((int)angle);
		}
		else
		{
			turnRight((int)angle * -1);
		}
	}

	public void steer(int angle)
	{
		sendCommand(3, angle);
	}

	public void turnLeft(int angle)
	{
		sendCommand(4, angle);
	}

	public void turnRight(int angle)
	{
		sendCommand(5, angle);
	}

	public void stop()
	{
		sendCommand(6, 0);
	}

	public void setSpeed(int motorSpeed)
	{
		sendCommand(97, motorSpeed);
	}

	public void finish()
	{
		sendCommand(99, 0);
	}

	public void sendCommand(int command, int argument)
	{
		this.command = command;
		this.argument = argument;
	}

	public int getCommand()
	{
		return this.command;
	}

	public int getArgument()
	{
		return this.argument;
	}
}

