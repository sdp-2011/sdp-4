import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.Before;
import org.junit.BeforeClass;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.google.inject.Injector;
import com.google.inject.Guice;

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
	private static Injector injector;

	private TrackBallStrategy strategy;

	@BeforeClass
	public static void beforeClass()
	{
		Logger root = Logger.getRootLogger();
		root.setLevel(Level.OFF);

		injector = Guice.createInjector(new TestModule());
	}

	@Before
	public void before()
	{
		strategy = injector.getInstance(TrackBallStrategy.class);
		strategy.setColour(RobotColour.BLUE);
	}

	@Test
	public void testDirectShot() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		WorldState state = new WorldState();
		state.setBall(new Ball(400, 160, 0, 0.0f));
		state.setBlue(new Robot(380, 160, 0, 0.0f, 90.0f, RobotColour.BLUE));
		state.setYellow(new Robot(0, 0, 0, 0.0f, 0.0f, RobotColour.YELLOW));

		// A fake vision client to report this.
		strategy.setVisionClient(new TestVisionClient(state));

		// Run an iteration
		strategy.tick();

		// Did we shoot?
		assertThat(strategy.getController().getCommand(), is(2));
	}

	@Test
	public void testBallNotVisibleTowardsGoal() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		WorldState state = new WorldState();
		state.setBall(new Ball(0, 0, 0, 0.0f));
		state.setBlue(new Robot(380, 160, 0, 0.0f, 90.0f, RobotColour.BLUE));
		state.setYellow(new Robot(0, 0, 0, 0.0f, 0.0f, RobotColour.YELLOW));

		// A fake vision client to report this.
		strategy.setVisionClient(new TestVisionClient(state));

		// Run an iteration
		strategy.tick();

		// Did we shoot?
		assertThat(strategy.getController().getCommand(), is(2));
	}

	@Test
	public void testBallNotVisibleAwayFromGoal() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		WorldState state = new WorldState();
		state.setBall(new Ball(0, 0, 0, 0.0f));
		state.setBlue(new Robot(60, 160, 0, 0.0f, 270.0f, RobotColour.BLUE));
		state.setYellow(new Robot(0, 0, 0, 0.0f, 0.0f, RobotColour.YELLOW));

		// A fake vision client to report this.
		strategy.setVisionClient(new TestVisionClient(state));

		// Run an iteration
		strategy.tick();

		// Did we not shoot?
		assertThat(strategy.getController().getCommand(), is(not(2)));
	}

	@Test
	public void testSimpleTurnRight() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		WorldState state = new WorldState();
		state.setBall(new Ball(400, 160, 0, 0.0f));
		state.setBlue(new Robot(320, 160, 0, 0.0f, 0.0f, RobotColour.BLUE));
		state.setYellow(new Robot(0, 0, 0, 0.0f, 0.0f, RobotColour.YELLOW));

		// A fake vision client to report this.
		strategy.setVisionClient(new TestVisionClient(state));

		// Run an iteration
		strategy.tick();

		// Slow down.
		assertThat(strategy.getController().getCommand(0), is(97));
		assertThat(strategy.getController().getArgument(0), is(300));

		// Turn!
		assertThat(strategy.getController().getCommand(1), is(5));
		assertThat(strategy.getController().getArgument(1), is(90));
	}

	@Test
	public void testSimpleTurnLeft() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		WorldState state = new WorldState();
		state.setBall(new Ball(400, 160, 0, 0.0f));
		state.setBlue(new Robot(320, 160, 0, 0.0f, 180.0f, RobotColour.BLUE));
		state.setYellow(new Robot(0, 0, 0, 0.0f, 0.0f, RobotColour.YELLOW));

		// A fake vision client to report this.
		strategy.setVisionClient(new TestVisionClient(state));

		// Run an iteration
		strategy.tick();

		// Slow down.
		assertThat(strategy.getController().getCommand(0), is(97));
		assertThat(strategy.getController().getArgument(0), is(300));

		// Turn!
		assertThat(strategy.getController().getCommand(1), is(4));
		assertThat(strategy.getController().getArgument(1), is(90));
	}

	@Test
	public void testOneEighty() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		WorldState state = new WorldState();
		state.setBall(new Ball(400, 160, 0, 0.0f));
		state.setBlue(new Robot(320, 160, 0, 0.0f, 270.0f, RobotColour.BLUE));
		state.setYellow(new Robot(0, 0, 0, 0.0f, 0.0f, RobotColour.YELLOW));

		// A fake vision client to report this.
		strategy.setVisionClient(new TestVisionClient(state));

		// Run an iteration
		strategy.tick();

		// Slow down.
		assertThat(strategy.getController().getCommand(0), is(97));
		assertThat(strategy.getController().getArgument(0), is(300));

		assertThat(strategy.getController().getCommand(1), is(4));
		assertThat(strategy.getController().getArgument(1), is(180));
	}

	@Test
	public void testDriveForwardsClose() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		WorldState state = new WorldState();
		state.setBall(new Ball(400, 160, 0, 0.0f));
		state.setBlue(new Robot(360, 160, 0, 0.0f, 90.0f, RobotColour.BLUE));
		state.setYellow(new Robot(0, 0, 0, 0.0f, 0.0f, RobotColour.YELLOW));

		// A fake vision client to report this.
		strategy.setVisionClient(new TestVisionClient(state));

		// Run an iteration
		strategy.tick();

		// Slow down.
		assertThat(strategy.getController().getCommand(0), is(97));
		assertThat(strategy.getController().getArgument(0), is(400));

		// Go!
		assertThat(strategy.getController().getCommand(1), is(0));
		assertThat(strategy.getController().getArgument(1), is(10));
	}

	@Test
	public void testDriveForwardsFar() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		WorldState state = new WorldState();
		state.setBall(new Ball(400, 160, 0, 0.0f));
		state.setBlue(new Robot(100, 160, 0, 0.0f, 90.0f, RobotColour.BLUE));
		state.setYellow(new Robot(0, 0, 0, 0.0f, 0.0f, RobotColour.YELLOW));

		// A fake vision client to report this.
		strategy.setVisionClient(new TestVisionClient(state));

		// Run an iteration
		strategy.tick();

		// Speed up.
		assertThat(strategy.getController().getCommand(0), is(97));
		assertThat(strategy.getController().getArgument(0), is(400));

		// Go forwards.
		assertThat(strategy.getController().getCommand(1), is(0));
		assertThat(strategy.getController().getArgument(1), is(75));
	}

	@Test
	public void testRefuseShot() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		WorldState state = new WorldState();
		state.setBall(new Ball(200, 160, 0, 0.0f));
		state.setBlue(new Robot(220, 160, 0, 0.0f, 270.0f, RobotColour.BLUE));
		state.setYellow(new Robot(0, 0, 0, 0.0f, 0.0f, RobotColour.YELLOW));

		// A fake vision client to report this.
		strategy.setVisionClient(new TestVisionClient(state));

		// Run an iteration
		strategy.tick();

		// Slow down.
		assertThat(strategy.getController().getCommand(0), is(97));
		assertThat(strategy.getController().getArgument(0), is(50));

		// Turn around.
		assertThat(strategy.getController().getCommand(1), is(4));
		assertThat(strategy.getController().getArgument(1), is(179));
	}

	@Ignore("Need to merge William's branch first.")
	@Test
	public void testGoalKeeperBlock() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		WorldState state = new WorldState();
		state.setBall(new Ball(400, 150, 0, 0.0f));
		state.setBlue(new Robot(380, 150, 0, 0.0f, 90.0f, RobotColour.BLUE));
		state.setYellow(new Robot(510, 150, 0, 0.0f, 270.0f, RobotColour.YELLOW));

		// A fake vision client to report this.
		strategy.setVisionClient(new TestVisionClient(state));

		// Run an iteration
		strategy.tick();

		// Slow down.
		assertThat(strategy.getController().getCommand(0), is(97));
		assertThat(strategy.getController().getArgument(0), is(50));

		// Turn around.
		assertThat(strategy.getController().getCommand(1), is(4));
		assertThat(strategy.getController().getArgument(1), is(179));
	}

	@Test
	public void testGoalKeeperNotBlock() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		WorldState state = new WorldState();
		state.setBall(new Ball(400, 150, 0, 0.0f));
		state.setBlue(new Robot(380, 150, 0, 0.0f, 90.0f, RobotColour.BLUE));
		state.setYellow(new Robot(510, 170, 0, 0.0f, 270.0f, RobotColour.YELLOW));

		// A fake vision client to report this.
		strategy.setVisionClient(new TestVisionClient(state));

		// Run an iteration
		strategy.tick();

		// Slow down.
		assertThat(strategy.getController().getCommand(), is(2));
	}
}

