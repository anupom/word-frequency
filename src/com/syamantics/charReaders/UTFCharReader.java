package com.syamantics.charReaders;

/**
 * Provides easy API to read characters from UTF encoded text files.
 * 
 * @author asyam
 */
public class UTFCharReader extends AbstractCharReader {

	@Override
	public char next() {
		return buffer.getChar();
	}

	@Override
	public char get(int i) {
		return buffer.getChar(i);
	}
}
