import java.io.*;
import lejos.nxt.*;
import lejos.nxt.comm.*;
import java.util.Queue;

public class Communicator
{
	public static Queue commands;

	private static BTConnection connection;
	private static DataOutputStream dataOut;
	private static DataInputStream dataIn;

	private static boolean keepReceiving = true;
	private CommunicationDelegate delegate = null;

	public Communicator()
	{
		commands = new Queue();
		//delegate = cd;
		new Thread(new CommandReciever()).start();
	}

	public int[] getCommand()
	{
		return (int[]) commands.pop();
	}

	private class CommandReciever implements Runnable
	{
		public void run()
		{
			LCD.drawString("No Connection...", 0, 0);
			connection = Bluetooth.waitForConnection();
			LCD.drawString("Connection Open", 0, 0);
			dataOut = connection.openDataOutputStream();
			dataIn = connection.openDataInputStream();

			while (keepReceiving)
			{
				try
				{
					int[] command = new int[2];
					command[0] = dataIn.readInt();
					command[1] = dataIn.readInt();
					commands.push(command);
					LCD.drawInt(command[0], 0, 1);
					LCD.drawInt(command[1], 0, 2);
				}
				catch (IOException e)
				{
					LCD.drawString("Connection Done", 0, 0);
					keepReceiving = false;
				}
			}
		}
	}
}
