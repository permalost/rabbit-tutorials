
package com.na.rabbit.producers.timing;

import com.na.rabbit.producers.TimingStrategy;

public class TimingFactory {
	
	public static TimingStrategy getTiming(String timing) {
		switch (timing)
		{
			case "secondDelay":
				return new SecondDelay();
			case "immediate":
			default:
				return new Immediate();
		}
	}

}
