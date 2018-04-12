package com.na.rabbit;

import com.na.rabbit.consumers.work.WorkFactory;
import com.na.rabbit.consumers.Worker;
import com.rabbitmq.client.*;

public class ConsumerMain {
  
  private static final String QUEUE_NAME_DEFAULT = "hello";
  private static final boolean DURABILITY_DEFAULT = false;
  private static final boolean EXCLUSIVITY_DEFAULT = false;
  private static final boolean AUTO_DELETE_DEFAULT = false;
  
  private static final String WORK_TYPE_DEFAULT = "immediate";

	public static void main(String[] argv) throws Exception 
	{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("rabbit_1");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME_DEFAULT, DURABILITY_DEFAULT, EXCLUSIVITY_DEFAULT, AUTO_DELETE_DEFAULT, null);

		Worker worker = new Worker(channel, QUEUE_NAME_DEFAULT, WorkFactory.getWorkStrategy(WORK_TYPE_DEFAULT));
		worker.start();
	}
  
}