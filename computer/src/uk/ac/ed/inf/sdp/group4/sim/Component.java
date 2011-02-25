package uk.ac.ed.inf.sdp.group4.sim;

import uk.ac.ed.inf.sdp.group4.world.WorldObject;

public abstract class Component
{
	public abstract void update(int time);
	public abstract	double getRadius();
	public abstract WorldObject getObject();
}
