
package com.na.rabbit.producers.messages;

import com.na.rabbit.producers.MessageStrategy;
import java.util.Random;

public class RandomPhrases implements MessageStrategy {
	
	private static final Random RANDOM = new Random(1234l);
	
	@Override
	public String message()
	{
		return Phrases.values()[(RANDOM.nextInt(Phrases.values().length))].getPhrase();
	}
	
	private static enum Phrases {
		PICKLE ("In a Pickle"),
		WAKE("Wake Up Call"),
		HEADS("Heads Up"),
		DROPPING("Dropping Like Flies"),
		TWO("Two Down, One to Go"),
		PLAYING("Playing For Keeps"),
		BEATING("Beating a Dead Horse"),
		KNUCKLE("Knuckle Down"),
		DOWN("Down For The Count"),
		CLOSE("Close But No Cigar"),
		RAIN("Rain on Your Parade"),
		JUDGE("You Can't Judge a Book By Its Cover"),
		FIGHT("Fight Fire With Fire"),
		FOOLS("Fool's Gold"),
		GREEK("All Greek To Me"),
		HEAT("If You Can't Stand the Heat, Get Out of the Kitchen"),
		DIME("A Dime a Dozen"),
		JIG("Jig Is Up"),
		BURST("Burst Your Bubble"),
		TUG("Tug of War");
		
		private final String phrase;

		private Phrases(String phrase)
		{
			this.phrase = phrase;
		}

		public String getPhrase()
		{
			return phrase;
		}
		
	}

}
