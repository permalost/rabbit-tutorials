
package com.na.rabbit.producers.messages;

import com.na.rabbit.producers.MessageStrategy;
import java.util.Arrays;
import java.util.Random;

class SeveralPeriods implements MessageStrategy {

	private static final Random RANDOM = new Random(1234l);	
	
	@Override
	public String message()
	{
		int length = 1 + RANDOM.nextInt(4);
		char[] chars = new char[length];
		Arrays.fill(chars, '.');
		return String.format("Hello%s", new String(chars));
	}

}
