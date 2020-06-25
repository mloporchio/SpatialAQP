/**
 *  File:     MRTreeNode.java
 *  Author:   Matteo Loporchio, 491283
 */

import java.util.*;

public class MRTreeNode implements Comparable<MRTreeNode> {
	public Rectangle MBR;
	public byte[] hash;
	public List<MRTreeNode> children; // Null if node is a leaf.
	public List<Point> data; // Not null if node is a leaf.

	// Creates an empty node.
	public MRTreeNode() {/* Nothing to do here! */}

	// Marks this node as a leaf.
	public void makeLeaf(List<Point> data) {
		this.MBR = Geometry.MBR(data);
		// The hash value of this node is the hash value of its points.
		this.hash = Hash.hashPoints(data);
		this.data = data;
		this.children = null;
	}

	// Marks this node as an internal one.
	public void makeInternal(List<MRTreeNode> children) {
		// take all the bounding rectangles of the children.
		List<Rectangle> rects = new ArrayList<Rectangle>();
		children.forEach((c) -> rects.add(c.MBR));
		this.MBR = Geometry.enlarge(rects);
		// Given its children, compute the hash of this node.
		this.hash = Hash.hashNode(children);
		this.children = children;
		this.data = null;
	}

	// This function returns true if and only if this node is a leaf.
	public boolean isLeaf() {
		return (this.children == null && this.data != null);
	}

	// To compare two nodes, we only need to compare their MBRs.
	public int compareTo(MRTreeNode n) {
		return this.MBR.compareTo(n.MBR);
	}
}
