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

	public static byte[] hashPoint(Point p) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] buf = ByteBuffer.allocate(16).putDouble(p.x).putDouble(p.y).array();
		return digest.digest(buf);
	}

	public static byte[] hashPoints(ArrayList<Point> pts) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		ByteBuffer bb = ByteBuffer.allocate(16 * pts.size());
		for (Point p : pts) bb.putDouble(p.x).putDouble(p.y);
		return digest.digest(bb.array());
	}

	public static byte[] hashRectangle(Rectangle r) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] buf = ByteBuffer.allocate(64).putDouble(r.lx).putDouble(r.ly).
		putDouble(r.ux).putDouble(r.uy).array();
		return digest.digest(buf);
	}

	public static byte[] hashBlock(Block b) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		ByteArrayOutputStream strm = new ByteArrayOutputStream();
		strm.write(b.header.prev);
		strm.write(b.header.indexHash);
		strm.write(b.header.skipHash);
		strm.write(hashPoints(b.content));
		return digest.digest(strm.toByteArray());
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
