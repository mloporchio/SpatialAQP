import java.io.*;
import java.nio.*;
import java.security.*;
import java.util.*;

/**
*	This class contains several methods to compute the hash values
*	of geometric entities (e.g. points and rectangles) and data structures
*	(e.g. blocks, MR-trees, skip lists).
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
	*	@return the hash value of all points in the list
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
	*	@param r a rectangle
	*	@return the digest of the input rectangle
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
	*	This function computes the hash value of a node,
	*	starting from its children.
	*	@param children the list of child nodes
	*	@return the digest of the node
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
	*	@param entry a reference to the entry
	*	@return the digest of the entry
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
	*	@param skip a reference to the skip list
	*	@return the digest of the skip list
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
	*	The hash value of a block is obtained by concatenating:
	*		(1) the hash of the previous block
	*		(2) the hash of the root of the MR-tree index
	*		(3) the hash of the skip list
	*		(4) the hash value of all points in the block
	*	@param b a block of the chain
	*	@return the hash value of the block
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
	*	This function returns the digest of the concatenation of all
	*	the hash values in the input list.
	*	@param hashes list of hash values
	*	@return the hash value of the concatenation of all values in the list
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
	*	Given a list of hash values and an integer i, this function
	*	returns the digest of the concatenation of the first 2^i hash values.
	*	@param hashes list of hash values
	*	@param i an integer value
	*	@return the hash value of the concatenation of the first 2^i values
	*/
	public static byte[] aggregate(List<byte[]> hashes, int i) {
		int j = ((int) Math.pow(2, i)) - 1;
		if (j > (hashes.size() - 1)) return null;
		return aggregate(hashes.subList(0, j));
	}

	/**
	*	This function can be used to reconstruct the hash of a MR-tree node
	*	starting from the list of minimum bounding rectangles
	*	and the list of digests of its children.
	*	@param rects the list of rectangles of the children
	* @param hashes the list of hash values of the children
	*	@return the digest of the node. We return a null value if the input
	*	lists do not have the same length.
	*/
	public static byte[] reconstruct(List<Rectangle> rects, List<byte[]> hashes)
	{
		// It is required that the two lists have the same length.
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
	*	@param hash array of bytes
	*	@return a readable string representing the content of the array
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
