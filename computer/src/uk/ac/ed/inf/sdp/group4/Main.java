package uk.ac.ed.inf.sdp.group4;

import org.apache.log4j.BasicConfigurator;
import uk.ac.ed.inf.sdp.group4.gui.CastleWindow;

import java.io.IOException;

public class Main
{
	public static void main(String[] args) throws InterruptedException, IOException
	{
		BasicConfigurator.configure();

		CastleWindow window = new CastleWindow();
		window.setTitle("Castle Control v0.2");
		window.setVisible(true);
	}
}
