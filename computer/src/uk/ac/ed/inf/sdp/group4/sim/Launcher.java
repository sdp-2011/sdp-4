package uk.ac.ed.inf.sdp.group4.sim;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

import uk.ac.ed.inf.sdp.group4.world.Robot;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.WorldState;
import uk.ac.ed.inf.sdp.group4.world.VisionClient;
import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;
import uk.ac.ed.inf.sdp.group4.strategy.RobotColour;	

public class Launcher
{

	final int SCALE = 3;
	final int PADDING = 5 * SCALE;

	//FPS
	final int FPS = 25;

	WorldState state;
	Robot blue;
	Robot yellow;
	Ball ball;

	int scoreB;
	int scoreY;

	Image iconB;
	Image iconY;

	private JFrame frame;

	public Launcher()
	{
		this.state = new WorldState();
		loadContent();
		setup();
	}

	public void run()
	{
		
	}

	private void loadContent()
	{
		try
		{
			iconB = ImageIO.read(new File("blue.png"));
			iconY = ImageIO.read(new File("yellow.png"));
		}
		catch (IOException ex)
		{
			System.out.println("You've forgotten an image");
		}
	}

	private void setup()
	{
		Pitch pitch = new Pitch();
		//set up display
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(pitch.getLENGTH() * SCALE + 2 * PADDING + 4 * SCALE,
		              pitch.getWIDTH() * SCALE + 2 * PADDING + 35 * SCALE);
		frame.setVisible(true);
		frame.getContentPane().add(new Situation(pitch));
	}

	private void update(int time)
	{
		if (time > 	40)
		{	
			draw();
		}
	}

	private void draw()
	{
		frame.getContentPane().repaint();
	}


	private class Situation extends JComponent
	{

		private Pitch pitch;

		public Situation(Pitch pitch)
		{
			this.pitch = pitch;
		}

		public void paint(Graphics g)
		{
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.fillRect(0, 0, pitch.getLENGTH() * SCALE + 2 * PADDING,
				pitch.getWIDTH() * SCALE + 2 * PADDING);
			g2d.setPaint(Color.red);
			g2d.fillRect(0, pitch.getGPOS() * SCALE + PADDING, pitch.getLENGTH() * SCALE + 2 * 								PADDING, pitch.getGLENGTH() * SCALE);
			g2d.setPaint(Color.getHSBColor(0.3f, 1, 0.392f));
			g2d.fillRect(PADDING, PADDING, pitch.getLENGTH() * SCALE, pitch.getWIDTH() * SCALE);

			//draw blue robot
			g2d.setPaint(Color.BLUE);
			g2d.fillRect(blue.getX(), blue.getY(), 20 * SCALE, 18 * SCALE);

			//draw yellow robot
			g2d.setPaint(Color.YELLOW);
			g2d.fillRect(yellow.getX(), yellow.getY(), 20 * SCALE, 18 * SCALE);

			g2d.rotate(blue.getRadFacing() * -1, blue.getX(), blue.getY());
			g2d.drawImage(iconB, (int)(blue.getX() - Math.sin(blue.getRadFacing()) 
				* 18 * SCALE / 2 - 18 * SCALE / 2), (int)(blue.getY()),
			              18 * SCALE, 20 * SCALE, null);
			g2d.rotate(blue.getRadFacing(), blue.getX(), blue.getY());
			g2d.rotate(yellow.getRadFacing(), yellow.getY(), yellow.getY());
			g2d.drawImage(iconY, (int)(yellow.getX() - Math.sin(yellow.getY()) * 18 * 
					SCALE / 2 - 18 * SCALE / 2), (int)yellow.getY() - 20 * SCALE,
			              18 * SCALE, 20 * SCALE, null);
			g2d.rotate(yellow.getRadFacing(), yellow.getX(), yellow.getY());
		}
	}

}
