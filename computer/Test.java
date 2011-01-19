public class Test
{
	public static void main(String[] args)
	{
		FatController fatty = new FatController();
		fatty.drivef(40);
		Thread.sleep(500);
		fatty.driveb(20);
		Thread.sleep(500);
		fatty.drivef(20);
		Thread.sleep(500);
		fatty.shoot();
		Thread.sleep(500);
		fatty.finish();
	}
}
