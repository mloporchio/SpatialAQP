/**
* File:		MRTree.java
* Author:	Matteo Loporchio, 491283
*/

import java.util.*;

public final class MRTree {

  /**
  * This value is the default page capacity for the MR-tree.
  */
  public static final int DEFAULT_CAPACITY = 20;

  /**
  *
  */
  public static MRTreeNode buildPacked(List<Point> pts, int c) {
    List<MRTreeNode> current = new ArrayList<MRTreeNode>();
    // Sort the points in ascending order.
    Collections.sort(pts);
    // Split the records into chunks of size c.
    // Then create a leaf node for each chunk.
    List<List<Point>> leaves = Utility.split(pts, c);
    for (List<Point> ck : leaves) current.add(MRTreeNode.nodeFromPoints(ck));
    // Start merging.
    while (current.size() > 1) {
      // Divide the list of current nodes into chunks.
      List<List<MRTreeNode>> chunks = Utility.split(current, c);
      // For each chunk, merge all its nodes and create a new one.
      List<MRTreeNode> merged = new ArrayList<MRTreeNode>();
      for (List<MRTreeNode> ck : chunks)
        merged.add(MRTreeNode.nodeFromChildren(ck));
      // These nodes become the new working set.
      current = merged;
    }
    // Return the root of the MR-tree, i.e. the only node left.
    return current.get(0);
  }

}
