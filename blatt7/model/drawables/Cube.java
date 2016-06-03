/**
 *
 */
package model.drawables;

import model.Face;
import model.Vertex;

/**
 * Repräsentiert einen 3D-Kubus
 * @author Nicolas Neubauer
 *
 */
public class Cube extends DrawableObject {


	/**
	 * Erzeugt einen neuen (Einheits-)Kubus im Koordinatenursprung skaliert auf Kantenlänge 2
	 */
	public Cube() {

		Face side;
		Vertex normal;
		side = new Face(new Vertex(-1,-1,1,1), new Vertex(-1,1,1,1), new Vertex(1,1,1,1), new Vertex(1,-1,1,1));
		normal = new Vertex(0,0,1,0);
		side.addNormal(normal);
		side.addNormal(normal);
		side.addNormal(normal);
		side.addNormal(normal);
		faces.add(side);

		side = new Face(new Vertex(1,-1,1,1), new Vertex(1,1,1,1), new Vertex(1,1,-1,1), new Vertex(1,-1,-1,1));
		normal = new Vertex(1,0,0,0);
		side.addNormal(normal);
		side.addNormal(normal);
		side.addNormal(normal);
		side.addNormal(normal);
		faces.add(side);

		side = new Face(new Vertex(1,-1,-1,1), new Vertex(1,1,-1,1), new Vertex(-1,1,-1,1), new Vertex(-1,-1,-1,1));
		normal = new Vertex(0,0,-1,0);
		side.addNormal(normal);
		side.addNormal(normal);
		side.addNormal(normal);
		side.addNormal(normal);
		faces.add(side);

		side = new Face(new Vertex(-1,-1,-1,1), new Vertex(-1,1,-1,1), new Vertex(-1,1,1,1), new Vertex(-1,-1,1,1));
		normal = new Vertex(-1,0,0,0);
		side.addNormal(normal);
		side.addNormal(normal);
		side.addNormal(normal);
		side.addNormal(normal);
		faces.add(side);

		side = new Face(new Vertex(-1,1,1,1), new Vertex(-1,1,-1,1), new Vertex(1,1,-1,1), new Vertex(1,1,1,1));
		normal = new Vertex(0,1,0,0);
		side.addNormal(normal);
		side.addNormal(normal);
		side.addNormal(normal);
		side.addNormal(normal);
		faces.add(side);

		side = new Face(new Vertex(-1,-1,1,1), new Vertex(-1,-1,-1,1), new Vertex(1,-1,-1,1), new Vertex(1,-1,1,1));
		normal = new Vertex(0,-1,0,0);
		side.addNormal(normal);
		side.addNormal(normal);
		side.addNormal(normal);
		side.addNormal(normal);
		faces.add(side);

	}
}
