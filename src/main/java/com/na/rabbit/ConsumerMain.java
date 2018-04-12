package com.na.rabbit;

import com.na.rabbit.consumers.work.WorkFactory;
import com.na.rabbit.consumers.Worker;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

public class ConsumerMain {
  
  private static final String QUEUE_NAME_DEFAULT = "default";
  
  private static final String WORK_TYPE_DEFAULT = "immediate";

	public static void main(String[] argv) throws Exception 
	{
		String queueName = Optional.ofNullable(System.getenv("QUEUE_NAME")).orElse(QUEUE_NAME_DEFAULT);
		String work = Optional.ofNullable(System.getenv("WORK")).orElse(WORK_TYPE_DEFAULT);
				
		Connection connection = prepareConnection();
		Channel channel = prepareChannel(connection, queueName);
		
		Worker worker = new Worker(channel, queueName, WorkFactory.getWorkStrategy(work));
		worker.start();
	}
	
	private static Channel prepareChannel(final Connection connection, final String queueName) throws IOException
	{
		boolean durable = Boolean.parseBoolean(Optional.ofNullable(System.getenv("DURABLE")).orElse("false"));
		boolean exclusive = Boolean.parseBoolean(Optional.ofNullable(System.getenv("EXCLUSIVE")).orElse("false"));
		boolean autoDelete = Boolean.parseBoolean(Optional.ofNullable(System.getenv("AUTO_DELETE")).orElse("false"));
		
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