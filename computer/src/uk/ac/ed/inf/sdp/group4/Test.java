package uk.ac.ed.inf.sdp.group4;
import java.io.*;

import uk.ac.ed.inf.sdp.group4.world.VisionClient;
import uk.ac.ed.inf.sdp.group4.world.WorldState;

public class Test
{
	public static void main(String[] args) throws InterruptedException, IOException
	{
		VisionClient visionClient = new VisionClient();
		WorldState now = visionClient.getWorldState();
	}

}
