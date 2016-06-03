package model.drawables;

import model.Face;
import model.Vertex;

public class Sphere extends DrawableObject{

	public Sphere(int n) {
		if(n < 2 || n%2!=0) throw new RuntimeException("Bad parameters!");
		
		double alpha, phi, theta;
		Vertex p1, p2, p3, p4, r1;
		Face f;
		alpha = (2*Math.PI) / n;
		
		// triangles
		p1 = new Vertex(0, 0,  1, 1);
		r1 = new Vertex(0, 0, -1, 1);
		for(int i=0; i<n; i++) {
			phi = i*alpha;			
			p2 = new Vertex(Math.sin(alpha)*Math.cos(phi), 
					Math.sin(alpha)*Math.sin(phi), Math.cos(alpha), 1);
			p3 = new Vertex(Math.sin(alpha)*Math.cos(phi+alpha), 
					Math.sin(alpha)*Math.sin(phi+alpha), Math.cos(alpha), 1);
			
			f = new Face(p1, p2, p3);
			f.addNormal(p1);
			f.addNormal(p2);
			f.addNormal(p3);
			faces.add(f);
			
			p2 = new Vertex(Math.sin(alpha)*Math.cos(phi), 
					Math.sin(alpha)*Math.sin(phi), -Math.cos(alpha), 1);
			p3 = new Vertex(Math.sin(alpha)*Math.cos(phi+alpha), 
					Math.sin(alpha)*Math.sin(phi+alpha), -Math.cos(alpha), 1);
			
			f = new Face(r1, p2, p3);
			f.addNormal(r1);
			f.addNormal(p2);
			f.addNormal(p3);
			faces.add(f);
		}
		
		// quads
		for(int i=0; i<(n/2 - 1); i++) {
			for(int j=0; j<n; j++) {
				phi = j*alpha;
				theta = i*alpha;

				p1 = new Vertex(Math.sin(theta)*Math.cos(phi), 
						Math.sin(theta)*Math.sin(phi), Math.cos(theta), 1);
				p2 = new Vertex(Math.sin(theta)*Math.cos(phi+alpha), 
						Math.sin(theta)*Math.sin(phi+alpha), Math.cos(theta), 1);
				p3 = new Vertex(Math.sin(theta+alpha)*Math.cos(phi+alpha), 
						Math.sin(theta+alpha)*Math.sin(phi+alpha), 
						Math.cos(theta+alpha), 1);
				p4 = new Vertex(Math.sin(theta+alpha)*Math.cos(phi), 
						Math.sin(theta+alpha)*Math.sin(phi), 
						Math.cos(theta+alpha), 1);
				
				f = new Face(p1, p2, p3, p4);
				f.addNormal(p1);
				f.addNormal(p2);
				f.addNormal(p3);
				f.addNormal(p4);
				faces.add(f);			
			}
		}
			
	}
	
}
