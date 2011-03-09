package integration;

import helper.TestController;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;
import uk.ac.ed.inf.sdp.group4.strategy.RobotColour;
import uk.ac.ed.inf.sdp.group4.strategy.tactic.PenaltyTakeTactic;
import uk.ac.ed.inf.sdp.group4.strategy.tactic.Tactic;
import uk.ac.ed.inf.sdp.group4.world.BadWorldStateException;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.Robot;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestPenaltyTakeTactic
{
	private static TestController testController;
	private Tactic penaltyTakeTactic;

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
		penaltyTakeTactic = null;
	}

	@Test
	public void testPenaltyLeft() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		Ball ball = new Ball(400, 160, 0, 0.0f);
		Robot blue = new Robot(380, 160, 0, 0.0f, 90.0f, RobotColour.BLUE);
		Robot yellow = new Robot(520, 170, 0, 0.0f, 0.0f, RobotColour.YELLOW);

		// Run an iteration
		penaltyTakeTactic = new PenaltyTakeTactic(testController, true);
		penaltyTakeTactic.tick(blue, yellow, ball);

		// Did we turn?
		assertThat(testController.getCommand(0), is(4));
		assertThat(testController.getArgument(0), is(15));

		// Did we shoot?
		assertThat(testController.getCommand(1), is(2));
	}

	@Test
	public void testPenaltyRight() throws InvalidAngleException, BadWorldStateException
	{
		// Set up the world state.
		Ball ball = new Ball(400, 160, 0, 0.0f);
		Robot blue = new Robot(380, 160, 0, 0.0f, 90.0f, RobotColour.BLUE);
		Robot yellow = new Robot(520, 150, 0, 0.0f, 0.0f, RobotColour.YELLOW);

		// Run an iteration
		penaltyTakeTactic = new PenaltyTakeTactic(testController, true);
		penaltyTakeTactic.tick(blue, yellow, ball);

		// Did we turn?
		assertThat(testController.getCommand(0), is(5));
		assertThat(testController.getArgument(0), is(15));

		// Did we shoot?
		assertThat(testController.getCommand(1), is(2));
	}
}


