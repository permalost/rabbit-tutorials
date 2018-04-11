
package com.na.rabbit.consumers.work;

import com.na.rabbit.consumers.WorkStrategy;

class ImmediateComplete implements WorkStrategy
{
	@Override
	public void work(String task)
	{
		System.out.println(String.format("Completed %s by %s", task, this.toString()));
	}
	
}
