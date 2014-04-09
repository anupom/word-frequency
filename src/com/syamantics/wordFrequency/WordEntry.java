package com.syamantics.wordFrequency;

public class WordEntry implements Comparable<WordEntry> {

	private String word;

	private int occurance;

	public WordEntry(String word, int occurance) {
		this.word = word;
		this.occurance = occurance;
	}

	public String getWord() {
		return word;
	}

	public int getOccurance() {
		return occurance;
	}

	@Override
	public int compareTo(WordEntry wordEntry) {
		if (this.occurance < wordEntry.occurance) {
			return -1;
		}

		if (this.occurance > wordEntry.occurance) {
			return 1;
		}

		return 0;
	}

	@Override
	public String toString() {
		return "WordEntry [word=" + word + ", occurance=" + occurance + "]";
	}
}
