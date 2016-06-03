/**
 *
 */
package model.drawables;

import java.util.ArrayList;
import java.util.List;

import model.Face;
import model.Matrix;
import model.Vertex;

/**
 * Basisklasse für zeichenbare 3D-Objekte
 *
 * @author Nicolas Neubauer
 *
 */
public abstract class DrawableObject {

    protected List<Face> faces;

    /**
     * Erzeugt ein neues DrawableObject, initalisiert die Liste der Flächen.
     */
    public DrawableObject() {
        faces = new ArrayList<Face>();
    }

    /**
     * Gibt eine Liste mit den einzelnen Flächen des Objekts zurück
     *
     * @return Liste mit Flächen
     */
    public List<Face> getFaces() {
        return faces;
    }

    /**
     * Zählt die Vertices des Objekts
     *
     * @return Anzahl der Vertices
     */
    public int countVertices() {
        int count = 0;
        for (Face f : this.faces) {
            count += f.getVertices().size();
        }
        return count;
    }

    /**
     * Wendet eine affine Transformation in Form einer 4x4 Matrix auf alle
     * Vertices aller Flächen an. Transformiert die Normalen der Flächen mit der
     * transponierten Inversen.
     *
     * @param t
     *            die Transformationsmatrx (4x4)
     */
    public void applyTransform(Matrix t) {

        if (t.getRows() != 4 || t.getCols() != 4) {
            throw new IllegalArgumentException(
                    "Transform-Matrix has to be 4x4."
            );
        }

        Matrix nt = t.invert().transpose();
        for (Face f : this.faces) {
            for (int i = 0; i < f.getVertices().size(); i++) {
                f.getVertices().set(i, t.multiply(f.getVertices().get(i)));

                Vertex normal = new Vertex(f.getNormals().get(i));
                normal = nt.multiply(normal);
                normal.w = 0; // Normale sind Richtungsvektoren

                f.getNormals().set(i, normal);
            }
        }
    }
}
