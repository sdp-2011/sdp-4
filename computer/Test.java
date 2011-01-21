import java.io.*;

public class Test
{
	public static void main(String[] args) throws InterruptedException, IOException
	{
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		FatController fatty = new FatController();
		
		while (true)
		{
			System.out.println("Command?");
			String command = keyboard.readLine();

			System.out.println("Argument?");
			String argument = keyboard.readLine();

			fatty.sendCommand(Integer.parseInt(command), Integer.parseInt(argument));
		}
	}	

}
