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


public class Launcher {
	
	final int SCALE = 3;
	final int PADDING = 5*SCALE;
	
	//FPS
	final int FPS = 25;
	
	Ball ball = new Ball();
	Robot rB = new Robot();
	Robot rY = new Robot();
	
	int scoreB = 0;
	int scoreY = 0;

	Image iconB;
	Image iconY;

	private JFrame frame;

	
	public static void main(String[] args) {
		
		new Launcher();
	}

	public Launcher()
	{
	
        loadContent();
		setup();


		//this section just a test at the moment
        Boolean test = true;

        while (test)
		{
	     	update(50);
			
			try
			{
				Thread.sleep(40);
			}
			catch (InterruptedException e)
			{
				System.out.println("ARGGGHHH");
			}
        }	
	}	

	public void loadContent()
	{
		try
		{
			iconB = ImageIO.read(new File("rB.png"));
			iconY = ImageIO.read(new File("rY.png"));
		} 
		catch(IOException ex) 
		{
			System.out.println("You've forgotten an image");
		}
	}

	public void setup()
	{
		Pitch pitch = new Pitch();

		//initialise locations
		rB.setxLoc(PADDING);
		rB.setyLoc(pitch.getWIDTH()*SCALE/2 + PADDING - rB.getWIDTH()*SCALE/2);
		
		rY.setxLoc(pitch.getLENGTH()* SCALE + PADDING - rY.getLENGTH()*SCALE);
		rY.setyLoc(pitch.getWIDTH()*SCALE/2 + PADDING - rY.getWIDTH()*SCALE/2);
		rY.setAngle(rY.getAngle()+180);
		
		//set up display
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(pitch.getLENGTH()*SCALE + 2*PADDING + 4*SCALE,
			pitch.getWIDTH()*SCALE + 2*PADDING + 35*SCALE);
        frame.setVisible(true);
		frame.getContentPane().add(new Situation(pitch));
	}

	public void update(int time)
	{
		if (time > 	40)
		{
			rB.setxLoc(rB.getxLoc() + rB.SPEED/FPS);
			draw();
		}
	}

	public void draw()
	{
		frame.getContentPane().repaint();
	}
		
	
	private class Situation extends JComponent {

		private Pitch pitch;

		public Situation(Pitch pitch)
		{
			this.pitch = pitch;
		}
	
		public void paint(Graphics g) {
			
			Graphics2D g2d = (Graphics2D)g;
			
			g2d.fill(new Rectangle2D.Double(0, 0, pitch.getLENGTH()*SCALE + 2*PADDING, pitch.getWIDTH()*SCALE + 2*PADDING));
			
			g2d.setPaint(Color.red);
			g2d.fill(new Rectangle2D.Double(0, pitch.getGPOS()*SCALE + PADDING, pitch.getLENGTH()*SCALE + 2*PADDING, 					pitch.getGLENGTH()*SCALE));
			
			g2d.setPaint(Color.getHSBColor(0.3f, 1, 0.392f));
			g2d.fill(new Rectangle2D.Double(PADDING, PADDING, pitch.getLENGTH()*SCALE, pitch.getWIDTH()*SCALE));
			
			g2d.setPaint(Color.BLUE);
			g2d.fill(new Rectangle2D.Double(rB.getxLoc(), rB.getyLoc(), rB.getLENGTH()*SCALE, rB.getWIDTH()*SCALE));
			g2d.fill(new Rectangle2D.Double(rY.getxLoc(), rY.getyLoc(), rY.getLENGTH()*SCALE, rY.getWIDTH()*SCALE));
			
			Graphics2D gRB = (Graphics2D) g;
			Graphics2D gRY = (Graphics2D) g;

			g2d.rotate(-rB.getRadAngle(),rB.getxLoc(), rB.getyLoc());
			g2d.drawImage(iconB,
						 (int) (rB.getxLoc() - Math.sin(rB.getRadAngle())*rB.getWIDTH()*SCALE/2 - rB.getWIDTH()*SCALE/2), 
						 (int) (rB.getyLoc()),
						  rB.getWIDTH()*SCALE,
						  rB.getLENGTH()*SCALE,
						  null);
			g2d.rotate( rB.getRadAngle(),rB.getxLoc(), rB.getyLoc());
			
			g2d.rotate(-rY.getRadAngle(),rY.getxLoc(),rY.getyLoc());
			g2d.drawImage(iconY,
						 (int) (rY.getxLoc() - Math.sin(rY.getRadAngle())*rY.getWIDTH()*SCALE/2 - rY.getWIDTH()*SCALE/2),
						 (int)rY.getyLoc() - rY.getLENGTH()*SCALE,
						  rY.getWIDTH()*SCALE, 
						  rY.getLENGTH()*SCALE, 
						  null);
			g2d.rotate(rY.getRadAngle(),rY.getxLoc(),rY.getyLoc());
		}
	}	
	
}
