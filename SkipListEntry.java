/**
* File:   SkipListEntry.java
* @author Matteo Loporchio, 491283
*/

import java.util.*;

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
  private byte[] MBRHash;

  /**
  *
  */
  private List<byte[]> digests;

  /**
  *
  */
  public SkipListEntry() {
    this.ref = null;
    this.MBR = Geometry.EMPTY_RECT;
    this.MBRHash = Hash.hashRectangle(this.MBR);
    this.digests = new ArrayList<byte[]>();
  }

  /**
  *
  */
  public SkipListEntry(byte[] ref, Rectangle MBR, byte[] MBRHash) {
    this.ref = ref;
    this.MBR = MBR;
    this.MBRHash = MBRHash;
    this.digests = new ArrayList<byte[]>();
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
  public byte[] getMBRHash() {
    return MBRHash;
  }

  /**
  *
  */
  public void setMBRHash(byte[] MBRHash) {
    this.MBRHash = MBRHash;
  }

}
