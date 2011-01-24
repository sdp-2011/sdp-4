import lejos.nxt.*;
import java.io.*;

public class Demo
{
	public static void newBuild(Robot robot) throws InterruptedException
	{
		robot.drive(10.0f, false);
		robot.drive(-10.0f, false);
		robot.shoot();
		Button.waitForPress();
	}
}
