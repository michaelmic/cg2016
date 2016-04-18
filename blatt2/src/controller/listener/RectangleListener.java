/**
 * 
 */
package controller.listener;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import controller.DrawableObjectProcessing;

import model.drawables.DashedRectangle;
import model.drawables.Point;
import model.drawables.Rectangle;

/**
 * 
 * Ein Listener, der Rectangles durch MouseEvents erzeugt und an ein Objekt, das
 * DrawableObjectProcessing implementiert übergibt.
 * 
 * @author Nicolas Neubauer
 * 
 */
public class RectangleListener extends MouseInputAdapter {

	private Point first;  // erster Klick
	private DrawableObjectProcessing delegate;

	public RectangleListener(DrawableObjectProcessing delegate) {
		this.delegate = delegate;
	}

	/**
	 * Merke die aktuelle Koordinate des Startpunktes oder, sofern schon
	 * vorhanden, erzeuge mit Hilfe des neuen (End-)punkts ein neues Rectangle
	 * und übergebe es dem Delegate.
	 */
	public void mouseClicked(MouseEvent e) {
		// Falls das erste Mal geklickt
		if (first == null) {
			first = new Point(e.getX(), e.getY());
		}
		// ... sonst
		else {
			// Lösche temporäres Object
			delegate.clearTemporaryDrawableObject();

			Point second = new Point(e.getX(), e.getY());
			Rectangle r = new Rectangle(first, second);
			first = null;
			delegate.processDrawableObject(r);
		}

	}

	/**
	 * Übergebe mit Hilfe der aktuellen Mausposition ein DashedRectangle als
	 * temporäres Objekt an das Delegate, sofern bereits ein Mittelpunkt
	 * vorliegt.
	 */
	public void mouseMoved(MouseEvent e) {
		if (first != null) {
			Point second = new Point(e.getX(), e.getY());
			DashedRectangle r = new DashedRectangle(first, second);
			// Temporäres Objekt neu zeichnen
			delegate.setTemporaryDrawableObject(r);
		}
	}

}
