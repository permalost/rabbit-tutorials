package com.na.rabbit;

import com.na.rabbit.consumers.work.WorkFactory;
import com.na.rabbit.consumers.Worker;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

public class ConsumerMain {

  private static final String WORK_TYPE_DEFAULT = "immediate";

	public static void main(String[] argv) throws Exception 
	{
		String queueName = System.getenv("QUEUE_NAME");
		final String exchangeName = System.getenv("EXCHANGE_NAME");
		
		if (queueName == null && exchangeName == null)
		{
			System.err.println("Please provide a QUEUE_NAME or EXCHANGE_NAME");
			System.exit(1);
		}
		Connection connection = prepareConnection();
		Channel channel = connection.createChannel();
		
		if (exchangeName != null)
		{
			queueName = prepareChannelWithExchange(channel, exchangeName);
		}
		else
		{
			prepareChannelWithQueue(channel, queueName);
		}
		
		String work = Optional.ofNullable(System.getenv("WORK")).orElse(WORK_TYPE_DEFAULT);
		Worker worker = new Worker(channel, queueName, WorkFactory.getWorkStrategy(work));
		worker.start();
	}
	
	private static String prepareChannelWithExchange(final Channel channel, final String exchange) throws IOException
	{
		final String type = Optional.ofNullable(System.getenv("EXCHANGE_TYPE")).orElse("direct");
		
		channel.exchangeDeclare(exchange, type);
		String queueName = channel.queueDeclare().getQueue();
		
		final String bindingKeys = Optional.ofNullable(System.getenv("BINDING_KEYS")).orElse("#");
		
		for (String bindingKey : bindingKeys.split(","))
		{
			channel.queueBind(queueName, exchange, bindingKey);
		}
		
		return queueName;
	}
	
	private static void prepareChannelWithQueue(final Channel channel, final String queueName) throws IOException
	{
		boolean durable = Boolean.parseBoolean(Optional.ofNullable(System.getenv("DURABLE")).orElse("false"));
		boolean exclusive = Boolean.parseBoolean(Optional.ofNullable(System.getenv("EXCLUSIVE")).orElse("false"));
		boolean autoDelete = Boolean.parseBoolean(Optional.ofNullable(System.getenv("AUTO_DELETE")).orElse("false"));
		
		channel.queueDeclare(queueName, durable, exclusive, autoDelete, null);
	}
	
	private static Connection prepareConnection() throws IOException, TimeoutException 
	{		
		ConnectionFactory factory = new ConnectionFactory();
		String host = Optional.ofNullable(System.getenv("RABBIT_HOST")).orElse("localhost");
		factory.setHost(host);
		return factory.newConnection();
	}
  
}