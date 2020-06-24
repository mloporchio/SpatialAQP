/**
 *  File:     Blockchain.java
 *  Author:   Matteo Loporchio, 491283
 */

import java.util.HashMap;

public class Blockchain {
  // This stores the hash value of the last block.
  private byte[] last;
  // We represent the blockchain with a hash map of blocks.
  // Each block is retrieved by means of its hash value.
  private HashMap<byte[], Block> storage;

  // This is the default constructor for the class.
	public Blockchain() {
    this.last = new byte[32]; // Should be automatically filled with zeros.
    this.storage = new HashMap<byte[], Block>();
	}

  // Returns the hash of the last block in the chain.
  public byte[] getLast() {
    return this.last;
  }

  // This appends a new block to the chain.
  public void append(Block b) throws Exception {
    // Make the new block point to the last one in the chain.
    b.header.prev = this.last;
    // Compute the hash of the new block.
    byte[] h = Hash.hashBlock(b);
    // Update the blockchain.
		this.last = h;
		this.storage.put(h, b);
  }
}
