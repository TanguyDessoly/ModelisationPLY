package modele.ply;

import modele.maths.Matrix3D;
import modele.maths.Vector3D;

/** Methode pour la manipulation d'un modele */
public abstract class ModelTransforming {

	public static void zoom(IModele model, double factor) {
		double[] center = model.getCenter();
		Matrix3D vertexes = model.getMatrice();
		Matrix3D result = vertexes.multiply(Matrix3D.getTranslationMatrix(new Vector3D(-1*center[0], -1*center[1], -1*center[2])));
		result = result.multiply(Matrix3D.getZoomMatrix(factor));
		result = result.multiply(Matrix3D.getTranslationMatrix(new Vector3D(center[0], center[1], center[2])));
		model.setVertex(result.asVertexArray());
	}
	
	public static void translate(IModele model, Vector3D vect) {
		Matrix3D vertexes = model.getMatrice();
		Matrix3D result = vertexes.multiply(Matrix3D.getTranslationMatrix(vect));
		model.setVertex(result.asVertexArray());
	}
	
	public static void rotateOnXAxis(IModele model, double angle) {
		double[] center = model.getCenter();
		Matrix3D vertexes = model.getMatrice();
		Matrix3D result = vertexes.multiply(Matrix3D.getTranslationMatrix(new Vector3D(-1*center[0], -1*center[1], -1*center[2])));
		result = result.multiply(Matrix3D.getXAxisRotationMatrix(angle));
		result = result.multiply(Matrix3D.getTranslationMatrix(new Vector3D(center[0], center[1], center[2])));
		model.setVertex(result.asVertexArray());
	}
	
	public static void rotateOnYAxis(IModele model, double angle) {
		double[] center = model.getCenter();
		Matrix3D vertexes = model.getMatrice();
		Matrix3D result = vertexes.multiply(Matrix3D.getTranslationMatrix(new Vector3D(-1*center[0], -1*center[1], -1*center[2])));
		result = result.multiply(Matrix3D.getYAxisRotationMatrix(angle));
		result = result.multiply(Matrix3D.getTranslationMatrix(new Vector3D(center[0], center[1], center[2])));
		model.setVertex(result.asVertexArray());
	}

	public static void rotateOnZAxis(IModele model, double angle) {
		double[] center = model.getCenter();
		Matrix3D vertexes = model.getMatrice();
		Matrix3D result = vertexes.multiply(Matrix3D.getTranslationMatrix(new Vector3D(-1*center[0], -1*center[1], -1*center[2])));
		result = result.multiply(Matrix3D.getZAxisRotationMatrix(angle));
		result = result.multiply(Matrix3D.getTranslationMatrix(new Vector3D(center[0], center[1], center[2])));
		model.setVertex(result.asVertexArray());
	}
	
}
