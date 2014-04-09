package com.syamantics.dataStructures.heap;

import java.util.PriorityQueue;

/**
 * A bounded priority queue implementation. 
 * 
 * @author asyam
 */
public class BoundedPriorityQueue<E extends Comparable<E>> extends
		PriorityQueue<E> {

	private static final long serialVersionUID = 1L;
	private int maxSize;

	/**
	 * Queue will always bounded by maxSize, that is of size <= maxSize.
	 * 
	 * @param maxSize	maximum size of the queue 
	 */
	public BoundedPriorityQueue(int maxSize) {
		super(maxSize);
		this.maxSize = maxSize;
	}

	/**
	 * Removes the head of the queue and inserts a new value if new values
	 * is of higher priority.
	 * 
	 * @param element	element to add
	 * @see java.util.PriorityQueue#offer(java.lang.Object)
	 */
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
