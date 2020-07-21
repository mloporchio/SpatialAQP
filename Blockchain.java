/**
 *  File:     Blockchain.java
 *  Author:   Matteo Loporchio, 491283
 */

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Blockchain {
  // Page capacity for each block.
  private int capacity;
  // Skip list size for each block.
  private int skipsize;
  // We represent the blockchain with a hash map of blocks.
  // Each block is retrieved by means of its hash value.
  private Map<byte[], Block> storage;
  // This stores the hash value of the last block.
  private byte[] last;

  /**
  * This is the default constructor for the class.
  */
	public Blockchain(int c, int m) {
    capacity = c;
    skipsize = m;
    storage = new HashMap<byte[], Block>();
    last = null;
	}

  /**
  * Returns a reference to the last block in the chain.
  */
  public Block getLastBlock() {
    return storage.get(last);
  }

  /**
  * Returns a reference to the block with the given address.
  */
  public Block getBlock(byte[] ref) {
    return storage.get(ref);
  }

  /**
  * Returns the number of blocks that are currently inserted in the chain.
  */
  public int getSize() {
    return storage.size();
  }

  /**
  * This method creates a new block with the sepecified content
  * and appends it to the chain.
  */
  public byte[] append(List<Point> content) throws Exception {
    // Build the MR-tree index.
    MRTree index = MRTree.buildPacked(content, capacity);
		byte[] indexHash = index.getRoot().getHash();
    // Build the skip list index.
    SkipListEntry[] skip = SkipList.buildSkip(storage, last, skipsize);
    byte[] skipHash = null;
    // Create the new block and compute its hash.
    int id = getSize()+1;
    Block b = new Block(last, indexHash, skipHash, index, skip, content, id);
    byte[] h = Hash.hashBlock(b);
    // Update the blockchain.
		last = h;
		storage.put(h, b);
    // Return the hash of the new block.
    return h;
  }

}
