package controller.listener;

import java.awt.MouseInfo;
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
		//Unglaublich hardgecoded aber funktioniert xD
		List<Point> points = new LinkedList<>();
		int x = (int) MouseInfo.getPointerInfo().getLocation().getX()-300;
		int y = (int) MouseInfo.getPointerInfo().getLocation().getY()-250;
		Point p1 = new Point(x+109,y+176);
		Point p2 = new Point(x+40, y+212);
		Point p3 = new Point(x+105, y+287);
		Point p4 = new Point(x+162, y+260);
		Point p5 = new Point(x+217, y+294);
		Point p6 = new Point(x+268, y+254);
		Point p7 = new Point(x+339, y+297);
		Point p8 = new Point(x+398,y+225);		
		Point p9 = new Point(x+328, y+189);
		Point p10 = new Point(x+292, y+208);
		Point p11 = new Point(x+244, y+103);
		Point p12 = new Point(x+222, y+149);
		Point p13 = new Point(x+205, y+107);
		Point p14 = new Point(x+156, y+209);

		// TODO: (A2) Mehr/Andere Punkt hinzufügen, um Symbol
		//        zu erzeugen. Koordinaten hardcoden ist OK.
		points.add(p1);
		points.add(p2);
		points.add(p3);
		points.add(p4);
		points.add(p5);
		points.add(p6);
		points.add(p7);
		points.add(p8);
		points.add(p9);
		points.add(p10);
		points.add(p11);
		points.add(p12);
		points.add(p13);
		points.add(p14);
		
		Polygon poly = new Polygon(points, filled);
		delegate.processDrawableObject(poly);
	}
	
	public String toString() {
		// TODO: (A2) Name geben
		String out = "Batman Logo";
		if (filled) {
			return out +  "(Gefüllt)"; 
		} else {
			return out;
		}
	}
}