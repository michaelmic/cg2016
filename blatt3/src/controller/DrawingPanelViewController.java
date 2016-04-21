package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.Stack;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import controller.listener.BezierListener;
// import controller.listener.BezierListener;
import controller.listener.LineListener;
import controller.listener.OwnSymbolListener;
import controller.listener.PointListener;
import controller.listener.PolygonListener;
import controller.listener.RectangleListener;
import controller.listener.TransformListener;

import model.Matrix;
import model.drawables.DrawableObject;
import model.drawables.Point;
import model.drawables.Polygon;

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
	private Stack<Matrix> matrixStackModel;

	// private BezierListener bezierListener;

	/**
	 * Der Konstruktor initialisiert die View und legt die Listener an.
	 */
	public DrawingPanelViewController() {
		drawableObjectsModel = new LinkedList<DrawableObject>();
		matrixStackModel = new Stack<Matrix>();
		drawingPanelView = new DrawingPanelView(640, 480, drawableObjectsModel);

		// das UI anpassen
		drawingPanelView.getButton().setText("Clear");
	
		// Diverse Listener für unterschiedliche Drawables anlegen
		MouseInputAdapter[] listener = {
			new PointListener(this),
			new LineListener(this, false),
			new LineListener(this, true),
			new RectangleListener(this),
			new PolygonListener(this, false),
			new PolygonListener(this, true),
			new OwnSymbolListener(this, false),
			new OwnSymbolListener(this, true),
			new TransformListener(this),
			new BezierListener(this),
		};
		
		// Listener in die ComboBox packen
		drawingPanelView
			.getComboBox()
			.setModel(new DefaultComboBoxModel<>(listener));


		// Event-Listener für Button
		ActionListener a = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Das Model leeren und die View neu zeichnen
				drawableObjectsModel.clear();
				drawingPanelView.getDrawingPanel().repaint();
			}
		};

		// ChangeListener um festzustellen, was gezeichnet werden soll
		ActionListener c = new ActionListener() {
			/**
			 * Hängt den passenden Listener an die Zeichenfläche
			 */
			public void actionPerformed(ActionEvent e) {
				// Hänge das aktuell gewählte Objekt als Listener an die
				// View
				@SuppressWarnings("unchecked")
				JComboBox<MouseInputAdapter> cb = 
						(JComboBox<MouseInputAdapter>) e.getSource();
			
				Object item = cb.getSelectedItem();
				if (cb.getSelectedItem() instanceof MouseInputAdapter) {
					changeMouseInputListenerTo((MouseInputAdapter) item);
				} else {
					throw new RuntimeException(
						"Non-MouseInputAdapter Objects in ComboBox!"
					);
				}
			}

			/**
			 * Convenience-Methode zum umsetzen des aktuellen MouseListeners an
			 * der Zeichenfläche der verwalteten View
			 * 
			 * @param newListener
			 *            der zu setzende Listener, alle anderen werden
			 *            ausgehängt
			 */
			private void changeMouseInputListenerTo(
					MouseInputListener newListener) {

				// InputListeners
				MouseListener[] currentInputListeners = drawingPanelView
						.getDrawingPanel().getMouseListeners();

				for (MouseListener curListener : currentInputListeners) {
					drawingPanelView.getDrawingPanel().removeMouseListener(
							curListener);
				}

				// MotionListeners
				MouseMotionListener[] currentMotionListners = drawingPanelView
						.getDrawingPanel().getMouseMotionListeners();
				for (MouseMotionListener curListener : currentMotionListners) {
					drawingPanelView.getDrawingPanel()
							.removeMouseMotionListener(curListener);
				}

				drawingPanelView.getDrawingPanel()
						.addMouseListener(newListener);
				drawingPanelView.getDrawingPanel().addMouseMotionListener(
						newListener);
			}

		};

		// ActionListener um die Matrix auf den Stack zu pushen
		ActionListener addMatrix = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// Lokales Array erstellen
				double[][] matrix = new double[3][3];

				// Versuche die Textfelder als Doubles zu interpretieren
				try {

					// Durch alle Textfelder gehen und Doubles rausziehen
					for (int row = 0; row < 3; row++) {
						for (int col = 0; col < 3; col++) {
							String text = drawingPanelView
								.getMatrixPanel()
								.getTextFields()[row][col]
								.getText();
							// Erspart das Eintippen von Nullen
							if (text.isEmpty()) {
								text = "0";
							}
							matrix[row][col] = Double.parseDouble(text);
						}
					}

				} catch (NumberFormatException ex) {
					JOptionPane
							.showMessageDialog(
									SwingUtilities
											.getWindowAncestor(drawingPanelView),
									"Eine oder mehrere Zahl(en) ist/sind fehlerhaft formatiert.",
									"Fehler", JOptionPane.ERROR_MESSAGE);
					return; // Keine weitere Verarbeitung
				}

				// Matrix erstellen und dem Stack hinzufügen
				Matrix m = new Matrix(matrix);
				matrixStackModel.push(m);

				// View aktualisieren
				drawingPanelView
					.getMatrixPanel()
					.getMatrixList()
					.setListData(matrixStackModel);

			}
		};

		// ActionListener um den Stack zu leeren
		ActionListener clearMatrices = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				matrixStackModel.clear();
				// View aktualisieren
				drawingPanelView
					.getMatrixPanel()
					.getMatrixList()
					.setListData(matrixStackModel);
			}
		};

		// Anhängen der Listener
		drawingPanelView.getButton().addActionListener(a);
		drawingPanelView.getComboBox().addActionListener(c);

		drawingPanelView.getMatrixPanel().getAddButton().addActionListener(
				addMatrix);
		drawingPanelView.getMatrixPanel().getClearButton().addActionListener(
				clearMatrices);

		// Event einmal auslösen, damit der korrekte Listener angehängt wird
		drawingPanelView.getComboBox().setSelectedIndex(0);
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
		if (temporaryObject != null)
			this.clearTemporaryDrawableObject();

		temporaryObject = drawableObject;
		this.processDrawableObject(temporaryObject);

	}

	/**
	 * Transformiert alle im Model verwalteten Polygone mit deren
	 * transformBy-Methode, wenn der übergebene Punkt innerhalb des Polygons
	 * liegt, also isNear() true zurück gibt. Aktualisiert anschließend die
	 * View.
	 */
	public void transformDrawableObjectsNear(Point p) {

		Matrix transformMatrix = getCombinedMatrixFromModel();

		for (DrawableObject d : drawableObjectsModel) {
			if (d instanceof Polygon) {
				Polygon polygon = (Polygon) d;
				if (polygon.isNear(p))
					polygon.transformBy(transformMatrix);
			}
		}

		drawingPanelView.getDrawingPanel().repaint();
	}

	/**
	 * Convenience-Methode, um aus dem Stack von Matrizen die Produktmatrix zu
	 * berechnen. Es wird die oberste von links mit der zweiten multipliziert
	 * usw.
	 * 
	 * @return die Produktmatrix des Stacks
	 */
	@SuppressWarnings("unchecked")
	private Matrix getCombinedMatrixFromModel() {
		Matrix returnMatrix = Matrix.createIdentity(3);

		Stack<Matrix> modelCopy = (Stack<Matrix>) matrixStackModel.clone();

		while (!modelCopy.empty())
			returnMatrix = returnMatrix.multiply(modelCopy.pop());

		return returnMatrix;
	}

}
