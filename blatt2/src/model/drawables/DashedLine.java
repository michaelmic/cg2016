/**
 * 
 */
package model.drawables;

import java.awt.Graphics;

/**
 * Eine DashedLine ist eine Linie, die im XOR-Modus gestrichelt gezeichnet wird.
 * 
 * @author Nicolas Neubauer
 * 
 */
// TODO: (A3) Klasse vervollständigen
public class DashedLine extends Line {
	// Abstand zwischen den gestrichelten Linien
	private final int DISTANCE = 10;
	private int dashCount;
	// falls true wird gezeichnet, sonst nicht
	private boolean toggleDraw;
	
	/**
	 * @see Line
	 */
	public DashedLine(Point a, Point e) {
		super(a, e);
		
		dashCount = 0;
		toggleDraw = true;
	}

	// TODO: (A3) Methoden überschreiben, damit die Linie
	// gestrichelt und im XOR-Modus gezeichnet wird
	@Override
	protected void setPixel(int x, int y, Graphics g) {
		
		// Zaehler erhoehen
		dashCount++;
		// ggf toggleDraw invertieren (sind bereits 10 Pixel gesetzt worden?)
		if (dashCount >= DISTANCE) {
			toggleDraw = !toggleDraw;
			dashCount = 0;
		}
		
		// der Stift ist auf dem Papier abgesetzt falls toggleDraw == true
		if(toggleDraw) {
			super.setPixel(x, y, g);
		}
	}

}
