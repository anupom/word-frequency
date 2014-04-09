package com.syamantics.charReaders;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.nio.MappedByteBuffer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ MappedByteBuffer.class, ASCIICharReader.class })
public class UTFCharReaderTest {

	private UTFCharReader charReader;
	private MappedByteBuffer buffer;

	@Before
	public void setUp() {
		buffer = PowerMockito.mock(MappedByteBuffer.class);
		when(buffer.position()).thenReturn(0).thenReturn(1);
		when(buffer.limit()).thenReturn(2);
		when(buffer.getChar()).thenReturn('a').thenReturn('b');
		when(buffer.getChar(0)).thenReturn('a');
		when(buffer.getChar(1)).thenReturn('b');

		charReader = new UTFCharReader();
		charReader.init(buffer);
	}

	@Test
	public final void testNext() {
		assertEquals('a', charReader.next());
		assertEquals('b', charReader.next());
	}

	@Test
	public final void testGet() {
		assertEquals('a', charReader.get(0));
		assertEquals('b', charReader.get(1));
	}

}
