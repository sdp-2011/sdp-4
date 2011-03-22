package unit;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.Before;
import org.junit.BeforeClass;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import helper.MockWorldObject;

import uk.ac.ed.inf.sdp.group4.domain.InvalidAngleException;
import uk.ac.ed.inf.sdp.group4.world.Ball;
import uk.ac.ed.inf.sdp.group4.world.BadWorldStateException;
import uk.ac.ed.inf.sdp.group4.world.Robot;
import uk.ac.ed.inf.sdp.group4.world.WorldState;

public class TestWorldObject
{
	@Test
	public void testInTopHalf() throws InvalidAngleException
	{
		MockWorldObject object = new MockWorldObject(30, 30, 0, 0.0f);
		assertThat(object.inTopHalf(), is(true));
		assertThat(object.inBottomHalf(), is(false));
	}

	@Test
	public void testInBottomHalf() throws InvalidAngleException
	{
		MockWorldObject object = new MockWorldObject(30, 170, 0, 0.0f);
		assertThat(object.inBottomHalf(), is(true));
		assertThat(object.inTopHalf(), is(false));
	}

	@Test
	public void testNearObject() throws InvalidAngleException
	{
		MockWorldObject object = new MockWorldObject(30, 170, 0, 0.0f);
		MockWorldObject otherObject = new MockWorldObject(40, 180, 0, 0.0f);

		assertThat(object.isNear(otherObject), is(true));
		assertThat(otherObject.isNear(object), is(true));
	}

	@Test
	public void testFarObject() throws InvalidAngleException
	{
		MockWorldObject object = new MockWorldObject(300, 10, 0, 0.0f);
		MockWorldObject otherObject = new MockWorldObject(40, 180, 0, 0.0f);

		assertThat(object.isNear(otherObject), is(false));
		assertThat(otherObject.isNear(object), is(false));
	}
}



