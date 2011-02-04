public class Match {

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
	
	public Match(){
		
		finder = new AStarPathFinder(pitch, 500);
		while (playing = true){
			while (ourRobot.hasBall() == false){
				path = finder.findPath(ourRobot, ourRobot.getPosX(), ourRobot.getPosY(), ball.getPosX(), ball.getPosY());
			}
		}
	}
	
	
	
	public static void main (String argv[]){
		Match test = new Match();
	}
	
	
}
