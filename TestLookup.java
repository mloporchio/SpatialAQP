/**
* This program reads a blockchain from a file and executes the lookup
* algorithm on the corresponding data set to retrieve matching records.
*
* In order to run it, the following parameters are required:
*
*   <filename> <capacity> <entries> <xmin> <ymin> <xmax> <ymax>
*
* @author Matteo Loporchio, 491283
*/

public class TestLookup {
  public static void main(String[] args) {
    try {
      // Read the blockchain file name.
      String filename = args[0];
      // Read the page capacity and size of the skip list from input.
      int c = Integer.parseInt(args[1]),
      m = Integer.parseInt(args[2]);
      // Read the vertices of the query rectangle.
      double xmin = Double.parseDouble(args[3]),
      ymin = Double.parseDouble(args[4]),
      xmax = Double.parseDouble(args[5]),
      ymax = Double.parseDouble(args[6]);
      Rectangle q = new Rectangle(xmin, ymin, xmax, ymax);
      // Build the blockchain.
      BlockchainRes bres = Utility.readChainB(filename, c, m);
      // Time the execution of the lookup algorithm.
      long tStart = System.nanoTime();
      Pair<VObject,Integer> r = Query.lookup(bres.chain, q);
      long tEnd = System.nanoTime();
      System.out.println("Number of blocks: " + bres.chain.getSize());
      System.out.println("Visited blocks: " + r.getSecond());
      System.out.println("Elapsed time: " + (tEnd-tStart) + " ns");
    }
    catch (Exception e) {
      System.err.println("Something went wrong!");
      e.printStackTrace();
    }
  }
}
