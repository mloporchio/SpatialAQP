/**
 *  File:     TestChain.java
 *  Author:   Matteo Loporchio, 491283
 *
 *  In this file we test the construction of blockchain by reading
 *  its content from a binary file.
 *  We measure the construction time in milliseconds.
 *
 *  Run this program with <filename> and <page capacity> as parameters.
 */

import java.util.*;

public class TestChain {
  public static void main(String[] args) {
    try {
      // Read the file name.
      String filename = args[0];
      // Read the page capacity from input.
      int c = Integer.parseInt(args[1]);
      // Build the blockchain.
      long tStart = System.nanoTime();
			Blockchain chain = Utility.readChainB(filename, c);
			long tEnd = System.nanoTime();
      System.out.println("Blocks read: " + chain.getSize());
      System.out.println("Index capacity: " + c);
      System.out.println("Elapsed time: " + (tEnd - tStart)/1000000 + " ms");
      // Get the last block of the chain.
      Block last = chain.getLastBlock();
      // Print its skip list.
      System.out.println(last.skip.toString());
    }
    catch (Exception e) {
      System.err.println("Something went wrong!");
      e.printStackTrace();
    }
  }
}
