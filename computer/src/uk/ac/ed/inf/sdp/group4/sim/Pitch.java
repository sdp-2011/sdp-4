package uk.ac.ed.inf.sdp.group4.sim;

import uk.ac.ed.inf.sdp.group4.domain.Position;
import uk.ac.ed.inf.sdp.group4.world.WorldObject;

public class Pitch implements Runnable
{
	public Component[] components;

	public Pitch(Component[] components)
	{
		this.components = components;
	}

	public void run()
	{
		for (int i = 0; i < components.length; i++)
		{
			if (i == components.length - 1)
			{
				break;
			}

			for (int c = i + 1; c < components.length; c++)
			{
				WorldObject one = components[i].getObject();
				WorldObject two = components[c].getObject();

				if (one.getPosition().distance(two.getPosition()) < (components[i].getRadius()
				        + components[c].getRadius()))
				{
					if (components[i] instanceof SimBall)
					{
						SimBall ball = (SimBall) components[i];
						SimBot bot = (SimBot) components[c];
						if (!bot.isShooting())
						{
							ball.grab(bot);
						}
					}
					else if (components[c] instanceof SimBall)
					{
						SimBall ball = (SimBall) components[c];
						SimBot bot = (SimBot) components[i];
						if (!bot.isShooting())
						{
							ball.grab(bot);
						}
					}
				}
			}
		}
	}
}
