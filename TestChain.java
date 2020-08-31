import java.util.*;

/**
* With this class we test the construction of blockchain by reading
* its content from a binary file.
* We measure the construction time in milliseconds.
*
* Run this program with <filename> and <page capacity> as parameters.
*
* @author Matteo Loporchio, 491283
*/
public class TestChain {
  public static void main(String[] args) {
    try {
      // Read the file name.
      String filename = args[0];
      // Read the page capacity and size of the skip list from input.
      int c = Integer.parseInt(args[1]),
      m = Integer.parseInt(args[2]);
      // Build the blockchain.
      long tStart = System.nanoTime();
      Blockchain chain = Utility.readChainB(filename, c, m);
      long tEnd = System.nanoTime();
      System.out.println("Blocks read: " + chain.getSize());
      System.out.println("Index capacity: " + c);
      System.out.println("Skip list size: " + m);
      System.out.println("Elapsed time: " + (tEnd - tStart) + " ns");
      // Get the last block of the chain and print its skip list.
      Block last = chain.getBlock(chain.getLast());
      SkipListEntry[] skip = last.getSkip();
      for (int i = 0; i < skip.length; i++) {
        byte[] ref = skip[i].getRef();
        System.out.println("Entry: " + i + "\tId: " +
        ((ref != null) ? chain.getBlock(ref).id : "(null)"));
      }
    }
    catch (Exception e) {
      System.err.println("Something went wrong!");
      e.printStackTrace();
    }
  }
}
