
public class Robot extends Mover
{
	// Simple booleans for Robot states
	public boolean blueColour; // We're either blue or we're not
	public boolean hasBall; // Have we got a ball?

	// Constructor
	public Robot(float direction, float speed, int posx, int posy, boolean blueColour)
	{
		super(direction, speed, posx, posy);
		this.blueColour = blueColour;
	}
	// Simple get functions for booleans
	public boolean getBlueColour()
	{
		return blueColour;
	}

	public boolean hasBall()
	{
		return hasBall;
	}
}
