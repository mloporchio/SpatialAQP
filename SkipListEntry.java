/**
* File:   SkipListEntry.java
* Author: Matteo Loporchio, 491283
*/

public class SkipListEntry {
  /**
  * Hash of the destination block.
  */
  private byte[] ref;

  /**
  * MBR of all skipped blocks.
  */
  private Rectangle MBR;

  /**
  * Hash of the MBR of all skipped blocks.
  */
  private byte[] rectHash;

  /**
  *
  */
  public SkipListEntry() {
    this.ref = null;
    this.MBR = null;
    this.rectHash = null;
  }

  /**
  *
  */
  public SkipListEntry(byte[] ref, Rectangle MBR, byte[] rectHash) {
    this.ref = ref;
    this.MBR = MBR;
    this.rectHash = rectHash;
  }

  /**
  *
  */
  public byte[] getRef() {
    return ref;
  }

  /**
  *
  */
  public void setRef(byte[] ref) {
    this.ref = ref;
  }

  /**
  *
  */
  public Rectangle getMBR() {
    return MBR;
  }

  /**
  *
  */
  public void setMBR(Rectangle MBR) {
    this.MBR = MBR;
  }

  /**
  *
  */
  public byte[] getRectHash() {
    return rectHash;
  }

  /**
  *
  */
  public void setRectHash(byte[] rectHash) {
    this.rectHash = rectHash;
  }

}
