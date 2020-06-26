/**
 *  File:     Block.java
 *  Author:   Matteo Loporchio, 491283
 */

import java.util.List;

public class Block {
	public BlockHeader header;
	public List<Point> content;
	public MRTree index;

	public Block(List<Point> content) {
		this.header = new BlockHeader();
		this.content = content;
		// Create the MR-tree index.
		this.index = new MRTree(this.content, Global.PAGE_CAPACITY);
		this.header.indexHash = this.index.root.hash;
	}
}
