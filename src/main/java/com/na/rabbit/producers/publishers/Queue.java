
package com.na.rabbit.producers.publishers;

import com.na.rabbit.producers.PublishingStrategy;
import com.rabbitmq.client.Channel;
import java.io.IOException;

public class Queue implements PublishingStrategy {
	
	String name;
	Channel channel;

	public Queue(String name, Channel channel)
	{
		this.name = name;
		this.channel = channel;
	}
	
	@Override
	public void publish(String message, String key) throws IOException
	{
		channel.basicPublish("", name, null, message.getBytes("UTF-8"));
	}

}
