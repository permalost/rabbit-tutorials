
package com.na.rabbit.consumers.work;

import com.na.rabbit.consumers.WorkStrategy;

public class WorkFactory {
	
	public static WorkStrategy getWorkStrategy(String workType) {
		switch (workType)
		{
			case "periods":
				return new DelayedByPeriods();
			case "immediate":
				return new ImmediateComplete();
			default:
				return new ImmediateComplete();
		}
	}
}
