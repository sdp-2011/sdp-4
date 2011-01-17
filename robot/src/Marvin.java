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
		InputStream stream = blueT.openInputStream();

		while (true)
		{
			try
			{
				System.out.println("Waiting for a signal...");
				int data = stream.read();
				System.out.println("Got a " + data);

				if (data == 0)
				{
					System.out.println("Driving forward...");
					robot.drive(Robot.Direction.FORWARD);
				}

				else if (data == 1)
				{
					System.out.println("Driving backward...");
					robot.drive(Robot.Direction.BACKWARD);
				}

				else if (data == 2)
				{
					System.out.println("Shooting...");
					robot.shoot();
				}
			}

			catch (IOException e)
			{
				System.out.println(e.getMessage());
				Thread.sleep(5000);
				System.exit(0);
			}
		}
	}
}
