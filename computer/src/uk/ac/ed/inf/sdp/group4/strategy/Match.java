package uk.ac.ed.inf.sdp.group4.strategy;

import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.world.VisionClient;

public class Match {
	VisionClient client;
	Controller controller;
	// The pitch grid
	private Pitch pitch = new Pitch();
	
	// Our pathfinder
	private PathFinder finder;
	
	// The path for our robot
	private Path path;
	
	// Declarations for robots
	Robot ourRobot = new Robot(0, 0, 0, 0, false);
	Robot otherBot = new Robot(0,0,0,0,false);
	Ball ball = new Ball(0,0,0,0);
	
	// Boolean for determining if we are in play or not
	private boolean playing = false;
	
	public Match(VisionClient client, Controller controller){
		this.client = client;
		this.controller = controller;
		finder = new AStarPathFinder(pitch, 500);
		while (playing == true){
			while (ourRobot.hasBall() == false){
				path = finder.findPath(ourRobot, ourRobot.getPosX(), ourRobot.getPosY(), ball.getPosX(), ball.getPosY());
			}
		}
	}
	
}
