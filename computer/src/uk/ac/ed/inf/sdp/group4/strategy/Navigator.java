package uk.ac.ed.inf.sdp.group4.strategy;

import static com.google.common.base.Preconditions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.domain.Position;
import uk.ac.ed.inf.sdp.group4.world.VisionClient;

public class Navigator
{
	private SamuelLJackson negotiator;

	private List<Waypoint> waypoints = Collections.synchronizedList(
			new ArrayList<Waypoint>());
	
	public Navigator(Controller controller, VisionClient client)
	{
		setNegotiator(new SamuelLJackson(controller, client));
		Thread negotiatorThread = new Thread(getNegotiator());
		negotiatorThread.start();
	}
	private SamuelLJackson getNegotiator() {
		return this.negotiator;
	}

	private void setNegotiator(SamuelLJackson samuelLJackson) {
		checkNotNull(samuelLJackson);
		this.negotiator = samuelLJackson;
	}

	public void navigateTo(Position destination, int angle)
	{
		clearWaypoints();
		addWaypoint(destination, angle);
	}
	
	private void addWaypoint(Position destination, int angle)
	{
		checkNotNull(destination);
		checkArgument(angle >= 0, "Angle must be >= 0");
		checkArgument(angle < 360, "Angle must be < 360");

		Waypoint waypoint = new Waypoint(destination, angle);
		this.waypoints.add(waypoint);
	}

	public void clearWaypoints()
	{
		this.waypoints.clear();
	}
	
	private class SamuelLJackson implements Runnable
	{
		private Controller controller;
		private VisionClient client;
		
		private Waypoint currentWaypoint;
		
		public SamuelLJackson(Controller controller, VisionClient client)
		{
			setController(controller);
			setClient(client);
		}
		
		public void setController(Controller controller)
		{
			checkNotNull(controller, "Controller cannot be null");
			this.controller = controller;
		}
		
		public void setClient(VisionClient client)
		{
			checkNotNull(client, "VisionClient cannot be null");
			this.client = client;
		}
		
		public void run()
		{
			
		}
		
		private Waypoint grabWaypoint()
		{
			synchronized(waypoints)
			{
				return waypoints.remove(0);
			}
		}
	}
}
