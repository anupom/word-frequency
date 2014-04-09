package com.syamantics.charReaders;

import java.nio.MappedByteBuffer;

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
