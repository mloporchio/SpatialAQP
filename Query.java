/**
* File:   Query.java
* Author: Matteo Loporchio, 491283
*/

import java.util.*;

public final class Query {

  /**
  *
  */
  private static class MRQueueEntry {
    private MRTreeNode node;
    private VObject parentVO;

    public MRQueueEntry(MRTreeNode node, VObject parentVO) {
      this.node = node;
      this.parentVO = parentVO;
    }

    public MRTreeNode getNode() {
      return node;
    }

    public VObject getParentVO() {
      return parentVO;
    }
  }

  /**
  * Given a list of points and a rectangle, this method
  * returns all points inside the rectangle.
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
  * This method can be used to query the MR-tree index in order
  * to retrieve all points that belong to the query rectangle.
  * The search process is recursive.
  * @param T the root of the MR-tree
  * @param query the query rectangle
  * @return a VO for the root
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
    T.getChildren().forEach((n) -> {
      VObject partial = treeSearchRec(n, query);
      cont.append(partial);
    });
    return cont;
  }

  /**
  * This method can be used to query the MR-tree index in order
  * to retrieve all points that belong to the query rectangle.
  * The search process is iterative.
  * @param T the root of the MR-tree
  * @param query the query rectangle
  * @return a VO for the root
  */
  public static VObject treeSearchIt(MRTreeNode T, Rectangle query) {
    Deque<MRQueueEntry> q = new ArrayDeque<>();
    q.add(new MRQueueEntry(T, null));
    VObject result = null;
    while (!q.isEmpty()) {
      MRQueueEntry curr = q.remove();
      MRTreeNode currNode = curr.getNode();
      VObject parentVO = curr.getParentVO();
      VObject currVO = null;
      // If the current node is a leaf, we construct a VO with all its points.
      if (currNode.isLeaf()) currVO = new VLeaf(currNode.getData());
      else {
        if (!Geometry.intersect(currNode.getMBR(), query))
          currVO = new VPruned(currNode.getMBR(), currNode.getHash());
        else {
          currVO = new VContainer();
          for (MRTreeNode n : currNode.getChildren())
            q.add(new MRQueueEntry(n, currVO));
        }
      }
      // If the current node has a parent,
      if (parentVO != null) ((VContainer) parentVO).append(currVO);
      else result = currVO;
    }
    return result;
  }


  /**
  * Chain search algorithm.
  */
  /*
  public static VObject chainSearch(Blockchain b, Rectangle query) {
    VObject result = null;
    byte[] currHash = b.getLastHash();
    while (currHash != null) {
      Block currBlock = b.getBlock(currHash);
      Rectangle currRect = currBlock.getIndex().getMBR();

      //
      currHash = currBlock.getPrev();
    }
    return result;
  }*/

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
