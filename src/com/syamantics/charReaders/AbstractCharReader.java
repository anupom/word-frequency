package com.syamantics.charReaders;

import java.nio.MappedByteBuffer;

/**
 * Character readers provide easy API to read characters from text files.
 * AbstractCharReader is the base class that defines API for all concrete
 * character readers.
 * It also includes some common method implementation that can be shared
 * amongst all concrete Character reader implementations.
 * 
 * @author asyam
 */
public abstract class AbstractCharReader {
	
	protected MappedByteBuffer buffer;

	public AbstractCharReader init(MappedByteBuffer buffer) {
		this.buffer = buffer;
		return this;
	}

	public int limit() {
		return buffer.limit();
	}

	public boolean hasNext() {
		return buffer.position() < buffer.limit();
	}

	public abstract char next();

	public abstract char get(int i);
}
