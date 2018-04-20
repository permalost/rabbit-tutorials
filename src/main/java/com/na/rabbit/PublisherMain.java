package com.na.rabbit;

import com.na.rabbit.producers.Publisher;
import com.na.rabbit.producers.PublishingStrategy;
import com.na.rabbit.producers.RoutingStrategy;
import com.na.rabbit.producers.messages.MessageFactory;
import com.na.rabbit.producers.publishers.PublisherFactory;
import com.na.rabbit.producers.routers.RoutingFactory;
import com.na.rabbit.producers.timing.TimingFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

public class PublisherMain
{
	private static final int MESSAGE_COUNT = 20;
	
	private static final String TIMING_DEFAULT = "immediate";
	private static final String MESSAGE_DEFAULT = "helloworld";
	private static final String ROUTING_DEFAULT = "none";

	public static void main(String[] argv) throws Exception
	{
		final String queueName = System.getenv("QUEUE_NAME");
		final String exchangeName = System.getenv("EXCHANGE_NAME");
		
		if (queueName == null && exchangeName == null)
		{
			System.err.println("Please provide a QUEUE_NAME or EXCHANGE_NAME");
			System.exit(1);
		}
		
		final String timing = Optional.ofNullable(System.getenv("TIMING")).orElse(TIMING_DEFAULT);
		final String message = Optional.ofNullable(System.getenv("MESSAGE")).orElse(MESSAGE_DEFAULT);
		final String routing = Optional.ofNullable(System.getenv("ROUTING")).orElse(ROUTING_DEFAULT);
		
		try (Connection connection = prepareConnection())
		{
			Channel channel;
			RoutingStrategy routingStrategy;
			PublishingStrategy publishingStrategy;
			
			if (exchangeName != null)
			{
				channel = prepareChannelWithExchange(connection, exchangeName);
				publishingStrategy = PublisherFactory.getPublishingStrategy("exchange", exchangeName, channel);
				routingStrategy = RoutingFactory.getRoutingStrategy(routing);
			} else {
				channel = prepareChannelWithQueue(connection, queueName);
				publishingStrategy = PublisherFactory.getPublishingStrategy("queue", queueName, channel);
				routingStrategy = RoutingFactory.getRoutingStrategy("none");
			}
			
			Publisher maker = new Publisher(
					TimingFactory.getTiming(timing),
					MessageFactory.getMessageStrategy(message),
					publishingStrategy, 
					routingStrategy, 
					MESSAGE_COUNT
			);

			maker.start();
			
			channel.close();
		}
	}
	
	private static Channel prepareChannelWithQueue(final Connection connection, final String queueName) throws IOException
	{
		boolean durable = Boolean.parseBoolean(Optional.ofNullable(System.getenv("DURABLE")).orElse("false"));
		boolean exclusive = Boolean.parseBoolean(Optional.ofNullable(System.getenv("EXCLUSIVE")).orElse("false"));
		boolean autoDelete = Boolean.parseBoolean(Optional.ofNullable(System.getenv("AUTO_DELETE")).orElse("false"));
		
		Channel channel = connection.createChannel();
		channel.queueDeclare(queueName, durable, exclusive, autoDelete, null);
		return channel;
	}
	
	private static Channel prepareChannelWithExchange(final Connection connection, final String exchange) throws IOException
	{
		final String type = Optional.ofNullable(System.getenv("EXCHANGE_TYPE")).orElse("direct");
		
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(exchange, type);
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