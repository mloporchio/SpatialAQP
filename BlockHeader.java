/*
	File:		BlockHeader.java
	Author:		Matteo Loporchio
*/

public class BlockHeader {
	public byte[] prev;
	public byte[] indexHash;
	public byte[] skipHash;

	public BlockHeader() {
		this.prev = new byte[32];
		this.indexHash = new byte[32];
		this.skipHash = new byte[32];
	}
}
