/**
 *  File:     Query.java
 *  Author:   Matteo Loporchio, 491283
 */

 import java.util.*;

 public class Query {

   /**
    *   This function
    *
    */
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
 }
