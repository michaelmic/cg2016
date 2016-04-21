package model.drawables;

import java.awt.Graphics;
import java.util.List;
import model.Matrix;

/**
 * Ein grafisches Objekt, dass ein Polygon repraesentiert. Die einzelnen
 * Polygonpunkte sind in einer Liste abgelegt. Der erste Punkt ist nicht doppelt
 * abgelegt, d.h. die letzte Kante ist vom letzten Punkt zum ersten Punkt zu
 * ziehen.
 * 
 * @author Nicolas Neubauer
 */
public class Polygon extends DrawableObject {

	protected List<Point> points; // Liste mit Eckpunkten
	protected boolean filled;

	/**
	 * Erstellt ein Polygon
	 * 
	 * @param points
	 *            Punkte-Liste
	 * @param filled
	 *            soll das Polygon gefüllt sein?
	 */
	public Polygon(List<Point> points, boolean filled) {
		this.points = points;
		this.filled = filled;
	}

	/**
	 * Zeichnet das Polygon in den uebergebenen grafischen Kontext und fuellt es
	 * gegebenenfalls.
	 * 
	 * @param g
	 *            der grafische Kontext, in den dieses Objekt sich zeichnen soll
	 */
	public void paint(Graphics g) {
		Line l;
		Point prev = null;

		for (Point p : points) {
			if (prev != null) {
				l = new Line(prev, p);
				l.paint(g);
			}
			prev = p;
		}

		// Polygon schließen, Linie zeichnen
		l = new Line(prev, points.get(0));
		l.paint(g);
		
		if (filled) {
			scanlineFill(g);
		}
	}
	
	/**
	 * Wendet das Scanline-Verfahren zum Fuellen eines Polygons an
	 * 
	 * @param g
	 *            der grafische Kontext, in den dieses Objekt sich zeichnen soll
	 */
	private void scanlineFill(Graphics g) {
		// TODO: (A1) Scanline Algorithmus implementieren
		//       (es können auch weitere Hilfs-Methoden angelegt werden)
		return;
	}

	/**
	 * Gibt true zurück, wenn der Punkt im Polygon liegt, false sonst.
	 * 
	 * @param p
	 *            der zu prüfende Punkt
	 * @return true, wenn p innerhalb des Polygons liegt
	 */
	public boolean isNear(Point p) {
		// TODO: (A3) Implementieren
		return true;
	}

	/**
	 * Transformiert das Polygon mit Hilfe der übergebenen
	 * Transformationsmatrix.
	 * 
	 * @param m
	 *            die Transformationsmatrix
	 */
	public void transformBy(Matrix m) {
		// TODO: (A3) Implementieren
	}
}