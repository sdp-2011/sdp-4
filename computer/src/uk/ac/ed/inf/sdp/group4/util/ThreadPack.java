package uk.ac.ed.inf.sdp.group4.util;

public class ThreadPack
{
	private Thread[] threads;

	public ThreadPack()
	{
		threads = new Thread[2];
	}

	public void add(Thread thread)
	{
		int count = -1;

		for (int i = 0; i < threads.length; i++)
		{
			if (threads[i] == null)
			{
				count = i;
				break;
			}
		}

		if (count < 0)
		{
			Thread[] temp = new Thread[threads.length * 2];

			for (int i = 0; i < threads.length; i++)
			{
				temp[i] = threads[i];
			}

			temp[threads.length] = thread;
			threads = temp;
		}

		else
		{
			threads[count] = thread;
		}

		thread.start();
	}

	public void start()
	{
		
	}

	public void pause()
	{

	}

	public void resume()
	{

	}

	public void release(Thread thread)	
	{
		for (int i = 0; i < threads.length; i++)
		{
			if (threads[i].equals(thread))
			{
				threads[i] = null;
			}
		}
	}
}
