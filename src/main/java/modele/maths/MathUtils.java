package modele.maths;

/** Class qui implemente le produit sclaire et le produit vectoriel*/
public abstract class MathUtils {

	public static Vector3D vectorProduct(Vector3D u, Vector3D v) {
		double ux = u.getX();
		double uy = u.getY();
		double uz = u.getZ();
		double vx = v.getX();
		double vy = v.getY();
		double vz = v.getZ();
		return new Vector3D(uy*vz-uz*vy, uz*vx-ux*vz, ux*vy-uy*vx);
	}
	
	public static double scalarProduct(Vector3D u, Vector3D v) {
		double ux = u.getX();
		double uy = u.getY();
		double uz = u.getZ();
		double vx = v.getX();
		double vy = v.getY();
		double vz = v.getZ();
		return ux*vx + uy*vy + uz*vz;
	}
	
}
