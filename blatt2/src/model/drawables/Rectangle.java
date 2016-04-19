package model.drawables;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Diese Klasse repräsentiert ein Rechteck. Ein Rechteck wird durch zwei Punkte
 * definiert, zum Einen die linke obere Ecke und zum Anderen die rechte untere
 * Ecke.
 * 
 * @author Nicolas Neubauer
 */
// TODO: (A1) Klasse vervollständigen
public class Rectangle extends DrawableObject {
	public Point a;
	public Point e;

	/**
	 * Konstruktor mit frei wählbaren Argumenten
	 * 
	 * @param a
	 *            Punkt in der linken oberen Ecke
	 * @param e
	 *            Punkt in der rechten unteren Ecke
	 */
	public Rectangle(Point a, Point e) {
		this.a = a;
		this.e = e;
	}

	/**
	 * Zeichnet das Rechteck in einen grafischen Kontext
	 * 
	 * @param g
	 *            der grafische Kontext in den das Rechteck gezeichnet wird
	 */
	public void paint(Graphics g) {
		g.setXORMode(Color.CYAN);
		// TODO: (A1) Rechteck zeichnen
		// x-------x
		// |.......|
		// |.......|
		// x-------x
		Line l1 = new Line(new Point(a.x, a.y), new Point(e.x, a.y));
		Line l2 = new Line(new Point(a.x, e.y), new Point(e.x, e.y));
		Line l3 = new Line(new Point(a.x, a.y), new Point(a.x, e.y));
		Line l4 = new Line(new Point(e.x, a.y), new Point(e.x, e.y));
		
		g.setPaintMode();
		
		l1.paint(g); l2.paint(g); l3.paint(g); l4.paint(g);
	}
}
