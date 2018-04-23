
package com.na.rabbit.producers.routers;

import com.na.rabbit.producers.RoutingStrategy;
import java.util.concurrent.ThreadLocalRandom;

public class Severity implements RoutingStrategy {
	
	@Override
	public String route()
	{
		return Level.values()[(ThreadLocalRandom.current().nextInt(Level.values().length))].name();
	}

	private static enum Level {
		FATAL,
		ERROR,
		WARNING,
		INFO;
	}
}
