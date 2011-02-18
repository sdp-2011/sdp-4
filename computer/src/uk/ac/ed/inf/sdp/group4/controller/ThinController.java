package uk.ac.ed.inf.sdp.group4.controller;

public class ThinController extends Controller
{
	public void driveForward(int val)
	{
		System.out.println("Driving forward:" + val);
		//tell sim robot to drive forward val amount
	}

	public void driveBackward(int val)
	{
		System.out.println("Driving backward:" + val);
		//tell sim robot to drive backwards val amount
	}

	public void shoot()
	{
		System.out.println("Shooting!");
		//tell sim robot to kick
	}

	public void beserk(boolean val)
	{
		System.out.println("BESERK:" + val);
		//switch beserk on or off on the sim robot
	}

	public void turn(double angle)
	{
		if (angle >= 0)
		{
			turnRight((int)angle);
		}
		else
		{
			turnLeft((int)angle * -1);
		}
	}

	public void turnLeft(int angle)
	{
		System.out.println("Driving left:" + angle);
		//turn the sim robot left
	}

	public void turnRight(int angle)
	{
		System.out.println("Driving right:" + angle);
		//turn the sim right
	}

	public void finish()
	{
		//tell sim robot to turn off
	}

	public void sendCommand(int command, int argument)
	{
		//send command to sim robot
	}
}
