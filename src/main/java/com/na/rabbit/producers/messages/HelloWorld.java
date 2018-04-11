
package com.na.rabbit.producers.messages;

import com.na.rabbit.producers.MessageStrategy;

class HelloWorld implements MessageStrategy {
	
	@Override
	public String message()
	{
		return "Hello World!";
	}

}
