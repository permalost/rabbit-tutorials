
package com.na.rabbit.producers.publishers;

import com.na.rabbit.producers.PublishingStrategy;
import com.rabbitmq.client.Channel;

public class PublisherFactory {
	
	public static PublishingStrategy getPublishingStrategy(String publisher, String name, Channel channel) {
		switch (publisher)
		{
			case "exchange":				
				return new Exchange(name, channel);
			case "queue":
			default:
				return new Queue(name, channel);
		}
	}

}
