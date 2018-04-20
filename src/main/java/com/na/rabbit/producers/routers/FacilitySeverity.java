
package com.na.rabbit.producers.routers;

import com.na.rabbit.producers.RoutingStrategy;

public class FacilitySeverity implements RoutingStrategy {
	
	private final RoutingStrategy facility;
	private final RoutingStrategy severity;

	public FacilitySeverity()
	{
		this.facility = RoutingFactory.getRoutingStrategy("facility");
		this.severity = RoutingFactory.getRoutingStrategy("severity");
	}
	
	@Override
	public String route()
	{
		return String.join(".", facility.route(), severity.route());
	}

}
