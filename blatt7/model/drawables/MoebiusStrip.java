package model.drawables;

import model.Face;
import model.Vertex;

public class MoebiusStrip extends DrawableObject {
	
	public MoebiusStrip(double alphaStep, double rStep) {
		Vertex p1, p2=null, p3=null, p4;
		Face f;
		
		double alpha, r;
		for(alpha=0; alpha+alphaStep<=2*Math.PI; alpha+=alphaStep) {
			for(r=-1; r+rStep<=1; r+=rStep) {
				p1 = func(r,       alpha);
				p2 = func(r,       alpha+alphaStep);
				p3 = func(r+rStep, alpha+alphaStep);
				p4 = func(r+rStep, alpha);
				
				f = new Face(p1, p2, p3, p4);
				f.addNormal(p1);
				f.addNormal(p2);
				f.addNormal(p3);
				f.addNormal(p4);
				faces.add(f);
			}
		} 
				
		// alpha zurücksetzen
		alpha -= alphaStep;
		// Hier soll das letzte Segment geschlossen werden:
		
		for(r=-1; r+rStep<=1; r+=rStep) {
			p1 = func(-r,       alpha);
			p2 = func(r,       0);
			p3 = func(r+rStep, 0);
			p4 = func(-(r+rStep), alpha);
			
			f = new Face(p1, p2, p3, p4);
			f.addNormal(p1);
			f.addNormal(p2);
			f.addNormal(p3);
			f.addNormal(p4);
			faces.add(f);
		}
		
	}
	
	private Vertex func(double r, double alpha) {
		return new Vertex(
				Math.cos(alpha) * ( 1 + r/2 * Math.cos(alpha/2) ),
				Math.sin(alpha) * ( 1 + r/2 * Math.cos(alpha/2) ),
				r/2 * Math.sin(alpha/2),
				1
		);
	}
}
