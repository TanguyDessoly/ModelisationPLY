package modele.maths;

import modele.ply.Vertex;

/** Classe employant les matrices */
public class Matrix3D {

	private Double[][] matrix;

	public Matrix3D(Double[][] matrix) {
		this.matrix = matrix;
	}

	public Double[][] getMatrix() {
		return this.matrix;
	}

	public static Matrix3D getTranslationMatrix(Vector3D vector) {
		return new Matrix3D(new Double[][] { new Double[] { 1d, vector.getZ(), vector.getY(), vector.getX() },
				new Double[] { 0d, 1d, 0d, 0d }, new Double[] { 0d, 0d, 1d, 0d }, new Double[] { 0d, 0d, 0d, 1d } });
	}

	public static Matrix3D getZoomMatrix(Double factor) {
		return new Matrix3D(new Double[][] { new Double[] { 1d, 0d, 0d, 0d }, new Double[] { 0d, factor, 0d, 0d },
				new Double[] { 0d, 0d, factor, 0d }, new Double[] { 0d, 0d, 0d, factor } });
	}

	public static Matrix3D getXYSymetricMatrix() {
		return new Matrix3D(new Double[][] { new Double[] { 1d, 0d, 0d, 0d }, new Double[] { 0d, 1d, 0d, 0d },
				new Double[] { 0d, 0d, -1d, 0d }, new Double[] { 0d, 0d, 0d, 1d } });
	}

	public static Matrix3D getXZSymetricMatrix() {
		return new Matrix3D(new Double[][] { new Double[] { 1d, 0d, 0d, 0d }, new Double[] { 0d, -1d, 0d, 0d },
				new Double[] { 0d, 0d, 1d, 0d }, new Double[] { 0d, 0d, 0d, 1d } });
	}

	public static Matrix3D getYZSymetricMatrix() {
		return new Matrix3D(new Double[][] { new Double[] { -1d, 0d, 0d, 0d }, new Double[] { 0d, 1d, 0d, 0d },
				new Double[] { 0d, 0d, 1d, 0d }, new Double[] { 0d, 0d, 0d, 1d } });
	}

	public static Matrix3D getZAxisRotationMatrix(Double angle) {
		return new Matrix3D(new Double[][] { new Double[] { 1d, 0d, 0d, 0d }, new Double[] { 0d, 1d, 0d, 0d },
				new Double[] { 0d, 0d, Math.cos(angle), -1 * Math.sin(angle) },
				new Double[] { 0d, 0d, Math.sin(angle), Math.cos(angle) } });
	}

	public static Matrix3D getXAxisRotationMatrix(Double angle) {
		return new Matrix3D(new Double[][] { new Double[] { 1d, 0d, 0d, 0d },
				new Double[] { 0d, Math.cos(angle), -1 * Math.sin(angle), 0d },
				new Double[] { 0d, Math.sin(angle), Math.cos(angle), 0d }, new Double[] { 0d, 0d, 0d, 1d } });
	}

	public static Matrix3D getYAxisRotationMatrix(Double angle) {
		return new Matrix3D(new Double[][] { new Double[] { 1d, 0d, 0d, 0d },
				new Double[] { 0d, Math.cos(angle), 0d, -1 * Math.sin(angle) }, new Double[] { 0d, 0d, 1d, 0d },
				new Double[] { 0d, Math.sin(angle), 0d, Math.cos(angle) } });
	}

	/**
	 * Méthode qui multiplie 2 matrice entre-elles
	 * 
	 * @param other , Matrice Externe
	 * @return le résulatat de la multiplication sous forme d'une nouvelle matrice
	 */
	public Matrix3D multiply(Matrix3D other) {
		Double[][] result = new Double[this.matrix.length][other.matrix[0].length];
		for (int i = 0; i < this.matrix.length; i++) {
			for (int j = 0; j < other.matrix[0].length; j++) {
				result[i][j] = 0d;
				for (int k = 0; k < other.matrix[0].length; k++)
					result[i][j] += this.matrix[i][k] * other.matrix[k][j];
			}
		}
		return new Matrix3D(result);
	}

	/**
	 * Méthode qui additionne 2 matrice entre-elles
	 * 
	 * @param other , Matrice Externe
	 * @return le résulatat de l'addition sous forme d'une nouvelle matrice
	 */
	public Matrix3D add(Matrix3D other) {
		int length = this.matrix.length;
		Double[][] result = new Double[length][length];

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				result[i][j] = 0d;
				result[i][j] += (this.matrix[i][j] + other.matrix[i][j]);
			}
		}
		return new Matrix3D(result);
	}

	public Vertex[] asVertexArray() {
		Vertex[] res = new Vertex[this.matrix.length];
		for (int i = 0; i < this.matrix.length; ++i) {
			res[i] = new Vertex(this.matrix[i][3], this.matrix[i][2], this.matrix[i][1]);
		}
		return res;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[1].length; j++) {
				sb.append(String.format("%12f", matrix[i][j]) + ",\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}