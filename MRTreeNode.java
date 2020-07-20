/**
 *  File:     MRTreeNode.java
 *  Author:   Matteo Loporchio, 491283
 */

import java.util.*;

public class MRTreeNode implements Comparable<MRTreeNode> {
	private Rectangle MBR;
	private byte[] hash;
	private List<Point> data;
	private List<MRTreeNode> children;

	/**
	 *	This is the default constructor for the MR-tree node.
	 */
	public MRTreeNode(Rectangle MBR, byte[] hash, List<Point> data,
	List<MRTreeNode> children) {
		this.MBR = MBR;
		this.hash = hash;
		this.data = data;
		this.children = children;
	}

	/**
	 *	Returns the MBR of the node.
	 */
	public Rectangle getMBR() {
		return MBR;
	}

	/**
	 *	Returns the hash of the MBR of the node.
	 */
	public byte[] getHash() {
		return hash;
	}

	/**
	 *
	 */
	public List<Point> getData() {
		return data;
	}

	/**
	 *
	 */
	public List<MRTreeNode> getChildren() {
		return children;
	}

	/**
	 *	Returns true if and only if the current node is a leaf.
	 */
	public boolean isLeaf() {
		return (data != null && children == null);
	}

	/**
	 *	Comparison function.
	 */
	public int compareTo(MRTreeNode n) {
		return MBR.compareTo(n.MBR);
	}

	// Creates a new leaf node.
	public static MRTreeNode fromPoints(List<Point> data) {
		return new MRTreeNode(Geometry.MBR(data), Hash.hashPoints(data),
		data, null);
	}

	// Creates a new internal node.
	public static MRTreeNode fromChildren(List<MRTreeNode> children) {
		// Take all the bounding rectangles of the children.
		List<Rectangle> rects = new ArrayList<Rectangle>();
		children.forEach((c) -> rects.add(c.getMBR()));
		return new MRTreeNode(Geometry.enlarge(rects), Hash.hashNode(children),
		null, children);
	}
}
