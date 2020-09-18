import java.util.*;

/**
* This class represents an entry of a skip list.
* In each entry we store information that is exploited to speed up
* the search along the entire blockchain, while guaranteeing the
* authenticity of the results.
*
* @author Matteo Loporchio, 491283
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
  * Aggregate hash of all skipped blocks.
  */
  private byte[] aggHash;

  /**
  * This is the default constructor for the skip list entry.
  */
  public SkipListEntry() {
    this.ref = null;
    this.MBR = Geometry.EMPTY_RECT;
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
  }

  /**
  *
  */
  public byte[] getAggHash() {
    return aggHash;
  }

  /**
  *
  */
  public void setAggHash(byte[] aggHash) {
    this.aggHash = aggHash;
  }

}
