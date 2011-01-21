import lejos.nxt.*;
import lejos.nxt.comm.*;
import java.io.*;


public class Marvin
{
	public static void main(String [] args) throws InterruptedException
	{

		TouchSensor sensor1 = new TouchSensor(SensorPort.S1);
		TouchSensor sensor2 = new TouchSensor(SensorPort.S2);
		UltrasonicSensor sensor3 = new UltrasonicSensor(SensorPort.S3);
		Robot robot = new Robot();
		Communicator communicator = new Communicator();

		while (true)
		{
			if (Button.ESCAPE.isPressed())
			{
				break;
			}

			//process commands
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
					robot.drive((float)command[1] * -1);
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

			//check sensors
			if ((sensor1.isPressed()) || (sensor2.isPressed()))
			{
				robot.drive(-20);
			}

			if (sensor3.getDistance() < 10) 
			{
				robot.drive(10);
			}
		}
		
		System.exit(0);
	}
}
