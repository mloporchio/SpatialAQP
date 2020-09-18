import java.util.*;

/**
* This program can be used to test the MR-tree index query and verification
* algorithms.
*
* To run it, the following parameters are required:
* <filename> <capacity> <xmin> <ymin> <xmax> <ymax>
*
* @author Matteo Loporchio, 491283
*/
public class TestQuery {
  public static void main(String[] args) {
    try {
      // Read the file name.
      String filename = args[0];
      // Read the page capacity from input.
      int c = Integer.parseInt(args[1]);
      // Read the vertices of the query rectangle.
      double xmin = Double.parseDouble(args[2]),
      ymin = Double.parseDouble(args[3]),
      xmax = Double.parseDouble(args[4]),
      ymax = Double.parseDouble(args[5]);
      Rectangle q = new Rectangle(xmin, ymin, xmax, ymax);
      // Read the records from a file.
      List<Point> records = Utility.readPointsB(filename);
      // Build an index for these records.
      long cstart = System.nanoTime();
      MRTreeNode T = MRTree.buildPacked(records, c);
      long cend = System.nanoTime();
      // Query the index.
      long qstart = System.nanoTime();
      VObject vo = Query.treeSearchIt(T, q);
      long qend = System.nanoTime();
      // Verify the results.
      long vstart = System.nanoTime();
      VResult vr = Query.verifyIt(vo);
      long vend = System.nanoTime();
      // Print the output information.
      System.out.println("Root hash: " +
      Hash.bytesToHex(T.getHash()));
      System.out.println("Reconstructed hash: " +
      Hash.bytesToHex(vr.getHash()));
      System.out.println("Index construction time: "
      + (cend - cstart) + " ns");
      System.out.println("Query time: " + (qend - qstart) + " ns");
      System.out.println("Verification time: "
      + (vend - vstart) + " ns");
      System.out.println("Returned records: " + vr.getContent().size());
      List<Point> matching = Query.filter(vr.getContent(), q);
      System.out.println("Matching records: " + matching.size());
    }
    catch (Exception e) {
      System.err.println("Something went wrong!");
      e.printStackTrace();
    }
  }
}
