/**
 * The Hand class maintains the list of cards and information about the rank and value of a hand. 
 * @author Matt Love (mflove1991@gmail.com)
 * @version 1.0 Last Updated August 1, 2013
 */

package com.mflove.casino.poker.cards;

import java.util.ArrayList;

public class Hand {
	
	public static final int NUM_CARDS = 5;
	
	public static final int HIGH_CARD = 0;
	public static final int PAIR = 1;
	public static final int TWO_PAIR = 2;
	public static final int THREE_OF_A_KIND = 3;
	public static final int STRAIGHT = 4;
	public static final int FLUSH = 5;
	public static final int FULL_HOUSE = 6;
	public static final int FOUR_OF_A_KIND = 7;
	public static final int STRAIGHT_FLUSH = 8;
	public static final int ROYAL_FLUSH = 9;
	
	public static String[] RANKS = { "High Card", "Pair", "Two Pair", "Three of a Kind", "Straight", "Flush", "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };
	
	/**
	 * Rank of the hand. General type between High Card and Royal Flush
	 */
	private int rank;
	
	/**
	 * Value of the hand. Determines strength of hand
	 */
	private long value;
	
	/**
	 * List of cards in the hand
	 */
	private ArrayList<Card> cards;
	
	public Hand() {
		rank = 0;
		value = 0L;
		cards = new ArrayList<Card>(NUM_CARDS);
	}
	
	/**
	 * Get the rank of the hand.
	 * @return rank of the hand
	 */
	public int getRank() {
		return rank;
	}
	
	/**
	 * Get the value of the hand.
	 * @return value of the hand
	 */
	public long getValue() {
		return value;
	}
	
	/**
	 * Get a card from the list.
	 * @param index index of card receiving
	 * @return card from the hand
	 */
	public Card getCard(int index) {
		return cards.get(index);
	}
	
	/**
	 * Counts number of pairs in hand. Used for determining hand rank.
	 * @return number of pairs in hand
	 */
	private int countPairs() {
		int pairs = 0;
		for(int i = 0; i < NUM_CARDS - 1; i++)
			for(int j = i + 1; j < NUM_CARDS; j++)
				if(cards.get(i).getFace() == cards.get(j).getFace())
					pairs++;
		return pairs;
	}
	
	/**
	 * Determines if hand is a straight. Used for determining hand rank.
	 * @return whether or not hand is straight 
	 */
	private boolean isStraight() {
		for(int i = 0; i < NUM_CARDS - 1; i++)
			if(cards.get(i).getFace() - cards.get(i + 1).getFace() != 1 && (cards.get(i).getFace() - cards.get(i + 1).getFace() != (Card.ACE - Card.FIVE) || cards.get(i).getFace() != Card.ACE))
				return false;
		return true;
	}
	
	/**
	 * Determines if hand is a flush. Used for determining hand rank.
	 * @return whether or not hand is flush
	 */
	private boolean isFlush() {
		for(int i = 0; i < NUM_CARDS - 1; i++)
			if(cards.get(i).getSuit() != cards.get(i + 1).getSuit())
				return false;
		return true;
	}
	
	/**
	 * Determines if hand contains a certain face. Used for determining hand rank and value.
	 * @param face face you wish to see if hand contains
	 * @return whether or not face is in hand
	 */
	private boolean containsFace(int face) {
		for(int i = 0; i < NUM_CARDS; i++)
			if(cards.get(i).getFace() == face)
				return true;
		return false;
	}
	
	/**
	 * Determines the rank of the hand.
	 * @return rank of the hand
	 */
	private int calculateRank() {
		// count the number of pairs in hand
		int pairs = countPairs();
		
		// hand based on pairs
		if(pairs > 0) {
			if(pairs == 1)
				return PAIR;
			if(pairs == 2)
				return TWO_PAIR;
			if(pairs == 3)
				return THREE_OF_A_KIND;
			if(pairs == 4)
				return FULL_HOUSE;
			if(pairs == 6)
				return FOUR_OF_A_KIND;
		}
		
		// hand based on straight or flush
		if(isStraight() && isFlush()) {
			if(containsFace(Card.ACE) && containsFace(Card.KING))
				return ROYAL_FLUSH;
			return STRAIGHT_FLUSH;
		}
		if(isFlush())
			return FLUSH;
		if(isStraight())
			return STRAIGHT;
		
		// hand is none of the above
		return HIGH_CARD;
	}
	
	/**
	 * Determines the value of the hand.
	 * @return value of the hand
	 */
	private long calculateValue() {
		// count the number each face appears in hand
		int[] faceCount = new int[Card.NUM_FACES];
		for(int i = 0; i < cards.size(); i++)
			faceCount[cards.get(i).getFace()]++;
		
		// order the hand such that the faces that appear most are in front
		int[] valueOrder = new int[NUM_CARDS];
		int numRelatedValues = 0;
		int current = 4;
		while(current > 0) {
			for(int i = faceCount.length - 1; i >= 0; i--)
				if(faceCount[i] == current)
					for(int j = 0; j < current; j++)
						valueOrder[numRelatedValues++] = i;
			current--;
		}
		
		// order the hand such that ace is low
		if(rank == STRAIGHT || rank == STRAIGHT_FLUSH)
			if(containsFace(Card.ACE) && containsFace(Card.FIVE)) {
				valueOrder[0] = Card.FIVE;
				valueOrder[1] = Card.FOUR;
				valueOrder[2] = Card.THREE;
				valueOrder[3] = Card.TWO;
				valueOrder[4] = Card.ACE;
			}
		
		// create long with rank at the front followed by the value order
		String total = String.format("%d", rank);
		for(int i = 0; i < NUM_CARDS; i++)
			total += String.format("%02d", valueOrder[i]);
		return Long.parseLong(total);
	}
	
	/**
	 * Adds a new card to the hand in high-to-low order
	 * @param card the new card to be added
	 */
	public void addCard(Card card) {
		// insert card into list where face is descending
		int index = 0;
		for(int i = 0; i < cards.size(); i++)
			if(card.getFace() <= cards.get(i).getFace())
				index = i + 1;
		cards.add(index, card);
		
		// if list of cards is full then calculate rank and value
		if(cards.size() == NUM_CARDS) {
			rank = calculateRank();
			value = calculateValue();
		}
	}
	
	/**
	 * Get a string representation of a hand.
	 * @return string representation of a hand
	 */
	@Override
	public String toString() {
		String s = RANKS[rank];
		for(int i = 0; i < NUM_CARDS; i++)
			s += " " + cards.get(i).toShortString();
		return s;
	}

}
