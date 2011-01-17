import lejos.nxt.*;

public class Marvin
{
	public static void main(String [] args) throws InterruptedException
	{
		Robot robot = new Robot();
		robot.drive(Robot.Direction.FORWARD);
		Thread.sleep(500);
		robot.reverseDirection();
		Thread.sleep(500);
		robot.stop();
		Button.waitForPress();
	}
}
