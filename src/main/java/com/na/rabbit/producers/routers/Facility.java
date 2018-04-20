
package com.na.rabbit.producers.routers;

import com.na.rabbit.producers.RoutingStrategy;
import java.util.Random;

public class Facility implements RoutingStrategy {
	
	private static final Random RANDOM = new Random(1234l);
	
	@Override
	public String route()
	{
		return Location.values()[(RANDOM.nextInt(Location.values().length))].name();
	}

	private static enum Location {
		USWEST,
		USEAST,
		EUWEST,
		HQ;
	}
}
