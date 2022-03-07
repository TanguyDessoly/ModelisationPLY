package modele.maths;

import modele.ply.Vertex;

/** Classe employant les vecteurs */
public class Vector3D {

	private Double x, y, z;

	public Vector3D(Double x, Double y, Double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3D(Vertex a, Vertex b) {
		this(b.getX() - a.getX(), b.getY() - a.getY(), b.getZ() - a.getZ());
	}

	public Double getX() {
		return this.x;
	}

	public Double getY() {
		return this.y;
	}

	public Double getZ() {
		return this.z;
	}
	
	public double norme() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	public void normalize() {
		double norme = this.norme();
		this.x = x / norme;
		this.y = y / norme;
		this.z = z / norme;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		result = prime * result + ((z == null) ? 0 : z.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector3D other = (Vector3D) obj;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		if (z == null) {
			if (other.z != null)
				return false;
		} else if (!z.equals(other.z))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "x: " + x + " y: " + y + " z: " + z;
	}

}
