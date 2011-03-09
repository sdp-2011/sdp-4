package uk.ac.ed.inf.sdp.group4.strategy;

import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.Robot;
import uk.ac.ed.inf.sdp.group4.world.WorldState;
import uk.ac.ed.inf.sdp.group4.world.WorldObject;
import uk.ac.ed.inf.sdp.group4.world.IVisionClient;

public class Pitch implements TileBasedMap
{
	// Height and width specifications
	public static final int WIDTH = 640; // Needs to be changed to method finding width of vision feed
	public static final int HEIGHT = 480; // As above

	// Visited boolean for the pathfinder
	private boolean visited[][] = new boolean [WIDTH][HEIGHT];

	// Grid references for terrain and units, units being the ball and robots
	private int terrain[][] = new int [WIDTH][HEIGHT];
	public int units[][] = new int [WIDTH][HEIGHT];

	// Tags used to indicate something at a given location
	public static int PITCH = 0;
	public static int OURS = 1;
	public static int THEIRS = 2;
	public static int BALL = 3;
	public static int GOALA = 4;
	public static int GOALB = 5;
	public static int WALL = 6;
	
	private Position enemyPos;

	public Pitch()
	{
		this.client = client;
		this.colour = colour;
		fillArea(0, 0, 245, 30, WALL);

		repaint();
	}

	public void repaint()
	{
		clearUnits();
		state = client.getWorldState();
		if (state.getTimestamp() != timestamp)
		{
			ball = state.getBall();
			units[ball.getX()][ball.getY()] = BALL;
			if (colour.equals(RobotColour.YELLOW))
			{
				ourRobot = state.getYellow();
				units[ourRobot.getX()][ourRobot.getY()] = OURS;
				theirRobot = state.getBlue();
				foeBlob();
			}
			else
			{
				ourRobot = state.getBlue();
				units[ourRobot.getX()][ourRobot.getY()] = OURS;
				theirRobot = state.getYellow();
				foeBlob();
			}
			ball = state.getBall();
			units[ball.getX()][ball.getY()] = BALL;
		}
	}

	protected void fillArea(int x, int y, int width, int height, int type)
	{
		for (int xp = x; xp < x + width; xp++)
		{
			for (int yp = y; yp < y + height; yp++)
			{
				terrain[xp][yp] = type;
			}
		}
	}

	public int getWidthInTiles()
	{
		return WIDTH;
	}

	public int getHeightInTiles()
	{
		return HEIGHT;
	}

	public int getTerrain(int x, int y)
	{
		return terrain[x][y];
	}

	public int getUnits(int x, int y)
	{
		return units[x][y];
	}

	public void clearVisited()
	{
		for (int x = 0; x < getWidthInTiles(); x++)
		{
			for (int y = 0; y < getHeightInTiles(); y++)
			{
				visited[x][y] = false;
			}
		}
	}

	public void clearUnits()
	{
		for (int x = 0; x < getWidthInTiles(); x++)
		{
			for (int y = 0; y < getHeightInTiles(); y++)
			{
				units[x][y] = 0;
			}
		}
	}

	public void foeBlob()
	{
		int X = theirRobot.getX() + 25;
		int Y = theirRobot.getY() + 25;
		for (int xp = X; xp < X + 50; xp++)
		{
			for (int yp = Y; yp < Y + 50; yp++)
			{
				units[xp][yp] = THEIRS;
			}
		}
	}

	public boolean blocked(WorldObject worldObject, int x, int y)
	{
		// Other bot blocks our movement
		Position pos = new Position(x,y);
		if (pos.distance(enemyPos) < 28)
		{
			return true;
		}
		// Terrain other than clear pitch blocks our movement
		return getTerrain(x, y) != 0;
	}

	// Fills an area based from starting point x,y by the width and height given

	public void pathFinderVisited(int x, int y)
	{
		visited[x][y] = true;
	}

	public float getCost(WorldObject worldObject, int sx, int sy, int tx, int ty)
	{	
		Position pos = new Position(tx,ty);
		if (pos.distance(enemyPos) <40){
			return 2;
		}
		return 1;
	}
}

