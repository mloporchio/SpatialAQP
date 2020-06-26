/**
 *  File:     MRTree.java
 *  Author:   Matteo Loporchio, 491283
 */

import java.util.*;

public class MRTree {
	public MRTreeNode root;
	public List<MRTreeNode> nodes;

	// This method constructs the MR-tree with the packed algorithm.
	public MRTree(List<Point> pts, int c) {
		// Create a new set of nodes.
		this.nodes = new ArrayList<MRTreeNode>();
		// This is the current working set.
		List<MRTreeNode> current = new ArrayList<MRTreeNode>();
		// Sort the points in ascending order.
		Collections.sort(pts);
		// Split the records into chunks of size c.
		// Then create a leaf node for each chunk.
		List<List<Point>> leafChunks = Utility.partition(pts, c);
		leafChunks.forEach((ck) -> {
			MRTreeNode n = new MRTreeNode();
			n.makeLeaf(ck);
			this.nodes.add(n);
		});
		// Add these new nodes to the current working set and start merging.
		current.addAll(this.nodes);
		while (current.size() > 1) {
			// Divide the list of current nodes into chunks.
			List<List<MRTreeNode>> chunks = Utility.partition(current, c);
			// For each chunk, merge all its nodes and create a new one.
			List<MRTreeNode> merged = new ArrayList<MRTreeNode>();
			chunks.forEach((ck) -> {
				MRTreeNode n = new MRTreeNode();
				n.makeInternal(ck);
				merged.add(n);
			});
			// Add the new nodes to the result.
			this.nodes.addAll(merged);
			// These nodes also become the new working set.
			current = merged;
			// Sort the current working set and repeat.
			Collections.sort(current);
		}
		// Set the root as the only node left.
		this.root = current.get(0);
	}
}
