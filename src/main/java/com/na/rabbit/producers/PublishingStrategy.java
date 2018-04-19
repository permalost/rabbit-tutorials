
package com.na.rabbit.producers;

import java.io.IOException;


public interface PublishingStrategy
{
	public void publish(String message, String key) throws IOException;
}
