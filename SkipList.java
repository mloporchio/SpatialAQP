/**
 *  File:     SkipList.java
 *  Author:   Matteo Loporchio, 491283
 */

public class SkipList {
  // The skip list is made up by several entries.
  public SkipListEntry[] entries;

  // Constructs the skip list for a given block.
  public SkipList(SkipListEntry[] entries) {
    this.entries = entries;
  }
}
