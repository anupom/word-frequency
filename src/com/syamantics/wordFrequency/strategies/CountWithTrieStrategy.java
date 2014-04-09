package com.syamantics.wordFrequency.strategies;

import java.util.List;
import java.util.Queue;

import com.syamantics.charReaders.AbstractCharReader;
import com.syamantics.dataStructures.heap.BoundedPriorityQueue;
import com.syamantics.dataStructures.trie.Trie;
import com.syamantics.wordFrequency.WordEntry;

public class CountWithTrieStrategy implements IWordCountingStrategy {

	private Trie trie;

	public CountWithTrieStrategy() {
		trie = new Trie();
	}

	@Override
	public void updateCount(AbstractCharReader charReader) {
		Trie.Node node = trie.getRoot();
		boolean startWord = false;

		while (charReader.hasNext()) {
			// Reading byte by byte assuming it's ASCII
			char ch = charReader.next();

			if (Character.isAlphabetic(ch)) {
				if (!startWord) {
					// Move to root node
					node = trie.getRoot();
					startWord = true;
				}

				// Add character and make it new node
				node = node.addChildren(Character.toLowerCase(ch));
			} else if (startWord) {
				// Increment number in node
				node.markAsWord();
				startWord = false;
			}
		}

		// For the last evaluated node
		if (startWord) {
			node.markAsWord();
		}
	}

	@Override
	public Queue<WordEntry> getTopWords(int n) {
		BoundedPriorityQueue<WordEntry> boundedMaxHeap = new BoundedPriorityQueue<>(
				n);

		List<Trie.Node> list = trie.getAllWordNodes();
		for (Trie.Node node : list) {
			boundedMaxHeap.add(new WordEntry(node.toString(), node.getCount()));
		}

		return boundedMaxHeap;
	}
}
