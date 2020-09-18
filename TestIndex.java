import java.util.*;

/**
* This program tests the construction of a MR-tree index for
* a certain set of 2D records. We measure the construction time
* in nanoseconds.
*
* @author Matteo Loporchio, 491283
*/
public class TestIndex {
  public static void main(String[] args) {
    try {
      // Read the contents from file.
      List<Point> pts = Utility.readPointsB(args[0]);
      // Read the number of records.
      int nrec = pts.size();
      // Read the page capacity from input.
      int c = Integer.parseInt(args[1]);
      // Time the execution of the construction algorithm.
      long tStart = System.nanoTime();
      MRTreeNode T = MRTree.buildPacked(pts, c);
      long tEnd = System.nanoTime();
      System.out.println("Number of records: " + nrec);
      System.out.println("Index capacity: " + c);
      System.out.println("Elapsed time: " + (tEnd-tStart) + " ns");
    }
    catch (Exception e) {
      System.err.println("Something went wrong!");
    }
  }
}
