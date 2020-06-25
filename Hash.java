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
			return null;
		}
	}

	/**
	 *	This function computes the hash value of a rectangle.
	 */
	public static byte[] hashNode(List<MRTreeNode> children) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			ByteArrayOutputStream strm = new ByteArrayOutputStream();
			// Read the content of all children.
			for (MRTreeNode c : children) {
				byte[] rbuf = ByteBuffer.allocate(64).putDouble(c.MBR.lx).
				putDouble(c.MBR.ly).putDouble(c.MBR.ux).putDouble(c.MBR.uy).array();
				strm.write(rbuf);
				strm.write(c.hash);
			}
			return digest.digest(strm.toByteArray());
		}
		catch (Exception e) {
			System.err.println("Something went wrong while hashing a node!");
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
			strm.write(b.header.prev);
			strm.write(b.header.indexHash);
			strm.write(b.header.skipHash);
			strm.write(hashPoints(b.content));
			return digest.digest(strm.toByteArray());
		}
		catch (Exception e) {
			System.err.println("Something went wrong while hashing a block!");
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
