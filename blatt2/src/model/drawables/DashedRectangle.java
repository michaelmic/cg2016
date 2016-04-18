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
		Point p = new Point(this.a);
		int dist = 10;
		
		// Horizontale Linie zeichnen
		if( this.a.x < this.e.x ) {
			while(p.x + dist < this.e.x) {
				g.drawLine(p.x, p.y, p.x + dist, p.y);
				g.drawLine(p.x, this.e.y, p.x + dist, this.e.y);
				p.x += 2*dist;
			}
			if(p.x < this.e.x) {
				g.drawLine(p.x, p.y, this.e.x, p.y);
				g.drawLine(p.x, this.e.y, this.e.x, this.e.y);
			}
		} else {
			while(p.x - dist > this.e.x) {	
				g.drawLine(p.x, p.y, p.x - dist, p.y);
				g.drawLine(p.x, this.e.y, p.x - dist, this.e.y);
				p.x -= 2*dist;
			}
			if(p.x > this.e.x) {
				g.drawLine(p.x, p.y, this.e.x, p.y);
				g.drawLine(p.x, this.e.y, this.e.x, this.e.y);
			}
		}
		//Vertikale zeichnen
		p = new Point(this.a);
		if( this.a.y < this.e.y ){
			while(p.y + dist < this.e.y) {
				g.drawLine(p.x, p.y, p.x, p.y + dist);
				g.drawLine(this.e.x, p.y, this.e.x, p.y + dist);
				p.y += 2*dist;
			}
			if(p.y < this.e.y) {
				g.drawLine(p.x, p.y, p.x, this.e.y);
				g.drawLine(this.e.x, p.y, this.e.x, this.e.y);
			}
		} else {
			while(p.y - dist > this.e.y) {
				g.drawLine(p.x, p.y, p.x, p.y - dist);
				g.drawLine(this.e.x, p.y, this.e.x, p.y - dist);
				p.y -= 2*dist;
			}
			if(p.y > this.e.y) {
				g.drawLine(p.x, p.y, p.x, this.e.y);
				g.drawLine(this.e.x, p.y, this.e.x, this.e.y);
			}
		}
	}
}
