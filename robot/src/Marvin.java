import lejos.nxt.*;
import lejos.nxt.comm.*;
import java.io.*;


public class Marvin
{
	public static void main(String [] args) throws InterruptedException
	{
		Robot robot = new Robot();
		Communicator communicator = new Communicator();

		while (true)
		{
			if (Button.ESCAPE.isPressed())
			{
				break;
			}

			if (communicator.commandRecieved)
			{
				int command = communicator.getCommand();
				int argument = communicator.getArgument();

				LCD.drawInt(command, 0, 4);
				LCD.drawInt(argument, 0, 5);

				if (command == 0)
				{
					robot.drive((float)argument);
				}
				else if (command == 1)
				{
					robot.drive((float)argument);
				}
				else if (command == 2)
				{
					robot.shoot();
				}
				else if (command == 3)
				{
					break;
				}

				communicator.commandRecieved = false;
			}
		}
		
		System.exit(0);
	}
}
