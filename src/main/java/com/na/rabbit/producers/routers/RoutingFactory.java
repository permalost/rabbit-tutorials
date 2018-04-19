
package com.na.rabbit.producers.routers;

import com.na.rabbit.producers.RoutingStrategy;

public class RoutingFactory {
	
	public static RoutingStrategy getRoutingStrategy(String strategy) {
		switch (strategy)
		{
			case "none":
			default:
				return new None();
		}
	}

}
