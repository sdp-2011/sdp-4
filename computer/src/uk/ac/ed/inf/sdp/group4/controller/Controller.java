package uk.ac.ed.inf.sdp.group4.controller;

import org.apache.log4j.Logger;

public abstract class Controller
{
	// Logger. Like a lumberjack but it prints stuff out.
	protected static Logger log = Logger.getLogger(Controller.class);

	// Drive Commands
	public abstract void driveForward(int cm);
	public abstract void driveBackward(int cm);
	
	// Turning Commands
	public abstract void turn(double degrees);
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
	protected abstract void sendCommand(int command, int argument);
}
