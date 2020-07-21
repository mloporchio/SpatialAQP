/**
*  File:     Block.java
*  Author:   Matteo Loporchio, 491283
*/

import java.util.Map;
import java.util.List;

public class Block {
	public final byte[] prev;
	public final byte[] indexHash;
	public final byte[] skipHash;
	public final MRTree index;
	public final SkipListEntry[] skip;
	public final List<Point> content;
	public final int id;

	/**
	*	This is the default constructor for the block.
	*/
	public Block(byte[] prev, byte[] indexHash, byte[] skipHash,
	MRTree index, SkipListEntry[] skip, List<Point> content, int id) {
		this.prev = prev;
		this.indexHash = indexHash;
		this.skipHash = skipHash;
		this.index = index;
		this.skip = skip;
		this.content = content;
		this.id = id;
	}
}
