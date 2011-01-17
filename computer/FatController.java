import lejos.pc.comm.*;
import java.io.*;

public class FatController
{
	public static void main(String[] args)
	{
		NXTComm nxtComm = null;

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
		

		OutputStream stream = nxtComm.getOutputStream();

		try
		{
			stream.write(1);
			stream.close();	
		}
		catch (IOException e)
		{
			System.out.println("IOException");
		}
	}
}
