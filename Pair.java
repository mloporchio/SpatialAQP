/**
* This class represents an immutable pair of generic objects.
* @author Matteo Loporchio, 491283
*/

public class Pair<T, U> {
  private T first;
  private U second;

  /**
  * Constructs a new pair.
  * @param first the first element of the pair
  * @param second the second element of the pair
  */
  public Pair(T first, U second) {
    this.first = first;
    this.second = second;
  }

  /**
  * @return the first element of the pair
  */
  public T getFirst() {
    return first;
  }

  /**
  * @return the second element of the pair
  */
  public U getSecond() {
    return second;
  }
}
