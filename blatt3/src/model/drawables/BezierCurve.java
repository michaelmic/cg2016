/**
 * 
 */
package model.drawables;

import java.awt.Graphics;
import java.util.List;

/**
 * @author Nicolas Neubauer
 * 
 */
public class BezierCurve extends DrawableObject {

	private List<Point> points;

	/**
	 * Erstellt eine Bezier-Kurve mit 4 Stützstellen
	 * 
	 * @param points
	 */
	public BezierCurve(List<Point> points) {

		if (points.size() < 4)
			throw new IllegalArgumentException(
					"Bezier-Kurven benötigen mindestens 4 Stützpunkte!");

		this.points = points;
	}

	/**
	 * Zeichnet eine Bezier-Kurve und die Stützpunkte in den grafischen Kontext
	 */
	public void paint(Graphics g) {
		// Zeichne Stützpunkte
		for (Point p : points) {
			p.paint(g);
			new Point(p.x + 1, p.y + 1).paint(g);
			new Point(p.x - 1, p.y + 1).paint(g);
			new Point(p.x - 1, p.y - 1).paint(g);
			new Point(p.x + 1, p.y - 1).paint(g);
		}

		// TODO: (A4) Zeichne eine einzelne Bezier Kurve mit 
		// den ersten 4 Punkten der Liste und mit Hilfe des
		// de Casteljau Algorithmus.
	}
}
