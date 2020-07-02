/**
 *  File:     Block.java
 *  Author:   Matteo Loporchio, 491283
 */

import java.util.Map;
import java.util.List;

public class Block {
	public byte[] prev;
	public byte[] indexHash;
	public byte[] skipHash;
	public List<Point> content;
	public MRTree index;
	public SkipList skip;

	/**
	 *	This is the default constructor for the block.
	 */
	public Block(List<Point> content, int c) {
		this.prev = null;
		// Construct the MR-tree index.
		this.index = new MRTree(content, c);
		this.indexHash = this.index.root.hash;
		//
		this.content = content;
	}

	/**
	 *	This function initializes the skip list for the current block.
	 */
	public void initSkipList(Map<byte[], Block> storage) {
		this.skip = new SkipList(this, storage);
		this.skipHash = null;
		//this.skipHash = Hash.hashSkip(this.skip);
	}
}
