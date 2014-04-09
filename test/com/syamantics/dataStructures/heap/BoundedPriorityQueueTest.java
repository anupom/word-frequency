package com.syamantics.dataStructures.heap;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BoundedPriorityQueueTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public final void testAdd() {
		BoundedPriorityQueue<Integer> queue = new BoundedPriorityQueue<>(4);
		queue.add(2);
		queue.add(4);
		queue.add(3);
		queue.add(7);
		queue.add(5);
		queue.add(1);

		assertEquals(3, queue.poll().intValue());
		assertEquals(4, queue.poll().intValue());
		assertEquals(5, queue.poll().intValue());
		assertEquals(7, queue.poll().intValue());

		exception.expect(NullPointerException.class);
		assertEquals(7, queue.poll().intValue());
	}
}
