import static org.junit.Assert.*;
import org.junit.Test;

import uk.ac.ed.inf.sdp.group4.domain.Position;
import uk.ac.ed.inf.sdp.group4.domain.Vector;
import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;

public class TestPosition
{
	@Test
	public void testVectorBetween() throws InvalidAngleException
	{
		Position a = new Position(100, 300);
		Position b = new Position(300, 300);

		Vector v = a.calcVectTo(b);
		assertEquals(200 , v.getMagnitude(), 0);
		assertEquals(90 , v.getDirection(), 0);
	}

	@Test
	public void testReverseVectorBetween() throws InvalidAngleException
	{
		Position a = new Position(100, 300);
		Position b = new Position(300, 300);

		Vector v = b.calcVectTo(a);
		assertEquals(200 , v.getMagnitude(), 0);
		assertEquals(270 , v.getDirection(), 0);
	}

	@Test
	public void testSameVectorBetween() throws InvalidAngleException
	{
		Position a = new Position(100, 300);

		Vector v = a.calcVectTo(a);
		assertEquals(0 , v.getMagnitude(), 0);
		assertEquals(0 , v.getDirection(), 0);
	}

	@Test
	public void testNegativeAngle() throws InvalidAngleException
	{
		Position a = new Position(100, 300);
		Position b = new Position(300, 500);

		Vector v = b.calcVectTo(a);
		assertEquals(282.84, v.getMagnitude(), 0.01);
		assertEquals(315, v.getDirection(), 0);
	}
}
