import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
* This class implements the blockchain.
* @author	Matteo Loporchio, 491283
*/
public class Blockchain {

  /**
  * Page capacity for the index inside each block.
  */
  private int c;

  /**
  * Skip list size for each block.
  */
  private int m;

  /**
  * The blockchain is implemented with a hash map of blocks.
  * Each block is retrieved by means of its hash value.
  */
  private Map<byte[], Block> storage;

  /**
  * This field stores the hash value of the last block.
  */
  private byte[] last;

  /**
  *
  */
  private List<byte[]> cache;

  /**
  *
  */
  private int maxSize;

  /**
  * This is the default constructor for the class.
  */
	public Blockchain(int c, int m) {
    this.c = c;
    this.m = m;
    this.storage = new HashMap<byte[], Block>();
    this.last = null;
    this.cache = new ArrayList<byte[]>();
    this.maxSize = ((int) Math.pow(2, m)) - 1;
	}

  /**
  * Returns the hash of the last block of the chain.
  * @return hash of the last block
  */
  public byte[] getLast() {
    return last;
  }

  /**
  * Returns a reference to the block with the given address.
  * @param ref address of the block
  * @return reference to the block
  */
  public Block getBlock(byte[] ref) {
    return storage.get(ref);
  }

  /**
  * Returns the number of currently inserted blocks
  * (i.e. the length of the blockchain).
  * @return number of inserted blocks
  */
  public int getSize() {
    return storage.size();
  }

  /**
  *
  */
  public List<byte[]> getCache() {
    return cache;
  }


  /**
  *
  */
  public void addToCache(byte[] ref) {
    int size = cache.size();
    // If we have reached the maximum size, we remove the last element.
    if (size == maxSize) cache.remove(size - 1);
    // We add new elements at the beginning of the list.
    cache.add(0, ref);
  }

  /**
  * Creates a new block containing the and appends it to the chain.
  * @return the hash value of the new block
  */
  public BlockRes append(List<Point> content) throws Exception {
    // Build the MR-tree index.
    long idxS = System.nanoTime();
    MRTreeNode index = MRTree.buildPacked(content, c);
		byte[] indexHash = index.getHash();
    long idxE = System.nanoTime();
    // Build the skip list index.
    long skipS = System.nanoTime();
    SkipListEntry[] skip = SkipList.buildSkip(this, m);
    byte[] skipHash = Hash.hashSkip(skip);
    long skipE = System.nanoTime();
    // Create the new block and compute its hash.
    int id = getSize()+1;
    Block b = new Block(last, indexHash, skipHash, index, skip, content, id);
    byte[] h = Hash.hashBlock(b);
    // Update the blockchain.
		last = h;
		storage.put(h, b);
    // Return the hash of the new block.
    return new BlockRes(h, idxE-idxS, skipE-skipS);
  }

}
