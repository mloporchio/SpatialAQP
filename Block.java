import java.util.Map;
import java.util.List;

/**
* This class represents a generic block of the chain.
*	For each block we store a reference to the previous one, along
*	with a list of records, the MR-tree index to retrieve them and
*	the skip list data structure that enables fast traversal of the chain.
* @author	Matteo Loporchio, 491283
*/
public class Block {
	/**
	*	This is an unique numeric identifier associated to the block.
	*	It is used for testing purposes only.
	*/
	public final int id;

	/**
	*	This is the hash of the previous block.
	*/
	private byte[] prev;

	/**
	*	This is the hash of the root node of the MR-tree index.
	*/
	private byte[] indexHash;

	/**
	*	This is the hash
	*/
	private byte[] skipHash;

	/**
	*	This is a reference to the root of the MR-tree index.
	*/
	private MRTreeNode index;

	/**
	*	This is a reference to the skip list.
	*/
	private SkipListEntry[] skip;

	/**
	*	This is a reference to the list of points included in the block.
	*/
	private List<Point> content;

	/**
	*	This is the default constructor for the block.
	*	@param prev hash of the previous block
	* @param indexHash hash of the MR-tree
	*	@param skipHash hash of the skip list
	*	@param indexHash hash of the root node of the MR-tree
	*	@param index reference to the MR-tree root
	*	@param skip reference to the skip list
	*	@param content list of points to be included in the block
	*	@param id unique identifier for the block
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
