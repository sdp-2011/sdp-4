import com.google.inject.Inject;

import uk.ac.ed.inf.sdp.group4.world.IVisionClient;
import uk.ac.ed.inf.sdp.group4.world.WorldState;

class TestVisionClient implements IVisionClient
{
	WorldState state;

	@Inject
	public TestVisionClient(WorldState state)
	{
		this.state = state;
	}

	public WorldState getWorldState()
	{
		return this.state;
	}
}
