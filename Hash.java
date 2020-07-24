/**
*  File:     Hash.java
*  Author:   Matteo Loporchio, 491283
*/

import java.io.*;
import java.nio.*;
import java.security.*;
import java.util.*;

/**
*	This class contains several methods to compute the hash values
*	of geometric elements (e.g. points and rectangles)
*/
class Hash {

	/**
	*	This function computes the hash value of a 2D point.
	*/
	public static byte[] hashPoint(Point p) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] buf = ByteBuffer.allocate(16)
			.putDouble(p.x).putDouble(p.y).array();
			return digest.digest(buf);
		}
		catch (Exception e) {
			System.err.println("Something went wrong while hashing a point!");
			e.printStackTrace();
			return null;
		}
	}

	/**
	*	This function computes the hash value of a list of 2D points.
	*/
	public static byte[] hashPoints(List<Point> pts) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			ByteBuffer bb = ByteBuffer.allocate(16 * pts.size());
			for (Point p : pts) bb.putDouble(p.x).putDouble(p.y);
			return digest.digest(bb.array());
		}
		catch (Exception e) {
			System.err.println("Something went wrong while hashing points!");
			e.printStackTrace();
			return null;
		}
	}

	/**
	*	This function computes the hash value of a rectangle.
	*/
	public static byte[] hashRectangle(Rectangle r) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] buf = ByteBuffer.allocate(64).putDouble(r.lx).putDouble(r.ly).
			putDouble(r.ux).putDouble(r.uy).array();
			return digest.digest(buf);
		}
		catch (Exception e) {
			System.err.println("Something went wrong while hashing a rectangle!");
			e.printStackTrace();
			return null;
		}
	}

	/**
	*	This function computes the hash value of a list of nodes.
	*/
	public static byte[] hashNode(List<MRTreeNode> children) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			ByteArrayOutputStream strm = new ByteArrayOutputStream();
			// Read the content of all children.
			for (MRTreeNode c : children) {
				Rectangle MBR = c.getMBR();
				byte[] rbuf = ByteBuffer.allocate(64).putDouble(MBR.lx).
				putDouble(MBR.ly).putDouble(MBR.ux).putDouble(MBR.uy).array();
				strm.write(rbuf);
				strm.write(c.getHash());
			}
			return digest.digest(strm.toByteArray());
		}
		catch (Exception e) {
			System.err.println("Something went wrong while hashing a node!");
			e.printStackTrace();
			return null;
		}
	}

	/**
	*	This function computes the hash value of a skip list.
	*/
	public static byte[] hashSkip(SkipListEntry[] skip) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			ByteArrayOutputStream strm = new ByteArrayOutputStream();
			for (int i = 0; i < skip.length; i++) {
				Rectangle r = skip[i].getMBR();
				byte[] rbuf = ByteBuffer.allocate(64).putDouble(r.lx).
				putDouble(r.ly).putDouble(r.ux).putDouble(r.uy).array();
				strm.write(skip[i].getRef());
				strm.write(rbuf);
				strm.write(skip[i].getRectHash());
			}
			return digest.digest(strm.toByteArray());
		}
		catch (Exception e) {
			System.err.println("Something went wrong while hashing a skip list!");
			e.printStackTrace();
			return null;
		}
	}

	/**
	*	This function computes the hash value of a block.
	*/
	public static byte[] hashBlock(Block b) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			ByteArrayOutputStream strm = new ByteArrayOutputStream();
			strm.write((b.prev != null) ? b.prev : Global.ZERO_BYTES);
			strm.write((b.indexHash != null) ? b.indexHash : Global.ZERO_BYTES);
			strm.write((b.skipHash != null) ? b.skipHash : Global.ZERO_BYTES);
			strm.write(hashPoints(b.content));
			return digest.digest(strm.toByteArray());
		}
		catch (Exception e) {
			System.err.println("Something went wrong while hashing a block!");
			e.printStackTrace();
			return null;
		}
	}


	/**
	*
	*/
	public static byte[] reconstruct(List<Rectangle> rects, List<byte[]> hashes)
	{
		//
		if (rects.size() != hashes.size()) return null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			ByteArrayOutputStream strm = new ByteArrayOutputStream();
			for (int i = 0; i < rects.size(); i++) {
				Rectangle r = rects.get(i);
				byte[] rbuf = ByteBuffer.allocate(64).putDouble(r.lx).
				putDouble(r.ly).putDouble(r.ux).putDouble(r.uy).array();
				strm.write(rbuf);
				strm.write(hashes.get(i));
			}
			return digest.digest(strm.toByteArray());
		}
		catch (Exception e) {
			System.err.println("Something went wrong while hashing a node!");
			e.printStackTrace();
			return null;
		}
	}

	/**
	* Converts an array of bytes to a human-readable hexadecimal string.
	*/
	public static String bytesToHex(byte[] hash) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1) hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}
}
