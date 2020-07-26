/**
* File:   SkipList.java
* Author: Matteo Loporchio, 491283
*/

import java.util.*;

public final class SkipList {

  /**
  * This represents the default skip list size for each block.
  */
  public static final int DEFAULT_SIZE = 8;

  /**
  * This method builds the skip list for a new block.
  * @param b the blockchain
  * @param m number of entries for the skip list
  */
  public static SkipListEntry[] buildSkip(Blockchain b, int m) {
    // Create a new array of m entries.
    SkipListEntry[] skip = new SkipListEntry[m];
    for (int i = 0; i < skip.length; i++) skip[i] = new SkipListEntry();
    // Check the last inserted block.
    Block curr = b.getLastBlock();
    // If the reference is null, the chain is currently empty and
    // there is nothing to compute for the skip list of the next block.
    if (curr == null) return skip;
    // Otherwise,
    skip[0].setMBR(curr.getIndex().getMBR());
    // If the last block of the chain is also the only one
    if (curr.getPrev() == null) return skip;
    // Otherwise the first entry will point to block i-2.
    skip[0].setRef(curr.getPrev());
    // Now we must fill the other entries...
    for (int i = 1; i < skip.length; i++) {
      curr = b.getBlock(skip[i-1].getRef());
      SkipListEntry[] currSkip = curr.getSkip();
      byte[] ref = currSkip[i-1].getRef();
      if (ref == null) break;
      // Set the reference.
      skip[i].setRef(ref);
      // Compute the MBR for all skipped blocks.
      Rectangle union = Geometry.enlarge(Arrays.asList(skip[i-1].getMBR(),
      curr.getIndex().getMBR(), currSkip[i-1].getMBR()));
      skip[i].setMBR(union);
      skip[i].setMBRHash(Hash.hashRectangle(union));
    }
    return skip;
  }
}
