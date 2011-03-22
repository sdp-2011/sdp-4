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

public class TrackBallStrategy extends Strategy
{
    private Robot robot;
    private Robot enemyRobot;
    private Ball ball;
    private WorldState state;
	private Navigator navigator;

	private static boolean penaltyTake = false;
	private static boolean penaltySave = false;

    Position westGoal = new Position(30, 162);
    Position eastGoal = new Position(525, 162);

	Position ballPos = new Position(0,0);

    public TrackBallStrategy(IVisionClient client, Controller controller, RobotColour colour)
    {
        this(client, controller, colour, false);
		this.navigator = new Navigator(controller, client, colour);
    }

    public TrackBallStrategy(IVisionClient client, Controller controller, RobotColour colour, boolean testing)
    {
        super(client, controller, colour, testing);
		this.navigator = new Navigator(controller, client, colour);
    }

    public TrackBallStrategy(Controller controller, WorldState state)
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

		if(penaltyTake){
			PenaltyTakeTactic ptt = new PenaltyTakeTactic(controller);
			ptt.tick(robot, enemyRobot, ball);
		}
		penaltyTake = false;

		if(penaltySave){
			PenaltySaveTactic pst = new PenaltySaveTactic(controller);
			pst.tick(robot, enemyRobot, ball);
		}
		penaltySave = false;
/*
		if(

		Boolean facingOwnGoal = true;

		if ( 
        if (ball.isHidden() && Math.abs(goalAngle) < 30)
        {
            log.debug("Can't see the ball - Shooting!");
            controller.shoot();
        }
*/
		controller.driveForward();

//		navigator.navigateTo(ball.getPosition(),0);
		navigator.navigateTo(eastGoal, 0);
		pause(10000);
    }

    private void refresh()
    {
        WorldState state = client.getWorldState();

        robot = (ourColour() == RobotColour.BLUE) ? state.getBlue() : state.getYellow();
        enemyRobot = (ourColour() == RobotColour.BLUE) ? state.getYellow() : state.getBlue();
        ball = state.getBall();

		if (ballPos.distance(ball.getPosition()) < 5) {
			ballPos = ball.getPosition();
		}
	
        log.debug("Robot is facing: " + robot.getFacing());
    }

    private Vector getGoalRoute()
    {
        Vector goalRoute = null;

        try
        {
			goalRoute = robot.getPosition().calcVectTo(currentGoal);
        }
        catch (InvalidAngleException e)
        {
            log.error(e.getMessage());
        }

        log.debug("Goal is towards: " + goalRoute.getDirection());
        log.debug("Goal is at distance: " + goalRoute.getMagnitude());

        return goalRoute;
    }

    private Vector getBallRoute()
    {
        Vector route = null;

        try
        {
            route = robot.getPosition().calcVectTo(ball.getPosition());
        }
        catch (InvalidAngleException e)
        {
            log.error(e.getMessage());
        }

        log.debug("Ball is towards: " + route.getDirection());
        log.debug("Ball is at distance: " + route.getMagnitude());

        return route;
	}

	public void penaltyAttack()
	{
		penaltyTake = true;
	}

	public void penaltyDefend()
	{
		penaltySave = true;
    }
        
	private Vector getEnemyBallRoute()
	{
		Vector route = null;

		try
		{
			route = enemyRobot.getPosition().calcVectTo(ball.getPosition());
		}       
		catch (InvalidAngleException e)
		{
			log.error(e.getMessage());
		}

		log.debug("Enemy's distance to the ball: " + route.getMagnitude());

		return route;
	}

	private Vector getEnemyUsRoute()
	{
		Vector route = null;

		try
		{
			route = robot.getPosition().calcVectTo(enemyRobot.getPosition());
		}
		catch (InvalidAngleException e)
		{
			log.error(e.getMessage());
		}

		log.debug("Enemy is towards: " + route.getDirection());
		log.debug("Enemy is at distance: " + route.getMagnitude());

		return route;
	 }
}
