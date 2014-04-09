package com.syamantics.charReaders;

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
