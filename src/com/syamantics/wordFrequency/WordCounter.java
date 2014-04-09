package com.syamantics.wordFrequency;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.syamantics.charReaders.AbstractCharReader;
import com.syamantics.wordFrequency.strategies.IWordCountingStrategy;

/**
 * Calculates and returns top words from text files ordered by their frequency.
 * 
 * @author asyam
 */
public class WordCounter {
	/**
	 * Maximum bytes to load on single read Should be adjusted depending on our
	 * system memory.
	 * Make sure it's multiple of 16 for Unicode support and multiple of 8 
	 * for ASCII support.
	 */
	private final static long MAX_SIZE_PER_READ = 1024 * 128L;

	private String filePath;
	private int numWords;
	private AbstractCharReader charReader;
	private IWordCountingStrategy wordCountingStrategy;

	public WordCounter(String filePath, int numWords,
			AbstractCharReader charReader,
			IWordCountingStrategy wordCountingStrategy) {
		this.filePath = filePath;
		this.numWords = numWords;
		this.charReader = charReader;
		this.wordCountingStrategy = wordCountingStrategy;
	}

	public List<String> getTopWords() {
		Path file = Paths.get(filePath);

		List<String> topWords = new ArrayList<>();
		
		try (FileChannel inChannel = FileChannel.open(file)) {
			long sizeRemaining = inChannel.size();

			// Load chunk by chunk
			while (sizeRemaining > 0) {
				long sizeToRead = Math.min(sizeRemaining, MAX_SIZE_PER_READ);

				// Using MappedByteBuffer to load a chunk of file fast
				MappedByteBuffer buffer = inChannel.map(
						FileChannel.MapMode.READ_ONLY, 0, sizeToRead);
				sizeRemaining -= sizeToRead;

				buffer.load();

				wordCountingStrategy.updateCount(charReader.init(buffer));

				buffer.clear();
			}

			Queue<WordEntry> queue = wordCountingStrategy.getTopWords(numWords);

			while (!queue.isEmpty()) {
				WordEntry wordEntry = queue.poll();
				topWords.add(wordEntry.getWord());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return topWords;
	}
}
