package controller.listener;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import controller.DrawableObjectProcessing;
import model.drawables.DashedPolygon;
import model.drawables.Point;
import model.drawables.Polygon;

public class PolygonListener extends MouseInputAdapter {
	private DrawableObjectProcessing delegate;
	private Point first;
	private DashedPolygon obj;
	private final int toleranz = 100;
	
	public PolygonListener(DrawableObjectProcessing delegate) {
		this.delegate = delegate;
	}
	public void mouseClicked(MouseEvent e) {
		if( first == null ) {
			first = new Point(e.getX(), e.getY());
			obj = new DashedPolygon(first);
		} else {
			if( e.getX() > first.x - toleranz && e.getX() < first.x  + toleranz && 
					e.getY() > first.y - toleranz && e.getY() < first.y + toleranz) {
				delegate.clearTemporaryDrawableObject();
				
				Polygon finalObj = new Polygon(obj);

				delegate.processDrawableObject(finalObj);
				this.first = null;
				this.obj = null;
			} else {
				obj.addPoint(new Point(e.getX(), e.getY()));
				
				delegate.setTemporaryDrawableObject(obj);
			}
		}
	}
	public void mouseMoved(MouseEvent e) {
		//Was passiert wenn woanders geklickt?
		//Gestrichelt zeichnen?
		if( first != null ) {
			delegate.setTemporaryDrawableObject(obj);
		}
	}
}


