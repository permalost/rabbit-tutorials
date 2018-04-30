
package com.na.rabbit.connections;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Connection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

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
		final com.rabbitmq.client.ConnectionFactory factory = new com.rabbitmq.client.ConnectionFactory();
		final String username = Optional.ofNullable(System.getenv("RABBIT_USER")).orElse("guest");
		final String password = Optional.ofNullable(System.getenv("RABBIT_PASSWORD")).orElse("guest");
		final String vhost = Optional.ofNullable(System.getenv("RABBIT_VHOST")).orElse("/");
		final String serverList = Optional.ofNullable(System.getenv("RABBIT_SERVERS")).orElse("localhost");
		
		final Collection<String> serverNames = new ArrayList<>(Arrays.asList(serverList.split(",")));		
		final List<Address> servers = serverNames.stream()
				.map((serverName) -> new Address(serverName))
				.collect(Collectors.toList());
		
		factory.setUsername(username);
		factory.setPassword(password);
		factory.setVirtualHost(vhost);
		return factory.newConnection(servers);
	}

}
