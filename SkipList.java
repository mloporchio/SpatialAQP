/**
* File:   SkipList.java
* Author: Matteo Loporchio, 491283
*/

import java.util.Map;

public final class SkipList {

  /**
  * 
  */
  public static SkipListEntry[] buildSkip(Map<byte[], Block> storage,
  byte[] last, int m) {
    // Create a new array of m entries.
    SkipListEntry[] skip = new SkipListEntry[m];
    for (int i = 1; i < skip.length; i++) skip[i] = new SkipListEntry();
    // If this is the first block we are inserting, then we are done.
    if (last == null) return skip;
    // Otherwise, we jump to the last block of the chain.
    Block curr = storage.get(last);
    // Again, if this is the last block of the chain we are done.
    if (curr.prev == null) return skip;
    // Otherwise the first entry will point to block i-2.
    skip[0].setRef(curr.prev);
    // Now we must fill the other entries...
    for (int i = 1; i < skip.length; i++) {
      curr = storage.get(skip[i-1].getRef());
      byte[] ref = curr.skip[i-1].getRef();
      if (ref == null) break;
      skip[i].setRef(ref);
    }
    return skip;
  }
}
