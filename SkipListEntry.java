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
  * List
  */
  private List<byte[]> hashes;

  /**
  *
  */
  private byte[] aggHash;

  /**
  *
  */
  public SkipListEntry() {
    this.ref = null;
    this.MBR = Geometry.EMPTY_RECT;
    this.MBRHash = Hash.hashRectangle(this.MBR);
    this.hashes = new ArrayList<byte[]>();
    this.aggHash = null;
  }

  /**
  * Returns the hash of the target block.
  * @return hash of the target block
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
    this.MBRHash = Hash.hashRectangle(MBR);
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
  public List<byte[]> duplicateHashes() {
    return new ArrayList<byte[]>(hashes);
  }

  /**
  *
  */
  public void setHashes(List<byte[]> hashes) {
    this.hashes = hashes;
    this.aggHash = Hash.aggregate(hashes);
  }

  /**
  *
  */
  public byte[] getAggHash() {
    return aggHash;
  }



}
