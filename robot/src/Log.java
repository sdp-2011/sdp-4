import lejos.nxt.*;

public class Log
{
	public static void m(String log)
	{
		LCD.drawString(log, 0, 6);
	}

	public static void e(String log)
	{	
		Sound.playTone(350, 500);
		LCD.drawString(log, 0, 6);
	}
}
