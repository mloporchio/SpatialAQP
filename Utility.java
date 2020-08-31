import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
* This class contains a set of static utility methods
* @author	Matteo Loporchio, 491283
*/
public final class Utility {

  /**
  * This function reads a binary file containing 2D points and returns
  * a list that contains them.
  * @param filename path of the input file
  * @return a list of points
  */
  public static List<Point> readPointsB(String filename) throws Exception {
    Path path = Paths.get(filename);
    byte[] content = Files.readAllBytes(path);
    DoubleBuffer buf = ByteBuffer.wrap(content).asDoubleBuffer();
    List<Point> pts = new ArrayList<Point>();
    while (buf.hasRemaining()) {
      double x = buf.get(), y = buf.get();
      pts.add(new Point(x, y));
    }
    return pts;
  }

  /**
  * This function creates a new blockchain by reading its content
  * from a given binary file. The index inside each block is built
  * with a page capacity equal to c.
  * @param filename path of the input file
  * @param c page capacity of the block index
  * @param m size of the skip list
  * @return a blockchain object
  */
  public static Blockchain readChainB(String filename, int c, int m)
  throws Exception {
    Path path = Paths.get(filename);
    byte[] content = Files.readAllBytes(path);
    ByteBuffer buf = ByteBuffer.wrap(content);
    // Create a new blockchain object.
    Blockchain chain = new Blockchain(c, m);
    // Read the number of blocks.
    int nblocks = buf.getInt();
    // Now, for each block...
    for (int i = 0; i < nblocks; i++) {
      // Read the number of records it contains.
      int nrec = buf.getInt();
      // Now collect all records and insert them into a list.
      List<Point> records = new ArrayList<Point>();
      for (int j = 0; j < nrec; j++) {
        double x = buf.getDouble(), y = buf.getDouble();
        records.add(new Point(x, y));
      }
      // Create a new block and append it to the chain.
      chain.append(records);
    }
    return chain;
  }

  /**
  * This function chops a list into sublists of a given length.
  * @param l the list to be divided
  * @param k the number of elements in each sublist
  * @return a list containing all the sublists
  */
  public static <T> List<List<T>> split(List<T> l, int k) {
    List<List<T>> parts = new ArrayList<List<T>>();
    for (int i = 0; i < l.size(); i += k) {
      //parts.add(new ArrayList<T>(l.subList(i, Math.min(l.size(), i + k))));
      parts.add(l.subList(i, Math.min(l.size(), i + k)));
    }
    return parts;
  }
}
