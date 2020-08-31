import java.util.*;

/**
* Run this program with the following parameters:
* <filename> <capacity> <xmin> <ymin> <xmax> <ymax>
* @author Matteo Loporchio, 491283
*/
public class TestQuery {
  public static void main(String[] args) {
    try {
      // Read the file name.
      String filename = args[0];
      // Read the page capacity from input.
      int c = Integer.parseInt(args[1]);
      //
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
      VObject vo = Query.treeSearchRec(T, q);
      long qend = System.nanoTime();
      // Verify the results.
      long vstart = System.nanoTime();
      VResult vr = Query.verify(vo);
      long vend = System.nanoTime();
      // Print the output information.
      System.out.println("Root hash :\t\t" +
      Hash.bytesToHex(T.getHash()));
      System.out.println("Reconstructed hash :\t" +
      Hash.bytesToHex(vr.getHash()));
      System.out.println("Index construction time: "
      + (cend - cstart) + " ns");
      System.out.println("Query time: " + (qend - qstart) + " ns");
      System.out.println("Verification time: "
      + (vend - vstart) + " ns");
      System.out.println("Matching records: " + vr.getContent().size());
    }
    catch (Exception e) {
      System.err.println("Something went wrong!");
      e.printStackTrace();
    }
  }
}
