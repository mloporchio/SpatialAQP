import java.util.List;

/**
*
* @author Matteo Loporchio, 491283
*/
public class VResult {
  /**
  *
  */
  private List<Point> content;

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
  public VResult() {
    this.content = null;
    this.MBR = null;
    this.hash = null;
  }

  /**
  *
  */
  public VResult(List<Point> content, Rectangle MBR, byte[] hash) {
    this.content = content;
    this.MBR = MBR;
    this.hash = hash;
  }

  /**
  *
  */
  public List<Point> getContent() {
    return content;
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
