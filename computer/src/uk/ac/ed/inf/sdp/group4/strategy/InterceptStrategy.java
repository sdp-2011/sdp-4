package uk.ac.ed.inf.sdp.group4.strategy;

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

    public InterceptStrategy(IVisionClient client, Controller controller, RobotColour colour)
    {
        this(client, controller, colour, false);
    }

    public InterceptStrategy(IVisionClient client, Controller controller, RobotColour colour, boolean testing)
    {
        super(client, controller, colour, testing);
    }

    public InterceptStrategy(Controller controller, WorldState state)
    {
        super(null, controller, RobotColour.BLUE, false);
    }

    @Override
    public void tick()
    {
        log.debug("=================================================");
        log.debug("Starting a new cycle...");

        // Get the world state.
        refresh();

		Vector interceptRoute = getInterceptRoute();
        double interceptAngle = interceptRoute.angleTo(robot.getFacing());

		if (Math.abs(interceptAngle) < 20)
		{
			controller.setSpeed(900);
			controller.driveForward((int)interceptRoute.getMagnitude());
		}
		else
		{
			controller.setSpeed(900);
			controller.turn((int)interceptAngle);
		}

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
		Position interceptPosition = new Position(robot.getX(), ball.getY());

        try
        {
			interceptRoute = robot.getPosition().calcVectTo(interceptPosition);
        }
        catch (InvalidAngleException e)
        {
            log.error(e.getMessage());
        }

        log.debug("Intercept is towards: " + interceptRoute.getDirection());
        log.debug("Intercept is at distance: " + interceptRoute.getMagnitude());

        return interceptRoute;
    }

}

