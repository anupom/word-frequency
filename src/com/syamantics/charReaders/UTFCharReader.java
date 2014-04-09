package com.syamantics.charReaders;

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
