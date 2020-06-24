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
		if (this.x < p.x) return -1;
		if (this.y > p.y) return +1;
		if (this.x < p.x) return -1;
		if (this.x > p.x) return +1;
		return 0;
	}
}
