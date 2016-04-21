package model.drawables;

import java.awt.Graphics;
import java.util.List;

/**
 * Ein DashedPolygon ist kein geschlossenes Polygon sondern vielmehr eine
 * Linien-Kette
 * 
 * @see Polygon
 * @author Nicolas Neubauer
 * 
 */
public class DashedPolygon extends Polygon {

	Point tmpPoint;

	/**
	 * @see Polygon
	 */
	public DashedPolygon(List<Point> points, Point tmpPoint) {
		super(points, false);
		this.tmpPoint = tmpPoint;
	}

	/**
	 * Zeichnet das Polygon in den uebergebenen grafischen Kontext mit Hilfe von
	 * DashedLines
	 * 
	 * @param g
	 *            der grafische Kontext, in den dieses Objekt sich zeichnen soll
	 */
	public void paint(Graphics g) {
		Line l;
		Point prev = null;

		for (Point p : points) {
			if (prev != null) {
				l = new DashedLine(prev, p);
				l.paint(g);
			}
			prev = p;
		}

		// tmpPoint einfügen
		l = new DashedLine(prev, tmpPoint);
		l.paint(g);

	}

}
