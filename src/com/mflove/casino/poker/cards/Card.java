/**
 * The Cardclass maintains information about the face and suit. 
 * @author Matt Love (mflove1991@gmail.com)
 * @version 1.0 Last Updated August 1, 2013
 */

package com.mflove.casino.poker.cards;

public class Card {
	
	public static final int NUM_FACES = 13;
	public static final int NUM_SUITS = 4;
	
	public static final int TWO = 0;
	public static final int THREE = 1;
	public static final int FOUR = 2;
	public static final int FIVE = 3;
	public static final int KING = 11;
	public static final int ACE = 12;
	
	public static final String[] FACES = { "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace" };
	public static final String[] SUITS = { "Hearts", "Spades", "Diamonds", "Clubs" };
	
	public static final String FACES_STRING = "23456789TJQKA";
	public static final String SUITS_STRING = "HSDC";

	/**
	 * face of the card
	 */
	private int face;
	
	/**
	 * suit of the card
	 */
	private int suit;
	
	public Card(int seed) {
		face = seed % NUM_FACES;
		suit = seed / NUM_FACES;
	}
	
	/**
	 * Get the face the of card.
	 * @return face of the card
	 */
	public int getFace() {
		return face;
	}
	
	/**
	 * Get the suit of the card.
	 * @return suit of the card
	 */
	public int getSuit() {
		return suit;
	}
	
	/**
	 * Get the shortened string representation of the card.
	 * @return shortened string representation of the card
	 */
	public String toShortString() {
		return String.format("%c%c", FACES_STRING.charAt(face), SUITS_STRING.charAt(suit));
	}
	/**
	 * Get the string representation of the card.
	 * @return string representation of teh card
	 */
	@Override
	public String toString() {
		return String.format("%s_of_%s", FACES[face], SUITS[suit]);
	}

}
