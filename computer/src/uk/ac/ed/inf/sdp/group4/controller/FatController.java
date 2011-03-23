package uk.ac.ed.inf.sdp.group4.controller;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

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
		System.out.println("Kicking!");
		log.debug("Shooting!");
		sendCommand(2, 0);
	}

	public void beserk(boolean val)
	{
		if (val)
		{
			log.debug("Beserk Mode: Enabled");
			sendCommand(98, 1);
		}
		else
		{
			log.debug("Beserk Mode: Disabled");
			sendCommand(98, 0);
		}
	}

	public void turnLeft(int angle)
	{
		log.debug(String.format("Turning Left: %d degrees", angle));

		sendCommand(4, (int)(angle*0.7));
	}

	public void turnRight(int angle)
	{
		log.debug(String.format("Turning Right: %d degrees", angle));

		sendCommand(5, (int)(angle*0.7));
	}

	public void steer(int angle)
	{
		log.debug(String.format("Steering: %d turn rate", angle));
		sendCommand(3, (int)(angle*0.7));
	}

	public void setSpeed(int motorSpeed)
	{
		log.debug(String.format("Changing motor speed: %d degrees per second", motorSpeed));
		sendCommand(97, motorSpeed);
	}

	public void stop()
	{
		log.debug("Stopping robot.");
		sendCommand(6, 0);
	}

	public void finish()
	{
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
					log.error("Something has gone wrong");
					System.exit(1);
				}
			}
		}
	}

	@Override
	public void driveForward()
	{
		log.debug("Driving forward indefinitely.");
		sendCommand(50, 0);
	}

	@Override
	public void driveBackward()
	{
		log.debug("Driving backward indefinitely.");
		sendCommand(51, 0);
	}

	@Override
	public void setLeftMotorSpeed(int speed)
	{
		checkArgument(speed >= -900, "Speed must be more than -900");
		checkArgument(speed <= 900, "Speed must be less than 900");
		
		log.debug(String.format("Setting the left motor speed: %d", speed));
		sendCommand(52, speed);
	}

	@Override
	public void setRightMotorSpeed(int speed) 
	{
		checkArgument(speed >= -900, "Speed must be more than -900");
		checkArgument(speed <= 900, "Speed must be less than 900");
		
		log.debug(String.format("Setting the right motor speed: %d", speed));
		sendCommand(53, speed);
	}
}
