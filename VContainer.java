/**
* File:   VContainer.java
* Author: Matteo Loporchio, 491283
*/

import java.util.ArrayList;
import java.util.List;

/**
*
*/
public class VContainer extends VObject {

  /**
  * This list contains several verification objects.
  */
  private List<VObject> list;

  /**
  *
  */
  public VContainer() {
    type = VObjectType.CONTAINER;
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
