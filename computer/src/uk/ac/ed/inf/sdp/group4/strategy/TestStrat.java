package uk.ac.ed.inf.sdp.group4.strategy;

import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.strategy.Path.Step;
import uk.ac.ed.inf.sdp.group4.world.VisionClient;

public class TestStrat {
	
	static VisionClient client = new VisionClient();
	static Controller controller;
	static RobotColour colour;
	static boolean testing;
	
	
	public static void main(String[] args){
		Match test = new Match(client, controller, colour, true);
		test.pitch = new FakePitch(client, colour);
		test.tick();
		Path path = test.path;
		for (int x = 0; x < path.getLength(); x++){
			Step step = path.getStep(x);
			System.out.println(step.getX() + " " + step.getY());
		}
		
	}
}
