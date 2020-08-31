import java.util.List;

/**
* This class contains a set of utility functions to work
* with points and rectangles.
* @author	Matteo Loporchio, 491283
*/
public final class Geometry {

  /**
  * This is the empty rectangle, whose lower-left vertex is (inf, inf)
  * and the upper-right one is (-inf, -inf).
  */
  public static final Rectangle EMPTY_RECT =
    new Rectangle(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
    Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);

  /**
  * This function returns true if and only if
  * the given point is inside the given rectangle.
  * @param r rectangle
  * @param p point
  * @return true if p is inside r, false otherwise
  */
  public static boolean contains(Rectangle r, Point p) {
    return (r.lx <= p.x && p.x <= r.ux && r.ly <= p.y && p.y <= r.uy);
  }

  /**
  * This function returns true if and only if two rectangles overlap.
  * @param
  * @param
  */
  public static boolean intersect(Rectangle r1, Rectangle r2) {
    boolean above = (r1.ly >= r2.uy), // r1 is above r2
    below = (r1.uy <= r2.ly), // r1 is below r2
    left = (r1.ux <= r2.lx), // r1 is to the left of r2
    right = (r1.lx >= r2.ux); // r1 is to the right of r2
    return !(above || below || left || right);
  }

  /**
  * Computes the MBR for a set of points.
  * @param pts list of points
  * @return MBR of the list of points
  */
  public static Rectangle MBR(List<Point> pts) {
    double lx = Double.POSITIVE_INFINITY, ly = Double.POSITIVE_INFINITY,
    ux = Double.NEGATIVE_INFINITY, uy = Double.NEGATIVE_INFINITY;
    for (Point p : pts) {
      if (p.x <= lx) lx = p.x;
      if (p.y <= ly) ly = p.y;
      if (p.x >= ux) ux = p.x;
      if (p.y >= uy) uy = p.y;
    }
    return new Rectangle(lx, ly, ux, uy);
  }

  /**
  * Computes the minimum bounding union between two rectangles.
  * @param r1 first rectangle
  * @param r2 second rectangle
  * @return minimum bounding union of the two rectangles
  */
  public static Rectangle enlarge(Rectangle r1, Rectangle r2) {
    return new Rectangle(
      Math.min(r1.lx, r2.lx),
      Math.min(r1.ly, r2.ly),
      Math.max(r1.ux, r2.ux),
      Math.max(r1.uy, r2.uy)
    );
  }

  /**
  * Computes the minimum bounding union of a list of rectangles.
  * @param rects list of rectangles
  * @return minimum bouding union of the rectangles in the list
  */
  public static Rectangle enlarge(List<Rectangle> rects) {
    double lx = Double.POSITIVE_INFINITY, ly = Double.POSITIVE_INFINITY,
		ux = Double.NEGATIVE_INFINITY, uy = Double.NEGATIVE_INFINITY;
    for (Rectangle r : rects) {
      if (r.lx <= lx) lx = r.lx;
      if (r.ly <= ly) ly = r.ly;
      if (r.ux >= ux) ux = r.ux;
      if (r.uy >= uy) uy = r.uy;
    }
    return new Rectangle(lx, ly, ux, uy);
  }
}
