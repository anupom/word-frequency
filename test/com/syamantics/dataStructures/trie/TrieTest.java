package com.syamantics.dataStructures.trie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TrieTest {

	private Trie trie;
	private Trie.Node nodeA;
	private Trie.Node nodeN;
	private Trie.Node nodeT;

	@Before
	public void setUp() {
		trie = new Trie();
		nodeA = trie.getRoot().addChildren('a');
		nodeN = nodeA.addChildren('n');
		nodeN.markAsWord();
		// Marking node N as word again
		nodeN.markAsWord();
		nodeT = nodeA.addChildren('t');
		nodeT.markAsWord();
	}

	@Test
	public final void testGetRoot() {
		assertNotNull(trie.getRoot());
	}

	@Test
	public final void testTrieNodeGetChildren() {
		Trie.Node[] children = trie.getRoot().getChildren();
		// The first index is for character 'a' should not be null
		assertNotNull(children[0]);
		assertEquals(nodeA, children[0]);
	}

	@Test
	public final void testTrieNodeGetParent() {
		assertEquals(nodeA, nodeN.getParent());
	}

	@Test
	public final void testTrieNodeIsWord() {
		assertFalse(nodeA.isWord());
		assertTrue(nodeN.isWord());
		assertTrue(nodeT.isWord());
	}

	@Test
	public final void testTrieNodeGetCount() {
		assertEquals(0, nodeA.getCount());
		assertEquals(2, nodeN.getCount());
		assertEquals(1, nodeT.getCount());
	}

	@Test
	public final void testGetAllWordNodes() {
		List<Trie.Node> nodes = trie.getAllWordNodes();
		assertEquals(2, nodes.size());
		assertEquals(nodeN, nodes.get(0));
		assertEquals(nodeT, nodes.get(1));
	}

}
