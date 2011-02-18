package uk.ac.ed.inf.sdp.group4.strategy;

import java.lang.Math.*;


import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.domain.*;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.Robot;
import uk.ac.ed.inf.sdp.group4.world.VisionClient;
import uk.ac.ed.inf.sdp.group4.world.WorldState;

public class TrackBallStrategy extends Strategy
{
    private Robot robot;
    private Ball ball;

    int goalNortX = 0;  //need to get real goal positions
    int goalNorthY = 0;
    int goalSouthX =0;
    int goalSouthY =0;

    public TrackBallStrategy(VisionClient client, Controller controller, RobotColour colour)
    {
        super(client, controller, colour);
    }

    public void runStrategy()
    {
        log.debug("Starting strategy loop...");
        while (true)
        {
            log.debug("Starting a new cycle...");
            refresh();

            //Vector robotVector = null;
            //Vector robotGoal = null;

            //try
            //{ 
                //robotVector = new Vector(robot.getFacing(), 0);
                //robotGoal = robot.getPosition().vectorTo(goalNorthX, goalNorthY);
            //}
            //catch (InvalidAngleException e)
            //{
                //log.error(e.getMessage());
            //}

            /**
             * The variable angle can be anywhere from -180 to +180. If it is
             * positive then it means turn right and inversely if it is
             * negative then it means turn left.
             */
            Vector ballRoute = getBallRoute();
            double ballAngle = ballRoute.angleTo(robot.getFacing());
            //double goalAngle = robotVector.angleTo(robotGoal);

            //log.debug("Goal is at distance: " + robotGoal.getMagnitude());

            // If we are a long turning distance from the ball then we should
            // turn towards it.
            if (Math.abs(ballAngle) > 10)
            {
                controller.turn(ballAngle);
                pause(3000);
            }
            else
            {   
                // If we're close to the ball and the goal is close  then we should shoot.
                if (ballRoute.getMagnitude() < 50) //&& robotGoal.getMagnitude() < 200)
                {
                    log.debug("Shooting - Distance to ball: " + ballRoute.getMagnitude());
                    controller.shoot();
                }

                // If we're close to the ball and the goal is far  then we should drive with a ball to the goal.
                //if (route.getMagnitude() < 50 && robotGoal.getMagnitude() >= 200)
                //{

                    //System.out.println("Driving to the goal! " + angle2);
                    //if (Math.abs(angle2) > 10)
                    //{
                        //System.out.println("Shiftin' " + angle2);

                        //if (right2)
                        //{
                            //controller.right((int)angle2);
                        //}
                        //else
                        //{
                            //controller.left((int)angle2 * -1);
                        //}
                    //}
                    //else 
                    //{
                        //System.out.println("drive! ");
                        //controller.drivef(Math.abs((int)RobotGoal.getMagnitude()/2 - 20));
                    //}
                //}

                // If we're not close then we should drive towards it.
                //
                // The messy distance at the end of the line is required until we get
                // accurate movement.
                controller.driveForward(Math.abs((int)ballRoute.getMagnitude()/2 - 15));
                pause(500);
            }
        }
    }

    private void pause(int milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        }
        catch (InterruptedException ignored)
        {

        }
    }

    private void refresh()
    {
        WorldState state = client.getWorldState();

        if (ourColour() == RobotColour.BLUE)
        {
            robot = state.getBlue();
        }
        else
        {
            robot = state.getYellow();
        }

        ball = state.getBall();
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

        log.debug("Robot is facing: " + robot.getFacing());
        log.debug("Ball is towards: " + route.getDirection());
        log.debug("Ball is at distance: " + route.getMagnitude());

        return route;
    }
}
