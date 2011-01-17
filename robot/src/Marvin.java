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
		String data = null;

		while (true)
		{
			try
			{
				data = stream.readLine();

				if (data == null)
				{
					System.exit(0);
				}

				System.out.println("command = " + data);
				Thread.sleep(5000);
			}

			catch (IOException e)
			{
				System.out.println("IOERROR");
				Thread.sleep(5000);
			}

			if (data.equals("drivef"))
			{
				System.out.println("Driving forward...");
				robot.drive(Robot.Direction.FORWARD, 20);
			}
			
			else if (data.equals("driveb"))
			{
				System.out.println("Driving backward...");
				robot.drive(Robot.Direction.BACKWARD);
			}

			else if (data.equals("shoot"))
			{
				System.out.println("Shooting...");
				robot.shoot();
			}

			else if (data.equals("finish"))
			{
				break;
			}
		}
		
		System.out.println("Exiting...");
		Thread.sleep(5000);
		System.exit(0);
	}
}
