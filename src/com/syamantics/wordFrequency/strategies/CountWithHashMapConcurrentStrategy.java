package com.syamantics.wordFrequency.strategies;

import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;

import com.syamantics.charReaders.AbstractCharReader;
import com.syamantics.dataStructures.heap.BoundedPriorityQueue;
import com.syamantics.wordFrequency.WordEntry;

/**
 * Word Counting Strategy implementation using a parallel algorithm.
 * Uses ConcurrentHashMap and AtomicInteger internally for concurrency.
 * 
 * @author asyam
 */
public class CountWithHashMapConcurrentStrategy implements
		IWordCountingStrategy {

	// Parallelism level
	private static final int PARALLELISM = 4;

	ConcurrentHashMap<String, AtomicInteger> map;

	public CountWithHashMapConcurrentStrategy() {
		map = new ConcurrentHashMap<>();
	}

	@Override
	public void updateCount(AbstractCharReader charReader) {
		WordCountTask counter = new WordCountTask(charReader, 0,
				charReader.limit(), map);
		ForkJoinPool pool = new ForkJoinPool(PARALLELISM);
		pool.invoke(counter);
	}

	@Override
	public Queue<WordEntry> getTopWords(int n) {
		BoundedPriorityQueue<WordEntry> boundedMaxHeap = new BoundedPriorityQueue<>(
				n);

		for (Entry<String, AtomicInteger> pair : map.entrySet()) {
			boundedMaxHeap.add(new WordEntry(pair.getKey(),
					pair.getValue().intValue()));
		}

		return boundedMaxHeap;
	}

	private class WordCountTask extends RecursiveAction {

		private static final long serialVersionUID = 1L;

		private AbstractCharReader charReader;
		int startPos;
		int endPos;
		private ConcurrentHashMap<String, AtomicInteger> map;

		// Don't divide it further if number of characters is less than this value
		private static final int THRESHOLD = 100_000;

		public WordCountTask(AbstractCharReader charReader, int startPos,
				int endPos, ConcurrentHashMap<String, AtomicInteger> map) {
			this.charReader = charReader;
			this.startPos = startPos;
			this.endPos = endPos;
			this.map = map;
		}

		protected void countWords() {
			StringBuffer sb = null;
			boolean startWord = false;

			for (int j = startPos; j < endPos; j++) {
				char ch = charReader.get(j);

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
			map.putIfAbsent(str, new AtomicInteger(0));
			map.get(str).incrementAndGet();
		}

		@Override
		protected void compute() {
			if ((endPos - startPos) < THRESHOLD) {
				countWords();
				return;
			}

			// We could do startPost + endPos / 2 but that may cause int
			// overflow
			int midPoint = startPos + (endPos - startPos) / 2;

			invokeAll(new WordCountTask(charReader, startPos, midPoint, map),
					new WordCountTask(charReader, midPoint, endPos, map));
		}
	}

}
