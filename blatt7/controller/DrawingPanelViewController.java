package controller;

import java.util.LinkedList;

import controller.listener.MoveListener;

import model.Camera;
import model.Matrix;
import model.Vertex;
import model.drawables.Cube;
import model.drawables.DrawableObject;
import model.drawables.MoebiusStrip;
import model.drawables.Sphere;
import view.DrawingPanelView;
import view.Renderer;

/**
 * Der Controller, der eine DrawingPanelView verwaltet und das dazugehörige
 * Model in Form einer Liste von DrawableObjects.
 *
 * @author Nicolas Neubauer
 *
 */
public class DrawingPanelViewController {

	// View
	private DrawingPanelView drawingPanelView;

	// Model, die Szene
	private LinkedList<DrawableObject> drawableObjects;

	// Renderer
	private Renderer renderer;

	/**
	 * Der Konstruktor initialisiert die View und legt die Listener an.
	 */
	public DrawingPanelViewController() {
		final int WIDTH = 640;
		final int HEIGHT = 480;

		// Liste von 3D-Objekten initalisieren
		drawableObjects = new LinkedList<DrawableObject>();

		// Renderer initialisieren

		// Zunächst Kamera festlegen
		Vertex eyePoint = new Vertex(4, 10, 10, 1);
		Vertex upVector = new Vertex(4, 11, 10, 1); // Einfach 1 hoeher als die
													// Kamera
		Vertex lookAtPoint = new Vertex(0, 0, 0, 1);

		// Eine neue Kamera erstellen mit obigen Eigenschaften, einer Brennweit
		// von 22,5° und
		// einer nearPlane bei 0.1 und einer farPlane bei 100
		// Außerdem wird der Kamera die Bildgröße übergeben, damit das
		// Seitenverhältnis
		// berechnet werden kann
		Camera c = new Camera(
				eyePoint, lookAtPoint, upVector,
				22.5, WIDTH, HEIGHT,
				0.1, 100
		);

		// Der Renderer kennt die Kamera und weiß was zu rendern ist
		renderer = new Renderer(c, drawableObjects);

		// Die DrawingPanelView wird mit der gleichen Auflösung wie oben
		// initalisiert
		drawingPanelView = new DrawingPanelView(WIDTH, HEIGHT, renderer);

		// Einen Cube in die Szene einfügen
		Cube cube = new Cube();
		drawableObjects.add(cube);

		// TODO: (A2) add sphere
		Matrix move_right = new Matrix(new double[][] {
			{1, 0, 0, 3},
			{0, 1, 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1},
		});
		Sphere sphere = new Sphere(64);
		sphere.applyTransform(move_right);
		drawableObjects.add(sphere);
		// TODO: (A3) add moebius strip
		Matrix move_left = new Matrix(new double[][] {
			{1, 0, 0, -3},
			{0, 1, 0,  0},
			{0, 0, 1,  0},
			{0, 0, 0,  1},
		});
		MoebiusStrip moebius = new MoebiusStrip(.05, .1);;
		moebius.applyTransform(move_left);
		drawableObjects.add(moebius);

		// Listener anhängen
		MoveListener m = new MoveListener(this);

		drawingPanelView.getDrawingPanel().addMouseListener(m);
		drawingPanelView.getDrawingPanel().addMouseMotionListener(m);

		// Neu rendern
		this.getView().repaint();
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
	 * Setzt den Augenpunkt auf den übergebenen Vertex und rendert neu
	 *
	 * @param p
	 *            neuer Augenpunkt
	 */
	public void changeEyePointTo(Vertex p) {
		renderer.getCamera().setEyePoint(p);
		this.getView().repaint();
	}

	/**
	 * Liefert Kopie des aktuellen Augenpunkts
	 *
	 * @return Augenpunkt
	 */
	public Vertex getEyePoint() {
		return new Vertex(renderer.getCamera().getEyePoint());
	}

	/**
	 * Setzt dem UpVektor neu und rendert anschließend neu
	 *
	 * @param p
	 *            der neue UpVektor
	 */
	public void changeUpVectorTo(Vertex p) {
		renderer.getCamera().setUpVector(p);
		this.getView().repaint();
	}

	/**
	 * Liefert Kopie des aktuellen UpVektors
	 *
	 * @return UpVektor
	 */
	public Vertex getUpVector() {
		return new Vertex(renderer.getCamera().getUpVector());
	}

}
