import java.io.*;
import java.nio.*;
import java.security.*;
import java.util.*;

/**
*	This class contains several methods to compute the hash values
*	of geometric entities (e.g. points and rectangles) and blockchain
*	elements (e.g. blocks, skip lists).
*	@author Matteo Loporchio, 491283
*/
public final class Hash {

	/**
	*	This function computes the hash value of a 2D point.
	*	@param p point to be hashed
	*	@return an array of bytes representing the hash value
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
	*	@param pts the list of points to be hashed
	*	@return an array of bytes representing the hash value
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
	*	@param
	*	@return
	*/
	public static byte[] hashNode(List<MRTreeNode> children) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			ByteArrayOutputStream strm = new ByteArrayOutputStream();
			// Read the content of all children.
			for (MRTreeNode c : children) {
				Rectangle r = c.getMBR();
				byte[] rbuf = ByteBuffer.allocate(64).putDouble(r.lx).
				putDouble(r.ly).putDouble(r.ux).putDouble(r.uy).array();
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
	*	This function computes the hash value of a single skip list entry.
	*/
	public static byte[] hashSkipEntry(SkipListEntry entry) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			ByteArrayOutputStream strm = new ByteArrayOutputStream();
			Rectangle r = entry.getMBR();
			byte[] rbuf = ByteBuffer.allocate(64).putDouble(r.lx).
			putDouble(r.ly).putDouble(r.ux).putDouble(r.uy).array();
			byte[] ref = entry.getRef(), aggHash = entry.getAggHash();
			strm.write(((ref != null) ? ref : new byte[1]));
			strm.write(rbuf);
			strm.write(((aggHash != null) ? aggHash : new byte[1]));
			return digest.digest(strm.toByteArray());
		}
		catch (Exception e) {
			System.err.println("Something went wrong while hashing a skip list!");
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
			for (int i = 0; i < skip.length; i++) strm.write(hashSkipEntry(skip[i]));
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
			strm.write((b.getPrev() != null) ? b.getPrev() : new byte[32]);
			strm.write((b.getIndexHash() != null) ? b.getIndexHash() : new byte[32]);
			strm.write((b.getSkipHash() != null) ? b.getSkipHash() : new byte[32]);
			strm.write(hashPoints(b.getContent()));
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
	public static byte[] aggregate(List<byte[]> hashes) {
		// If the list contains only one hash value, return it.
		if (hashes.size() == 1) return hashes.get(0);
		// Otherwise we compute the hash of their concatenation.
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			ByteArrayOutputStream strm = new ByteArrayOutputStream();
			for (byte[] h : hashes) strm.write(h);
			return digest.digest(strm.toByteArray());
		}
		catch (Exception e) {
			System.err.println("Something went wrong while aggregating hashes!");
			e.printStackTrace();
			return null;
		}
	}

	/**
	*
	*/
	public static byte[] aggregate(List<byte[]> hashes, int i) {
		int j = ((int) Math.pow(2, i)) - 1;
		if (j > (hashes.size() - 1)) return null;
		return aggregate(hashes.subList(0, j));
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
	*	@param hash
	*	@return
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
