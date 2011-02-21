package uk.ac.ed.inf.sdp.group4.controller;

import uk.ac.ed.inf.sdp.group4.world.WorldState;
import uk.ac.ed.inf.sdp.group4.domain.Vector;
import uk.ac.ed.inf.sdp.group4.sim.SimBot;
import uk.ac.ed.inf.sdp.group4.sim.Action;
import java.lang.Math;

public class ThinController extends Controller
{
	private SimBot bot;
	
	public ThinController(SimBot bot)
	{
		this.bot = bot;
	}

	public void driveForward(int val)
	{
		System.out.println("Driving forward:" + val);
		bot.newAction(new Action(Action.Type.FORWARD, val));
	}

	public void driveBackward(int val)
	{
		System.out.println("Driving backward:" + val);
		bot.newAction(new Action(Action.Type.REVERSE, val));
	}

	public void shoot()
	{
		System.out.println("Shooting!");
		bot.newAction(new Action(Action.Type.SHOOT, 0));
	}

	public void beserk(boolean val)
	{
		System.out.println("BESERK:" + val);
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

	public void turnRight(int angle)
	{
	}

	public void turnLeft(int angle)
	{
		bot.newAction(new Action(Action.Type.RIGHT, angle));
	}

	public void turnLeft(int angle)
	{
		bot.newAction(new Action(Action.Type.LEFT, angle));
	}

	public void setSpeed(int val)
	{

	}

	public void steer(int val)
	{

	}

	public void finish()
	{
		//tell sim robot to turn off
	}

	public void sendCommand(int command, int argument)
	{
		if (command == 0)
		{
			driveForward(argument);
		}
		else if (command == 1)
		{
			driveBackward(argument);
		}
		else if (command == 2)
		{
			shoot();
		}
	}
}
