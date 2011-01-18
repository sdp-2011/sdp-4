import lejos.nxt.*;
import lejos.nxt.comm.*;
import java.io.*;


public class Marvin
{
	public static void main(String [] args) throws InterruptedException
	{
		Robot robot = new Robot();
		System.out.println("Waiting for a connection...");
		NXTConnection blueT = Bluetooth.waitForConnection();
		System.out.println("Got a connection.");
		DataInputStream stream = blueT.openDataInputStream();
		String[] data = null;

		while (true)
		{
			try
			{
				data = split(stream.readLine());

				if (data == null)
				{
					System.exit(0);
				}

				System.out.println("command = " + data[0]);
				Thread.sleep(5000);
			}

			catch (IOException e)
			{
				System.out.println("IOERROR");
				Thread.sleep(5000);
			}

			if (data[0].equals("drivef"))
			{
				System.out.println("Driving forward....");
				robot.drive(Float.parseFloat(data[1]));
			}

			else if (data[0].equals("driveb"))
			{
				System.out.println("Driving forward....");
				robot.drive(Float.parseFloat(data[1]));
			}

			else if (data[0].equals("shoot"))
			{
				System.out.println("Shooting...");
				robot.shoot();
			}

			else if (data[0].equals("finish"))
			{
				break;
			}
		}
		
		System.out.println("Exiting...");
		Thread.sleep(1000);
		System.exit(0);
	}

	private static String[] split(String line)
	{
		String[] arr = new String[2];
		int split = line.indexOf(",");

		if (split == -1)
		{
			return new String[] { line };
		}
		
		arr[0] = line.substring(0, split);
		arr[1] = line.substring(split+1, line.length());

		return arr;
	}
}
