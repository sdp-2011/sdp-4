package uk.ac.ed.inf.sdp.group4.strategy;

import uk.ac.ed.inf.sdp.group4.world.IVisionClient;
import uk.ac.ed.inf.sdp.group4.controller.Controller;
import java.io.*;

public class KeyboardStrategy extends Strategy
{
	private BufferedReader keyboard;

	public KeyboardStrategy(IVisionClient client, Controller controller, RobotColour colour)
	{
		super(client, controller, colour, false);

		keyboard = new BufferedReader(new InputStreamReader(System.in));
	}

	public void tick()
	{
		try
		{
			System.out.println("Command?");
			int command = Integer.parseInt(keyboard.readLine());
			System.out.println("Argument?");
			int argument = Integer.parseInt(keyboard.readLine());
			controller.sendCommand(command, argument);
		}
		catch (Exception e)
		{
			System.out.println("IS THAT A NUMBER? NO! TRY AGAIN DUMBASS");
		}
	}

	public void attack()
	{
	}

	public void defend()
	{
	}
}
