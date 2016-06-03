package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Ein DrawingPanel ist eine weiße Zeichenfläche auf der gerendert werden kann.
 *
 * @author Nicolas Neubauer
 *
 */
@SuppressWarnings("serial")
public class DrawingPanel extends JPanel {

	private Renderer renderer;

	/**
	 * Konstruktor, der die bevorzugte Größe und einen Renderer
	 *
	 * @param width
	 * @param height
	 * @param drawableObjectsModel
	 */
	public DrawingPanel(int width, int height, Renderer renderer) {
		this.renderer = renderer;

		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException(
					"Groesse muss >= 1x1 Pixel sein."
			);
		}

		// Setze die bevorzugte Größe
		Dimension d = new Dimension(width, height);
		setPreferredSize(d);

		// Setze die Farben
		setForeground(Color.BLACK);
		setBackground(Color.WHITE);
	}

	/**
	 * Zeichne alle DrawableObjects aus dem Model mit dem Renderer auf die
	 * Zeichenfläche
	 */
	public void paint(Graphics g) {
		super.paint(g);

		// rendere die Szene mit dem Renderer
		renderer.render(g);

	}

}
