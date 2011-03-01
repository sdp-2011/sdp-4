package uk.ac.ed.inf.sdp.group4;

import java.io.*;
import org.apache.log4j.BasicConfigurator;
import uk.ac.ed.inf.sdp.group4.gui.CastleWindow;

public class Main
{
	public static void main(String[] args) throws InterruptedException, IOException
	{
		BasicConfigurator.configure();

		CastleWindow window = new CastleWindow();
		window.setTitle("Castle Control v0.1");
		window.setVisible(true);
	}
}
