/**
 *  File:     Utility.java
 *  Author:   Matteo Loporchio, 491283
 */

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Utility {

  /**
   *  This function reads a text file containing one 2D point for each line
   *  and returns a list of these points.
   */
  public static List<Point> readPoints(String filename) throws Exception {
    // Read all the lines from the file.
    Path path = Paths.get(filename);
    List<String> lines = Files.readAllLines(path);
    List<Point> pts = new ArrayList<Point>();
    lines.forEach((s) -> {
      String[] p = s.split(",");
      double x = Double.parseDouble(p[0]), y = Double.parseDouble(p[1]);
      pts.add(new Point(x, y));
    });
    return pts;
  }

  /**
   *  This function reads a binary file containing 2D points and returns
   *  a list that contains them.
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
   *  This function creates a new blockchain by reading its content
   *  from a given text file.
   */
  public static Blockchain readFromFile(String filename) throws Exception {
    // Read all the lines from the file.
    Path path = Paths.get(filename);
    List<String> lines = Files.readAllLines(path);
    // Create a new blockchain object.
    Blockchain chain = new Blockchain();
    // The first line of the file contains the number of blocks.
    int nblocks = Integer.parseInt(lines.get(0));
    int i = 1;
    while (i < lines.size()) {
        // Read the number of records in the current block.
        int nrec = Integer.parseInt(lines.get(i));
        // Now collect all records and insert them into a list.
        List<Point> records = new ArrayList<Point>();
        for (int j = i+1; j<i+nrec+1; j++) {
          String[] s = lines.get(j).split(",");
          double x = Double.parseDouble(s[0]), y = Double.parseDouble(s[1]);
    			records.add(new Point(x, y));
        }
    		chain.append(new Block(records));
    		i += nrec + 1;
    }
    return chain;
  }

  // chops a list into non-view sublists of length L
  public static <T> List<List<T>> partition(List<T> l, int k) {
    List<List<T>> parts = new ArrayList<List<T>>();
    int N = l.size();
    for (int i = 0; i < N; i += k) {
      parts.add(new ArrayList<T>(l.subList(i, Math.min(N, i + k))));
    }
    return parts;
  }

}
