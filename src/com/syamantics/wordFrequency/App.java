package com.syamantics.wordFrequency;

import java.util.HashMap;
import java.util.List;

import com.syamantics.charReaders.ASCIICharReader;
import com.syamantics.charReaders.AbstractCharReader;
import com.syamantics.charReaders.UTFCharReader;
import com.syamantics.wordFrequency.strategies.CountWithHashMapConcurrentStrategy;
import com.syamantics.wordFrequency.strategies.CountWithHashMapStrategy;
import com.syamantics.wordFrequency.strategies.CountWithTrieStrategy;
import com.syamantics.wordFrequency.strategies.IWordCountingStrategy;

/**
 * Main class to run the app.
 * 
 * Usage: App [file-location] [number-of-words] [ascii|utf] [strategy-name]
 * 
 * @author asyam
 */
public class App {
	private static final int DEFAULT_COUNT = 10;
	
	private enum Strategies {
	    TRIE, HASHMAP, CONCURRENT 
	}
	
	private enum CharReaders {
	    ASCII, UTF
	}
	
	private static HashMap<CharReaders, AbstractCharReader> charReaders;
	private static HashMap<Strategies, IWordCountingStrategy> strategies;
	static {
		charReaders = new HashMap<CharReaders, AbstractCharReader>();
		charReaders.put(CharReaders.ASCII, new ASCIICharReader());
		charReaders.put(CharReaders.UTF, new UTFCharReader());
		
		strategies = new HashMap<Strategies, IWordCountingStrategy>();
		strategies.put(Strategies.TRIE, new CountWithTrieStrategy());
		strategies.put(Strategies.HASHMAP, new CountWithHashMapStrategy());
		strategies.put(Strategies.CONCURRENT, new CountWithHashMapConcurrentStrategy());
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Please provide a text file name");
			System.exit(1);
		}

		String fileName = args[0].toString();
		int count = DEFAULT_COUNT;

		if (args.length > 1) {
			try {
				count = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				System.err.println("Number of words " + args[1] + " must be an integer.");
				System.exit(1);
			}
		}

		AbstractCharReader charReader = charReaders.get(CharReaders.ASCII);
		if (args.length > 2) {
			String argument = args[2].toString().toUpperCase(); 
			try {
				charReader = charReaders.get(CharReaders.valueOf(argument));
			} catch(IllegalArgumentException iae) {
				System.err.println("CharReader " + args[2] + " is invalid.");
				System.exit(1);
			}
		}
		
		IWordCountingStrategy strategy = strategies.get(Strategies.TRIE);
		if (args.length > 3) {
			String argument = args[3].toString().toUpperCase(); 
			try {
				strategy = strategies.get(Strategies.valueOf(argument));
			} catch(IllegalArgumentException iae) {
				System.err.println("Strategy " + args[3] + " is invalid.");
				System.exit(1);
			}
		}

		List<String> words = new WordCounter(fileName, count, charReader,strategy)
			.getTopWords();
		
		for (String word : words) {
			System.out.println(word);
		}
	}
}
