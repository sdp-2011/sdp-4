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
		trackBallStrategy = new TrackBallStrategy(visionClient, testController, RobotColour.BLUE, true);
		trackBallStrategy.tick();

		// Did we shoot?
		assertEquals(2, testController.getCommand());
	}

	@Test
	public void testSimpleTurnRight() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		Ball ball = new Ball(400, 160, 0, 0.0f);
		Robot blue = new Robot(320, 160, 0, 0.0f, 0.0f, RobotColour.BLUE);
		Robot yellow = new Robot(0, 0, 0, 0.0f, 0.0f, RobotColour.YELLOW);
		WorldState state = new WorldState(ball, blue, yellow);

		// A fake vision client to report this.
		visionClient = new TestVisionClient(state);
		
		// Run an iteration
		trackBallStrategy = new TrackBallStrategy(visionClient, testController, RobotColour.BLUE, true);
		trackBallStrategy.tick();

		// Did we turn?
		assertEquals(5, testController.getCommand());
		assertEquals(90, testController.getArgument());
	}

	@Test
	public void testSimpleTurnLeft() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		Ball ball = new Ball(400, 160, 0, 0.0f);
		Robot blue = new Robot(320, 160, 0, 0.0f, 180.0f, RobotColour.BLUE);
		Robot yellow = new Robot(0, 0, 0, 0.0f, 0.0f, RobotColour.YELLOW);
		WorldState state = new WorldState(ball, blue, yellow);

		// A fake vision client to report this.
		visionClient = new TestVisionClient(state);
		
		// Run an iteration
		trackBallStrategy = new TrackBallStrategy(visionClient, testController, RobotColour.BLUE, true);
		trackBallStrategy.tick();

		// Did we turn?
		assertEquals(4, testController.getCommand());
		assertEquals(90, testController.getArgument());
	}

	@Test
	public void testOneEighty() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		Ball ball = new Ball(400, 160, 0, 0.0f);
		Robot blue = new Robot(320, 160, 0, 0.0f, 270.0f, RobotColour.BLUE);
		Robot yellow = new Robot(0, 0, 0, 0.0f, 0.0f, RobotColour.YELLOW);
		WorldState state = new WorldState(ball, blue, yellow);

		// A fake vision client to report this.
		visionClient = new TestVisionClient(state);
		
		// Run an iteration
		trackBallStrategy = new TrackBallStrategy(visionClient, testController, RobotColour.BLUE, true);
		trackBallStrategy.tick();

		// Did we turn?
		assertEquals(4, testController.getCommand());
		assertEquals(180, testController.getArgument());
	}

	@Test
	public void testDriveForwardsClose() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		Ball ball = new Ball(400, 160, 0, 0.0f);
		Robot blue = new Robot(360, 160, 0, 0.0f, 90.0f, RobotColour.BLUE);
		Robot yellow = new Robot(0, 0, 0, 0.0f, 0.0f, RobotColour.YELLOW);
		WorldState state = new WorldState(ball, blue, yellow);

		// A fake vision client to report this.
		visionClient = new TestVisionClient(state);
		
		// Run an iteration
		trackBallStrategy = new TrackBallStrategy(visionClient, testController, RobotColour.BLUE, true);
		trackBallStrategy.tick();

		// Stop what you're doing.
		assertEquals(6, (int)testController.getInstructions().get(0)[0]);

		// Slow down.
		assertEquals(97, (int)testController.getInstructions().get(1)[0]);
		assertEquals(100, (int)testController.getInstructions().get(1)[1]);
		
		// Go!
		assertEquals(0, (int)testController.getInstructions().get(2)[0]);
		assertEquals(5, (int)testController.getInstructions().get(2)[1]);
	}

	@Test
	public void testDriveForwardsFar() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		Ball ball = new Ball(400, 160, 0, 0.0f);
		Robot blue = new Robot(100, 160, 0, 0.0f, 90.0f, RobotColour.BLUE);
		Robot yellow = new Robot(0, 0, 0, 0.0f, 0.0f, RobotColour.YELLOW);
		WorldState state = new WorldState(ball, blue, yellow);

		// A fake vision client to report this.
		visionClient = new TestVisionClient(state);
		
		// Run an iteration
		trackBallStrategy = new TrackBallStrategy(visionClient, testController, RobotColour.BLUE, true);
		trackBallStrategy.tick();

		// Stop what you're doing.
		assertEquals(6, (int)testController.getInstructions().get(0)[0]);

		// Sped up.
		assertEquals(97, (int)testController.getInstructions().get(1)[0]);
		assertEquals(900, (int)testController.getInstructions().get(1)[1]);

		// Go forwards.
		assertEquals(0, (int)testController.getInstructions().get(2)[0]);
		assertEquals(37, (int)testController.getInstructions().get(2)[1]);
	}
}

