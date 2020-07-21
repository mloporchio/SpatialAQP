/**
*	File:     Rectangle.java
*	Author:   Matteo Loporchio, 491283
*/

public class Rectangle implements Comparable<Rectangle> {
	public final double lx;
	public final double ly;
	public final double ux;
	public final double uy;

	/**
	*	The default class constructor.
	*/
	public Rectangle(double lx, double ly, double ux, double uy) {
		this.lx = lx;
		this.ly = ly;
		this.ux = ux;
		this.uy = uy;
	}

	// Builds an empty rectangle.
	public Rectangle() {
		this.lx = Double.POSITIVE_INFINITY;
		this.ly = Double.POSITIVE_INFINITY;
		this.ux = Double.NEGATIVE_INFINITY;
		this.uy = Double.NEGATIVE_INFINITY;
	}

	// To compare two rectangles, we just compare their lower-left vertices.
	public int compareTo(Rectangle r) {
		int s = Double.compare(this.lx, r.lx);
		return ((s != 0) ? s : Double.compare(this.ly, r.ly));
	}

	// Prints a human-readable string representing the rectangle.
	public String toString() {
		return "(" + this.lx + "," + this.ly + "," + this.ux + "," + this.uy + ")";
	}
}
