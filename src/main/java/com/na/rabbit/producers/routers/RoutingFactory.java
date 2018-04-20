
package com.na.rabbit.producers.routers;

import com.na.rabbit.producers.RoutingStrategy;

public class RoutingFactory {
	
	public static RoutingStrategy getRoutingStrategy(String strategy) {
		switch (strategy)
		{
			case "facility":
				return new Facility();
			case "severity":
				return new Severity();
			case "facilityseverity":
				return new FacilitySeverity();
			case "none":
			default:
				return new None();
		}
	}

}
