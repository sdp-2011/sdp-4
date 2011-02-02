public interface IPredictor
{
	public WorldState predict(WorldState worldState, int millisecondsInFuture);
}
