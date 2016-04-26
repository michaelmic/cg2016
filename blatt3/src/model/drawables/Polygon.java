package model.drawables;

import java.awt.Graphics;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import model.Matrix;

/**
 * Ein grafisches Objekt, dass ein Polygon repraesentiert. Die einzelnen
 * Polygonpunkte sind in einer Liste abgelegt. Der erste Punkt ist nicht doppelt
 * abgelegt, d.h. die letzte Kante ist vom letzten Punkt zum ersten Punkt zu
 * ziehen.
 * 
 * @author Nicolas Neubauer
 */
public class Polygon extends DrawableObject {

	protected List<Point> points; // Liste mit Eckpunkten
	protected boolean filled;

	/**
	 * Erstellt ein Polygon
	 * 
	 * @param points
	 *            Punkte-Liste
	 * @param filled
	 *            soll das Polygon gefüllt sein?
	 */
	public Polygon(List<Point> points, boolean filled) {
		this.points = points;
		this.filled = filled;
	}

	/**
	 * Zeichnet das Polygon in den uebergebenen grafischen Kontext und fuellt es
	 * gegebenenfalls.
	 * 
	 * @param g
	 *            der grafische Kontext, in den dieses Objekt sich zeichnen soll
	 */
	public void paint(Graphics g) {
		Line l;
		Point prev = null;

		for (Point p : points) {
			if (prev != null) {
				l = new Line(prev, p);
				l.paint(g);
			}
			prev = p;
		}

		// Polygon schließen, Linie zeichnen
		l = new Line(prev, points.get(0));
		l.paint(g);
		
		if (filled) {
			scanlineFill(g);
		}
	}
	
	private class Edge implements Comparable<Edge> {
		int y_top;
		int delta_y;
		int x_int;
		int delta_x;
		
		boolean sort_y; // falls false: sort x_int;
		
		public Edge(Edge o) {
			this.y_top = o.y_top;
			this.delta_y = o.delta_y;
			this.x_int = o.x_int;
			this.delta_x = o.delta_x;
			
			this.sort_y = o.sort_y;
		}
		
		public Edge(Point from, Point to) {
			if(from.y < to.y) {
				y_top = to.y;
				delta_y = to.y - from.y; 
				x_int = to.x;
				delta_x = 1 / ( (to.y - from.y) / (to.x - from.x) );
			} else {
				y_top = from.y;
				delta_y = from.y - to.y; 
				x_int = from.x;
				delta_x = 1 / ( (from.y - to.y) / (from.x - to.x) );
			}
			sort_y = true;
		}

		@Override
		public int compareTo(Edge o) {
			if(sort_y) {
				return this.y_top - o.y_top;
			} else {
				return this.x_int - o.x_int;
			}
		}
	}
	
	/**
	 * Wendet das Scanline-Verfahren zum Fuellen eines Polygons an
	 * 
	 * @param g
	 *            der grafische Kontext, in den dieses Objekt sich zeichnen soll
	 */
	private void scanlineFill(Graphics g) {
		// TODO: (A1) Scanline Algorithmus implementieren
		//       (es können auch weitere Hilfs-Methoden angelegt werden)
		
		Point prev_p = null;
		List<Edge> edges = new LinkedList<>();
		// Kanten erstellen
		for(Point p : this.points) {
			if (prev_p != null) {
				edges.add(new Edge(prev_p, p));
			}
			prev_p = p;
		}		
		
		// Kanten sortieren
		Collections.sort(edges);
		
		// Scanline
		int max_y = 200; // TODO sinnvoller Wert
		for(int scan = max_y; scan < max_y; scan--) {
			List<Edge> intersects = new LinkedList<>();
			for(Edge e : edges) {
				if (e.y_top < scan && e.y_top + e.delta_y > scan) {
					Edge copy = new Edge(e);
					copy.sort_y = false;
					intersects.add(copy);
				}
			}
			Collections.sort(intersects);
			
			for(int i = 1; i < intersects.size(); i+=2) {
				Edge e1 = intersects.get(i-1);
				Edge e2 = intersects.get(i);
				
				new Line(new Point(e1.x_int, scan), new Point(e2.x_int, scan));

				Polygon.calculate(e1);
				Polygon.calculate(e2);
			}
		}
		
		return;
	}
	
	private static void calculate(Edge e) {
		e.x_int += e.delta_x;
		e.delta_y--;
	}

	/**
	 * Gibt true zurück, wenn der Punkt im Polygon liegt, false sonst.
	 * 
	 * @param p
	 *            der zu prüfende Punkt
	 * @return true, wenn p innerhalb des Polygons liegt
	 */
<<<<<<< HEAD
	public boolean isNear(Point p) {
        int x1 = points.get(points.size()-1).x; 
        int y1 = points.get(points.size()-1).y;
        int x2 = points.get(0).x;
        int y2 = points.get(0).y;
        boolean inside = false;
        boolean startUeber = y1 >= p.y ? true : false;
        for (int i=1; i<points.size(); i++) {
        boolean endUeber = y2 >= p.y ? true : false;
        if ((startUeber != endUeber &&
=======
	public boolean isNear(Point p) {       
	        int x1 = points.get(points.size()-1).x; 
	        int y1 = points.get(points.size()-1).y;
	        int x2 = points.get(0).x;
	        int y2 = points.get(0).y;
	        boolean inside = false;
	        boolean startUeber = y1 >= p.y ? true : false;
	        for (int i=1; i<points.size(); i++) {
	        boolean endUeber = y2 >= p.y ? true : false;
	        if ((startUeber != endUeber &&
>>>>>>> ebbe37389ff7bdc12d8878b2c112c40f94a0cc57
	        (double)(p.y*(x2-x1)- x2*y1 + x1*y2)/(y2-y1)>=p.x))
	        inside = !inside; 
	        startUeber = endUeber;
	        y1=y2; x1=x2; x2=points.get(i).x; y2=points.get(i).y;
<<<<<<< HEAD
        } 
        return inside;
=======
	        } 
	        return inside;
>>>>>>> ebbe37389ff7bdc12d8878b2c112c40f94a0cc57
	}

	/**
	 * Transformiert das Polygon mit Hilfe der übergebenen
	 * Transformationsmatrix.
	 * 
	 * @param m
	 *            die Transformationsmatrix
	 */
	public void transformBy(Matrix m) {
		
		// TODO: (A3) Implementieren
	}
}