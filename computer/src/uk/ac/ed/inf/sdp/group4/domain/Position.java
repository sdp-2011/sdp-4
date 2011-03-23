package uk.ac.ed.inf.sdp.group4.domain;

public class Position
{
	private int x;
	private int y;

	public Position(int x, int y)
	{
		setX(x);
		setY(y);
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getX()
	{
		return this.x;
	}

	public int getY()
	{
		return this.y;
	}

	public boolean equals(Position other)
	{
		return (this.getX() == other.getX()) && (this.getY() == other.getY());
	}

	public Vector calcVectTo(Position dest) throws InvalidAngleException
	{
		if (this.equals(dest))
		{
			return new Vector(0, 0);
		}
		return Vector.calcVect(this, dest);
	}

	public boolean isNear(Position pos)
	{	
		return distance(pos) < 10;
	}

	public double distance(Position dest)
	{
		int partX = (dest.getX() - x);
		int partY = (dest.getY() - y);

		return Math.sqrt((partX * partX) + (partY * partY));
	}

	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}

    public static Position centerPoint(Position a, Position b)
    {
        int x = (a.getX() + b.getX())/2;
        int y = (a.getY() + b.getY())/2;
        return new Position(x, y);
    }
}
