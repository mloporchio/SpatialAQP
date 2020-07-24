/**
*
*
*/

public class VPruned extends VObject {

  /**
  *
  */
  private Rectangle MBR;

  /**
  *
  */
  private byte[] hash;

  /**
  *
  */
  public VPruned(Rectangle MBR, byte[] hash) {
    this.type = VObjectType.PRUNED;
    this.MBR = MBR;
    this.hash = hash;
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
  public byte[] getHash() {
    return hash;
  }
}
