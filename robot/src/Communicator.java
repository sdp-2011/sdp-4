import java.io.*;
import lejos.nxt.*;
import lejos.nxt.comm.*;
import java.util.Queue;

public class Communicator
{
	public static boolean commandRecieved = false;
	public static int command = -1;
	public static int argument = -1;

	private static BTConnection connection;
	private static DataOutputStream dataOut;
	private static DataInputStream dataIn;

	private static boolean keepReceiving = true;

	private CommunicationDelegate delegate = null;

	public Communicator()
	{
		//delegate = cd;
		new Thread(new CommandReciever()).start();
	}

	public int getCommand()
	{
		commandRecieved = false;
		return command;
	}

	public int getArgument()
	{
		return argument;
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
					command = dataIn.readInt();
					argument = dataIn.readInt();
					commandRecieved = true;
					LCD.drawInt(command, 0, 1);
					LCD.drawInt(argument, 0, 2);
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
