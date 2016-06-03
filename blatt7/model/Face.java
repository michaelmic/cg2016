/**
 *
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Beschreibt eine Fläche im 3D-Raum
 * @author Nicolas Neubauer
 *
 */
public class Face {

	/**
	 * Konvention: An der i-ten Stelle der Liste der Normalen findet sich die
	 * Normale für den Punkt an der i-ten Stelle der Liste der Vertices.
	 */
	private List<Vertex> vertices;
	private List<Vertex> normals;

	/**
	 * Eine Fläche besteht aus einer Menge an Punkten
	 *
	 * @param vertices
	 *            die Punkte
	 */
	public Face(Vertex... vertices) {

		this.vertices = new ArrayList<Vertex>();
		this.normals = new ArrayList<Vertex>();

		for (Vertex v : vertices) {
			this.vertices.add(v);
		}

	}

	/**
	 * Legt eine "leere" Fläche an.
	 */
	public Face() {
		this.vertices = new ArrayList<Vertex>();
		this.normals = new ArrayList<Vertex>();
	}

	/**
	 * Einen Vertex hinzufügen
	 *
	 * @param v
	 *            Vertex
	 */
	public void addVertex(Vertex v) {
		this.vertices.add(v);
	}

	/**
	 * Eine Normale hinzufügen
	 *
	 * @param n
	 *            eine Normale
	 */
	public void addNormal(Vertex n) {
		this.normals.add(n);
	}

	/**
	 * Convenience Methode.
	 *
	 * Berechnet eine Flächennormale durch Bildung eines geeigneten
	 * Kreuzproduktes von Vektoren zwischen den Vertices der Fläche
	 * und weist diese Normale dann allen Eckpunkten als Normale zu.
	 */
	public void createNormalsForFace() {
		Vertex u12 = vertices.get(1).minus(vertices.get(0));
		u12.homogenize();
		Vertex u23 = vertices.get(2).minus(vertices.get(1));
		u23.homogenize();

		Vertex normal = u12.crossProduct(u23);

		normal.normalize();

		normal.w = 0;

		normals.clear();

		for (int i = 0; i < vertices.size(); i++) {
			normals.add(normal);
		}
	}

	/**
	 * Liefert die Punkte zurück
	 *
	 * @return die Punkte als Liste
	 */
	public List<Vertex> getVertices() {
		return vertices;
	}

	/**
	 * Liefert die Normalen zurück
	 * @return die Normalen als Liste
	 */
	public List<Vertex> getNormals() {
		return normals;
	}

}
