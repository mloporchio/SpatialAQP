import java.util.ArrayList;
import java.util.List;

/**
*
* @author Matteo Loporchio, 491283
*/
public class VContainer implements VObject {

  /**
  * This list contains several verification objects.
  */
  private List<VObject> list;

  /**
  *
  */
  public VContainer() {
    list = new ArrayList<VObject>();
  }

  /**
  *
  */
  public int size() {
    return list.size();
  }

  /**
  *
  */
  public void append(VObject e) {
    list.add(e);
  }

  /**
  *
  */
  public VObject get(int i) {
    return list.get(i);
  }
}
