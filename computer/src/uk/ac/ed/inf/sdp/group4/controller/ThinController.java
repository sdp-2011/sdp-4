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

	public void drivef(int val)
	{
		System.out.println("Driving forward:" + val);
		bot.newAction(new Action(Action.Type.FORWARD, val));
	}

	public void driveb(int val)
	{
		System.out.println("Driving backwards:" + val);
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
		bot.newAction(new Action(Action.Type.TURN, angle));	
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
