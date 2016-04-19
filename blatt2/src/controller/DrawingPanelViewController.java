package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.event.MouseInputListener;

import controller.listener.LineListener;
import controller.listener.PointListener;
import controller.listener.PolygonListener;
import controller.listener.RectangleListener;
import model.drawables.DashedPolygon;
import model.drawables.DrawableObject;
import model.drawables.Point;
import view.DrawingPanelView;

/**
 * Der Controller, der eine DrawingPanelView verwaltet und das dazugehörige
 * Model in Form einer Liste von DrawableObjects. Das Model des Controllers ist
 * hart mit dem der View verbunden. Änderungen werden also direkt in der View
 * passieren. Ggf. muss die View aufgefordert werden sich neu zu zeichnen.
 * 
 * @author Nicolas Neubauer
 * 
 */
public class DrawingPanelViewController implements DrawableObjectProcessing {

	// View
	private DrawingPanelView drawingPanelView;

	// Model
	private LinkedList<DrawableObject> drawableObjectsModel;
	private DrawableObject temporaryObject;

	// Listener für die einzelnen grafischen Objekte
	private PointListener pointListener;
	private LineListener lineListener;
	private LineListener antiAliasedLineListener;
	private RectangleListener rectangleListener;
	private PolygonListener polygonListener;

	/**
	 * Der Konstruktor initialisiert die View und legt die Listener an.
	 */
	public DrawingPanelViewController() {

		drawableObjectsModel = new LinkedList<DrawableObject>();
		drawingPanelView = new DrawingPanelView(640, 480, drawableObjectsModel);

		// welche Objekte sollen gezeichnet werden können?
		String[] objectArray = { "Punkt", "Linie", "Anti-Aliased Linie", "Rechteck", "Polygon" };
		drawingPanelView.getComboBox().setModel(new DefaultComboBoxModel<String>(objectArray));

		// Listener für die einzelnen Grafischen Objekte anlegen.
		pointListener = new PointListener(this);
		lineListener = new LineListener(this, false);
		antiAliasedLineListener = new LineListener(this, true);
		rectangleListener = new RectangleListener(this);
		polygonListener = new PolygonListener(this);
		
//		//TODO löschen
//		DashedPolygon p = new DashedPolygon(new Point(5, 5), new Point(522, 124), new Point(200, 100));
//		p.addPoint(new Point(640,  480));
//		this.processDrawableObject(p);

		// ChangeListener um festzustellen, was gezeichnet werden soll
		ActionListener c = new ActionListener() {
			/**
			 * Hängt den passenden Listener an die Zeichenfläche
			 */
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				// Listener aktualisieren
				JComboBox<String> cb = (JComboBox<String>) e.getSource();

				if (cb.getSelectedItem().equals("Punkt")) {
					changeMouseInputListenerTo(pointListener);
				} else if (cb.getSelectedItem().equals("Linie")) {
					changeMouseInputListenerTo(lineListener);
				} else if (cb.getSelectedItem().equals("Anti-Aliased Linie")) {
					changeMouseInputListenerTo(antiAliasedLineListener);
				} else if (cb.getSelectedItem().equals("Rechteck")) {
					changeMouseInputListenerTo(rectangleListener);
				} else if (cb.getSelectedItem().equals("Polygon")) {
					changeMouseInputListenerTo(polygonListener);
				}
			}

			/**
			 * Convenience-Methode zum Umsetzen des aktuellen MouseListeners an
			 * der Zeichenfläche der verwalteten View
			 * 
			 * @param newListener
			 *            der zu setzende Listener, alle anderen werden
			 *            ausgehängt
			 */
			private void changeMouseInputListenerTo(MouseInputListener newListener) {

				// InputListeners
				MouseListener[] currentInputListeners = drawingPanelView.getDrawingPanel().getMouseListeners();

				for (MouseListener curListener : currentInputListeners) {
					drawingPanelView.getDrawingPanel().removeMouseListener(curListener);
				}

				// MotionListeners
				MouseMotionListener[] currentMotionListeners = drawingPanelView.getDrawingPanel()
						.getMouseMotionListeners();
				for (MouseMotionListener curListener : currentMotionListeners) {
					drawingPanelView.getDrawingPanel().removeMouseMotionListener(curListener);
				}

				drawingPanelView.getDrawingPanel().addMouseListener(newListener);
				drawingPanelView.getDrawingPanel().addMouseMotionListener(newListener);
			}

		};

		// Anhängen des Listeners
		drawingPanelView.getComboBox().addActionListener(c);

		// Event einmal auslösen, damit der korrekte Listener angehängt wird
		drawingPanelView.getComboBox().setSelectedItem("Punkt");

		// Event-Listener für Clear-Button
		ActionListener a = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Das Model leeren und die View neu zeichnen
				drawableObjectsModel.clear();
				drawingPanelView.getDrawingPanel().repaint();
			}
		};

		// Anhängen des Listeners
		drawingPanelView.getButton().addActionListener(a);
	}

	/**
	 * Gibt die verwaltete View zurück
	 * 
	 * @return die View
	 */
	public DrawingPanelView getView() {
		return drawingPanelView;
	}

	/**
	 * Fügt das DrawableObject in das Model ein und fordert die View zum
	 * Neuzeichnen auf.
	 */
	public void processDrawableObject(DrawableObject drawableObject) {
		drawableObjectsModel.add(drawableObject);
		drawingPanelView.getDrawingPanel().repaint();
	}

	/**
	 * Löscht das temporäre DrawableObject aus dem Model und fordert die View
	 * zum Neuzeichnen auf.
	 */
	public void clearTemporaryDrawableObject() {
		drawableObjectsModel.remove(temporaryObject);
		drawingPanelView.getDrawingPanel().repaint();
		temporaryObject = null;
	}

	/**
	 * Fügt ein temporäres Objekt in das Model ein und fordert die View zum
	 * Neuzeichnen auf. Das Objekt kann mit clearTemporaryDrawableObject wieder
	 * aus dem Model entfernt werden.
	 */
	public void setTemporaryDrawableObject(DrawableObject drawableObject) {
		// Erst löschen
		if (temporaryObject != null) {
			this.clearTemporaryDrawableObject();
		}

		temporaryObject = drawableObject;
		this.processDrawableObject(temporaryObject);
	}
}
