/**
* File:		Point.java
* Author:	Matteo Loporchio, 491283
*/

public class Point implements Comparable<Point> {
	public final double x;
	public final double y;

	/**
	*	This is the default constructor.
	*	@param x the x-coordinate
	*	@param y the y-coordinate
	*/
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	*	Method for comparison.
	*	@param p the point to be compared with the current one
	*	@return an integer
	*/
	public int compareTo(Point p) {
		int s = Double.compare(this.x, p.x);
		return ((s != 0) ? s : Double.compare(this.y, p.y));
	}

	/**
	*	Prints a human-readable string representing the point.
	*	@return a string representing the point
	*/
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}
}
