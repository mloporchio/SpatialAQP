/**
* This class represents a rectangle, a 2D element which
*	is characterized by its lower-left and upper-right vertices.
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
	*	Computes the width of the rectangle.
	*	@return the rectangle width
	*/
	public double getWidth() {
		return ux-lx;
	}

	/**
	*	Computes the height of the rectangle.
	*	@return the rectangle height
	*/
	public double getHeight() {
		return uy-ly;
	}

	/**
	*	Computes the area of the rectangle.
	* @return the rectangle area
	*/
	public double getArea() {
		return (ux-lx)*(uy-ly);
	}


	/**
	*	Computes the centroid of the rectangle.
	*	@return a point corresponding to the centroid
	*/
	public Point getCentroid() {
		return new Point(lx + getWidth() / 2, ly + getHeight() / 2);
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
