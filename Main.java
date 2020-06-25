/**
 *  File:     Main.java
 *  Author:   Matteo Loporchio, 491283
 */

import java.util.*;

public class Main {
	/*
		This function tests the construction of a blockchain.
		We load a file from disk and then build the corresponding blockchain
		block by block. Then we print the hash value of the last block.
	*/
	public static void test1() {
		try {
			Blockchain chain = Utility.readFromFile("blockchain.dat");
			String lastHash = Hash.bytesToHex(chain.getLast());
			System.out.println(lastHash);
		}
		catch (Exception e) {
			System.err.println("Something went wrong!");
		}
	}

	/*
		This function tests the construction of a MR-tree index from
		a fixed set of 2D data points.
	*/
	public static void test2() {
		// This is the set of points.
		Point[] pts = new Point[] {new Point(3, 4), new Point(2, 2),
		new Point(2, 4), new Point(1, 8), new Point(5, 3), new Point(5, 4),
		new Point(6, 5), new Point(5, 5), new Point(6, 2), new Point(7, 2)};
		// We convert it into an ArrayList data structure.
		ArrayList<Point> points = new ArrayList<Point>(Arrays.asList(pts));
		// Then we build a new MR-tree index with page size = 2.
		MRTree T = new MRTree(points, 2);
		String rootHash = Hash.bytesToHex(T.root.hash);
		System.out.println("ptsRect = " + Geometry.MBR(points).toString());
		System.out.println("rootRect = " + T.root.MBR.toString());
		System.out.println("rootHash = " + rootHash);
	}

	public static void main(String[] args) {
		test2();
	}
}
