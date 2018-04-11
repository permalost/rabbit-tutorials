
package com.na.rabbit.producers.messages;

import com.na.rabbit.producers.MessageStrategy;

public class MessageFactory {
	
	public static MessageStrategy getMessageStrategy(String messageStrategy) {
		switch (messageStrategy)
		{
			case "periods":
				return new SeveralPeriods();
			case "helloworld":
			default:
				return new HelloWorld();
		}
	}

}
