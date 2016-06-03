/**
 *
 */
package model;

/**
 * Repräsentiert die synthetische Kamera der Viewing-Pipeline. Enthält alle
 * Methoden zur Berechnung der nötigen Matrizen und aus Convenience Gründen auch
 * die zum Mapping auf das Device.
 *
 * @author Nicolas Neubauer
 *
 */
public class Camera {

    private Vertex eyePoint; // =prp
    private Vertex lookAtPoint; // =vrp
    private Vertex upVector; // vuv

    private double dmin; // Abstand zur Nearplane
    private double dmax; // Abstand zur Farplane

    private double fov; // Brennweite in Grad
    private double aspect; // Seitenverhältnis

    private int xsize; // Breite des Ausgabegeräts in Pixeln
    private int ysize; // Höhe des Ausgabegeräts in Pixeln

    /**
     * Erstellt eine neue Kamera
     *
     * @param eyePoint
     * @param lookAtPoint
     * @param upVector
     * @param fov
     * @param xsize
     * @param ysize
     * @param near
     * @param far
     */
    public Camera(Vertex eyePoint, Vertex lookAtPoint, Vertex upVector,
                  double fov, int xsize, int ysize, double near, double far) {
        this.eyePoint = eyePoint;
        this.lookAtPoint = lookAtPoint;
        this.upVector = upVector;
        this.fov = fov;
        this.xsize = xsize;
        this.ysize = ysize;
        this.dmin = near;
        this.dmax = far;

        this.aspect = ysize / (double) xsize;
    }

    /**
     * Berechnet die Matrix zur Transformation aus den Weltkoordinaten in die
     * View-Reference-Koordinaten auf Basis der angegeben Kameradaten
     *
     * @return die Matrix
     */
    public Matrix WC2VRC() {
        // Berechne U,V,N-Basis mit lookAtPoint als Ursprung

        // N ergibt sich aus der Blickrichtung PRP - VRP
        Vertex n = eyePoint.minus(lookAtPoint);
        n.normalize();

        // V ergibt sich aus dem auf die Bildebene projezierten VUV
        Vertex c = n.crossProduct(upVector);
        Vertex v = c.crossProduct(n);
        v.normalize();

        // U ergibt sich aus dem Kreuzprodukt von V und N
        Vertex u = v.crossProduct(n);
        u.normalize();

        // Matrix aufstellen
        double[][] matrix = new double[4][4];

        matrix[0][0] = u.x;
        matrix[1][0] = u.y;
        matrix[2][0] = u.z;
        matrix[3][0] = 0;

        matrix[0][1] = v.x;
        matrix[1][1] = v.y;
        matrix[2][1] = v.z;
        matrix[3][1] = 0;

        matrix[0][2] = n.x;
        matrix[1][2] = n.y;
        matrix[2][2] = n.z;
        matrix[3][2] = 0;

        Vertex origin = new Vertex(lookAtPoint);
        origin.homogenize();

        matrix[0][3] = origin.x;
        matrix[1][3] = origin.y;
        matrix[2][3] = origin.z;
        matrix[3][3] = origin.w; // =1

        Matrix newBase = new Matrix(matrix);

        // Noch invertieren
        Matrix wc2vrc = newBase.invert();

        return wc2vrc;
    }

    /**
     * Berechnet die Matrix zur Transformation aus dem
     * View-Reference-Koordinatensystem in das
     * Normalized-Projection-Koordinatensystem auf Basis der angegebenen
     * Kameradaten
     *
     * @return die berechnete Matrix
     */
    public Matrix VRC2NPC() {
        // Verschieben des Koordinatensystems in den Augenpunkt
        // Obacht, wir sind bereits im VRC
        Matrix translateToEye = Matrix.createIdentity(4);
        Matrix wc2vrc = WC2VRC();
        Vertex eye = wc2vrc.multiply(new Vertex(eyePoint));
        eye.homogenize();

        translateToEye.setValue(0, 3, -eye.x);
        translateToEye.setValue(1, 3, -eye.y);
        translateToEye.setValue(2, 3, -eye.z);


        // Spiegelung an der xy-Ebene
        Matrix mirrorXY = Matrix.createIdentity(4);
        mirrorXY.setValue(2, 2, -1);

        // Transformation in den Einheitswürfel (siehe Skript)

        // d ist Abstand von Auge zu ViewPlane in z-Richtung
        double d = eyePoint.minus(lookAtPoint).norm();


        // Zunächst berechne ymin/ymax
        double xmax = Math.abs(Math.tan((Math.PI * 2 * (fov / 360)) / 2) * d);
        double xmin = -1 * xmax;

        double ymax = aspect * xmax;
        double ymin = -1 * ymax;


        Matrix toCube = Matrix.createIdentity(4);
        toCube.setValue(0, 0, d / (xmax - xmin));

        toCube.setValue(1, 1, d / (ymax - ymin));

        toCube.setValue(0, 2, 0.5 * (1 - (xmin + xmax) / (xmax - xmin)));
        toCube.setValue(1, 2, 0.5 * (1 - (ymin + ymax) / (ymax - ymin)));
        toCube.setValue(2, 2, dmax / (dmax - dmin));
        toCube.setValue(3, 2, 1);

        toCube.setValue(2, 3, (-dmax * dmin) / (dmax - dmin));
        toCube.setValue(3, 3, 0);

        // Verschiebe um -1 entlang der z-Achse
        Matrix translateBack = Matrix.createIdentity(4);
        translateBack.setValue(2, 3, -1);

        // Nun Multiplikation aller Matrizen
        Matrix vrc2npc = mirrorXY.multiply(translateBack.multiply(toCube
                .multiply(mirrorXY.multiply(translateToEye))));

        return vrc2npc;
    }

    /**
     * Berechnet die Transformationsmatrix vom NPC in das
     * Device-Koordinatensystem
     *
     * @return die berechnete Matrix
     */
    public Matrix NPC2DC() {
        Matrix npc2dc = Matrix.createIdentity(4);
        npc2dc.setValue(0, 0, xsize);
        npc2dc.setValue(1, 1, -ysize);
        npc2dc.setValue(1, 3, ysize);
        return npc2dc;
    }

    /**
     * Liefert den aktuellen Augenpunkt
     * @return Augenpunkt
     */
    public Vertex getEyePoint() {
        return eyePoint;
    }

    /**
     * Setzt den Augenpunkt neu
     * @param p neuer Augenpunkt
     */
    public void setEyePoint(Vertex p) {
        this.eyePoint = p;
    }

    /**
     * Liefert den aktuellen UpVector
     * @return UpVector
     */
    public Vertex getUpVector() {
        return upVector;
    }

    /**
     * Setzt den UpVector neu
     * @param p der neue UpVector
     */
    public void setUpVector(Vertex p) {
        this.upVector = p;
    }
}
