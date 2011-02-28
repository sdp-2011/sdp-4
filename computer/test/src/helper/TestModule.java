import com.google.inject.AbstractModule;

import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.strategy.RobotColour;
import uk.ac.ed.inf.sdp.group4.strategy.TestingFlag;
import uk.ac.ed.inf.sdp.group4.world.IVisionClient;

public class TestModule extends AbstractModule
{
	@Override 
	protected void configure()
	{
		// Vision Client Configuration
		bind(IVisionClient.class).to(TestVisionClient.class);

		// Controller Configuration
		bind(Controller.class).to(TestController.class);

		// Testing Configuration
		bind(Boolean.class)
			.annotatedWith(TestingFlag.class)
			.toInstance(true);
	}

}


