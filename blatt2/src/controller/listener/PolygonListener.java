package controller.listener;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import controller.DrawableObjectProcessing;

public class PolygonListener extends MouseInputAdapter {
	private DrawableObjectProcessing delegate;
	
	public PolygonListener(DrawableObjectProcessing delegate) {
		this.delegate = delegate;
	}
	public void mouseClicked(MouseEvent e) {
		//Was passiert beim klicken? (Erster Klick usw)
	}
	public void mouseMoved(MouseEvent e) {
		//Was passiert wenn woanders geklickt?
		//Gestrichelt zeichnen?
	}
}


