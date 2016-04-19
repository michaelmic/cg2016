/**
 * 
 */
package model.drawables;

import java.awt.Graphics;

/**
 * Repräsentiert ein Rectangle, das gestrichelt und im XOR-Modus gezeichnet
 * wird.
 * 
 * @author Nicolas Neubauer
 * 
 */
// TODO: (A3) Klasse vervollständigen, um Rechteck gestrichelt zu zeichnen
public class DashedRectangle extends Rectangle {
	/**
	 * @see Rectangle
	 */
	public DashedRectangle(Point a, Point e) {
		super(a, e);
	}
	
	@Override
	public void paint(Graphics g) {
		DashedLine l1 = new DashedLine(a, new Point(a.x, e.y));
		DashedLine l2 = new DashedLine(a, new Point(e.x, a.y));
		DashedLine l3 = new DashedLine(e, new Point(a.x, e.y));
		DashedLine l4 = new DashedLine(e, new Point(e.x, a.y));
		
		l1.paint(g); l2.paint(g); l3.paint(g); l4.paint(g);
	}
}
