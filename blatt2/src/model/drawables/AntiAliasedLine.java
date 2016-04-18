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
		int inc_x = 0;
		int inc_y = 0;
		int dy = e.y - a.y;
		int dx = e.x - a.x;
		int x = a.x;
		int y = a.y;

		if (dx > 0) // Linie nach rechts?
			inc_x = 1; // x inkrementieren
		else // Linie nach links
			inc_x = -1; // x dekrementieren

		if (dy > 0) // Linie nach unten?
			inc_y = 1; // y inkrementieren
		else // Linie nach oben
			inc_y = -1; // y dekrementieren

		if (Math.abs(dy) < Math.abs(dx)) {
			float error = -Math.abs(dx); // Fehler bestimmen
			int delta = 2 * Math.abs(dx); // Delta bestimmen
			float schritt = 2 * error; // Schwelle bestimmen
			float fehler = Math.abs(error) / 100f;
			if (fehler > 1) {
				fehler = 1;
			}
			if (fehler < 0) {
				fehler = 0;
			}
			float brightness = 1 - fehler;
			while (x != e.x) {
				setPixel(x, y, brightness, g);// setze Pixel
				x += inc_x;
				error = error + delta;
				if (error > 0) {
					y += inc_y;
					error += schritt;
				}
				if (error > 0.5) {
					setPixel(x, y - 1, fehler, g);// setze Error Pixel
				}
			}
		} else { // steil nach oben oder unten
			float error = -Math.abs(dy); // Fehler bestimmen
			int delta = 2 * Math.abs(dx); // Delta bestimmen
			float schritt = 2 * error; // Schwelle bestimmen
			float fehler = Math.abs(error) / 100f;
			if (fehler > 1) {
				fehler = 1;
			}
			if (fehler < 0) {
				fehler = 0;
			}
			float brightness = 1 - fehler;
			while (y != e.y) { // fuer jede y-Koordinate
				setPixel(x, y, brightness, g);// setze Pixel

				y += inc_y; // naechste y-Koordinate
				error = error + delta; // Fehler aktualisieren
				if (error > 0) { // neue Zeile erreicht?
					x += inc_x; // x-Koord. aktualisieren
					error += schritt; // Fehler aktualisieren
				}
				if (error > 0.5) {
					setPixel(x, y - 1, fehler, g);// setze Error Pixel
				}
			}

		}
		setPixel(e.x, e.y, g);
		g.setPaintMode();
	}
}
