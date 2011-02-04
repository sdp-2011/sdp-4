package uk.ac.ed.inf.sdp.group4.sim;

public class Robot
{

	final int LENGTH = 20;
	final int WIDTH = 18;

	final boolean HOLO = false;

	//Speed: cm / s
	final int SPEED = 100;

	//TURN: s / 360 degrees
	final int TURN = 2;

	//Forward 1, backward -1
	private int direction = 0;

	// angle 0 is north (degrees)
	private double angle = 90;

	private double xLoc = 0;
	private double yLoc = 0;

	private double kickPower = 1;

	public int getDirection()
	{
		return direction;
	}

	public void setDirection(int direction)
	{
		this.direction = direction;
	}

	public double getAngle()
	{
		return angle;
	}

	public double getRadAngle()
	{
		return Math.toRadians(angle);
	}

	public void setAngle(double angle)
	{
		this.angle = angle;
	}

	public double getxLoc()
	{
		return xLoc;
	}

	public void setxLoc(double xLoc)
	{
		this.xLoc = xLoc;
	}

	public double getyLoc()
	{
		return yLoc;
	}

	public void setyLoc(double yLoc)
	{
		this.yLoc = yLoc;
	}

	public double getKickPower()
	{
		return kickPower;
	}

	public void setKickPower(double kickPower)
	{
		this.kickPower = kickPower;
	}

	public int getLENGTH()
	{
		return LENGTH;
	}

	public int getWIDTH()
	{
		return WIDTH;
	}

	public boolean isHOLO()
	{
		return HOLO;
	}

	public int getSPEED()
	{
		return SPEED;
	}

	public int getTURN()
	{
		return TURN;
	}


}
