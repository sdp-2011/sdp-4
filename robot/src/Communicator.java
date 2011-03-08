import java.io.*;
import java.util.Queue;

import lejos.nxt.*;
import lejos.nxt.comm.*;

public class Communicator
{
	public static Queue commands;
	public static int status;

	private static BTConnection connection;
	private static DataOutputStream dataOut;
	private static DataInputStream dataIn;

	private static boolean keepReceiving;
	private static boolean sendLock;
	
	private boolean connected;

	public Communicator()
	{
		connected = false;

		while (true)
		{
			if (!connected)
			{
				connected = true;
				keepReceiving = true;
				sendLock = false;
				commands = new Queue();
				new Thread(new CommandReciever()).start();
			}
		}
	}

	public int[] getCommand()
	{
		return (int[]) commands.pop();
	}

	public void sendStatus(int val)
	{
		status = val;
		new Thread(new StatusSender()).start();
	}

	private class CommandReciever implements Runnable
	{
		public void run()
		{
			Log.connectionOpen(false);
			connection = Bluetooth.waitForConnection();
			Log.connectionOpen(true);
			dataIn = connection.openDataInputStream();
			dataOut = connection.openDataOutputStream();
			while (keepReceiving)
			{
				try
				{
					int[] command = new int[2];
					command[0] = dataIn.readInt();
					command[1] = dataIn.readInt();
					commands.push(command);
				}
				catch (IOException e)
				{
					Log.connectionOpen(false);
					keepReceiving = false;
				}
			}

			connected = false;
		}
	}

	public void finish()
	{
		keepReceiving = false;
	}

	private class StatusSender implements Runnable
	{
		public void run()
		{
			while (sendLock)
			{
				//block
			}
			sendLock = true;
			try
			{
				dataOut.writeInt(status);
				dataOut.flush();
			}
			catch (IOException e)
			{
				Log.e("Status cannot be sent");
			}
			sendLock = false;
		}
	}
}
