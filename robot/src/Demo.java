import lejos.nxt.*;
import java.io.*;

public class Demo
{
	public static void main(String [] args) throws InterruptedException
	{
		Robot robot = new Robot();
		robot.drive(10.0f, false);
		robot.shoot();
		Button.waitForPress();
	}
}


