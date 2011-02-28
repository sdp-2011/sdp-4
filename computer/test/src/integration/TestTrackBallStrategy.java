import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.Ignore;
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
		assertThat(testController.getCommand(), is(2));
	}

	@Test
	public void testBallNotVisibleTowardsGoal() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		Ball ball = new Ball(0, 0, 0, 0.0f);
		Robot blue = new Robot(380, 160, 0, 0.0f, 90.0f, RobotColour.BLUE);
		Robot yellow = new Robot(0, 0, 0, 0.0f, 0.0f, RobotColour.YELLOW);
		WorldState state = new WorldState(ball, blue, yellow);

		// A fake vision client to report this.
		visionClient = new TestVisionClient(state);

		// Run an iteration
		trackBallStrategy = new TrackBallStrategy(visionClient, testController, RobotColour.BLUE, true);
		trackBallStrategy.tick();

		// Did we shoot?
		assertThat(testController.getCommand(), is(2));
	}

	@Test
	public void testBallNotVisibleAwayFromGoal() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		Ball ball = new Ball(0, 0, 0, 0.0f);
		Robot blue = new Robot(60, 160, 0, 0.0f, 270.0f, RobotColour.BLUE);
		Robot yellow = new Robot(0, 0, 0, 0.0f, 0.0f, RobotColour.YELLOW);
		WorldState state = new WorldState(ball, blue, yellow);

		// A fake vision client to report this.
		visionClient = new TestVisionClient(state);

		// Run an iteration
		trackBallStrategy = new TrackBallStrategy(visionClient, testController, RobotColour.BLUE, true);
		trackBallStrategy.tick();

		// Did we not shoot?
		assertThat(testController.getCommand(), is(not(2)));
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

		// Slow down.
		assertThat(testController.getCommand(0), is(97));
		assertThat(testController.getArgument(0), is(300));

		// Turn!
		assertThat(testController.getCommand(1), is(5));
		assertThat(testController.getArgument(1), is(90));
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

		// Slow down.
		assertThat(testController.getCommand(0), is(97));
		assertThat(testController.getArgument(0), is(300));

		// Turn!
		assertThat(testController.getCommand(1), is(4));
		assertThat(testController.getArgument(1), is(90));
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

		// Slow down.
		assertThat(testController.getCommand(0), is(97));
		assertThat(testController.getArgument(0), is(300));

		assertThat(testController.getCommand(1), is(4));
		assertThat(testController.getArgument(1), is(180));
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

		// Slow down.
		assertThat(testController.getCommand(0), is(97));
		assertThat(testController.getArgument(0), is(400));

		// Go!
		assertThat(testController.getCommand(1), is(0));
		assertThat(testController.getArgument(1), is(10));
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

		// Speed up.
		assertThat(testController.getCommand(0), is(97));
		assertThat(testController.getArgument(0), is(400));

		// Go forwards.
		assertThat(testController.getCommand(1), is(0));
		assertThat(testController.getArgument(1), is(75));
	}

	@Test
	public void testRefuseShot() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		Ball ball = new Ball(200, 160, 0, 0.0f);
		Robot blue = new Robot(220, 160, 0, 0.0f, 270.0f, RobotColour.BLUE);
		Robot yellow = new Robot(0, 0, 0, 0.0f, 0.0f, RobotColour.YELLOW);
		WorldState state = new WorldState(ball, blue, yellow);

		// A fake vision client to report this.
		visionClient = new TestVisionClient(state);

		// Run an iteration
		trackBallStrategy = new TrackBallStrategy(visionClient, testController, RobotColour.BLUE, true);
		trackBallStrategy.tick();

		// Slow down.
		assertThat(testController.getCommand(0), is(97));
		assertThat(testController.getArgument(0), is(50));

		// Turn around.
		assertThat(testController.getCommand(1), is(4));
		assertThat(testController.getArgument(1), is(179));
	}

	@Ignore("Need to merge William's branch first.")
	@Test
	public void testGoalKeeperBlock() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		Ball ball = new Ball(400, 150, 0, 0.0f);
		Robot blue = new Robot(380, 150, 0, 0.0f, 90.0f, RobotColour.BLUE);
		Robot yellow = new Robot(510, 150, 0, 0.0f, 270.0f, RobotColour.YELLOW);
		WorldState state = new WorldState(ball, blue, yellow);

		// A fake vision client to report this.
		visionClient = new TestVisionClient(state);

		// Run an iteration
		trackBallStrategy = new TrackBallStrategy(visionClient, testController, RobotColour.BLUE, true);
		trackBallStrategy.tick();

		// Slow down.
		assertThat(testController.getCommand(0), is(97));
		assertThat(testController.getArgument(0), is(50));

		// Turn around.
		assertThat(testController.getCommand(1), is(4));
		assertThat(testController.getArgument(1), is(179));
	}

	@Test
	public void testGoalKeeperNotBlock() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		Ball ball = new Ball(400, 150, 0, 0.0f);
		Robot blue = new Robot(380, 150, 0, 0.0f, 90.0f, RobotColour.BLUE);
		Robot yellow = new Robot(510, 170, 0, 0.0f, 270.0f, RobotColour.YELLOW);
		WorldState state = new WorldState(ball, blue, yellow);

		// A fake vision client to report this.
		visionClient = new TestVisionClient(state);

		// Run an iteration
		trackBallStrategy = new TrackBallStrategy(visionClient, testController, RobotColour.BLUE, true);
		trackBallStrategy.tick();

		// Slow down.
		assertThat(testController.getCommand(), is(2));
	}
}

