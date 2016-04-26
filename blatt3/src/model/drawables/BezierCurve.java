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
	Point halbiere(Point P1, Point P2){
		int x = (P1.x + P2.x)/2;
		int y = (P1.y + P2.y)/2;
		Point P = new Point(x,y);

		return P;
		}
	/**
	 * Zeichnet eine Bezier-Kurve und die Stützpunkte in den grafischen Kontext
	 */
	public void paint(Graphics g) {
		// Zeichne Stützpunkte
		for (Point p : points) {
			p.paint(g);
			int tiefe = 0;
			new Point (p.x + 1, p.y + 1).paint(g);
			new Point(p.x - 1, p.y + 1).paint(g);
			new Point(p.x - 1, p.y - 1).paint(g);
			new Point(p.x + 1, p.y - 1).paint(g);
			Point P0 = new Point (p.x + 1, p.y + 1);
			Point P1 = new Point(p.x - 1, p.y + 1);
			Point P2 = new Point(p.x - 1, p.y - 1);
			Point P3 = new Point(p.x + 1, p.y - 1);
			bezier(P0, P1, P2, P3, tiefe, g);
				}  

	
		// TODO: (A4) Zeichne eine einzelne Bezier Kurve mit 
		// den ersten 4 Punkten der Liste und mit Hilfe des
		// de Casteljau Algorithmus.
	} 
	void bezier(Point P0, Point P1, 
			Point P2, Point P3, int tiefe, Graphics g) {
			if (tiefe == 0) new Line(P0, P3).paint(g); else {
			tiefe--;
			Point P01   = halbiere(P0,   P1);
			Point P12   = halbiere(P1,   P2);
			Point P23   = halbiere(P2,   P3);
			Point P012  = halbiere(P01,  P12);
			Point P123  = halbiere(P12,  P23);
			Point P0123 = halbiere(P012, P123);
			bezier(P0, P01, P012, P0123, tiefe, g);
			bezier(P0123, P123, P23, P3, tiefe, g);
			}
			}  
}
