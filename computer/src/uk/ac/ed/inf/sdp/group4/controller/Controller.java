package uk.ac.ed.inf.sdp.group4.controller;

import org.apache.log4j.Logger;

import uk.ac.ed.inf.sdp.group4.domain.Position;

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
			turnRight((int)angle);
		}
		else
		{
			turnLeft((int)angle * -1);
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
	
	// Smooth Commands
	public abstract void driveForward();
	public abstract void driveBackward();
	
	public abstract void setLeftMotorSpeed(int speed);
	public abstract void setRightMotorSpeed(int speed);	
}
