package com.syamantics.dataStructures.trie;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Trie {

	private Node root;

	public Trie() {
		root = new Node();
	}

	public Node getRoot() {
		return root;
	}

	public List<Node> getAllWordNodes() {
		List<Node> words = new ArrayList<>();

		LinkedList<Node> nodesToVisit = new LinkedList<>();
		nodesToVisit.add(root);

		while (!nodesToVisit.isEmpty()) {
			Node current = nodesToVisit.poll();
			// Check current
			if (current.isWord()) {
				words.add(current);
			}

			Node[] children = current.getChildren();

			for (int i = 0; i < children.length; i++) {
				if (children[i] != null) {
					nodesToVisit.add(children[i]);
				}
			}
		}

		return words;
	}

	public static class Node {
		private Node parent;
		private Node[] children;
		private char ch;
		private int count;

		public Node() {
			children = new Node[26];
		}

		public Node(char ch, Node parent) {
			this();
			this.ch = ch;
			this.parent = parent;
		}

		public Node addChildren(char ch) {
			int charPos = ch - 'a';
			if (children[charPos] == null) {
				children[charPos] = new Node(ch, this);
			}

			return children[charPos];
		}

		public Node getParent() {
			return parent;
		}

		public Node[] getChildren() {
			return children;
		}

		public char getCharacter() {
			return ch;
		}

		public boolean isWord() {
			return count > 0;
		}

		public int getCount() {
			return count;
		}

		public void markAsWord() {
			count++;
		}

		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			Node node = this;

			// Ignore root node
			while (node.getParent() != null) {
				sb.insert(0, node.getCharacter());
				node = node.getParent();
			}

			return sb.toString();
		}
	}
}
