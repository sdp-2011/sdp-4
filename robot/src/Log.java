import lejos.nxt.*;

public class Log
{
	public static void connectionOpen(boolean open)
	{
		if (open)
		{
			connectionLine("Connection Open");
		}
		else
		{
			connectionLine("No Connection...");
		}
	}

	public static void sensorsActive(boolean active)
	{
		if (active)
		{
			sensorLine("Sensors: ACTIVE  ");
		}
		else
		{
			sensorLine("Sensors: INACTIVE");
		}
	}

	public static void m(String log)
	{
		LCD.drawString(log, 0, 6);
	}

	public static void e(String log)
	{
		Sound.playTone(350, 500);
		LCD.drawString(log, 0, 6);
	}

	private static void connectionLine(String line)
	{
		LCD.drawString(line, 0, 0);
	}

	private static void sensorLine(String line)
	{
		LCD.drawString(line, 0, 7);
	}
}
