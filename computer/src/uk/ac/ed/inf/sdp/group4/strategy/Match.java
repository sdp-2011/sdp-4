package uk.ac.ed.inf.sdp.group4.strategy;

import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.world.VisionClient;

public class Match {
	VisionClient client;
	Controller controller;

	public Pitch pitch = new Pitch();
	// Our pathfinders
	private PathFinder pathfinder;
	private PathFinder trajectory;
	
	// The path for our robot
	private Path path;
	
	// Declarations for robots
	Robot ourRobot = new Robot(0, 0, 0, 0, false);
	Robot otherBot = new Robot(0,0,0,0,false);
	Ball ball = new Ball(0,0,0,0);
	
	// Boolean for determining if we are in play or not
	private boolean playing = false;
	float usToBall;
	float themToBall;
	float usToThem;
	
	public Match(VisionClient client, Controller controller){
		
		
		this.client = client;
		this.controller = controller;
		pathfinder = new AStarPathFinder(pitch, 500);
		trajectory = new TrajectoryFinder(pitch, ourRobot);
		
		while (playing == true){
			// These will probably be useful eventually
			usToBall = (float)(Math.sqrt(Math.pow((ourRobot.getPosX() - ball.getPosX()), 2) + Math.pow((ourRobot.getPosY() - ball.getPosY()), 2)));
			themToBall = (float)(Math.sqrt(Math.pow((otherBot.getPosX() - ball.getPosX()), 2) + Math.pow((ourRobot.getPosY() - ball.getPosY()), 2)));
			usToThem = (float)(Math.sqrt(Math.pow((ourRobot.getPosX() - otherBot.getPosX()), 2) + Math.pow((ourRobot.getPosY() - otherBot.getPosY()), 2)));
			
			// Check if we have the ball
			while (ourRobot.hasBall() == false){
				// Check if it's worth trying to get to the ball, using ratio of distance between us and opponent
				if (((float) usToBall / themToBall) > 1.2){
					path = pathfinder.findPath(ourRobot, ourRobot.getPosX(), ourRobot.getPosY(), ball.getPosX(), ball.getPosY());
					if (path != null){
						// follow path
					}
					else {
						// can't get too ball, play defensively until we get it
					}
				}
				else {
					// Defensive play
				}
			}
			while (ourRobot.hasBall() == true){
				// Goal-finding and scoring algorithm here
			}
				
		}
	}
	
}
