import uk.ac.ed.inf.sdp.group4.world.IVisionClient;
import uk.ac.ed.inf.sdp.group4.world.WorldState;

class TestVisionClient implements IVisionClient
{
	WorldState state;

	public TestVisionClient(WorldState state)
	{
		this.state = state;
	}

	public WorldState getWorldState()
	{
		return this.state;
	}
}
