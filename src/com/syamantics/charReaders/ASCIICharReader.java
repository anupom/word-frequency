package com.syamantics.charReaders;

/**
 * Provides easy API to read characters from ASCII encoded text files.
 * 
 * @author asyam
 */
public class ASCIICharReader extends AbstractCharReader {
	@Override
	public char next() {
		return (char) buffer.get();
	}

	@Override
	public char get(int i) {
		return (char) buffer.get(i);
	}
}
