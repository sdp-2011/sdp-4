package uk.ac.ed.inf.sdp.group4.strategy;

public class Mover
{

	// Variables for mover
	public float direction;

	public float speed;

	public int posx;

	public int posy;

	// Constructor
	public Mover(float direction, float speed, int posx, int posy)
	{
		this.direction = direction;
		this.speed = speed;
		this.posx = posx;
		this.posy = posy;
	}

	// Functions that should probably be affected by vision system
	public float getDirection()
	{
		return direction;
	}

	public float getSpeed()
	{
		return speed;
	}

	public int getPosX()
	{
		return posx;
	}

	public int getPosY()
	{
		return posy;
	}
}
