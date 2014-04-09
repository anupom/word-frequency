package com.syamantics.wordFrequency.strategies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Queue;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.syamantics.charReaders.AbstractCharReader;
import com.syamantics.wordFrequency.WordEntry;

public class HashMapConcurentStrategyTest {

	private static final String TEST_STRING = "the ant an ant a ant the man the dog the b c a";

	@Test
	public final void test() {
		AbstractCharReader charReader = mock(AbstractCharReader.class);
		when(charReader.limit()).thenReturn(TEST_STRING.length());
		when(charReader.get(anyInt())).thenAnswer(new Answer<Character>() {
			int index;

			@Override
			public Character answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				index = (int) args[0];
				return TEST_STRING.charAt(index);
			}
		});

		HashMapConcurrentStrategy strategy = new HashMapConcurrentStrategy();
		strategy.updateCount(charReader);
		Queue<WordEntry> queue = strategy.getTopWords(3);

		WordEntry we = queue.poll();
		assertEquals("a", we.getWord());
		assertEquals(2, we.getOccurance());
		we = queue.poll();
		assertEquals("ant", we.getWord());
		assertEquals(3, we.getOccurance());
		we = queue.poll();
		assertEquals("the", we.getWord());
		assertEquals(4, we.getOccurance());
		assertNull(queue.poll());
	}
}
