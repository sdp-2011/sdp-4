package uk.ac.ed.inf.sdp.group4.sim;

public class Action
{
	private String type;
	private int argument;
	private double completed;

	public Action(String type, int argument)
	{
		this.type = type;
		this.argument = argument;
		this.completed = 0;
	}

	public void addProgress(double progress)
	{
		completed += progress;
	}

	public boolean isDone()
	{
		if (completed >= argument)
		{
			return true;
		}
			
		return false;
	}
}
