package helper;

import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.domain.Position;

import java.util.ArrayList;

public class TestController extends Controller
{
	int command;
	int argument;
	ArrayList<Integer[]> instructions = new ArrayList<Integer[]>();

	public void reset()
	{
		instructions.clear();
	}

	public void driveForward(int distance)
	{
		sendCommand(0, distance);
	}

	public void driveBackward(int distance)
	{
		sendCommand(1, distance);
	}

	public void shoot()
	{
		sendCommand(2, 0);
	}

	public void beserk(boolean val)
	{
		if (val)
		{
			sendCommand(98, 1);
		}
		else
		{
			sendCommand(98, 0);
		}
	}

	public void steer(int angle)
	{
		sendCommand(3, angle);
	}

	public void turnLeft(int angle)
	{
		sendCommand(4, angle);
	}

	public void turnRight(int angle)
	{
		sendCommand(5, angle);
	}

	public void stop()
	{
		sendCommand(6, 0);
	}

	public void setSpeed(int motorSpeed)
	{
		sendCommand(97, motorSpeed);
	}

	public void finish()
	{
		sendCommand(99, 0);
	}

	public void sendCommand(int command, int argument)
	{
		Integer[] instruction = new Integer[] {command, argument};
		instructions.add(instruction);
	}

	public int getCommand()
	{
		return this.getCommand(0);
	}

	public int getCommand(int index)
	{
		return this.instructions.get(index)[0];
	}

	public int getArgument()
	{
		return this.getArgument(0);
	}

	public int getArgument(int index)
	{
		return this.instructions.get(index)[1];
	}

	public ArrayList<Integer[]> getInstructions()
	{
		return this.instructions;
	}

	@Override
	public void driveForward() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void driveBackward() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLeftMotorSpeed(int speed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRightMotorSpeed(int speed) {
		// TODO Auto-generated method stub
		
	}
}

