/**
 *  File:     Block.java
 *  Author:   Matteo Loporchio, 491283
 */

import java.util.ArrayList;

public class Block {
	public BlockHeader header;
	public ArrayList<Point> content;

	public Block(ArrayList<Point> content) {
		this.header = new BlockHeader();
		this.content = content;
	}
}
