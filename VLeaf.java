/**
*
*
*/

import java.util.ArrayList;
import java.util.List;

public class VLeaf extends VObject {

  /**
  *
  */
  private List<Point> records;

  /**
  *
  */
  public VLeaf(List<Point> records) {
    this.type = VObjectType.LEAF;
    this.records = new ArrayList<Point>();
    this.records.addAll(records);
  }

  /**
  *
  */
  public List<Point> getRecords() {
    return records;
  }
}
