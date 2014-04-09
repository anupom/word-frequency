package com.syamantics.wordFrequency;

import com.syamantics.charReaders.ASCIICharReader;
import com.syamantics.wordFrequency.strategies.CountWithHashMapConcurrentStrategy;

public class App {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();

		new WordCounter("/Users/asyam/Documents/workspace/big.txt", 10,
				new ASCIICharReader(), new CountWithHashMapConcurrentStrategy())
				.getTopWords();

		System.out.println(System.currentTimeMillis() - start);
	}
}
