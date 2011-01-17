import lejos.nxt.*;
import lejos.nxt.comm.*;
import java.io.*;


public class Marvin
{
	public static void main(String [] args) throws InterruptedException
	{
		Robot robot = new Robot();
		NXTConnection blueT = Bluetooth.waitForConnection();
		DataInputStream stream = blueT.openDataInputStream();

		while (true)
		{
			try
			{
				byte data = stream.readByte();

				if (data == 0)
				{
					robot.drive(Robot.Direction.FORWARD);
				}

				else if (data == 1)
				{
					robot.drive(Robot.Direction.BACKWARD);
				}

				else if (data == 2)
				{
					robot.shoot();
				}
			}

			catch (IOException e)
			{
				System.exit(0);
			}
		}
	}
}
