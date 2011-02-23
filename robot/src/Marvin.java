import lejos.nxt.*;
import lejos.nxt.comm.*;
import java.io.*;

/**
 * Main class for all of the code running on the robot.
 *
 * Operation Instructions:
 *
 *   Escape Button - Exit program.
 *   Left button   - Toggle sensors.
 */
public class Marvin
{
	// Movement
	private final int REACTION_DISTANCE = 10;
	private final int ULTRASONIC_THRESHOLD = 10;
	private final boolean MOTORS_REVERSED = false;

	// Basic control and communication utility classes
	Robot robot = new Robot(MOTORS_REVERSED);
	Communicator communicator = new Communicator();

	// Sensors
	TouchSensor leftTouchSensor = new TouchSensor(SensorPort.S1);
	TouchSensor rightTouchSensor = new TouchSensor(SensorPort.S2);
	UltrasonicSensor ultrasonicSensor = new UltrasonicSensor(SensorPort.S3);

	// Fields
	private boolean sensorsActive = true;
	

	/**
	 * Allows for a standard interface of instructions throughout the code.
	 *
	 * This is to stop "magic" integer comparisons appearing and getting out of
	 * hand.
	 */
	public enum Instruction
	{
		FORWARD(0),
		BACKWARD(1),
		SHOOT(2),
		STEER(3),
		LEFT(4),
		RIGHT(5),
		STOP(6),
		SETSPEED(97),
		BESERK(98),
		FINISH(99);

		private int value;

		/**
		 * Enum Constructor taking a command number
		 */
		private Instruction(int value)
		{
			this.value = value;
		}

		/**
		 * Returns the value of a command for comparisons.
		 *
		 * @return the command number
		 */
		public int getValue()
		{
			return this.value;
		}
	}

	/**
	 * The starting point for the program.
	 *
	 * This method just makes in instance of the {@link Marvin} class in
	 * a non-static way and then gets the program going.
	 */
	public static void main(String [] args)
	{
		new Marvin().start();
	}

	/**
	 * Runs the main control loop for the robot along with other
	 * responsibilities.
	 *
	 * This method has an infinite while loop in which is the main event loop
	 * for the robot. Each cycle it checks if any buttons have been pressed and
	 * handles their actions. It then checks for if any commands have been sent
	 * to the robot with the {@link Communicator} class. Finally, it checks to
	 * see if any of the sensors have been activated and reacts accordingly.
	 *
	 * @see Communicator
	 * @see Robot
	 */
	public void start()
	{
		// UI Setup
		Log.sensorsActive(true);
		while (true)
		{
			checkButtons();
			processCommands();
			checkSensors();
		}
	}

	/**
	 * Shuts the robot down.
	 *
	 * Perform any clean up actions that need to be done in this method.
	 */
	private void shutdown()
	{
		System.exit(0);
	}

	/**
	 * Checks to see if any of the buttons have been pressed in this loop
	 * iteration.
	 */
	private void checkButtons()
	{
		// If the escape button is pressed then we should exit out of the
		// program. This saves us from having to do hard resets on the
		// device and having to endure the annoying-as-fuck startup sound.
		if (Button.ESCAPE.isPressed())
		{
			shutdown();
		}
		// If the left arrow is pressed then the robot should stop reacting
		// to its sensors. This allows the robot to be moved and tested
		// without it spinning up it's tracks because the sensors have been
		// accidentally triggered.
		if (Button.LEFT.isPressed())
		{
			sensorsActive = !sensorsActive;
			Log.sensorsActive(sensorsActive);
		}
	}

	/**
	 * Runs any new commands from the PC controller.
	 *
	 * If there are any commands in the command queue from the PC then
	 * parse them and run them.
	 */
	private void processCommands()
	{
		// Process Commands
		if (!communicator.commands.empty())
		{
			int[] command = communicator.getCommand();
			int instruction = command[0];
			int argument = command[1];

			// What should we do?
			if (instruction == Instruction.FORWARD.getValue())
			{
				if (MOTORS_REVERSED)
				{
					robot.drive((float)argument * -1);
				}
				else
				{
					robot.drive((float)argument);
				}
			}
			else if (instruction == Instruction.BACKWARD.getValue())
			{
				if (MOTORS_REVERSED)
				{
					robot.drive((float)argument);
				}
				else
				{
					robot.drive((float)argument * -1);
				}
			}
			else if (instruction == Instruction.SHOOT.getValue())
			{
				robot.shoot();
			}
			else if (instruction == Instruction.FINISH.getValue())
			{
				shutdown();
			}
			else if (instruction == Instruction.BESERK.getValue())
			{
				sensorSwitch(argument != 0);
			}
			else if (instruction == Instruction.LEFT.getValue())
			{
				if (MOTORS_REVERSED)
				{
					robot.right(argument);
				}
				else
				{
					robot.left(argument);
				}
			}
			else if (instruction == Instruction.RIGHT.getValue())
			{
				if (MOTORS_REVERSED)
				{
					robot.left(argument);
				}
				else
				{
					robot.right(argument);
				}
			}
			else if (instruction == Instruction.STEER.getValue())
			{
				robot.steer(argument);
			}
			else if (instruction == Instruction.SETSPEED.getValue())
			{
				robot.setSpeed(argument);
			}
			else if (instruction == Instruction.STOP.getValue())
			{
				robot.stop();
			}
		}
	}

	/**
	 * Check any sensors for input this loop iteration. This method will
	 * block if sensors aren't actually connected.
	 */
	private void checkSensors()
	{
		// Check Sensors
		if (sensorsActive)
		{
			// If one of the (front) touch sensors are pressed then the
			// robot should drive a short distance backwards.
			if ((leftTouchSensor.isPressed()) || (rightTouchSensor.isPressed()))
			{
				if (MOTORS_REVERSED)
				{
					robot.drive(REACTION_DISTANCE, false);
				}
				else
				{
					robot.drive(-REACTION_DISTANCE, false);
				}
			}
			// If the (back) ultrasonic sensors are triggered then the
			// robot should drive a short distance forwards.
			if (ultrasonicSensor.getDistance() < ULTRASONIC_THRESHOLD)
			{
				if (MOTORS_REVERSED)
				{
					robot.drive(-REACTION_DISTANCE, false);
				}
				else
				{
					robot.drive(REACTION_DISTANCE, false);
				}
			}
		}
	}

	private void sensorSwitch(boolean val)
	{
		sensorsActive = val;
	}
}
