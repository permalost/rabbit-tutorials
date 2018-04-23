package com.na.rabbit.consumers;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Worker
{
	WorkStrategy workStrategy;
	Channel channel;
	String queueName;

	public Worker(Channel channel, String queueName, WorkStrategy workStrategy)
	{
		this.workStrategy = workStrategy;
		this.queueName = queueName;
		this.channel = channel;
	}

	public void start() {
		try
		{
			startStuff(channel, workStrategy);
		}
		catch (IOException ex)
		{
			Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private void startStuff(final Channel channel, WorkStrategy workStrategy) throws IOException
	{
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel)
		{
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException
			{
				String message = new String(body, "UTF-8");
				workStrategy.work(message);
				System.out.println(String.format(" [x] Received '%s':'%s'", envelope.getRoutingKey(), message));
			}
		};
		
		channel.basicConsume(queueName, true, consumer);
	}
}
