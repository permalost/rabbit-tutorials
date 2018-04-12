package com.na.rabbit;

import com.na.rabbit.producers.Maker;
import com.na.rabbit.producers.messages.MessageFactory;
import com.na.rabbit.producers.timing.TimingFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

public class MakerMain
{
	private static final int MESSAGE_COUNT = 20;

	private static final String QUEUE_NAME_DEFAULT = "default";
	
	private static final String TIMING_DEFAULT = "immediate";
	private static final String MESSAGE_DEFAULT = "helloworld";

	public static void main(String[] argv) throws Exception
	{
		final String queueName = Optional.ofNullable(System.getenv("QUEUE_NAME")).orElse(QUEUE_NAME_DEFAULT);
		final String timing = Optional.ofNullable(System.getenv("TIMING")).orElse(TIMING_DEFAULT);
		final String message = Optional.ofNullable(System.getenv("MESSAGE")).orElse(MESSAGE_DEFAULT);		
		
		try (Connection connection = prepareConnection())
		{
			Channel channel = prepareChannel(connection, queueName);
			
			Maker maker = new Maker(
					channel,
					queueName,
					MESSAGE_COUNT,
					TimingFactory.getTiming(timing),
					MessageFactory.getMessageStrategy(message)
			);
			maker.start();
		}
	}

	private static final String DURABILITY_DEFAULT = "false";
	private static final String EXCLUSIVITY_DEFAULT = "false";
	private static final String AUTO_DELETE_DEFAULT = "false";
	
	private static Channel prepareChannel(final Connection connection, final String queueName) throws IOException
	{
		boolean durable = Boolean.parseBoolean(Optional.ofNullable(System.getenv("DURABLE")).orElse(DURABILITY_DEFAULT));
		boolean exclusive = Boolean.parseBoolean(Optional.ofNullable(System.getenv("EXCLUSIVE")).orElse(EXCLUSIVITY_DEFAULT));
		boolean autoDelete = Boolean.parseBoolean(Optional.ofNullable(System.getenv("AUTO_DELETE")).orElse(AUTO_DELETE_DEFAULT));
		
		Channel channel = connection.createChannel();
		channel.queueDeclare(queueName, durable, exclusive, autoDelete, null);
		return channel;
	}
	
	private static Connection prepareConnection() throws IOException, TimeoutException 
	{		
		ConnectionFactory factory = new ConnectionFactory();
		String host = Optional.ofNullable(System.getenv("RABBIT_HOST")).orElse("localhost");
		factory.setHost(host);
		return factory.newConnection();
	}

}
