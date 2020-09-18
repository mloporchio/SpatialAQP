/**
* This class is used as a wrapper for the results of the append function.
* @author Matteo Loporchio, 491283
*/
public class BlockRes {
  public final byte[] hash;
  public final long indexTime;
  public final long skipTime;

  public BlockRes(byte[] hash, long indexTime, long skipTime) {
    this.hash = hash;
    this.indexTime = indexTime;
    this.skipTime = skipTime;
  }
}
