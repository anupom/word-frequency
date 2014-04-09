package com.syamantics.wordFrequency.strategies;

import java.util.Queue;

import com.syamantics.charReaders.AbstractCharReader;
import com.syamantics.wordFrequency.WordEntry;

/**
 * Defines the interface for Word Counting Strategy concrete implementations.
 * 
 * @author asyam
 */
public interface IWordCountingStrategy {
	
	/**
	 * Given a Character Reader it should be able to analyze the characters
	 * and update word counts internally.
	 * 
	 * @param charReader character reader instance
	 */
	public void updateCount(AbstractCharReader charReader);

	
	/**
	 * Return top n words sorted by frequency
	 * 
	 * @param n	number of words to return
	 * @return	queue of words of size n sorted by their number of occurrence.
	 */
	public Queue<WordEntry> getTopWords(int n);

}
