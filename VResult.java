/**
* File:   VResult.java
* Author: Matteo Loporchio, 491283
*/

import java.util.List;

/**
*
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
