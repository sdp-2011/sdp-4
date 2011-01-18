import lejos.pc.comm.*;
import java.io.*;

public class FatController
{
	private NXTComm nxtComm;

	public FatController()
	{
		try
		{
			nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
			NXTInfo nxtInfo = new NXTInfo(NXTCommFactory.BLUETOOTH, "WAR BASTARD", "00:16:53:0A:07:1D");
			nxtComm.open(nxtInfo);
		}
		catch (NXTCommException e)
		{
			e.printStackTrace();
		}
	}

	public void drivef(int distance)
	{
		//calls drive on the robot
		sendCommand("drivef," + distance);
	}

	public void driveb(int distance)
	{
		//calls drive on the robot
		sendCommand("driveb," + distance);
	}

	public void shoot()
	{
		//calls shoot on the robot
		sendCommand("shoot");
	}

	public void finish()
	{
		//calls finish on robot
		try
		{
			nxtComm.close();
		}

		catch (IOException e)
		{
			e.printStackTrace();
		}

		sendCommand("finish");
	}

	private void sendCommand(String command)
	{
		try
		{
			OutputStream istream = nxtComm.getOutputStream();
			DataOutputStream stream = new DataOutputStream(istream);
			stream.writeBytes(command);
			stream.flush();
			stream.close();
		}

		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
