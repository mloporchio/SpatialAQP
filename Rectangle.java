/**
* File:		Rectangle.java
* @author Matteo Loporchio, 491283
*/

public class Rectangle implements Comparable<Rectangle> {
	/**
	* The x-coordinate of the lower-left vertex.
	*/
	public final double lx;

	/**
	* The y-coordinate of the lower-left vertex.
	*/
	public final double ly;

	/**
	* The x-coordinate of the upper-right vertex.
	*/
	public final double ux;

	/**
	*	The y-coordinate of the upper-right vertex.
	*/
	public final double uy;

	/**
	*	The default rectangle constructor.
	*	@param lx x-coordinate of the lower-left vertex
	*	@param ly y-coordinate of the lower-left vertex
	* @param ux x-coordinate of the upper-right vertex
	*	@param uy y-coordinate of the upper-right vertex
	*/
	public Rectangle(double lx, double ly, double ux, double uy) {
		this.lx = lx;
		this.ly = ly;
		this.ux = ux;
		this.uy = uy;
	}

	/**
	*	Comparison function for rectangles.
	*	To compare two rectangles, we just compare their lower-left vertices.
	* @param r rectangle to be compared with the current one
	* @return an integer
	*/
	public int compareTo(Rectangle r) {
		int s = Double.compare(this.lx, r.lx);
		return ((s != 0) ? s : Double.compare(this.ly, r.ly));
	}

	/**
	*	Prints a human-readable string representing the rectangle.
	* @return a string representing the rectangle
	*/
	public String toString() {
		return "(" + this.lx + "," + this.ly + "," + this.ux + "," + this.uy + ")";
	}
}
