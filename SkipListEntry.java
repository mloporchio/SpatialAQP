/**
 *  File:     SkipList.java
 *  Author:   Matteo Loporchio, 491283
 */

public class SkipListEntry {
  // Hash of the destination block.
  public byte[] ref;
  // MBR of all skipped blocks.
  public Rectangle MBR;
  // Hash of the MBR of all skipped blocks.
  public byte[] rectHash;

  // Default constructor.
  public SkipListEntry(byte[] ref) {
    this.ref = ref;
    this.MBR = new Rectangle(0,0,0,0);
    this.rectHash = Hash.hashRectangle(this.MBR);
  }
}
