/**
 *  File:     Blockchain.java
 *  Author:   Matteo Loporchio, 491283
 */

import java.util.Map;
import java.util.HashMap;

public class Blockchain {
  // This stores the hash value of the last block.
  private byte[] last;
  // We represent the blockchain with a hash map of blocks.
  // Each block is retrieved by means of its hash value.
  private Map<byte[], Block> storage;

  // This is the default constructor for the class.
	public Blockchain() {
    last = null;
    storage = new HashMap<byte[], Block>();
	}

  // Returns a reference to the last block in the chain.
  public Block getLastBlock() {
    return storage.get(last);
  }

  // Returns a reference to the block with the given address.
  public Block getBlock(byte[] ref) {
    return storage.get(ref);
  }

  // Returns the number of blocks that are currently inserted in the chain.
  public int getSize() {
    return storage.size();
  }

  // This appends a new block to the chain.
  public void append(Block b) throws Exception {
    // Make the new block point to the last one in the chain.
    b.prev = last;
    // Initialize the skip list for the new block.
    b.buildSkipList(storage);
    // Compute the hash of the new block.
    byte[] h = Hash.hashBlock(b);
    // Update the blockchain.
		last = h;
		storage.put(h, b);
  }
}
