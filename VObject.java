/**
*
*
*/

public abstract class VObject {

  /**
  *
  */
  enum VObjectType {
    CONTAINER,
    PRUNED,
    LEAF
  }

  /**
  *
  */
  protected VObjectType type;

  /**
  *
  */
  public VObjectType getType() {
    return type;
  }
}
