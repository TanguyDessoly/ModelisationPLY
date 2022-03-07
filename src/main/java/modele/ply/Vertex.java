package modele.ply;

/** Classe employant les points */
public class Vertex {

	protected double x;
	protected double y;
	protected double z;
	
	protected RGB rgb;


	public Vertex(double x, double y, double z, RGB rgb) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.rgb=rgb;
	}
	
	public Vertex(double x, double y, double z) {
		this(x,y,z,null);
	}

	public double getX() {
		return this.x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return this.y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return this.z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public RGB getRgb() {
		return rgb;
	}

	public void setRgb(RGB rgb) {
		this.rgb = rgb;
	}

	public String toString() {
		return "[x=" + this.x + "; y=" + this.y + "; z=" + this.z + "]";
	}

}
