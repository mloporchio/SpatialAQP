import java.util.List;

/**
*
* @author Matteo Loporchio, 491283
*/
public class VBlock implements VObject {
  /**
  *
  */
  private VObject block;

  /**
  *
  */
  private SkipListEntry entry;

  /**
  *
  */
  private List<byte[]> entryHashes;

  /**
  *
  */
  public VBlock(VObject block, SkipListEntry entry, List<byte[]> entryHashes) {
    this.block = block;
    this.entry = entry;
    this.entryHashes = entryHashes;
  }

}
