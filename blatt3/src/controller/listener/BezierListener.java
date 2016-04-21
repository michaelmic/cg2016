/**
 * 
 */
package controller.listener;

import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.MouseInputAdapter;

import model.drawables.BezierCurve;
import model.drawables.DashedPolygon;
import model.drawables.Point;
import controller.DrawableObjectProcessing;

/**
 * @author Nicolas Neubauer
 * 
 */
public class BezierListener extends MouseInputAdapter {

	protected List<Point> tmpPoints;
	private DrawableObjectProcessing delegate;

	public BezierListener(DrawableObjectProcessing delegate) {
		tmpPoints = new LinkedList<Point>();
		this.delegate = delegate;
	}

	/**
	 * Füge den aktuellen Punkt als Point in die Punktliste ein oder erzeuge
	 * neues Objekt und übergebe es an das Delegate.
	 */
	public void mouseClicked(MouseEvent e) {

		if (tmpPoints.size() < 3)
			tmpPoints.add(new Point(e.getX(), e.getY()));
		else {
			// 4. Punkt
			tmpPoints.add(new Point(e.getX(), e.getY()));
			BezierCurve c = new BezierCurve(tmpPoints);
			delegate.processDrawableObject(c);
			delegate.clearTemporaryDrawableObject();
			tmpPoints = new LinkedList<Point>();
		}
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
	
	public String toString() {
		return "Bezier-Kurve";
	}
}
