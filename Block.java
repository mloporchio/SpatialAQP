/**
* File:		Block.java
* @author	Matteo Loporchio, 491283
*/

import java.util.Map;
import java.util.List;

public class Block {
	public final int id;
	private byte[] prev;
	private byte[] indexHash;
	private byte[] skipHash;
	private MRTreeNode index;
	private SkipListEntry[] skip;
	private List<Point> content;

	/**
	*	This is the default constructor for the block.
	*/
	public Block(byte[] prev, byte[] indexHash, byte[] skipHash,
	MRTreeNode index, SkipListEntry[] skip, List<Point> content, int id) {
		this.prev = prev;
		this.indexHash = indexHash;
		this.skipHash = skipHash;
		this.index = index;
		this.skip = skip;
		this.content = content;
		this.id = id;
	}

	/**
	*	@return the hash value of the previous block
	*/
	public byte[] getPrev() {
		return prev;
	}

	/**
	*	@return the hash value of the MR-tree index
	*/
	public byte[] getIndexHash() {
		return indexHash;
	}

	/**
	*	@return the hash value of the skip list
	*/
	public byte[] getSkipHash() {
		return skipHash;
	}

	/**
	*	@return a reference to the root node of the MR-tree index
	*/
	public MRTreeNode getIndex() {
		return index;
	}

	/**
	*	@return a reference to the skip list
	*/
	public SkipListEntry[] getSkip() {
		return skip;
	}

	/**
	*	@return a reference to the list of points included in the block
	*/
	public List<Point> getContent() {
		return content;
	}
}
