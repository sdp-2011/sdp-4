import java.io.*;

public class Test
{
	public static void main(String[] args) throws InterruptedException, IOException
	{
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		FatController fatty = new FatController();
		
		while (true)
		{
			try
			{
				System.out.println("Command?");
				int command = Integer.parseInt(keyboard.readLine());

				System.out.println("Argument?");
				int argument = Integer.parseInt(keyboard.readLine());

				fatty.sendCommand(command, argument);
			}

			catch (NumberFormatException e)
			{
				System.out.println("IS THAT A NUMBER? NO! TRY AGAIN DUMBASS");
			}
		}
	}	

}
