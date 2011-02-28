package uk.ac.ed.inf.sdp.group4.sim;

import com.google.inject.Inject;

import uk.ac.ed.inf.sdp.group4.world.WorldState;
import uk.ac.ed.inf.sdp.group4.world.IVisionClient;

public class FakeVision implements IVisionClient
{
	public WorldState state;

	@Inject
	public FakeVision(WorldState state)
	{
		this.state = state;
	}

	public WorldState getWorldState()
	{
		return state;
	}
}


