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
		g.setXORMode(Color.MAGENTA);
		// TODO: (A1) Rechteck zeichnen
		// x-------x
		// |.......|
		// |.......|
		// x-------x
		g.drawLine(a.x, a.y, e.x, a.y);
		g.drawLine(a.x, e.y, e.x, e.y);
		g.drawLine(a.x, a.y, a.x, e.y);
		g.drawLine(e.x, a.y, e.x, e.y);
		g.setPaintMode();
	}
}
