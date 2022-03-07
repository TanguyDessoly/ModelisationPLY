package modele.ply;

import modele.maths.Matrix3D;
import modele.maths.Vector3D;
import modele.subject_observer.Subject;

/**
 * Classe qui regroupe toutes les informations d'un fichier ply
 */
public class ModelePly extends Subject implements IModele {

	protected Vertex[] vertex;
	protected int nbVertex;
	protected int cptVertex;
	
	protected int cptFace;
	protected Face[] faces;
	protected int nbFace;

	private char rgbType = ' ';
	protected boolean alpha = false;
	
	protected String name;
	
	protected double[] min = new double[3];
	protected double[] max = new double[3];
	protected double[] total = new double[2];


	public ModelePly(String name) {
		this.cptFace = 0;
		this.cptVertex = 0;
		this.name = name;
	}

	public void negativeVertex() {
		for (int i = 0; i < vertex.length; i++) {
			if (min[0] < 0)
				vertex[i].x -= min[0];
			if (min[1] < 0)
				vertex[i].y -= min[1];
			if (min[2] < 0)
				vertex[i].z -= min[2];
		}

		for (int i = 0; i < vertex.length; i++) {
			if (max[0] < 0)
				vertex[i].x -= max[0];
			if (max[1] < 0)
				vertex[i].y -= max[1];
			if (max[2] < 0)
				vertex[i].z -= max[2];
		}
		updateMinMax();
	}

	/**
	 * Methode qui determine le plus petit et plus grand point d'un fichier et met a
	 * jour la variable total
	 */
	public void totalMinMax() {
		total[0] = min[0];
		total[1] = max[0];
		for (int i = 1; i < min.length; i++) {
			if (total[0] > min[i])
				total[0] = min[i];
			if (total[1] < max[i])
				total[1] = max[i];
		}
	}

	public void scaleVertex() {
		negativeVertex();
		int taille = 300;
		double echelle = taille / total[1];
		for (int i = 0; i < vertex.length; i++) {
			vertex[i].x = vertex[i].x * echelle;
			vertex[i].y = vertex[i].y * echelle;
			vertex[i].z = vertex[i].z * echelle;
		}
		updateMinMax();
		startCenteredModel();
	}

	public void updateMinMax() {
		min[0] = vertex[0].x;
		min[1] = vertex[0].y;
		min[2] = vertex[0].z;
		max[0] = vertex[0].x;
		max[1] = vertex[0].y;
		max[2] = vertex[0].z;
		for (Vertex v : vertex) {
			min[0] = Math.min(v.x, min[0]);
			min[1] = Math.min(v.y, min[1]);
			min[2] = Math.min(v.z, min[2]);

			max[0] = Math.max(v.x, max[0]);
			max[1] = Math.max(v.y, max[1]);
			max[2] = Math.max(v.z, max[2]);
		}
		totalMinMax();
	}

	public void startCenteredModel() {
		double canvasW = 775;
		double canvasH = 375;
		Vertex centreCanvas = new Vertex((double) canvasW / 2, (double) canvasH / 2, (double) canvasH / 2);
		Vertex centreModel = new Vertex(getCenter()[0], getCenter()[1], getCenter()[2]);
		ModelTransforming.translate(this, new Vector3D(centreModel, centreCanvas));
		updateMinMax();
	}

	public void setVertex(Vertex[] vertex) {
		this.vertex = vertex;
		this.updateMinMax();
		this.notifyObservers();
	}

	public Vertex getVertex(int index) {
		return this.vertex[index];
	}

	public Vertex[] getVertex() {
		return vertex;
	}

	public void addVertex(Vertex vertex) {
		this.vertex[this.cptVertex] = vertex;
		this.cptVertex++;
	}

	public void setNbVertex(int nbVertex) {
		this.nbVertex = nbVertex;
		this.vertex = new Vertex[this.nbVertex];
	}

	public int getNbVertex() {
		return nbVertex;
	}

	public void setFaces(Face[] faces) {
		this.faces = faces;
	}

	public void addFace(Face face) {
		this.faces[this.cptFace] = face;
		this.cptFace++;
	}

	public void addFace(int[] vertexIndexes) {
		this.faces[this.cptFace] = new Face(vertexIndexes, this);
		this.cptFace++;
	}

	public Face getFace(int index) {
		return this.faces[index];
	}

	public Face[] getFaces() {
		return faces;
	}

	public void setNbFace(int nbFace) {
		this.nbFace = nbFace;
		this.faces = new Face[this.nbFace];
	}

	public int getNbFace() {
		return nbFace;
	}

	public Matrix3D getMatrice() {
		Double[][] vertexMatrix = new Double[getNbVertex()][4];

		for (int i = 0; i < getNbVertex(); i++) {
			vertexMatrix[i][3] = getVertex(i).getX();
			vertexMatrix[i][2] = getVertex(i).getY();
			vertexMatrix[i][1] = getVertex(i).getZ();
			vertexMatrix[i][0] = 1d;
		}

		return new Matrix3D(vertexMatrix);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getInfos() {
		StringBuilder builder = new StringBuilder("Nom du fichier : ");
		builder.append(this.getName() + "\n");

		builder.append("Nombre de points : " + this.getNbVertex() + "\n");
		builder.append("Nombre de faces : " + this.getNbFace() + "\n");

		return builder.toString();
	}

	/**
	 * Getter du point le plus petit d'un modele
	 * 
	 * @return retourne le point minimal
	 */
	public double[] getMin() {
		return min;
	}

	/**
	 * Setter du point le plus petit d'un modele avec un tableau de ses donnees
	 * 
	 * @param min
	 */
	public void setMin(double[] min) {
		for (int i = 0; i < min.length; i++) {
			this.min[i] = min[i];
		}
	}

	/**
	 * Setter du point le plus petit d'un modele avec index
	 * 
	 * @param min
	 * @param index
	 */
	public void setMin(double min, int index) {
		this.min[index] = min;
	}

	/**
	 * Getter du point le plus grand d'un modele
	 * 
	 * @return retourne le point maximal
	 */
	public double[] getMax() {
		return max;
	}

	/**
	 * Setter du point le plus grand d'un modele avec un tableau de ses donnees
	 * 
	 * @param min
	 */
	public void setMax(double[] max) {
		for (int i = 0; i < max.length; i++) {
			this.max[i] = max[i];
		}
	}

	/**
	 * Setter du point le plus grand d'un modele avec index
	 * 
	 * @param max
	 * @param index
	 */
	public void setMax(double max, int index) {
		this.max[index] = max;
	}

	/**
	 * Setter qui indique ou se trouve le rgb
	 * 
	 * @param vertex
	 * @param face
	 * @param alpha
	 */
	public void setRgb(boolean vertex, boolean face, boolean alpha) {
		if (vertex) {
			rgbType = 'V';
		} else if (face) {
			rgbType = 'F';
		}
		if (alpha)
			alpha = true;
	}
	
	/**
	 * Getter du mode de couleur
	 * 
	 * @return 'V' pour couleurs sur vertex / 'F' pour couleurs sur faces
	 */
	public char getRgbType() {
		return rgbType;
	}

	/**
	 * Methode qui compte le nombre de rgb present sur chaque point d'un modele
	 * 
	 * @return un tableau de couleur avec le nombre de couleur present pour chaque
	 *         couleur
	 */
	public void RgbPoints(Face[] face) {
		int r = 0;
		int g = 0;
		int b = 0;
		double a = 0;
		for (int i = 0; i < nbFace; i++) {
			for (int j = 0; j < faces[i].getVertex().length; j++) {
				r += vertex[faces[i].getVertex()[j]].getRgb().getRed();
				g += vertex[faces[i].getVertex()[j]].getRgb().getGreen();
				b += vertex[faces[i].getVertex()[j]].getRgb().getBlue();
				if (alpha)
					a += vertex[faces[i].getVertex()[j]].getRgb().getAlpha();
			}

			if (alpha) {
				face[i].setRgb(new RGB(r / faces[i].getVertex().length, g / faces[i].getVertex().length,
						b / faces[i].getVertex().length, a / faces[i].getVertex().length));
			} else
				face[i].setRgb(new RGB(r / faces[i].getVertex().length, g / faces[i].getVertex().length,
						b / faces[i].getVertex().length));
			r = 0;
			g = 0;
			b = 0;
		}
	}

	public boolean isAlpha() {
		return alpha;
	}

	public double[] getCenter() {
		return new double[] { (max[0] + min[0]) / 2, (max[1] + min[1]) / 2, (max[2] + min[2]) / 2 };
	}

	public double[] getTotal() {
		return total;
	}

	public int getCptFace() {
		return cptFace;
	}

	public int getCptVertex() {
		return cptVertex;
	}

	public boolean isNull() {
		return false;
	}

}
