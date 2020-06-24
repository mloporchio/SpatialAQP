/**
 *  File:     Main.java
 *  Author:   Matteo Loporchio, 491283
 */

public class Main {
	/*
		This is a simple program that tests the construction of a blockchain.
		We load a file from disk and then build the corresponding blockchain
		block by block. Then we print the hash value of the last block.
	*/
	public static void main(String[] args) {
		try {
			Blockchain chain = Utility.readFromFile("blockchain.dat");
			String lastHash = Hash.bytesToHex(chain.getLast());
			System.out.println(lastHash);
		}
		catch (Exception e) {
			System.err.println("Something went wrong!");
		}
	}
}
