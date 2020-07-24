/**
* File:		MRTree.java
* Author:	Matteo Loporchio, 491283
*/

import java.util.*;

public class MRTree {
	private MRTreeNode root;
	private List<MRTreeNode> nodes;

	/**
	 *	This is the default constructor of the MRTree data structure.
	 */
	public MRTree(MRTreeNode root, List<MRTreeNode> nodes) {
		this.root = root;
		this.nodes = nodes;
	}

	/**
	 *
	 */
	public static MRTree buildPacked(List<Point> pts, int c) {
		//
		List<MRTreeNode> nodes = new ArrayList<MRTreeNode>(),
		current = new ArrayList<MRTreeNode>();
		// Sort the points in ascending order.
		Collections.sort(pts);
		// Split the records into chunks of size c.
		// Then create a leaf node for each chunk.
		List<List<Point>> leafChunks = Utility.partition(pts, c);
		leafChunks.forEach((ck) -> {nodes.add(MRTreeNode.fromPoints(ck));});
		// Add these new nodes to the current working set and start merging.
		current.addAll(nodes);
		while (current.size() > 1) {
			// Divide the list of current nodes into chunks.
			List<List<MRTreeNode>> chunks = Utility.partition(current, c);
			// For each chunk, merge all its nodes and create a new one.
			List<MRTreeNode> merged = new ArrayList<MRTreeNode>();
			chunks.forEach((ck) -> {merged.add(MRTreeNode.fromChildren(ck));});
			// Add the new nodes to the result.
			nodes.addAll(merged);
			// These nodes also become the new working set.
			current = merged;
			// Sort the current working set and repeat.
			Collections.sort(current);
		}
		// Create a new MR-tree whose root is the only node left.
		return new MRTree(current.get(0), nodes);
	}

	/**
	 *	This method returns a reference to the root of the tree.
	 */
	public MRTreeNode getRoot() {
		return root;
	}

}
