package uk.ac.ed.inf.sdp.group4.controller;

public class ThinController extends Controller
{
	public void drivef(int val)
	{
		System.out.println("Driving forward:" + val);
		//tell sim robot to drive forward val amount
	}

	public void driveb(int val)
	{
		System.out.println("Driving forward:" + val);
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

	public void left(int angle)
	{
		System.out.println("Driving left:" + angle);
		//turn the sim robot left
	}

	public void right(int angle)
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
