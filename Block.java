import java.util.Map;
import java.util.List;

/**
* This class represents a generic block of the chain.
* @author	Matteo Loporchio, 491283
*/
public class Block {
	/**
	*
	*/
	public final int id;

	/**
	*	Hash of the previous block.
	*/
	private byte[] prev;

	/**
	*
	*/
	private byte[] indexHash;

	/**
	*
	*/
	private byte[] skipHash;

	/**
	*
	*/
	private MRTreeNode index;

	/**
	*
	*/
	private SkipListEntry[] skip;

	/**
	*
	*/
	private List<Point> content;

	/**
	*	This is the default constructor for the block.
	*	@param prev
	* @param
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
	*	Returns the hash value of the predecessor.
	*	@return the hash value of the previous block
	*/
	public byte[] getPrev() {
		return prev;
	}

	/**
	*	Returns the hash value of the MR-tree index for this block.
	*	@return the hash value of the MR-tree index
	*/
	public byte[] getIndexHash() {
		return indexHash;
	}

	/**
	*	Returns the hash value of the skip list for this block.
	*	@return the hash value of the skip list
	*/
	public byte[] getSkipHash() {
		return skipHash;
	}

	/**
	*	Returns the root node of the MR-tree index for this block.
	*	@return a reference to the root node of the MR-tree index
	*/
	public MRTreeNode getIndex() {
		return index;
	}

	/**
	*	Returns a reference to the skip list data structure.
	*	@return a reference to the skip list
	*/
	public SkipListEntry[] getSkip() {
		return skip;
	}

	/**
	*	Returns the list of points included in the block.
	*	@return a reference to the list of points included in the block
	*/
	public List<Point> getContent() {
		return content;
	}
}
