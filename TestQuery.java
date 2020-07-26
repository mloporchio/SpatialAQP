/**
* File:   TestQuery.java
* Author: Matteo Loporchio, 491283
*/

import java.util.*;

public class TestQuery {
  public static void main(String[] args) {
    try {
      // Read the file name.
      String filename = args[0];
      // Read the page capacity from input.
      int c = Integer.parseInt(args[1]);

      // Read the records from a file.
      List<Point> records = Utility.readPointsB(filename);

      // Build an index for these records.
      long cstart = System.nanoTime();
      MRTreeNode T = MRTree.buildPacked(records, c);
      long cend = System.nanoTime();

      // Query the index.
      long qstart = System.nanoTime();
      VObject vo = Query.treeSearchRec(T, new Rectangle(0.5, 0.5, 1.0, 1.0));
      long qend = System.nanoTime();

      // Verify the results.
      long vstart = System.nanoTime();
      VResult vr = Query.verify(vo);
      long vend = System.nanoTime();

      System.out.println("Root hash =\t\t" +
      Hash.bytesToHex(T.getHash()));
      System.out.println("Reconstructed hash =\t" +
      Hash.bytesToHex(vr.getHash()));
      System.out.println("Index construction time: "
      + (cend - cstart)/1000000 + " ms");
      System.out.println("Query time: " + (qend - qstart)/1000000 + " ms");
      System.out.println("Verification time: "
      + (vend - vstart)/1000000 + " ms");
    }
    catch (Exception e) {
      System.err.println("Something went wrong!");
      e.printStackTrace();
    }
  }
}
