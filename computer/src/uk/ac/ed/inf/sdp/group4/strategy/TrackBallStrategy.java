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

	//Position ballPos = new Position(0,0);

    public TrackBallStrategy(IVisionClient client, Controller controller, RobotColour colour)
    {
        this(client, controller, colour, false);
		this.navigator = new Navigator(controller, client, colour);
		controller.beserk(false);
    }

    public TrackBallStrategy(IVisionClient client, Controller controller, RobotColour colour, boolean testing)
    {
        super(client, controller, colour, testing);
		this.navigator = new Navigator(controller, client, colour);
		controller.beserk(false);
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

		Boolean facingOwnGoal = true;
/*
		if (currentGoal.equals(westGoal))
		{
			int roBang = (int) (robot.getFacing() % 360);

			if ((roBang > 190) && (roBang < 350))
			{
				facingOwnGoal = false;
			}

		}		
		else if (currentGoal.equals(eastGoal))
		{
			int roBang = (int) (robot.getFacing() % 360);

			if ((roBang > 10) && (roBang < 170))
			{
				facingOwnGoal = false;
			}

		}	
	
		log.debug("Facing our own goal : " + facingOwnGoal);

        if (ball.isHidden() && !facingOwnGoal)
        {
            log.debug("Can't see the ball - Shooting!");
            controller.shoot();
        }
*/
		navigator.navigateTo(ball.getPosition(),0); 

		if (robot.isNear(ball))
		{
			controller.shoot();
		}
		
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
