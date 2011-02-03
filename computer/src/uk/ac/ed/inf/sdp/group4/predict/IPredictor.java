package uk.ac.ed.inf.sdp.group4.predict;

import uk.ac.ed.inf.sdp.group4.world.WorldState;

public interface IPredictor
{
	public WorldState predict(WorldState worldState, int millisecondsInFuture);
}
