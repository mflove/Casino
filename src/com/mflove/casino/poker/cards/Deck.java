/**
 * The Deck class maintains the list of cards to be dealt during gameplay. 
 * @author Matt Love (mflove1991@gmail.com)
 * @version 1.0 Last Updated August 1, 2013
 */

package com.mflove.casino.poker.cards;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	
	public static final int NUM_CARDS = Card.NUM_FACES * Card.NUM_SUITS;
	
	/**
	 * List of cards in the deck
	 */
	private ArrayList<Card> cards;
	
	public Deck() {
		// build list of cards
		cards = new ArrayList<Card>(NUM_CARDS);
		for(int i = 0; i < NUM_CARDS; i++)
			cards.add(new Card(i));
		
		// randomize order of cards
		Collections.shuffle(cards);
	}
	
	/**
	 * Remove and return the card on the top of the deck.
	 * @return card on the top of the deck
	 */
	public Card dealCard() {
		return cards.remove(0);
	}
	
}
