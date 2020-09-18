/**
*
* @author Matteo Loporchio, 491283
*/
public class BlockchainRes {
  /**
  *
  */
  public final Blockchain chain;

  /**
  *
  */
  public final long indexTime;

  /**
  *
  */
  public final long skipTime;

  /**
  *
  */
  public BlockchainRes(Blockchain chain, long indexTime, long skipTime) {
    this.chain = chain;
    this.indexTime = indexTime;
    this.skipTime = skipTime;
  }
}
