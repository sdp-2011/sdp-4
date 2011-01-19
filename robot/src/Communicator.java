public class Communicator
{
	public static boolean commandRecieved = false

	private DataOutputStream dataOut;
	private DataIntputStream dataIn;

	private static boolean keepReceiving = true;

	public Communicator()
	{
		LCD.drawString("No connection...");
		BTConnection connection = Bluetooth.waitForConnection();
		dataOut = connection.openDataOutputStream();
		dataIn = connection.openDataIntputStream();
		LCD.drawString("Connection Opened");

		new Thread(CommandReciever).start();
	}

	private class CommandReciever implements Runnable
	{
		public void run()
		{
			while (keepReceiving)
			{
				commandRecieved = false;
				String command = dataIn.readUTF();
				commandRecieved = true;
				LCD.drawString(command, 1, 0);
			}
		}
	}
}
