
package com.na.rabbit.producers.routers;

import com.na.rabbit.producers.RoutingStrategy;
import java.util.Random;

public class Severity implements RoutingStrategy {
	
	private static final Random RANDOM = new Random(1234l);
	
	@Override
	public String route()
	{
		return Level.values()[(RANDOM.nextInt(Level.values().length))].name();
	}

	private static enum Level {
		FATAL,
		ERROR,
		WARNING,
		INFO;
	}
}
