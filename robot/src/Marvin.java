import lejos.nxt.*;
import lejos.nxt.comm.*;
import java.io.*;

public class Marvin
{
	boolean sensorsActive = true;

	public enum Instruction
	{
		FORWARD  (0),
		BACKWARD (1),
		SHOOT    (2),
		FINISH   (3);

		private int value;

		private Instruction(int value)
		{
			this.value = value;
		}

		public int getValue()
		{
			return this.value;
		}
	}

	public static void main(String [] args)
	{
		new Marvin().start();
	}

	public void start()
	{
		// Basic control and communication utility classes
		Robot robot = new Robot();
		Communicator communicator = new Communicator();

		// Sensors
		TouchSensor leftTouchSensor = new TouchSensor(SensorPort.S1);
		TouchSensor rightTouchSensor = new TouchSensor(SensorPort.S2);
		UltrasonicSensor ultrasonicSensor = new UltrasonicSensor(SensorPort.S3);

		// UI Setup
		LCD.drawString("Sensors: ACTIVE", 0, 7);

		while (true)
		{
			// If the escape button is pressed then we should exit out of the
			// program. This saves us from having to do hard resets on the
			// device and having to endure the annoying-as-fuck startup sound.
			if (Button.ESCAPE.isPressed())
			{
				break;
			}

			// If the left arrow is pressed then the robot should stop reacting
			// to its sensors. This allows the robot to be moved and tested
			// without it spinning up it's tracks because the sensors have been
			// accidentally triggered.
			if (Button.LEFT.isPressed())
			{
				sensorsActive = !sensorsActive;

				StringBuffer message = new StringBuffer("Sensors: ");
				message.append(sensorsActive ? "ACTIVE" : "INACTIVE");

				LCD.drawString(message.toString(), 0, 7);
			}

			// Process Commands
			if (!communicator.commands.empty())
			{
				int[] command = communicator.getCommand();
				int instruction = command[0];
				int argument = command[1];

				LCD.drawInt(instruction, 0, 4);
				LCD.drawInt(argument, 0, 5);

				if (instruction == Instruction.FORWARD.getValue())
				{
					robot.drive((float)argument);
				}
				else if (instruction == Instruction.BACKWARD.getValue())
				{
					robot.drive((float)argument * -1);
				}
				else if (instruction == Instruction.SHOOT.getValue())
				{
					robot.shoot();
				}
				else if (instruction == Instruction.FINISH.getValue())
				{
					break;
				}
			}

			// Check Sensors
			if (sensorsActive)
			{
				if ((leftTouchSensor.isPressed()) || (rightTouchSensor.isPressed()))
				{
					robot.drive(-20);
				}

				if (ultrasonicSensor.getDistance() < 10) 
				{
					robot.drive(10);
				}
			}
		}
		
		System.exit(0);
	}
}
