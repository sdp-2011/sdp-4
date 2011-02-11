import static org.junit.Assert.*;
import org.junit.Test;

import uk.ac.ed.inf.sdp.group4.domain.Position;
import uk.ac.ed.inf.sdp.group4.domain.Vector;
import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;

public class TestVector
{
	@Test
	public void testAngleRight() throws InvalidAngleException
	{
		Vector a = new Vector(0, 0);
		Vector b = new Vector(90, 100);

		double angle = a.angleTo(b);
		assertEquals(90, angle, 0);
	}

	@Test
	public void testAngleLeft() throws InvalidAngleException
	{
		Vector a = new Vector(0, 0);
		Vector b = new Vector(270, 100);

		double angle = a.angleTo(b);
		assertEquals(-90, angle, 0);
	}
}
