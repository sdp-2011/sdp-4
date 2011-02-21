package uk.ac.ed.inf.sdp.group4.strategy;

public class Pitch implements TileBasedMap
{

	// Height and width specifications
	public static final int WIDTH = 0; // Needs to be changed to method finding width of vision feed
	public static final int HEIGHT = 0; // As above

	// Visited boolean for the pathfinder
	private boolean visited[][] = new boolean [WIDTH][HEIGHT];

	// Grid references for terrain and units, units being the ball and robots
	private int terrain[][] = new int [WIDTH][HEIGHT];
	private int units[][] = new int [WIDTH][HEIGHT];

	// Tags used to indicate something at a given location
	public static int PITCH = 0;
	public static int OURROBOT = 1;
	public static int OTHERBOT = 2;
	public static int BALL = 3;
	public static int GOALA = 4;
	public static int GOALB = 5;
	public static int WALL = 6;

	public Pitch()
	{
		// Vision builds grid image of pitch here
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

	public boolean blocked(Mover mover, int x, int y)
	{
		// Other bot blocks our movement
		if (getUnits(x, y) == 2)
		{
			return true;
		}
		// Terrain other than clear pitch blocks our movement
		return getTerrain(x, y) != 0;
	}
	
	// Fills an area based from starting point x,y by the width and height given
	
	private void fillArea(int x, int y, int width, int height, int type) {
		for (int xp=x;xp<x+width;xp++) {
			for (int yp=y;yp<y+height;yp++) {
				if ((type < 4) && (type != 0)){
					units[xp][yp] = type;
				}
				else terrain[xp][yp] = type;
			}
		}
	}
	
	public void pathFinderVisited(int x, int y)
	{
		visited[x][y] = true;
	}

	public float getCost(Mover mover, int sx, int sy, int tx, int ty)
	{
		return 1;
	}
}

