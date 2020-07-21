/**
*  File:     TestIndex.java
*  Author:   Matteo Loporchio, 491283
*
*  In this file we test the construction of a MR-tree index for
*  a certain set of 2D records. We measure the construction time
*  in milliseconds.
*/

import java.util.*;

public class TestIndex {
  public static void main(String[] args) {
    try {
      // Read the page capacity from input.
      int c = Integer.parseInt(args[0]);
      // Read the contents from a file.
      List<Point> pts = Utility.readPointsB("test/records.bin");
      // Read the number of records.
      int nrec = pts.size();
      // Time the execution of the construction algorithm.
      long tStart = System.nanoTime();
      MRTree T = MRTree.buildPacked(pts, c);
      long tEnd = System.nanoTime();
      System.out.println("Number of records: " + nrec);
      System.out.println("Index capacity: " + c);
      System.out.println("Elapsed time: " + (tEnd-tStart)/1000000 + " ms");
    }
    catch (Exception e) {
      System.err.println("Something went wrong!");
    }
  }
}
