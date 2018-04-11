
package com.na.rabbit.producers.messages;

import com.na.rabbit.producers.MessageStrategy;
import java.util.Arrays;
import java.util.Random;

class SeveralPeriods implements MessageStrategy {
	
	private static final Random RANDOM = new Random(1234l);	
	
	@Override
	public String message()
	{
		int length = RANDOM.nextInt(10);
		char[] chars = new char[length];
		Arrays.fill(chars, '.');
		return String.format("Hello World%s", new String(chars));
	}

}
