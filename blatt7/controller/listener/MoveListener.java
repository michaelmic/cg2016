/**
 *
 */
package controller.listener;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import controller.DrawingPanelViewController;

import model.Vertex;

/**
 * Ein Listener, der den Augenpunkt parallel zur Bildfläche wandern lässt
 *
 * @author Nicolas Neubauer
 *
 */
public class MoveListener extends MouseInputAdapter {

	private int x;
	private int y;
	private Vertex eyePoint;
	private Vertex upVector;
	private DrawingPanelViewController controller;

	// 30 Pixel entsprechen einer Einheit im 3D-Raum (per autoritärer Willkür)
	private static final double PX23DVAL = 30.0;

	/**
	 * Erstellt einen neuen Listerner
	 *
	 * @param controller
	 *            der zugehörige Controller
	 */
	public MoveListener(DrawingPanelViewController controller) {
		this.controller = controller;
	}

	/**
	 * Merkt sich die aktuellen Kameradaten und Klickkoordinaten
	 */
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		eyePoint = controller.getEyePoint();
		upVector = controller.getUpVector();
	}

	/**
	 * Bewegt die Kamera bzw. den Augenpunkt analog zur Mausbewegung
	 */
	public void mouseDragged(MouseEvent e) {
		// Berechne Differenz
		int dx = e.getX() - x;
		int dy = y - e.getY();

		Vertex eyePoint = controller.getEyePoint();
		Vertex upVector = controller.getUpVector();

		// Setze neue Werte
		eyePoint.x = this.eyePoint.x + (dx / PX23DVAL);
		upVector.x = this.upVector.x + (dx / PX23DVAL);

		eyePoint.y = this.eyePoint.y + (dy / PX23DVAL);
		upVector.y = this.upVector.y + (dy / PX23DVAL);

		controller.changeEyePointTo(eyePoint);
		controller.changeUpVectorTo(upVector);
	}

}
