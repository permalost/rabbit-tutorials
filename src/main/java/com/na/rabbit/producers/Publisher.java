
package com.na.rabbit.producers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Publisher {
	
	private final TimingStrategy timingStrategy;
	private final MessageStrategy messageStrategy;
	private final PublishingStrategy publishingStrategy;
	private final RoutingStrategy routingStrategy;
	private final int count;

	public Publisher(TimingStrategy timingStrategy, MessageStrategy messageStrategy, PublishingStrategy publishingStrategy, RoutingStrategy routingStrategy, int count)
	{
		this.timingStrategy = timingStrategy;
		this.messageStrategy = messageStrategy;
		this.publishingStrategy = publishingStrategy;
		this.routingStrategy = routingStrategy;
		this.count = count;
	}
	
	public void start() {
		try
		{			
			for (int i = 0; i < count; i++)
			{
				String message = messageStrategy.message();
				String route = routingStrategy.route();
				
				timingStrategy.delay(message);
				publishingStrategy.publish(message, route);
				
				System.out.println(" [x] Sent '" + message + "'");
			}
		}
		catch (IOException ex)
		{
			Logger.getLogger(Publisher.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}

}
