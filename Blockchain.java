/**
* File:		Blockchain.java
* Author:	Matteo Loporchio, 491283
*/

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Blockchain {
  // Page capacity for each block.
  private int c;
  // Skip list size for each block.
  private int m;
  // We represent the blockchain with a hash map of blocks.
  // Each block is retrieved by means of its hash value.
  private Map<byte[], Block> storage;
  // This stores the hash value of the last block.
  private byte[] last;

  /**
  * This is the default constructor for the class.
  */
	public Blockchain(int c, int m) {
    this.m = c;
    this.m = m;
    this.storage = new HashMap<byte[], Block>();
    this.last = null;
	}

  /**
  * @return a reference to the last block in the chain.
  */
  public Block getLastBlock() {
    if (last == null) return null;
    return storage.get(last);
  }

  /**
  * @return a reference to the block with the given address.
  */
  public Block getBlock(byte[] ref) {
    return storage.get(ref);
  }

  /**
  * @return the number of blocks that are currently inserted in the chain.
  */
  public int getSize() {
    return storage.size();
  }

  /**
  * This method creates a new block with the sepecified content
  * and appends it to the chain.
  * @return the hash value of the new block
  */
  public byte[] append(List<Point> content) throws Exception {
    // Build the MR-tree index.
    MRTreeNode index = MRTree.buildPacked(content, c);
		byte[] indexHash = index.getHash();
    // Build the skip list index.
    System.out.println("Building skip for block " + getSize());
    SkipListEntry[] skip = SkipList.buildSkip(this, m);
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
