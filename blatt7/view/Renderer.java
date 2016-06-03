/**
 *
 */
package view;

import java.awt.Graphics;
import java.util.List;

import model.Camera;
import model.Face;
import model.Matrix;
import model.Vertex;
import model.drawables.DrawableObject;

/**
 * Repräsentiert einen einfachen 3D-Wireframe-Renderer, der zwischen den
 * projezierten Punkten der Objekte Linien zeichnet. Es wird kein Clipping o.Ä.
 * durchgeführt.
 *
 * @author Nicolas Neubauer
 *
 */
public class Renderer {

	private Camera camera;
	private List<DrawableObject> drawableObjects;

	/**
	 * Erzeugt einen neuen Renderer mit einer Kamera und einer Menge von
	 * DrawableObjects
	 *
	 * @param camera
	 *            die Kamera
	 * @param drawableObjects
	 *            die Liste der Objekte
	 */
	public Renderer(Camera camera, List<DrawableObject> drawableObjects) {
		this.camera = camera;
		this.drawableObjects = drawableObjects;
	}

	/**
	 * Rendert die Szene mit dem übergebenen Grafischen Kontext und den aus der
	 * Kamera berechneten Matrizen - hier kommt die Viewing-Pipeline zum Tragen
	 *
	 * @param g
	 */
	public void render(Graphics g) {

		// Viewing-Pipeline-Matrix berechnen
		Matrix viewingPipeline = camera.NPC2DC().multiply(
				camera.VRC2NPC().multiply(camera.WC2VRC())
		);


		// Genau diese Schleife, die auf alle Vertices angewandt wird, wird
		// (vereinfacht gesagt) im
		// hardwarebeschleunigten Fall parallelisiert und mit extrem hoher
		// Performance auf der Grafikkarte ausgeführt - dazu später in OpenGL
		// mehr
		for (DrawableObject d : drawableObjects) {
			for (Face f : d.getFaces()) {
				// Face sichtbar? (Backface-Culling)
				if (faceVisibleFromCamera(f)) {
					Vertex start = viewingPipeline.multiply(f.getVertices().get(0));
					Vertex end = null;

					for (int i = 1; i < f.getVertices().size(); i++) {
						end = viewingPipeline.multiply(f.getVertices().get(i));
						drawLine(start, end, g);
						start = end;
					}

					// Letzte Kante zeichnen
					end = viewingPipeline.multiply(f.getVertices().get(0));
					drawLine(start, end, g);
				}
			}
		}
	}

	/**
	 * Prüft ob eine Fläche aus Sicht der Kamera abgewand ist.
	 *
	 * @param f
	 *            die zu prüfende Fläche
	 * @return true, wenn die Fläche zugewandt/sichtbar ist
	 */
	private boolean faceVisibleFromCamera(Face f) {
        // TODO: (A1)
		Vertex p, n, a;
		double e;
		int counter = 0;
		
		a = camera.getEyePoint(); 
		
		int num = f.getVertices().size();
		for(int i=0; i<num; i++) {
			a = f.getVertices().get(i);
			n = f.getNormals().get(i);
			p = camera.getEyePoint();
			
			e = p.scalarProduct(n) - a.scalarProduct(n);
			
			if(e < 0) counter++;
		}
		
		return counter == 0;
	}

	/**
	 * Eine Linie zwischen den (2D-)Koordinaten zweier Vertices zeichnen
	 *
	 * @param start
	 *            Startvertex
	 * @param end
	 *            Endvertex
	 * @param g
	 *            grafischer Kontext
	 */
	public void drawLine(Vertex start, Vertex end, Graphics g) {

		start.homogenize();
		end.homogenize();
		// System.out.println("Drawing Start To End : " + start + " -> " + end);
		int x1 = (int) (start.x);
		int x2 = (int) (end.x);
		int y1 = (int) (start.y);
		int y2 = (int) (end.y);

		int x, y, error, delta, schritt, dx, dy, inc_x, inc_y;

		x = x1; // Koordinaten retten
		y = y1;

		dy = y2 - y1; // Hoehenzuwachs
		dx = x2 - x1; // Schrittweite

		if (dx > 0) { // Linie nach rechts?
			inc_x = 1; // x inkrementieren
		} else {
			// Linie nach links
			inc_x = -1; // x dekrementieren
		}
		if (dy > 0) { // Linie nach unten?
			inc_y = 1; // y inkrementieren
		} else {
			// Linie nach oben
			inc_y = -1; // y dekrementieren
		}
		if (Math.abs(dy) < Math.abs(dx)) { // flach nach oben oder unten
			error = -Math.abs(dx); // Fehler bestimmen
			delta = 2 * Math.abs(dy); // Delta bestimmen
			schritt = 2 * error; // Schwelle bestimmen

			while (x != x2) { // Fuer jede x-Koordinate
				g.fillRect(x, y, 1, 1);// setPixel
				x += inc_x; // naechste x-Koordinate
				error = error + delta; // Fehler aktualisieren
				if (error > 0) { // neue Spalte erreicht?
					y += inc_y; // y-Koord. aktualisieren
					error += schritt; // Fehler aktualisieren
				}
			}
		} else { // steil nach oben oder unten
			error = -Math.abs(dy); // Fehler bestimmen
			delta = 2 * Math.abs(dx); // Delta bestimmen
			schritt = 2 * error; // Schwelle bestimmen

			while (y != y2) {// fuer jede y-Koordinate
				g.fillRect(x, y, 1, 1);// setPixel
				y += inc_y; // naechste y-Koordinate
				error = error + delta; // Fehler aktualisieren
				if (error > 0) { // neue Zeile erreicht?
					x += inc_x; // x-Koord. aktualisieren
					error += schritt; // Fehler aktualisieren
				}
			}
		}
		g.fillRect(x2, y2, 1, 1); // letztes Pixel hier setzen,
	} // falls (x1==x2) & (y1==y2)

	/**
	 * Liefert eine Referenz auf die aktuelle Kamera zurück
	 *
	 * @return die aktuelle Kamera
	 */
	public Camera getCamera() {
		return camera;
	}

}
