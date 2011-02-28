package uk.ac.ed.inf.sdp.group4.strategy;

import com.google.inject.Inject;

import uk.ac.ed.inf.sdp.group4.world.IVisionClient;
import uk.ac.ed.inf.sdp.group4.controller.Controller;
import java.io.*;

public class KeyboardStrategy extends Strategy
{
	private BufferedReader keyboard;

	@Inject
	public KeyboardStrategy(IVisionClient client, Controller controller)
	{
		super(client, controller, false);
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
}
