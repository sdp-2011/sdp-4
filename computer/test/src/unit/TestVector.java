import org.junit.Test;
import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;
import uk.ac.ed.inf.sdp.group4.domain.Vector;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TestVector
{
	@Test
	public void testAngleRight() throws InvalidAngleException
	{
		Vector a = new Vector(0, 0);
		double b = 90;

		double angle = a.angleTo(b);
		assertEquals(90, angle, 0);
	}

	@Test
	public void testAngleLeft() throws InvalidAngleException
	{
		Vector a = new Vector(0, 0);
		double b = 270;

		double angle = a.angleTo(b);
		assertEquals(-90, angle, 0);
	}

	@Test
	public void testAngleFront() throws InvalidAngleException
	{
		Vector a = new Vector(0, 0);
		double b = 0;

		double angle = a.angleTo(b);
		assertEquals(0, angle, 0);
	}

	@Test
	public void testAngleBehind() throws InvalidAngleException
	{
		Vector a = new Vector(0, 0);
		double b = 180;

		double angle = a.angleTo(b);
		assertEquals(180, angle, 0);
	}

	@Test()
	public void testBounds() throws InvalidAngleException
	{
		Vector a = new Vector(0, 0);

		a.setDirection(0);
		assertThat(a.getDirection(), is(0.0));

		a.setDirection(359.5);
		assertThat(a.getDirection(), is(359.5));
	}

	@Test(expected = InvalidAngleException.class)
	public void testExceptionBoundsLower() throws InvalidAngleException
	{
		Vector a = new Vector(0, 0);
		a.setDirection(-1);
	}

	@Test(expected = InvalidAngleException.class)
	public void testExceptionBoundsUpper() throws InvalidAngleException
	{
		Vector a = new Vector(0, 0);
		a.setDirection(360);
	}
}
