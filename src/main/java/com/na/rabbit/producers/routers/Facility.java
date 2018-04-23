
package com.na.rabbit.producers.routers;

import com.na.rabbit.producers.RoutingStrategy;
import java.util.concurrent.ThreadLocalRandom;

public class Facility implements RoutingStrategy {
	
	@Override
	public String route()
	{
		return Location.values()[(ThreadLocalRandom.current().nextInt(Location.values().length))].name();
	}

	private static enum Location {
		USWEST,
		USEAST,
		EUWEST,
		HQ;
	}
}
