
package com.na.rabbit.producers.routers;

import com.na.rabbit.producers.RoutingStrategy;

public class None implements RoutingStrategy {
	@Override
	public String route()
	{
		return "";
	}

}
