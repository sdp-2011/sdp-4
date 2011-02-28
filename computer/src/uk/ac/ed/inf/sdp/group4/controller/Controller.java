package uk.ac.ed.inf.sdp.group4.controller;

import org.apache.log4j.Logger;

public abstract class Controller
{
	// Logger. Like a lumberjack but it prints stuff out.
	protected static Logger log = Logger.getLogger(Controller.class);

	// Drive Commands
	public abstract void driveForward(int cm);
	public abstract void driveBackward(int cm);
	public abstract void stop();

	// Turning Commands
	public void turn(double angle)
	{
		if (angle >= 0)
		{
			turnLeft((int)angle);
		}
		else
		{
			turnRight((int)angle * -1);
		}
	}

	public abstract void turnLeft(int degrees);
	public abstract void turnRight(int degrees);

	public abstract void steer(int turnRate);

	// Misc. Commands
	public abstract void shoot();
	public abstract void beserk(boolean val);
	public abstract void setSpeed(int speed);

	// End the program.
	public abstract void finish();

	// All of the other commands use this.
	public abstract void sendCommand(int command, int argument);

	// For Testing
	public abstract int getCommand();
	public abstract int getCommand(int index);
	public abstract int getArgument();
	public abstract int getArgument(int index);
}
