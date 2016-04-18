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
		// TODO: (A1) Rechteck zeichnen
		// d/x3-----e/x4
		// |--------|
		// |--------|
		// a/x1-----b/x2
		// 4 Linien zu zeichnen
		g.setXORMode(Color.RED);
		// a
		int x1 = a.x;
		int y1 = a.y;
		// e
		int x4 = e.x;
		int y4 = e.y;
		// unten rechts
		int x2 = x4;
		int y2 = y1;
		// oben links
		int x3 = y1;
		int y3 = y4;
		// unten rechts als punkt
		Point b = new Point(x2, y2);
		Point d = new Point(x3, y3);

		Line unten = new Line(a, b);
		Line oben = new Line(d, e);
		Line links = new Line(a, d);
		Line rechts = new Line(b, e);

		while (x1 <= x2) {
			setPixel(x1, y1, g);
			x1++;
		}
		while (x3 <= x4) {
			setPixel(x3, y1, g);
			x3++;
		}
		while (y1 <= y3) {
			setPixel(x1, y1, g);
			y1++;
		}
		while (y2 <= y4) {
			setPixel(x2, y2, g);
			y2++;
		}
		g.setPaintMode();
	}
}
