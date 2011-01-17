import java.lang.Math;

import lejos.nxt.*;

/**
 * This class
 *
 * @author	Chris Brown <cb@tardis.ed.ac.uk>
 */
public class Robot
{
	private float WHEEL_DIAMETER = 5.5f;
	private double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;

	public enum Direction {
		FORWARD,
		BACKWARD
	}

	public boolean isMoving()
	{
		return (Motor.A.isMoving() || Motor.B.isMoving());
	}

	public void drive(Direction direction)
	{
		if (direction.equals(Direction.FORWARD))
		{
			Motor.A.forward();
			Motor.B.forward();
		}
		else if (direction.equals(Direction.BACKWARD))
		{
			Motor.A.backward();
			Motor.B.backward();
		}
	}

	public void drive(Direction direction, int distance)
	{
		double rotations = distance / WHEEL_CIRCUMFERENCE;
		int angle = (int)(rotations * 360);

		if (direction.equals(Direction.BACKWARD))
		{
			angle *= -1;
		}

		Motor.A.rotate(angle, true);
		Motor.B.rotate(angle, true);
	}

	public void reverseDirection()
	{
		Motor.A.reverseDirection();
		Motor.B.reverseDirection();
	}

	public void floatMotors()
	{
		Motor.A.flt();
		Motor.B.flt();
	}

	public void stop()
	{
		Motor.A.stop();
		Motor.B.stop();
	}

	public void hold()
	{
		this.hold(100);
	}

	public void hold(int power)
	{
		Motor.A.lock(power);
		Motor.B.lock(power);
	}

	public void shoot()
	{
		Motor.C.rotate(180);
		Motor.C.rotate(-180);
	}

	// The code following this line is a complete travesty. I'm not even
	// going to give it the honor of some documentation.
	public void singHappyBirthday()
	{
		try
		{
			playNote(392,500);
			playNote(392,200);
			playNote(440,500);
			playNote(392,500);
			playNote(523,500);
			playNote(493,500);
			Thread.sleep(500);
			playNote(392,500);
			playNote(392,200);
			playNote(440,500);
			playNote(392,200);
			playNote(587,500);
			playNote(523,500);
			Thread.sleep(500);
			playNote(392,500);
			playNote(392,200);
			playNote(784,500);
			playNote(659,500);
			playNote(523,200);
			playNote(523,200);
			playNote(493,500);
			playNote(440,500);
			Thread.sleep(500);
			playNote(698,500);
			playNote(698,500);
			playNote(659,500);
			playNote(523,500);
			playNote(587,500);
			playNote(523,500);
		}
		catch (InterruptedException e)
		{
		}
	}

	private void playNote(int frequency, int length)
	{
		Sound.playTone(frequency, length);

		try
		{
			Thread.sleep(length);
		}
		catch (InterruptedException e)
		{
		}
	}
}
