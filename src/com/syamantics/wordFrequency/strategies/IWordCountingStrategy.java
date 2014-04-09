package com.syamantics.wordFrequency.strategies;

import java.util.Queue;

import com.syamantics.charReaders.AbstractCharReader;
import com.syamantics.wordFrequency.WordEntry;

public interface IWordCountingStrategy {

	public void updateCount(AbstractCharReader charReader);

	public Queue<WordEntry> getTopWords(int n);

}
