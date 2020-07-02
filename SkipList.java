/**
 *  File:     SkipList.java
 *  Author:   Matteo Loporchio, 491283
 */

 import java.util.*;

public class SkipList {
  // The skip list is made up by several entries.
  public SkipListEntry[] entries;

  // Constructs the skip list for a given block.
  public SkipList(Block b, Map<byte[], Block> storage) {
    // Initialize the entries.
    entries = new SkipListEntry[Global.SKIP_LIST_SIZE];
    for (int i = 0; i < entries.length; i++) entries[i] = new SkipListEntry();
    // If this is the first block of the chain, we are done.
    if (b.prev == null) return;
    // Otherwise, we jump to the last inserted block of the chain.
    Block curr = storage.get(b.prev);
    // Again, if this is the last block of the chain we are done.
    if (curr.prev == null) return;
    // Otherwise the first entry will point to block i-2.
    entries[0].ref = curr.prev;
    // Now we must fill the other entries...
    for (int i = 1; i < entries.length; i++) {
      curr = storage.get(entries[i-1].ref);
      byte[] ref = curr.skip.entries[i-1].ref;
      if (ref == null) break;
      entries[i].ref = ref;
    }
	}

  // This function produces a string representation of the skip list.
  public String toString() {
    String s = "";
    for (int i = 0; i < entries.length; i++) {
      s += "Entry: " + i + "\tRef: " +
      ((entries[i].ref != null) ? Hash.bytesToHex(entries[i].ref) : "null")
      + "\n";
    }
    return s;
  }
}
