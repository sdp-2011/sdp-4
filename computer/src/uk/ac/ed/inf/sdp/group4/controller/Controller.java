package uk.ac.ed.inf.sdp.group4.controller;

public abstract class Controller
{
	public abstract void drivef(int val);
	public abstract void driveb(int val);
	public abstract void shoot();
	public abstract void left(int angle);
	public abstract void right(int angle);
	public abstract void beserk(boolean val);
	public abstract void finish();
	public abstract void sendCommand(int command, int argument);
}
