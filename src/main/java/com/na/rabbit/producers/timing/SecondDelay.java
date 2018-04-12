
package com.na.rabbit.producers.timing;

import com.na.rabbit.producers.TimingStrategy;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SecondDelay implements TimingStrategy {
	
	@Override
	public void delay(String message)
	{
		try
		{
			TimeUnit.SECONDS.sleep(1);
		}
		catch (InterruptedException ex)
		{
			Logger.getLogger(SecondDelay.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
