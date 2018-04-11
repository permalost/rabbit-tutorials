package com.na.rabbit;

import com.na.rabbit.producers.Maker;
import com.na.rabbit.producers.messages.MessageFactory;
import com.na.rabbit.producers.timing.TimingFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MakerMain
{
	private static final String QUEUE_NAME = "hello";
	private static final int MESSAGE_COUNT = 20;

	private static final String QUEUE_NAME_DEFAULT = "hello";
	private static final boolean DURABILITY_DEFAULT = false;
	private static final boolean EXCLUSIVITY_DEFAULT = false;
	private static final boolean AUTO_DELETE_DEFAULT = false;
	
	private static final String TIMING_DEFAULT = "immediate";
	private static final String MESSAGE_DEFAULT = "helloworld";

	public static void main(String[] argv) throws Exception
	{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		final Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME_DEFAULT, DURABILITY_DEFAULT, EXCLUSIVITY_DEFAULT, AUTO_DELETE_DEFAULT, null);

		Maker maker = new Maker(
				connection, 
				QUEUE_NAME, 
				MESSAGE_COUNT, 
				TimingFactory.getTiming(TIMING_DEFAULT), 
				MessageFactory.getMessageStrategy(MESSAGE_DEFAULT)
		);
		maker.start();
	}

}
