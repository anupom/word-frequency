package com.syamantics.wordFrequency;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class WordEntryTest {

	@Test
	public final void testCompareTo() {
		WordEntry entry1 = new WordEntry("word1", 1);
		WordEntry entry2 = new WordEntry("word2", 2);
		WordEntry entry3 = new WordEntry("word2", 3);
		WordEntry entry4 = new WordEntry("word4", 3);
		
		assertEquals(-1, entry1.compareTo(entry2));
		assertEquals(1, entry3.compareTo(entry2));
		assertEquals(0, entry4.compareTo(entry3));
	}
}
