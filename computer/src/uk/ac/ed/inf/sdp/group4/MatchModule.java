package uk.ac.ed.inf.sdp.group4;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import uk.ac.ed.inf.sdp.group4.world.IVisionClient;
import uk.ac.ed.inf.sdp.group4.world.VisionClient;

public class MatchModule extends AbstractModule
{
	@Override 
	protected void configure()
	{
		// Vision Client Configuration
		bind(IVisionClient.class).to(VisionClient.class);

		bind(String.class)
			.annotatedWith(Names.named("Vision Client Hostname"))
			.toInstance("localhost");

		bind(Integer.class)
			.annotatedWith(Names.named("Vision Client Port Number"))
			.toInstance(50008);
	}
}

