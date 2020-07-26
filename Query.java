/**
* File:   Query.java
* Author: Matteo Loporchio, 491283
*/

import java.util.*;

public final class Query {

  /**
  * Given a list of points and a rectangle, returns all the points
  * that lie inside the rectangle.
  * @param pts list of points
  * @param query rectangle
  * @return list of points inside the rectangle
  */
  public static List<Point> filter(List<Point> pts, Rectangle query) {
    List<Point> result = new ArrayList<Point>();
    pts.forEach((p) -> {
      if (Geometry.contains(query, p)) result.add(p);
    });
    return result;
  }

  /**
  * Recursive lookup.
  */
  public static VObject treeSearchRec(MRTreeNode T, Rectangle query) {
    // If the node is a leaf, we construct a VO with all its points.
    if (T.isLeaf()) return new VLeaf(T.getData());
    // Otherwise, we need to check if the MBR of the node intersects
    // the query rectangle.
    // If this is not the case, then the subtree will not contain any
    // interesting record.
    if (!Geometry.intersect(T.getMBR(), query))
      return new VPruned(T.getMBR(), T.getHash());
    // Otherwise, we need to explore recursively all subtrees rooted in
    // the current node.
    VContainer cont = new VContainer();
    List<Point> result = new ArrayList<Point>();
    T.getChildren().forEach((n) -> {
      VObject partial = treeSearchRec(n, query);
      cont.append(partial);
    });
    return cont;
  }

  /**
  * Verification algorithm.
  */
  public static VResult verify(VObject vo) {
    // Reconstruct a leaf node.
    if (vo instanceof VLeaf) {
      List<Point> records = ((VLeaf) vo).getRecords();
      Rectangle MBR = Geometry.MBR(records);
      byte[] h = Hash.hashPoints(records);
      return new VResult(records, MBR, h);
    }
    // Reconstruct a pruned internal node.
    if (vo instanceof VPruned) {
      VPruned pr = ((VPruned) vo);
      return new VResult(new ArrayList<Point>(), pr.getMBR(), pr.getHash());
    }
    // Otherwise we must reconstruct a non-pruned internal node.
    // This node is represented by means of a VO container.
    List<Point> records = new ArrayList<Point>();
    List<Rectangle> rects = new ArrayList<Rectangle>();
    List<byte[]> hashes = new ArrayList<byte[]>();
    // Obtain the VO container.
    VContainer cont = (VContainer) vo;
    // Recursively examine each VO in the container.
    for (int i = 0; i < cont.size(); i++) {
      VResult partial = verify(cont.get(i));
      // Take all the matching records and add them to the result set.
      records.addAll(partial.getContent());
      // Collect all rectangles and hashes.
      rects.add(partial.getMBR());
      hashes.add(partial.getHash());
    }
    // Compute the union of all the MBRs of
    Rectangle u = Geometry.enlarge(rects);
    byte[] hash = Hash.reconstruct(rects, hashes);
    return new VResult(records, u, hash);
  }

}

/**

   public static List<Point> filter(List<Point> pts, Rectangle query) {
     List<Point> result = new ArrayList<Point>();
     pts.forEach((p) -> {
       if (Geometry.contains(query, p)) result.add(p);
     });
     return result;
   }

   public static void filterExt(List<Point> pts, Rectangle query,
   List<Point> result) {
     pts.forEach((p) -> {
       if (Geometry.contains(query, p)) result.add(p);
     });
   }

   // Recursive version of tree search.
   public static List<Point> treeSearchRec(MRTreeNode T, Rectangle query) {
   		// If the node is a leaf, we need to scan its data points.
   		if (T.isLeaf()) return filter(T.getData(), query);
   		// Otherwise, we need to check if the MBR of the node intersects
      // the query rectangle.
      // If this is not the case, then the subtree will not contain any
      // interesting record.
   		if (!Geometry.intersect(T.getMBR(), query))
        return new ArrayList<Point>();
      // Otherwise, we need to explore recursively all subtrees rooted in
      // the current node.
      List<Point> result = new ArrayList<Point>();
      T.getChildren().forEach((n) -> {
        List<Point> partial = treeSearchRec(n, query);
        result.addAll(partial);
      });
      return result;
   	}

    // Iterative version of the previous algorithm.
    // Check: https://www.baeldung.com/java-depth-first-search
    public static List<Point> treeSearchIt(MRTreeNode T, Rectangle query) {
      List<Point> result = new ArrayList<Point>();
      Stack<MRTreeNode> s = new Stack<MRTreeNode>();
      MRTreeNode curr = T;
      s.push(T);
      while (!s.isEmpty()) {
        curr = s.pop();
        // Check what kind of node we have reached.
        // If the node is a leaf, then we need to scan its data points.
        if (curr.isLeaf()) result.addAll(filter(curr.getData(), query));
        // Otherwise it is an internal node.
        else {
          // We only need to explore its children
          // if its MBR intersects the query rectangle.
          if (Geometry.intersect(curr.getMBR(), query))
            curr.getChildren().forEach((n) -> s.push(n));
        }
      }
      return result;
    }

    // Optimized version of the iterative algorithm.
    public static void treeSearchExt(MRTreeNode T, Rectangle query,
    List<Point> result) {
      Stack<MRTreeNode> s = new Stack<MRTreeNode>();
      MRTreeNode curr = T;
      s.push(T);
      while (!s.isEmpty()) {
        curr = s.pop();
        // Check what kind of node we have reached.
        // If the node is a leaf, then we need to scan its data points.
        if (curr.isLeaf()) {
          curr.getData().forEach((p) -> {
            if (Geometry.contains(query, p)) result.add(p);
          });
        }
        // Otherwise it is an internal node.
        else {
          // We only need to explore its children
          // if its MBR intersects the query rectangle.
          if (Geometry.intersect(curr.getMBR(), query))
            curr.getChildren().forEach((n) -> s.push(n));
        }
      }
    }
**/
