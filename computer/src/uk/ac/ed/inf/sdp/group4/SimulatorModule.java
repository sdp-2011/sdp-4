package uk.ac.ed.inf.sdp.group4;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import uk.ac.ed.inf.sdp.group4.controller.Controller;
import uk.ac.ed.inf.sdp.group4.controller.ThinController;

import uk.ac.ed.inf.sdp.group4.sim.FakeVision;

import uk.ac.ed.inf.sdp.group4.world.IVisionClient;

public class SimulatorModule extends AbstractModule
{
	@Override 
	protected void configure()
	{
		// Vision Client Configuration
		bind(IVisionClient.class).to(FakeVision.class);

		// Controller Configuration
		bind(Controller.class).to(ThinController.class);
	}
}


