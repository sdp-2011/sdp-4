package uk.ac.ed.inf.sdp.group4.sim;

import uk.ac.ed.inf.sdp.group4.world.WorldState;
import uk.ac.ed.inf.sdp.group4.world.IVisionClient;

public class FakeVision implements IVisionClient
{
	public WorldState state;

	public FakeVision(WorldState state)
	{
		this.state = state;
	}

	public WorldState getWorldState()
	{
		return state;
	}
}


