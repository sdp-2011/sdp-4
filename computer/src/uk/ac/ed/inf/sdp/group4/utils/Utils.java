package uk.ac.ed.inf.sdp.group4.utils;

public class Utils
{
	public static int clamp(int val, int low, int high)
	{
		if (val > high) 
		{
			val = high;
		}

		else if (val < low) 
		{
			val = low;
		}

		return val;
	}

	public static void pause(int time)
	{
		try
		{
			Thread.sleep(time);
		}
		catch (InterruptedException e)
		{
			System.out.println("Threading is borked");
		}
	}
}
