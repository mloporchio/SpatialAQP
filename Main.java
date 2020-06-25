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
			long tstart = System.nanoTime();
			Blockchain chain = Utility.readFromFile("blockchain.dat");
			long tend = System.nanoTime();
			String lastHash = Hash.bytesToHex(chain.getLast());
			System.out.println("Hash of last block = " + lastHash);
			System.out.println("Duration = " + (tend - tstart)/1000000 + " ms");
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

	/*
		In this test we measure how much time it takes to load blockchains
		of variable size (with a fixed number of records per block) without
		any index construction.
	*/
	public static void test3() {
		try {
			int[] size = new int[] {100, 200, 500, 1000, 2000, 5000, 10000};
			for (int i = 0; i < size.length; i++) {
				String filename = "test/blockchain_" + size[i] + "_1000.dat";
				long tstart = System.nanoTime();
				Blockchain chain = Utility.readFromFile(filename);
				long tend = System.nanoTime();
				System.out.println("Duration for " + size[i] + " blocks = "
				+ (tend - tstart)/1000000 + " ms");
			}
		}
		catch (Exception e) {
			System.err.println("Something went wrong!");
		}
	}

	public static void main(String[] args) {
		test3();
	}
}
