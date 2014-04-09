package com.syamantics.wordFrequency.strategies;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import com.syamantics.charReaders.AbstractCharReader;
import com.syamantics.dataStructures.heap.BoundedPriorityQueue;
import com.syamantics.wordFrequency.WordEntry;

/**
 * Word Counting Strategy implementation using plain HashMap.
 * Only works for English words.
 * 
 * @author asyam
 */
public class HashMapStrategy implements IWordCountingStrategy {

	private HashMap<String, Integer> map;

	public HashMapStrategy() {
		map = new HashMap<>();
	}

	@Override
	public void updateCount(AbstractCharReader charReader) {
		StringBuffer sb = new StringBuffer();
		boolean startWord = false;

		while (charReader.hasNext()) {
			char ch = charReader.next();

			if (Character.isAlphabetic(ch)) {
				if (!startWord) {
					sb = new StringBuffer();
					startWord = true;
				}

				sb.append(Character.toLowerCase(ch));
			} else if (startWord) {
				addToMap(sb);
				startWord = false;
			}
			// Non alphabetics and no word ended...
		}

		if (sb.length() > 0) {
			addToMap(sb);
		}
	}

	private void addToMap(StringBuffer sb) {
		String str = sb.toString();
		if (!map.containsKey(str)) {
			map.put(str, new Integer(1));
		} else {
			map.put(str, map.get(str) + 1);
		}
	}

	@Override
	public Queue<WordEntry> getTopWords(int n) {
		BoundedPriorityQueue<WordEntry> boundedMaxHeap = 
				new BoundedPriorityQueue<>(n);

		for (Map.Entry<String, Integer> pair : map.entrySet()) {
			boundedMaxHeap.add(new WordEntry(pair.getKey(),
					pair.getValue().intValue()));
		}

		return boundedMaxHeap;
	}

}
