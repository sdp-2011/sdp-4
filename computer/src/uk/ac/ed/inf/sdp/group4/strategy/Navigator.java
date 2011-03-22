package uk.ac.ed.inf.sdp.group4.strategy;

import static com.google.common.base.Preconditions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;
import uk.ac.ed.inf.sdp.group4.domain.Position;
import uk.ac.ed.inf.sdp.group4.domain.Vector;
import uk.ac.ed.inf.sdp.group4.world.Robot;
import uk.ac.ed.inf.sdp.group4.world.IVisionClient;
import uk.ac.ed.inf.sdp.group4.world.WorldState;

public class Navigator
{
	private SamuelLJackson negotiator;

	private List<Waypoint> waypoints = Collections.synchronizedList(
			new ArrayList<Waypoint>());
	
	public Navigator(Controller controller, IVisionClient client, RobotColour colour)
	{
		setNegotiator(new SamuelLJackson(controller, client, colour));
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
	
	public void addWaypoint(Position destination, int angle)
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
		this.negotiator.abortCurrentWaypoint();
	}
	
	private class SamuelLJackson implements Runnable
	{
		private final int MAX_SPEED = 400;
		
		private Controller controller;
		private IVisionClient client;
		private RobotColour colour;
		
		private Waypoint currentWaypoint;
		
		public SamuelLJackson(Controller controller, IVisionClient client,
				RobotColour colour)
		{
			setController(controller);
			setClient(client);
			setColour(colour);
		}
		
		private void setColour(RobotColour colour)
		{		
			checkNotNull(colour);
			this.colour = colour;
		}

		public void setController(Controller controller)
		{
			checkNotNull(controller, "Controller cannot be null");
			this.controller = controller;
		}
		
		public void setClient(IVisionClient client)
		{
			checkNotNull(client, "IVisionClient cannot be null");
			this.client = client;
		}
		
		public void run()
		{
			boolean atDestination = false;
			
			while(true)
			{
				// Get the current waypoint.
				if (atDestination || this.currentWaypoint == null)
				{
					this.currentWaypoint = grabWaypoint();
					if (this.currentWaypoint == null)
					{
						continue;
					}
					atDestination = false;
				}
				
				// Get the current robot position.
				Robot robot = getCurrentPosition();
				
				System.out.println("Robot: [Angle: " + robot.getFacing() + "] [Position: (" + robot.getX() + ", " + robot.getY() + ")]");
				System.out.println("Waypoint: (" + this.currentWaypoint.getX() + ", " + this.currentWaypoint.getY() + ")");
				
				Vector vectorToTarget = null;
				try {
					vectorToTarget = robot.getPosition().calcVectTo(
							this.currentWaypoint.getPosition());
				} catch (InvalidAngleException e) {
					e.printStackTrace();
				}
				
				// Are we at the destination?
				if (vectorToTarget.getMagnitude() < 10)
				{
					atDestination = true;
					continue;
				}
				else
				{
					double angle = vectorToTarget.angleFrom(robot.getFacing())/2;

					System.out.println("S-Angle: " + angle);
					
					if (angle < 0)
					{
						System.out.println("Turning left");
						
						int rightMotorSpeed = (int)(MAX_SPEED * 0.85);
						int leftMotorSpeed = (int)(0.85 * (MAX_SPEED - Math.sqrt(MAX_SPEED)*Math.abs(angle)/45));

						//checkState(rightMotorSpeed > leftMotorSpeed);

						if (leftMotorSpeed > 900) 
						{
							leftMotorSpeed = 900;
						}
						else if (leftMotorSpeed < -900) 
						{
							leftMotorSpeed = -900;
						}

						if (rightMotorSpeed > 900) 
						{
							rightMotorSpeed = 900;
						}
						else if (rightMotorSpeed < -900) 
						{
							rightMotorSpeed = -900;
						}

						controller.setRightMotorSpeed(rightMotorSpeed);
						controller.setLeftMotorSpeed(leftMotorSpeed);
					}
					else
					{
						System.out.println("Turning right");

						int leftMotorSpeed = MAX_SPEED;
						int rightMotorSpeed = (int)(0.85 * (MAX_SPEED - Math.sqrt(MAX_SPEED)*Math.abs(angle)/45));

						//checkState(leftMotorSpeed > rightMotorSpeed);

						if (leftMotorSpeed > 900) 
						{
							leftMotorSpeed = 900;
						}
						else if (leftMotorSpeed < -900) 
						{
							leftMotorSpeed = -900;
						}

						if (rightMotorSpeed > 900) 
						{
							rightMotorSpeed = 900;
						}
						else if (rightMotorSpeed < -900) 
						{
							rightMotorSpeed = -900;
						}

						controller.setLeftMotorSpeed(leftMotorSpeed);
						controller.setRightMotorSpeed(rightMotorSpeed);
					}
					
					try
					{
						Thread.sleep(40);
					}
					catch (InterruptedException ie)
					{
					}
				}
				
			}
		}
		
		public void abortCurrentWaypoint()
		{
			this.currentWaypoint = null;
		}
		
		private Waypoint grabWaypoint()
		{
			synchronized(waypoints)
			{
				if (waypoints.isEmpty())
				{
					return null;
				}
				return waypoints.remove(0);
			}
		}
		
		private Robot getCurrentPosition()
		{
			WorldState state = client.getWorldState();
			return (this.colour == RobotColour.BLUE) ? state.getBlue() : state.getYellow();
		}
	}
}
