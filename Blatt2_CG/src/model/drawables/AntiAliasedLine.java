package model.drawables;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Klasse, die eine Linie mit Anti-Aliasing-Effekt repräsentiert. Eine Linie
 * besteht aus zwei Punkten. Diese Punkte dienen als Start- und Endpunkt fuer
 * den Bresenham Algorithmus.
 * 
 * @author Nicolas Neubauer
 */
// TODO: (A2) Klasse vervollständigen
public class AntiAliasedLine extends DrawableObject {
	public Point a;
	public Point e;

	/**
	 * Konstruktor zum Erzeugen eines Linienobjektes
	 * 
	 * @param a
	 *            Startpunktes
	 * @param e
	 *            Endpunktes
	 */
	public AntiAliasedLine(Point a, Point e) {
		this.a = a;
		this.e = e;
	}

	/**
	 * Paint-Methode der Linienklasse Nutzt den Bresenham-Algorithmus. Zeichnet
	 * eine Linie mit einem einfachen Anti-Aliasing-Effekt.
	 * 
	 * @param g
	 *            der Graphikkontext, in den das Objekt gezeichnet werden soll
	 */
	public void paint(Graphics g) {
		// TODO: (A2) AntiAlias-Bresenham mit Hilfe von `setPixel()` aus der
		// Superklasse implementieren
		g.setXORMode(Color.ORANGE);

		int dy = e.y - a.y;
		int dx = e.x - a.x;
		int delta = 2 * dy;
		int error = dx;
		int schritt = -2 * dx;
		int x = a.x;
		int y = a.y;
		while (x <= e.x) {
			setPixel(x, y, g);
			x++;
			error = error + delta;
			if (error > 0) {
				y++;
				error = error + schritt;
			}
		}
		g.setPaintMode();
	}

}
