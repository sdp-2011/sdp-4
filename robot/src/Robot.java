import java.lang.Math;

import lejos.nxt.*;
import lejos.robotics.navigation.Pilot;
import lejos.robotics.navigation.TachoPilot;

/**
 * This class
 *
 * @author Chris Brown <cb@tardis.ed.ac.uk>
 */
public class Robot
{
	private Motor LEFT_MOTOR = Motor.A;
	private Motor RIGHT_MOTOR = Motor.B;

	private Motor SHOOT_MOTOR = Motor.C;
	private int SHOOT_ANGLE = 180;

	private float WHEEL_DIAMETER = 5.5f;
	private float TRACK_WIDTH = 10.0f;

	private Pilot pilot;

	public Robot()
	{
		pilot = new TachoPilot(WHEEL_DIAMETER, TRACK_WIDTH, LEFT_MOTOR, RIGHT_MOTOR);
	}

	public boolean isMoving()
	{
		return pilot.isMoving();
	}

	public void drive(float distance)
	{
		pilot.travel(distance, true);
	}

	public void stop()
	{
		pilot.stop();
	}

	public void hold()
	{
		this.hold(100);
	}

	public void hold(int power)
	{
		LEFT_MOTOR.lock(power);
		RIGHT_MOTOR.lock(power);
	}

	public void shoot()
	{
		SHOOT_MOTOR.rotate(SHOOT_ANGLE);
		SHOOT_MOTOR.rotate(-SHOOT_ANGLE);
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
		catch (InterruptedException e) {}
	}

	private void playNote(int frequency, int length) throws InterruptedException
	{
		Sound.playTone(frequency, length);
		Thread.sleep(length);
	}
}
