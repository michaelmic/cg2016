package model.drawables;

import java.awt.Graphics;

public class DashedPolygon extends Polygon{
	

	public DashedPolygon(Point... a) {
		super(a);
		//Konstruktor
		this.finished = false;
	}	

	@Override
	public void addPoint(Point p) {
		this.addLine(new DashedLine(last, p));
		last = p;
	}
}
