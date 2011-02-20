package uk.ac.ed.inf.sdp.group4.controller;

import lejos.pc.comm.*;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class FatController extends Controller
{
	private NXTComm nxtComm;
	private DataInputStream dataIn;
	private DataOutputStream dataOut;
	private Queue<Integer> robotStatus;

	public FatController()
	{
		try
		{
			log.info("Attempting connection with robot...");
			nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
			NXTInfo nxtInfo = new NXTInfo(NXTCommFactory.BLUETOOTH, "WAR BASTARD", "00:16:53:0A:07:1D");
			nxtComm.open(nxtInfo);
			log.info("Connection achieved!");
		}
		catch (NXTCommException e)
		{
			log.error("Failed to connect.");
			e.printStackTrace();
		}

		// Create the IO streams
		dataOut = new DataOutputStream(nxtComm.getOutputStream());
		dataIn = new DataInputStream(nxtComm.getInputStream());

		robotStatus = new LinkedList<Integer>();
		new Thread(new StatusReciever()).start();
	}

	public void driveForward(int distance)
	{
		log.debug(String.format("Driving forwards: %dcm", distance));
		sendCommand(0, distance);
	}

	public void driveBackward(int distance)
	{
		log.debug(String.format("Driving backwards: %dcm", distance));
		sendCommand(1, distance);
	}

	public void shoot()
	{
		log.debug("Shooting!");
		sendCommand(2, 0);
	}

	public void beserk(boolean val)
	{
		if (val)
		{
			log.debug("Beserk Mode: Enabled");
			sendCommand(3, 1);
		}
		else
		{
			log.debug("Beserk Mode: Disabled");
			sendCommand(3, 0);
		}
	}

	public void turn(double angle)
	{
		if (angle >= 0)
		{
			turnRight((int)angle);
		}
		else
		{
			turnLeft((int)angle * -1);
		}
	}

	public void turnLeft(int angle)
	{
		log.debug(String.format("Turning Left: %d degrees", angle));
		sendCommand(4, angle);
	}

	public void turnRight(int angle)
	{
		log.debug(String.format("Turning Right: %d degrees", angle));
		sendCommand(5, angle);
	}

	public void finish()
	{
		//calls finish on robot
		log.debug("Ending control of the robot...");
		sendCommand(99, 0);
		try
		{
			dataIn.close();
			dataOut.close();
			nxtComm.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void sendCommand(int command, int argument)
	{
		try
		{
			dataOut.writeInt(command);
			dataOut.writeInt(argument);
			dataOut.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public int getStatus()
	{
		return robotStatus.remove();
	}

	public boolean hasStatus()
	{
		return !robotStatus.isEmpty();
	}

	private class StatusReciever implements Runnable
	{
		public void run()
		{
			while (true)
			{
				try
				{
					robotStatus.add(dataIn.readInt());
				}
				catch (IOException e)
				{
					System.out.println("Something has gone wrong");
				}
			}
		}
	}
}
