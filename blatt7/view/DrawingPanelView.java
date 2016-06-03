package view;

import java.awt.BorderLayout;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class DrawingPanelView extends JPanel {

	// View-Components
	private DrawingPanel drawingPanel;

	/**
	 * Konstruktor, der eine neue View anlegt.
	 *
	 * @param preferredWidth
	 * @param preferredHeight
	 * @param drawableObjectsModel
	 *            das Model, das an das DrawingPanel übergeben wird
	 */

	public DrawingPanelView(int preferredWidth,
			int preferredHeight,
			Renderer renderer) {

		if (preferredWidth < 1 || preferredHeight < 1) {
			throw new IllegalArgumentException(
					"Groesse des DrawingPanels muss 1x1 oder groesser sein."
			);
		}

		this.setLayout(new BorderLayout());

		// Besorge ein DrawingPanel und übergebe den Renderer
		drawingPanel = new DrawingPanel(
				preferredWidth,
				preferredHeight,
				renderer
		);
		this.add(drawingPanel, BorderLayout.CENTER);
	}

	/**
	 * @return the drawingPanel
	 */
	public DrawingPanel getDrawingPanel() {
		return drawingPanel;
	}

}
