/**
 *  File:     Block.java
 *  Author:   Matteo Loporchio, 491283
 */

import java.util.Map;
import java.util.List;

public class Block {
	public BlockHeader header;
	public List<Point> content;
	public MRTree index;
	public SkipList skip;

	/**
	 *	This is the default constructor for the block.
	 */
	public Block(List<Point> content, int c) {
		this.header = new BlockHeader();
		this.content = content;
		// Construct the MR-tree index.
		this.index = new MRTree(this.content, c);
		this.header.indexHash = this.index.root.hash;
	}

	/**
	 *	This function computes the skip list for the current block
	 *	according to the current blockchain.
	 */
	public void updateSkipList(byte[] last, Map<byte[], Block> storage) {
		// Create the array of entries.
		SkipListEntry[] entries = new SkipListEntry[Global.SKIP_LIST_SIZE];
		// The first entry must refer to block i-2.
		/*
		Block curr = storage[this.header.prev];
		if (curr != null) entries[0] = new SkipListEntry(curr.header.prev);
		// Now we must fill the other entries...
		for (int i = 0; i < entries.length; i++) {
			curr = storage[curr.];
			byte[] ref =
			entries[i] = new SkipListEntry(ref);
		}
		*/
		// Write the result to the current block.
		this.skip = new SkipList(entries);
	}
}
