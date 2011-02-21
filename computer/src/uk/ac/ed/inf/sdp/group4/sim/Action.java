package uk.ac.ed.inf.sdp.group4.sim;

public class Action
{
	private Type type;
	private int argument;
	private double completed;

	public enum Type
	{
		FORWARD, REVERSE, SHOOT, LEFT, RIGHT
	}

	public Action(Type type, int argument)
	{
		this.type = type;
		this.argument = argument;
		this.completed = 0;
	}

	public Type getType()
	{
		return type;
	}

	public void addProgress(double progress)
	{
		completed += progress;
	}

	public boolean isDone()
	{
        return completed >= argument;
	}
}
