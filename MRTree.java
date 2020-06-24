/*
	File:		MRTreeNode.java
	Author:		Matteo Loporchio
*/

import java.util.ArrayList;
import java.util.Collections; 

class MRTreeEntry {
	public Rectangle MBR;
	public String hash;
	public MRTree child;
}

class MRTreeNode {
	public ArrayList<Point> data;
	public ArrayList<MRTreeEntry> entries;
}

public class MRTree {
	private MRTreeNode root;
	private ArrayList<MRTreeNode> nodes;
	// This method constructs the MR-tree with the packed algorithm.
	public MRTree(ArrayList<Point> pts, int c) {
		// Sort the points in ascending order.
		Collections.sort(pts); 
		//
		
		
	}
}