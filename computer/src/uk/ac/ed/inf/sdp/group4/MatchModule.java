package uk.ac.ed.inf.sdp.group4;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.controller.FatController;

import uk.ac.ed.inf.sdp.group4.strategy.TestingFlag;

import uk.ac.ed.inf.sdp.group4.world.IVisionClient;
import uk.ac.ed.inf.sdp.group4.world.VisionClient;
import uk.ac.ed.inf.sdp.group4.world.VisionClientHostname;
import uk.ac.ed.inf.sdp.group4.world.VisionClientPortNumber;

public class MatchModule extends AbstractModule
{
	@Override 
	protected void configure()
	{
		// Vision Client Configuration
		bind(IVisionClient.class).to(VisionClient.class);

		bind(String.class)
			.annotatedWith(VisionClientHostname.class)
			.toInstance("localhost");

		bind(Integer.class)
			.annotatedWith(VisionClientPortNumber.class)
			.toInstance(50008);

		// Controller Configuration
		bind(Controller.class).to(FatController.class);

		// Testing Configuration
		bind(Boolean.class)
			.annotatedWith(TestingFlag.class)
			.toInstance(false);
	}
}

