/**
*  File:     TestQuery.java
*  Author:   Matteo Loporchio, 491283
*
*/

import java.util.*;

public class TestQuery {
  public static void main(String[] args) {
    try {
      // Read the file name.
      String filename = args[0];
      // Read the page capacity from input.
      int c = Integer.parseInt(args[1]);
      // Build the blockchain.
      Blockchain chain = Utility.readChainB(filename, c);
      // Build the query rectangle.
      Rectangle q = new Rectangle(0.5, 0.5, 1.0, 1.0);
      // Traverse the chain and scan each block.
      long tStart = System.nanoTime();
      Block curr = chain.getLastBlock();
      List<Point> result = new ArrayList<Point>();
      while (curr != null) {
        Query.treeSearchExt(curr.index.getRoot(), q, result);
        //Query.filterExt(curr.content, q, result);
        curr = chain.getBlock(curr.prev);
      }
      long tEnd = System.nanoTime();
      // Print the number of records found.
      System.out.println("Records found: " + result.size());
      System.out.println("Elapsed time: " + (tEnd - tStart)/1000000 + " ms");
    }
    catch (Exception e) {
      System.err.println("Something went wrong!");
      e.printStackTrace();
    }
  }
}
