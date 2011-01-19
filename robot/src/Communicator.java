import java.io.*;
import lejos.nxt.*;
import lejos.nxt.comm.*;

public class Communicator
{
	public static boolean commandRecieved = false;

	private static BTConnection connection;
	private static DataOutputStream dataOut;
	private static DataInputStream dataIn;

	private static boolean keepReceiving = true;

	public Communicator()
	{
		new Thread(new CommandReciever()).start();
	}

	private class CommandReciever implements Runnable
	{
		public void run()
		{
			LCD.drawString("No connection...", 0, 0);
			connection = Bluetooth.waitForConnection();
			LCD.drawString("Connection Open", 0, 0);
			dataOut = connection.openDataOutputStream();
			dataIn = connection.openDataInputStream();

			while (keepReceiving)
			{
				commandRecieved = false;

				try
				{
					String command = dataIn.readLine();
					commandRecieved = true;
					LCD.drawString(command, 0, 1);
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
