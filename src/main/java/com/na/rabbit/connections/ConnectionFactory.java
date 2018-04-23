
package com.na.rabbit.connections;

import com.rabbitmq.client.Connection;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

public class ConnectionFactory {
	
	public static Connection getConnection(String connectionSource) throws IOException, TimeoutException {
		switch (connectionSource)
		{
			case "default":
			default:
				return prepareConnection();
		}
	}	
	
	private static Connection prepareConnection() throws IOException, TimeoutException 
	{		
		com.rabbitmq.client.ConnectionFactory factory = new com.rabbitmq.client.ConnectionFactory();
		String host = Optional.ofNullable(System.getenv("RABBIT_HOST")).orElse("localhost");
		factory.setHost(host);
		return factory.newConnection();
	}

}
