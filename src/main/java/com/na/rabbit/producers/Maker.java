
package com.na.rabbit.producers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Maker {
	
	private final TimingStrategy timingStrategy;
	private final MessageStrategy messageStrategy;
	private final int count;
	private final Channel channel;
	private final String queueName;

	public Maker(Channel channel, String queueName, int count, TimingStrategy timingStrategy, MessageStrategy messageStrategy)
	{
		this.timingStrategy = timingStrategy;
		this.messageStrategy = messageStrategy;
		this.count = count;
		this.channel = channel;
		this.queueName = queueName;
	}
	
	public void start() {
		try
		{			
			for (int i = 0; i < count; i++)
			{
				String message = messageStrategy.message();
				channel.basicPublish("", queueName, null, message.getBytes("UTF-8"));
				System.out.println(" [x] Sent '" + message + "'");
				timingStrategy.delay(message);
			}

			channel.close();
		}
		catch (IOException | TimeoutException ex)
		{
			Logger.getLogger(Maker.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}

}
