
package com.na.rabbit.producers.messages;

import com.na.rabbit.producers.MessageStrategy;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

class SeveralPeriods implements MessageStrategy {	
	
	@Override
	public String message()
	{
		int length = 1 + ThreadLocalRandom.current().nextInt(4);
		char[] chars = new char[length];
		Arrays.fill(chars, '.');
		return String.format("Hello%s", new String(chars));
	}

}
