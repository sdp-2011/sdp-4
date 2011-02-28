package uk.ac.ed.inf.sdp.group4;

import java.io.*;

import org.apache.log4j.BasicConfigurator;

import com.google.inject.Guice;
import com.google.inject.Injector;

import uk.ac.ed.inf.sdp.group4.*;
import uk.ac.ed.inf.sdp.group4.controller.*;
import uk.ac.ed.inf.sdp.group4.domain.*;
import uk.ac.ed.inf.sdp.group4.sim.*;
import uk.ac.ed.inf.sdp.group4.strategy.*;
import uk.ac.ed.inf.sdp.group4.world.*;

public class Main
{
	public static void main(String[] args) throws InterruptedException, IOException
	{
		// Setup the logger
		BasicConfigurator.configure();
		
		// Jenkins! Bring me my injector!
		Injector injector = Guice.createInjector(new MatchModule());

		// Building blocks of perfection.
		IVisionClient client = injector.getInstance(IVisionClient.class);
		Controller controller = null;
		Strategy strategy = null;

		// Display the team logo.
		logo();

		// Java IO. Worst IO.
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

		// Which colour are we playing as?
		System.out.println("  > 1. Blue");
		System.out.println("  > 2. Yellow");
		System.out.println("What colour are we?");

		int colourChoice = Integer.parseInt(keyboard.readLine());
		RobotColour colour = (colourChoice == 1) ? RobotColour.BLUE : RobotColour.YELLOW;

		// What game would you like to play today?
		// > GLOBAL THERMONUCLEAR WAR
		System.out.println("Menu:");
		System.out.println("  > 1. Keyboard Control");
		System.out.println("  > 2. Navigate to Ball");
		System.out.println("  > 3. Simulator");
		System.out.println("  > 4. Test Movement");
		System.out.println("  > 5. Match");
		System.out.println("Where would you like to go today?");

		int option = Integer.parseInt(keyboard.readLine());

		switch (option)
		{
			case 1:
				controller = new FatController();
				strategy = new KeyboardStrategy(client, controller, colour);
				break;
			case 2:
				controller = new FatController();
				strategy = new TrackBallStrategy(client, controller, colour);
				break;
			case 3:
				colour = RobotColour.BLUE;
				WorldState state = new WorldState();
				client = new FakeVision(state);
				//state.getBall().setPosition(150, 80);
				state.getBall().setPosition(170, 40);
				state.getBlue().setPosition(20, 60);
				Component[] components = new Component[2];
				SimBot bot = new SimBot(state.getBlue());
				SimBall ball = new SimBall(state.getBall());
				components[0] = bot;
				components[1] = ball;

				controller = new ThinController(bot);
				//strategy = new KeyboardStrategy(client, controller, RobotColour.BLUE);
				strategy = new TrackBallStrategy(client, controller, RobotColour.BLUE);
				//strategy = new Match(client, controller, RobotColour.BLUE, false);

				Launcher launcher = new Launcher(state, components);
				strategy.setGoals(0, 60, 243, 60);
				new Thread(launcher).start();
				break;
			case 4:
				controller = new FatController();
				controller.driveForward(50);
				controller.turn(-360);
				Thread.sleep(3000);
				System.exit(0);
				break;
			case 5:
				controller = new FatController();
				strategy = new Match(client, controller, colour, false);
				break;

			default:
				System.out.println("Goddammit. Give me a real number!");
		}

		strategy.runStrategy();
	}

	private static void logo()
	{
		System.out.println("      ____    _    ____ _____ _     _____");
		System.out.println("     / ___|  / \\  / ___|_   _| |   | ____|");
		System.out.println("    | |     / _ \\ \\___ \\ | | | |   |  _|");
		System.out.println("    | |___ / ___ \\ ___) || | | |___| |___");
		System.out.println("     \\____/_/   \\_\\____/ |_| |_____|_____|");
		System.out.println();
		System.out.println("      ____ ____      _    ____  _   _ _____ ____  ____");
		System.out.println("     / ___|  _ \\    / \\  / ___|| | | | ____|  _ \\/ ___|");
		System.out.println("    | |   | |_) |  / _ \\ \\___ \\| |_| |  _| | |_) \\___ \\ ");
		System.out.println("    | |___|  _ <  / ___ \\ ___) |  _  | |___|  _ < ___) |");
		System.out.println("     \\____|_| \\_\\/_/   \\_\\____/|_| |_|_____|_| \\_\\____/");
		System.out.println();
	}
}
