package controller.listener;

import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.MouseInputAdapter;

import model.drawables.Point;
import model.drawables.Polygon;
import controller.DrawableObjectProcessing;

/**
 * Ein Listener, der am Punkt des Mausklicks ein spezielles
 * Symbol mit Hilfe der Polygon-Klasse erstellt.
 * 
 * @author Nicolas Neubauer
 * 
 */
public class OwnSymbolListener extends MouseInputAdapter {
	private DrawableObjectProcessing delegate;
	private boolean filled;

	public OwnSymbolListener(DrawableObjectProcessing delegate, boolean filled) {
		this.delegate = delegate;
		this.filled = filled;
	}

	/**
	 * Erzeuge einen neuen Point bei den Klick-Koordinaten und übergebe ihn an
	 * das Delegate.
	 */
	public void mouseClicked(MouseEvent e) {
		Point p = new Point(e.getX(), e.getY());
		List<Point> points = new LinkedList<>();
		
		// TODO: (A2) Mehr/Andere Punkt hinzufügen, um Symbol
		//        zu erzeugen. Koordinaten hardcoden ist OK.
		points.add(p);
		
		Polygon poly = new Polygon(points, filled);
		delegate.processDrawableObject(poly);
	}
	
	public String toString() {
		// TODO: (A2) Name geben
		String out = "Mein Symbol [TODO: ändern]";
		if (filled) {
			return out +  "(Gefüllt)"; 
		} else {
			return out;
		}
	}
}