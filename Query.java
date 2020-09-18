
import java.util.*;

/**
*
* @author Matteo Loporchio, 491283
*/
public final class Query {

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
  public static VObject treeSearch(MRTreeNode T, Rectangle query) {
    // If the node is a leaf, we construct a VO with all its points.
    if (T.isLeaf()) {
      return new VLeaf(T.getData());
    }
    // Otherwise, we need to check if the MBR of the node intersects
    // the query rectangle.
    // If this is not the case, then the subtree will not contain any
    // interesting record.
    if (!Geometry.intersect(T.getMBR(), query)) {
      return new VPruned(T.getMBR(), T.getHash());
    }
    // Otherwise, we need to explore recursively all subtrees rooted in
    // the current node.
    VContainer cont = new VContainer();
    T.getChildren().forEach((n) -> {
      VObject partial = treeSearch(n, query);
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
    Deque<Pair<MRTreeNode, VObject>> q = new ArrayDeque<>();
    q.add(new Pair<MRTreeNode, VObject>(T, null));
    VObject result = null;
    while (!q.isEmpty()) {
      Pair<MRTreeNode, VObject> curr = q.remove();
      MRTreeNode currNode = curr.getFirst();
      VObject parentVO = curr.getSecond();
      VObject currVO = null;
      // If the current node is a leaf, we construct a VO with all its points.
      if (currNode.isLeaf()) currVO = new VLeaf(currNode.getData());
      else {
        if (!Geometry.intersect(currNode.getMBR(), query))
          currVO = new VPruned(currNode.getMBR(), currNode.getHash());
        else {
          currVO = new VContainer();
          for (MRTreeNode n : currNode.getChildren())
            q.add(new Pair<MRTreeNode, VObject>(n, currVO));
        }
      }
      // If the current node has a parent,
      if (parentVO != null) ((VContainer) parentVO).append(currVO);
      else result = currVO;
    }
    return result;
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
    //
    Rectangle u = Geometry.enlarge(rects);
    byte[] hash = Hash.reconstruct(rects, hashes);
    return new VResult(records, u, hash);
  }


  /**
  *
  */
  public static VResult verifyIt(VObject vo) {
    VResult result = null;
    Stack<Pair<VObject, VObject>> s = new Stack<>();
    Map<VObject, Boolean> visited = new HashMap<>();
    Map<VObject, List<VResult>> content = new HashMap<>();
    s.push(new Pair<>(vo, null));
    visited.put(vo, false);
    while (!s.isEmpty()) {
      Pair<VObject, VObject> el = s.peek();
      VObject curr = el.getFirst();
      VObject parent = el.getSecond();
      // Check the nature of the current node.
      // If it is a container...
      if (curr instanceof VContainer) {
        // We check if this node has already been visited.
        // If this is not the case, we need to push its children
        // on the stack for exploration.
        if (!visited.get(curr)) {
          content.put(curr, new ArrayList<VResult>());
          VContainer cont = ((VContainer) curr);
          for (int i = cont.size()-1; i >= 0; i--) {
            VObject child = cont.get(i);
            s.push(new Pair<>(child, curr));
            visited.put(child, false);
          }
          visited.put(curr, true);
        }
        // On the other hand, we have to reconstruct.
        else {
          List<VResult> currContent = content.get(curr);
          List<Point> records = new ArrayList<Point>();
          List<Rectangle> rects = new ArrayList<Rectangle>();
          List<byte[]> hashes = new ArrayList<byte[]>();
          for (VResult r : currContent) {
            records.addAll(r.getContent());
            rects.add(r.getMBR());
            hashes.add(r.getHash());
          }
          Rectangle u = Geometry.enlarge(rects);
          byte[] h = Hash.reconstruct(rects, hashes);
          VResult partial = new VResult(records, u, h);
          if (parent != null) content.get(parent).add(partial);
          else result = partial;
          s.pop();
        }
      }
      // Otherwise we have reached a leaf or pruned node.
      else {
        VResult partial = null;
        // If the node is a leaf...
        if (curr instanceof VLeaf) {
          List<Point> records = ((VLeaf) curr).getRecords();
          partial = new VResult(records, Geometry.MBR(records),
          Hash.hashPoints(records));
        }
        // Otherwise it is a pruned node...
        else {
          VPruned pr = ((VPruned) curr);
          partial = new VResult(new ArrayList<Point>(), pr.getMBR(),
          pr.getHash());
        }
        if (parent != null) content.get(parent).add(partial);
        else result = partial;
        // In both cases, we pop the node from the stack.
        s.pop();
      }
    }
    return result;
  }



  /**
  *
  */
  public static VObject lookup(Blockchain b, Rectangle query) {
    VContainer result = new VContainer();
    byte[] curr = b.getLast();
    while (curr != null) {
      Block currBlock = b.getBlock(curr);
      Rectangle currRect = currBlock.getIndex().getMBR();
      SkipListEntry[] skip = currBlock.getSkip();
      SkipListEntry e = null;
      // We inspect the current block to find matching records.
      VObject currVO = treeSearchIt(currBlock.getIndex(), query);
      // Then we examine the skip list entries in reversed order.
      for (int j = skip.length - 1; j >= 0; j--) {
        // We skip all entries with a null reference.
        if (skip[j].getRef() == null) continue;
        // The search terminates if we find an entry whose MBR
        // does not intersect the query rectangle.
        if (!Geometry.intersect(currRect, query)) {
          e = skip[j];
          break;
        }
      }
      // At this point, we construct the verification object for the
      // current block and choose the next one to be examined.
      List<byte[]> entryHashes = new ArrayList<>();
      if (e != null) {
        for (int j = 0; j < skip.length; j++)
          if (skip[j] != e) entryHashes.add(Hash.hashSkipEntry(skip[j]));
        curr = e.getRef();
      }
      else {
        for (int j = 0; j < skip.length; j++)
          entryHashes.add(Hash.hashSkipEntry(skip[j]));
        curr = currBlock.getPrev();
      }
      // Update the content of the VO.
      result.append(new VBlock(currVO, e, entryHashes));
    }
    return result;
  }

}
