import lejos.nxt.*;
import lejos.nxt.comm.*;
import java.io.*;


public class Marvin
{
	public static void main(String [] args)
	{
		new Marvin().start();
	}

	public void start()
	{
		Robot robot = new Robot();
		Communicator communicator = new Communicator();

		while (true)
		{
			if (Button.ESCAPE.isPressed())
			{
				break;
			}

			if (!communicator.commands.empty())
			{
				int[] command = communicator.getCommand();

				LCD.drawInt(command[0], 0, 4);
				LCD.drawInt(command[1], 0, 5);

				if (command[0] == 0)
				{
					robot.drive((float)command[1]);
				}
				else if (command[0] == 1)
				{
					robot.drive((float)command[1]);
				}
				else if (command[0] == 2)
				{
					robot.shoot();
				}
				else if (command[0] == 3)
				{
					break;
				}
			}
		}
		
		System.exit(0);
	}
}
