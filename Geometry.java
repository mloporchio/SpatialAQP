/**
 *  File:     Geometry.java
 *  Author:   Matteo Loporchio, 491283
 */

import java.util.List;

public class Geometry {

  // This function returns true if and only if a point lies inside a rectangle.
  public static boolean contains(Rectangle r, Point p) {
    return (r.lx <= p.x && p.x <= r.ux && r.ly <= p.y && p.y <= r.uy);
  }

  // This function returns true if and only if two rectangles overlap.
  public static boolean intersect(Rectangle r1, Rectangle r2) {
    boolean above = (r1.ly >= r2.uy), // r1 is above r2
    below = (r1.uy <= r2.ly), // r1 is below r2
    left = (r1.ux <= r2.lx), // r1 is to the left of r2
    right = (r1.lx >= r2.ux); // r1 is to the right of r2
    return !(above || below || left || right);
  }

  // This function computes the MBR from a set of points.
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

  // Computes the union between two rectangles.
  public static Rectangle enlarge(Rectangle r1, Rectangle r2) {
    return new Rectangle(
      Math.min(r1.lx, r2.lx),
      Math.min(r1.ly, r2.ly),
      Math.max(r1.ux, r2.ux),
      Math.max(r1.uy, r2.uy)
    );
  }

  // Computes the union of a list of rectangles.
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
