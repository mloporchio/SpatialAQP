/**
* File:		MRTreeNode.java
* @author	Matteo Loporchio, 491283
*/

import java.util.*;

public class MRTreeNode implements Comparable<MRTreeNode> {
	private Rectangle MBR;
	private byte[] hash;
	private List<Point> data;
	private List<MRTreeNode> children;

	/**
	*	Constructor for the MR-tree node.
	*	@param MBR
	*	@param hash
	*/
	public MRTreeNode(Rectangle MBR, byte[] hash, List<Point> data,
	List<MRTreeNode> children) {
		this.MBR = MBR;
		this.hash = hash;
		this.data = data;
		this.children = children;
	}

	/**
	*	@return the MBR of the node
	*/
	public Rectangle getMBR() {
		return MBR;
	}

	/**
	*	@return the hash value of the node
	*/
	public byte[] getHash() {
		return hash;
	}

	/**
	* @return
	*/
	public List<Point> getData() {
		return data;
	}

	/**
	*	Returns the list of children 
	*/
	public List<MRTreeNode> getChildren() {
		return children;
	}

	/**
	*	Determines whether the current node is a leaf or an internal one.
	*	@return true if and only if the current node is a leaf
	*/
	public boolean isLeaf() {
		return (data != null && children == null);
	}

	/**
	*	Comparison function.
	*	@param n node to be compared with the current one
	*	@return an integer
	*/
	public int compareTo(MRTreeNode n) {
		return MBR.compareTo(n.MBR);
	}

	/**
	*	Creates a new leaf node from a list of points.
	*	@param pts the list of points
	*	@return a leaf MR-tree node
	*/
	public static MRTreeNode nodeFromPoints(List<Point> pts) {
		return new MRTreeNode(Geometry.MBR(pts), Hash.hashPoints(pts),
		pts, null);
	}

	/**
	*	Creates a new internal node from a list of child nodes.
	*	The new node is the parent of all these children.
	*	@param children the list of child nodes
	*	@return an internal MR-tree node
	*/
	public static MRTreeNode nodeFromChildren(List<MRTreeNode> children) {
		// Take all the bounding rectangles of the children.
		List<Rectangle> rects = new ArrayList<Rectangle>();
		children.forEach((c) -> rects.add(c.getMBR()));
		return new MRTreeNode(Geometry.enlarge(rects), Hash.hashNode(children),
		null, children);
	}

}
