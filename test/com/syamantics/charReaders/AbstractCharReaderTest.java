package com.syamantics.charReaders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.MappedByteBuffer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ MappedByteBuffer.class, AbstractCharReader.class })
public class AbstractCharReaderTest {

	private AbstractCharReader charReader;
	private MappedByteBuffer buffer;

	@Before
	public void setUp() {
		buffer = PowerMockito.mock(MappedByteBuffer.class);
		when(buffer.position()).thenReturn(0).thenReturn(1);
		when(buffer.limit()).thenReturn(2);

		charReader = mock(AbstractCharReader.class, Mockito.CALLS_REAL_METHODS);
		charReader.init(buffer);
	}

	@Test
	public final void testLimit() {
		assertEquals(2, charReader.limit());
	}

	@Test
	public final void testHasNext() {
		assertTrue(charReader.hasNext());
	}

}
