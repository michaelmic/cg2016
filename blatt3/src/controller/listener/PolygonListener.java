package controller.listener;

import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.MouseInputAdapter;

import controller.DrawableObjectProcessing;

import model.drawables.DashedPolygon;
import model.drawables.Point;
import model.drawables.Polygon;

/**
 * Ein Listener, der Polygone durch MouseEvents erzeugt.
 * 
 * @author Nicolas Neubauer
 * 
 */
public class PolygonListener extends MouseInputAdapter {

	protected List<Point> tmpPoints;
	private DrawableObjectProcessing delegate;
	private boolean filled;

	public PolygonListener(DrawableObjectProcessing delegate, boolean filled) {
		tmpPoints = new LinkedList<Point>();
		this.delegate = delegate;
		this.filled = filled;
	}

	/**
	 * Füge den aktuellen Punkt als Point in die Punktliste ein.
	 */
	public void mouseClicked(MouseEvent e) {

		if (tmpPoints.size() == 0)
			tmpPoints.add(new Point(e.getX(), e.getY()));
		else {

			int maxEntfernung = 5;
			// Prüfen ob fertig
			if (tmpPoints.size() > 2
					&& Math.abs(tmpPoints.get(0).x - e.getX()) <= maxEntfernung
					&& Math.abs(tmpPoints.get(0).y - e.getY()) <= maxEntfernung) {
				// Lösche temporäres Object
				delegate.clearTemporaryDrawableObject();
				// Fertig, also Polygon erzeugen und übergeben
				delegate.processDrawableObject(createPolygon());
				tmpPoints = new LinkedList<Point>();
			} else {
				// Nicht fertig, ist der neue Punkt weit genug weg?
				if (Math.abs(tmpPoints.get(0).x - e.getX()) > maxEntfernung
						|| Math.abs(tmpPoints.get(0).y - e.getY()) > maxEntfernung) {
					// Ja, also Punkt hinzufügen
					tmpPoints.add(new Point(e.getX(), e.getY()));
				}
				// Ansonsten, tue einfach nichts
			}

		}

	}

	/**
	 * Erzeugt ein ungefülltes Polygon und gibt dieses zurück
	 * 
	 * @return ungefülltes Polygon-Objekt
	 */
	protected Polygon createPolygon() {
		return new Polygon(tmpPoints, filled);
	}

	/**
	 * Übergebe mit Hilfe der aktuellen Mausposition ein DashedPolygon als
	 * temporäres Objekt an das Delegate.
	 */
	public void mouseMoved(MouseEvent e) {
		if (tmpPoints.size() > 0) {
			DashedPolygon p = new DashedPolygon(tmpPoints, new Point(e.getX(),
					e.getY()));
			// Temporäres Objekt neu zeichnen
			delegate.setTemporaryDrawableObject(p);
		}
	}

	/**
	 * Setze den Status des Listeners zurück.
	 */
	public void mouseExited(MouseEvent e) {
		// Lösche temporäres Object
		delegate.clearTemporaryDrawableObject();
		// Erzeuge neue Liste
		tmpPoints = new LinkedList<Point>();
	}
	
	public String toString() {
		if (filled) {
			return "Polygon (gefüllt)";
		} else {
			return "Polygon";
		}
	}

}
