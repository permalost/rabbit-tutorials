
package com.na.rabbit.consumers.work;

import com.na.rabbit.consumers.WorkStrategy;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

class DelayedByPeriods implements WorkStrategy {
	
	@Override
	public void work(String task)
	{
		try
		{
			long count = task.chars().filter(character -> character == '.').count();
			TimeUnit.SECONDS.sleep(count);
			System.out.println(String.format("Completed %s by %s", task, this.toString()));
		}
		catch (InterruptedException ex)
		{
			Logger.getLogger(DelayedByPeriods.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
