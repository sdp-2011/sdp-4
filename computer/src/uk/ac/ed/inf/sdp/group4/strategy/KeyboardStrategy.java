package uk.ac.ed.inf.sdp.group4.strategy;

import uk.ac.ed.inf.sdp.group4.world.VisionClient;
import uk.ac.ed.inf.sdp.group4.controller.Controller;
import java.io.*;

public class KeyboardStrategy extends Strategy
{
	public KeyboardStrategy(VisionClient client, Controller controller, RobotColour colour)
	{
			super(client, controller, colour);
	}

	public void runStrategy()
	{
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		
		while (true)
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
}
