import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import uk.ac.ed.inf.sdp.group4.domain.Position;
import uk.ac.ed.inf.sdp.group4.domain.Vector;
import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;

import uk.ac.ed.inf.sdp.group4.controller.Controller;

import uk.ac.ed.inf.sdp.group4.strategy.Strategy;
import uk.ac.ed.inf.sdp.group4.strategy.RobotColour;
import uk.ac.ed.inf.sdp.group4.strategy.TrackBallStrategy;
import uk.ac.ed.inf.sdp.group4.world.BadWorldStateException;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.IVisionClient;
import uk.ac.ed.inf.sdp.group4.world.WorldState;
import uk.ac.ed.inf.sdp.group4.world.Robot;

public class TestTrackBallStrategy
{
	static TestController testController;
	IVisionClient visionClient;
	Strategy trackBallStrategy;

	@BeforeClass
	public static void beforeClass()
	{
		testController = new TestController();
		Logger root = Logger.getRootLogger();
		root.setLevel(Level.OFF);
	}

	@Before
	public void beforeTest()
	{
		testController.reset();
		trackBallStrategy = null;
	}

	@Test
	public void testDirectShot() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		Ball ball = new Ball(400, 160, 0, 0.0f);
		Robot blue = new Robot(380, 160, 0, 0.0f, 90.0f, RobotColour.BLUE);
		Robot yellow = new Robot(0, 0, 0, 0.0f, 0.0f, RobotColour.YELLOW);
		WorldState state = new WorldState(ball, blue, yellow);

		// A fake vision client to report this.
		visionClient = new TestVisionClient(state);
		
		// Run an iteration
		trackBallStrategy = new TrackBallStrategy(visionClient, testController, RobotColour.BLUE);
		trackBallStrategy.tick();

		// Did we shoot?
		assertEquals(2, testController.getCommand());
	}
}

