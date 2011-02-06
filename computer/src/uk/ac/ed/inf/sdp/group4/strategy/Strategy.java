package uk.ac.ed.inf.sdp.group4.strategy;

import uk.ac.ed.inf.sdp.group4.world.VisionClient;
import uk.ac.ed.inf.sdp.group4.controller.Controller;

public abstract class Strategy implements IStrategy
{
	private VisionClient client;
	private Controller controller;
	private RobotColour ourColour;

	public Strategy(VisionClient client, Controller controller, RobotColour ourColour)
	{
		this.client = client;
		this.controller = controller;
		this.ourColour = ourColour;
	}

	public void runStrategy()
	{
	}

	public VisionClient getVisionClient()
	{
		return this.client;
	}

	public Controller getController()
	{
		return this.controller;
	}

	public RobotColour ourColour()
	{
		return this.ourColour;
	}

	public RobotColour enemyColour()
	{
		if (ourColour().equals(RobotColour.BLUE))
		{
			return RobotColour.YELLOW;
		}
		else
		{
			return RobotColour.BLUE;
		}
	}
}