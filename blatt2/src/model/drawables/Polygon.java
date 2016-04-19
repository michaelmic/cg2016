package model.drawables;

import java.awt.Graphics;
import java.util.LinkedList;

public class Polygon extends DrawableObject {
	public LinkedList<Line> lines;
	public boolean finished;
	public Point last; 
	
	public Polygon(Polygon copy) {
		this.lines = new LinkedList<>();
		this.last = new Point(copy.lines.getFirst().a);
		
		for (int i = 0; i < copy.lines.size(); i++) {
			Point newPoint = new Point(copy.lines.get(i).e);
			this.addPoint(newPoint);
			last = newPoint;
		}
		finished = true;
		last = copy.lines.getFirst().a;
	}
	
	public Polygon(Point...a) {
		//Konstruktor

		lines = new LinkedList<>();
		if(a.length == 0) {
			last = new Point(0, 0);
		} else {
			last = a[0];
		}
		
		for (int i = 1; i < a.length; i++) {
			addPoint(a[i]);
		}
		
		this.finished = true;
	}
	
	public void addPoint(Point p) {
		this.addLine(new Line(last, p));
		last = p;
	}
	
	public void addLine(Line l) {
		lines.add(l);
	}
	
	public void paint(Graphics g){ 
		//Paint Methode
		for (Line line : this.lines) {
			line.paint(g);
		}
		// letzte Linie zeichnen (Start zu Ende)
		if(finished) {
			Line last = new Line(lines.getFirst().a, lines.getLast().e);
			last.paint(g);
		}
	}

}
