package uk.ac.ed.inf.sdp.group4.strategy;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;
import uk.ac.ed.inf.sdp.group4.domain.Position;
import uk.ac.ed.inf.sdp.group4.domain.Vector;
import uk.ac.ed.inf.sdp.group4.strategy.tactic.PenaltySaveTactic;
import uk.ac.ed.inf.sdp.group4.strategy.tactic.PenaltyTakeTactic;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.IVisionClient;
import uk.ac.ed.inf.sdp.group4.world.Robot;
import uk.ac.ed.inf.sdp.group4.world.WorldState;

public class InterceptStrategy extends Strategy
{
    private Robot robot;
    private Robot enemyRobot;
    private Ball ball;
    private WorldState state;

    Position westGoal = new Position(30, 162);
    Position eastGoal = new Position(525, 162);

    private static int line = -1;

    public InterceptStrategy(IVisionClient client, Controller controller, RobotColour colour)
    {
        this(client, controller, colour, false);
		Logger root = Logger.getRootLogger();
		root.setLevel(Level.OFF);
    }

    public InterceptStrategy(IVisionClient client, Controller controller, RobotColour colour, boolean testing)
    {
        super(client, controller, colour, testing);
		Logger root = Logger.getRootLogger();
		root.setLevel(Level.OFF);
    }

    public InterceptStrategy(Controller controller, WorldState state)
    {
        super(null, controller, RobotColour.BLUE, false);
		Logger root = Logger.getRootLogger();
		root.setLevel(Level.OFF);
    }

    @Override
    public void tick()
    {
	    log.debug("=================================================");
	    log.debug("Starting a new cycle...");

	    // Get the world state.
	    refresh();

		if (line == -1)
			line = robot.getX();

		Vector interceptRoute = getInterceptRoute();
		double interceptAngle = interceptRoute.angleFrom(robot.getFacing());

		double robotDirection = (robot.getFacing())%360;

		if (false && Math.abs(robot.getY() - ball.getY()) < 20)
		{
			return;
		}

		else if (((robotDirection > 10) && (robotDirection <= 90))
			|| ((robotDirection >= 190) && (robotDirection <= 270)))
		{
			controller.setSpeed(900);
			int turnamount = (-10); 
			controller.turn(turnamount);
			pause(150);
		}
		else if (((robotDirection > 90) && (robotDirection <= 170))
		    || ((robotDirection >= 270) && (robotDirection <= 350)))
		{
			controller.setSpeed(900);
			int turnamount = 10;
			controller.turn(turnamount);
			pause(150);
		}
		else// if (ball.getVector().getMagnitude() < 2)
		{
		/*
		    int enemyAngle = (int)Math.tan(Math.toRadians(enemyRobot.getFacing()-90));
		    int destination = ((robot.getX() - enemyRobot.getX()) * enemyAngle) + enemyRobot.getY();

		    int distance = robot.getY() - destination;
		    System.out.println(distance);
		    
		    controller.driveForward((int)(-distance/2.1));
		    pause(1000);
		    */
		    
			if (Math.abs(interceptAngle) < 15)
			{
				controller.setSpeed(900);
				controller.driveForward((int)(interceptRoute.getMagnitude()/2));
				//pause((int)(interceptRoute.getMagnitude()/2)*10);
				pause(1000);
			}
			else if ((Math.abs(interceptAngle) > 165) && (Math.abs(interceptAngle) < 195)) 
			{
				controller.setSpeed(900);
				controller.driveBackward((int)(interceptRoute.getMagnitude()/2));
				//pause((int)(interceptRoute.getMagnitude()/2)*10);
				pause(1000);
			}
}
/*
		if (Math.abs(robot.getY() - ball.getY()) < 20)
		{
			return;
		}
		else if (Math.abs(interceptAngle) < 15)
		{
			controller.setSpeed(900);
			controller.driveForward((int)(interceptRoute.getMagnitude()/2));
			pause((int)(interceptRoute.getMagnitude()/2)*10);
		}
		else if ((Math.abs(interceptAngle) > 165) && (Math.abs(interceptAngle) < 195)) 
		{
			controller.setSpeed(900);
			controller.driveBackward((int)(interceptRoute.getMagnitude()/2));
			pause((int)(interceptRoute.getMagnitude()/2)*10);
		}
		else if (Math.abs(interceptAngle) < 95)
		{
			controller.setSpeed(400);
			int turnamount =((int)interceptAngle) % 360; 
			controller.turn(turnamount);
			pause(Math.abs(turnamount*9));
		}
		else 
		{
			controller.setSpeed(400);
			int turnamount =((int)interceptAngle-180) % 360; 
			controller.turn(turnamount);
			pause(Math.abs(turnamount*9));
		}
*/
	}

    private void refresh()
    {
        WorldState state = client.getWorldState();

        robot = (ourColour() == RobotColour.BLUE) ? state.getBlue() : state.getYellow();
        enemyRobot = (ourColour() == RobotColour.BLUE) ? state.getYellow() : state.getBlue();
        ball = state.getBall();

        log.debug("Robot is facing: " + robot.getFacing());
    }

	public void penaltyAttack()
	{
	}

	public void penaltyDefend()
	{
    }

	private Vector getInterceptRoute()
    {
        Vector interceptRoute = null;
		Position interceptPosition = new Position(line, ball.getY());

        try
        {
	    interceptRoute = robot.getPosition().calcVectTo(interceptPosition);
        }
        catch (InvalidAngleException e)
        {
            log.error(e.getMessage());
        }

        log.debug(String.format("Intercept is at position: (%d, %d)", interceptPosition.getX(), interceptPosition.getY()));
        log.debug("Intercept is towards: " + interceptRoute.getDirection());

        return interceptRoute;
    }

}

