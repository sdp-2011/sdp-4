package uk.ac.ed.inf.sdp.group4.strategy;

import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;
import uk.ac.ed.inf.sdp.group4.domain.Position;
import uk.ac.ed.inf.sdp.group4.domain.Vector;
import uk.ac.ed.inf.sdp.group4.strategy.Path.Step;
import uk.ac.ed.inf.sdp.group4.world.IVisionClient;
import uk.ac.ed.inf.sdp.group4.world.Robot;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.WorldState;


public class Match extends Strategy
{

	public Pitch pitch;
	// Our pathfinders
	private PathFinder pathfinder;
	//private PathFinder trajectory;

	// The path for our robot
	public Path path;

	// Declarations for robots
	private Robot ourRobot;
	private Robot otherBot;
	private Ball ball;
	
	// estimated goal positions
	Position westGoal = new Position(30, 162);
	Position eastGoal = new Position(525, 162);
	
	// Boolean for determining if we are in play or not
	float usToBall;
	float themToBall;
	float usToThem;
	
	public Match(IVisionClient client, Controller controller, RobotColour robotColour, boolean testing){
		
		super(client, controller, robotColour, testing);
		pitch = new Pitch();
		pathfinder = new AStarPathFinder(pitch, 5000);
	}
/*
	public Match(Controller controller, WorldState state)
	{
		super(null, controller, RobotColour.BLUE, false);
	}
*/
	public void tick(){
		pathfinder = new AStarPathFinder(pitch, 5000);
		//trajectory = new TrajectoryFinder(pitch, ourRobot);
		refresh();
		log.debug("Starting tick");
		pitch.repaint(otherBot.getPosition());
		// These will probably be useful eventually
		usToBall = (float)(Math.sqrt(Math.pow((ourRobot.getX() - ball.getX()), 2) + Math.pow((ourRobot.getY() - ball.getY()), 2)));
		themToBall = (float)(Math.sqrt(Math.pow((otherBot.getX() - ball.getX()), 2) + Math.pow((ourRobot.getY() - ball.getY()), 2)));
		usToThem = (float)(Math.sqrt(Math.pow((ourRobot.getX() - otherBot.getX()), 2) + Math.pow((ourRobot.getY() - otherBot.getY()), 2)));	
		controller.setSpeed(300);
		// Check if we have the ball
			
		try {
			if (ourRobot.hasBall(ball) == false){
				// Check if it's worth trying to get to the ball, using ratio of distance between us and opponent
				if (((float) usToBall / themToBall) > 0){
					path = pathfinder.findPath(ourRobot, ourRobot.getX(), ourRobot.getY(), ball.getX(), ball.getY());
					if (path != null){
						// follow path
						log.debug("Moving to ball");
						Step step = path.getStep(10);
						Position stepPos = new Position (step.getX(), step.getY());
						log.debug("Going to: (" + stepPos.getX() + ", " + stepPos.getY() + ")");
						Position robotPos = new Position (ourRobot.getX(), ourRobot.getY());
						Vector stepBot = Vector.calcVect(robotPos, stepPos);
						double ang = stepBot.angleTo(ourRobot.getFacing());
						if ((ang < 15) && (ang > -15)){
							log.debug("Full steam ahead");
							controller.driveForward(1);
							pause(1000);
						}
						else {
							log.debug("Turning " + ang);
							controller.turn(ang);
							pause(1000);
							controller.driveForward(1);
							pause(1000);
						}
						
					}
					else {
						log.debug("Can't move to ball");
						controller.driveBackward(5);
						pause(1000);
						// can't get too ball, play defensively until we get it
					}
				}
				else {
					// Defensive play
				}
			}
		} catch (InvalidAngleException e) {
		log.error(e.getMessage());
		}
		try {
			if (ourRobot.hasBall(ball) == true){
				// Goal-finding and scoring algorithm here
				log.debug("We has ball");
				Vector botToGoal = Vector.calcVect(ourRobot.getPosition(), eastGoal);
				double ang = botToGoal.angleTo(ourRobot.getFacing());
				// Can we shoot? If not move towards goal for better shot
				if (canShoot(ourRobot, ball) == true){
					if ((ang < 15) && (ang > -15)){
						log.debug("Firing main cannon");
						controller.shoot();
						pause(1000);
						controller.stop();
						pause(1000);
					}
					else {
						log.debug("Turning " + ang);
						controller.turn(ang);
						pause(1000);
						controller.shoot();
						pause(1000);
					}
				}
				else {
						path = pathfinder.findPath(ourRobot, ourRobot.getX(), ourRobot.getY(), eastGoal.getX(), eastGoal.getY());
						if (path != null){
							// follow path
							log.debug("Moving to goal with ball");
							Step step = path.getStep(1);
							Position stepPos = new Position (step.getX(), step.getY());
							log.debug("Going to: (" + stepPos.getX() + ", " + stepPos.getY() + ")");
							Position robotPos = new Position (ourRobot.getX(), ourRobot.getY());
							Vector stepBot = Vector.calcVect(robotPos, stepPos);
							double angTo = stepBot.angleTo(ourRobot.getFacing());
							if ((angTo < 15) && (angTo > -15)){
								log.debug("Full steam ahead");
								controller.driveForward(1);
								pause(1000);
							}
							else {
								log.debug("Turning " + angTo);
								controller.turn(angTo);
								pause(1000);
								controller.driveForward(1);
								pause(1000);
							}
						}
						else {
							log.debug("Can't move to goal :(");
							controller.driveBackward(5);
							pause(1000);
						}
					}	
				}

		} catch (InvalidAngleException e) {
			log.error(e.getMessage());
		}
	}	
	
	
	// Checks if shot is blocked
	public boolean canShoot(Robot robot, Ball ball) throws InvalidAngleException {
		double facing = ourRobot.getPosition().calcVectTo(eastGoal).getDirection();
		boolean blocked = false;
		for (int y = robot.getY(); y < eastGoal.getY(); y++){
			int x = (int)(y * Math.tan(facing) + ball.getX() + ball.getY() * Math.tan(facing));
			blocked = pitch.blocked(ball, x, y);
			if (blocked = true){
				break;
			}
		}
		log.debug("Can shoot = " + blocked);
		return blocked;
		
		
	}
	
	private void refresh()
	{
		WorldState state = client.getWorldState();

		ourRobot = (ourColour() == RobotColour.BLUE) ? state.getBlue() : state.getYellow();
		otherBot = (ourColour() == RobotColour.BLUE) ? state.getYellow() : state.getBlue();
		ball = state.getBall();

		log.debug("Robot is facing: " + ourRobot.getFacing());
	}

}
