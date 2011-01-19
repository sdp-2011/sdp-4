import lejos.nxt.*;
import lejos.nxt.comm.*;
import java.io.*;


public class Marvin
{
	public static void main(String [] args) throws InterruptedException
	{
		Robot robot = new Robot();
		Communicator communicator = new Communicator();

		while (true)
		{
			if (Button.isPressed)
			{
				System.exit(0);
			}
		}
			//if (communicator.commandRecieved)
			//{

			//}

			//try
			//{
				//String idata = stream.readLine();

				//if (idata == null)
				//{
					//System.out.println("Recieved null command");
					//Thread.sleep(1000);
					//System.exit(0);
				//}

				//data = split(idata);

				//System.out.println("command = " + idata);
				//Thread.sleep(5000);
			//}

			//catch (IOException e)
			//{
				//System.out.println("IOERROR");
				//Thread.sleep(5000);
			//}

			//if (data[0].equals("drivef"))
			//{
				//System.out.println("Driving forward....");
				//robot.drive(Float.parseFloat(data[1]));
			//}

			//else if (data[0].equals("driveb"))
			//{
				//System.out.println("Driving backward....");
				//robot.drive(-Float.parseFloat(data[1]));
			//}

			//else if (data[0].equals("shoot"))
			//{
				//System.out.println("Shooting...");
				//robot.shoot();
			//}

			//else if (data[0].equals("finish"))
			//{
				//break;
			//}
		//}
		
		//System.out.println("Exiting...");
		//Thread.sleep(1000);
		//System.exit(0);
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
