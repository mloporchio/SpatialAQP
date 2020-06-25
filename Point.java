/**
 *  File:     Point.java
 *  Author:   Matteo Loporchio, 491283
 */

public class Point implements Comparable<Point> {
	public final double x;
	public final double y;

	//  This is the default constructor.
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	// Method for comparison.
	public int compareTo(Point p) {
		int s = Double.compare(this.x, p.x);
		return ((s != 0) ? s : Double.compare(this.y, p.y));
	}
}
