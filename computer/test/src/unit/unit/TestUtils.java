package unit;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.Before;
import org.junit.BeforeClass;

import uk.ac.ed.inf.sdp.group4.utils.Utils;

public class TestUtils
{
	@Test
	public void testClampInRange()
	{
		assertThat(Utils.clamp(5,0,10), is(5));
	}

	@Test
	public void testClampGreater()
	{
		assertThat(Utils.clamp(11,0,10), is(10));
	}

	@Test
	public void testClampLess()
	{
		assertThat(Utils.clamp(-4,5,10), is(5));
	}

	@Test
	public void testClampLimitLow()
	{
		assertThat(Utils.clamp(0,0,10), is(0));
	}

	@Test
	public void testClampLimitHigh()
	{
		assertThat(Utils.clamp(10,0,10), is(10));
	}
}



