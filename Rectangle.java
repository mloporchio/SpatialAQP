/**
 *  File:     Rectangle.java
 *  Author:   Matteo Loporchio, 491283
 */

import java.util.ArrayList;

public class Rectangle {
	public final double lx;
	public final double ly;
	public final double ux;
	public final double uy;

	// The default class onstructor.
	public Rectangle(double lx, double ly, double ux, double uy) {
		this.lx = lx;
		this.ly = ly;
		this.ux = ux;
		this.uy = uy;
	}

	// This constructor builds the MBR from a set of points.
	public Rectangle(ArrayList<Point> pts) {
		double lx = Double.POSITIVE_INFINITY,
		ly = Double.POSITIVE_INFINITY,
		ux = Double.NEGATIVE_INFINITY,
		uy = Double.NEGATIVE_INFINITY;
		for (Point p : pts) {
			if (p.x <= lx) lx = p.x;
			if (p.y <= ly) ly = p.y;
			if (p.x >= ux) ux = p.x;
			if (p.y >= uy) uy = p.y;
		}
		this.lx = lx;
		this.ly = ly;
		this.ux = ux;
		this.uy = uy;
	}

	// Computes the union between two rectangles.
	public Rectangle enlarge(Rectangle r) {
		return new Rectangle(
			Math.min(this.lx, r.lx),
			Math.min(this.ly, r.ly),
			Math.max(this.ux, r.ux),
			Math.max(this.uy, r.uy)
		);
	}
}
