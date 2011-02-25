package uk.ac.ed.inf.sdp.group4.strategy;

import uk.ac.ed.inf.sdp.group4.world.IVisionClient;

public class FakePitch extends Pitch{
	
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	
	public FakePitch(IVisionClient client, RobotColour colour) {
		super(client, colour);
		fillArea(15, 0, 1, 15, WALL);
		units[14][0] = OURS;
		units [0][0] = THEIRS;
		units [16][0] = BALL;
	}
	
	public void repaint(){
		
	}
	
}
