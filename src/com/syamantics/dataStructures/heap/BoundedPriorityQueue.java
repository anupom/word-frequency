package com.syamantics.dataStructures.heap;

import java.util.PriorityQueue;

public class BoundedPriorityQueue<E extends Comparable<E>> extends
		PriorityQueue<E> {

	private static final long serialVersionUID = 1L;
	private int maxSize;

	public BoundedPriorityQueue(int maxSize) {
		super(maxSize);
		this.maxSize = maxSize;
	}

	@Override
	public boolean add(E element) {
		if (super.size() < maxSize) {
			return super.add(element);
		}

		if (super.peek().compareTo(element) < 0) {
			super.poll();
			return super.add(element);
		}

		return false;
	}
}
