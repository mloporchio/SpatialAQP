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
	public SkipListEntry[] skip;
	// The block ID is only for debugging purposes.
	public static int counter = 0;
	public int id;

	/**
	 *	This is the default constructor for the block.
	 */
	public Block(List<Point> pts, int c) {
		prev = null;
		// Construct the MR-tree index.
		index = new MRTree(pts, c);
		indexHash = index.root.hash;
		// Copy a reference to the content.
		content = pts;
		// Assign a unique ID to each block.
		id = counter++;
	}

	/**
	 *	This function initializes the skip list for the current block.
	 */
	public void buildSkipList(Map<byte[], Block> storage) {
		// Initialize the skip list with null entries.
    skip = new SkipListEntry[Global.SKIP_LIST_SIZE];
    for (int i = 0; i < skip.length; i++) skip[i] = new SkipListEntry();
    // If this is the first block of the chain, we are done.
    if (prev == null) return;
    // Otherwise, we jump to the last inserted block of the chain.
    Block curr = storage.get(prev);
    // Again, if this is the last block of the chain we are done.
    if (curr.prev == null) return;
    // Otherwise the first entry will point to block i-2.
    skip[0].ref = curr.prev;
    // Now we must fill the other entries...
    for (int i = 1; i < skip.length; i++) {
      curr = storage.get(skip[i-1].ref);
      byte[] ref = curr.skip[i-1].ref;
      if (ref == null) break;
      skip[i].ref = ref;
    }
		// Compute the hash of the skip list.
		skipHash = null; // = Hash.hashSkip(skip);
	}
}
