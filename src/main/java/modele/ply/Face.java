package modele.ply;

/**
 * Classe employant les faces
 */
public class Face implements Comparable<Face> {

	protected int[] tab;
	protected RGB rgb;
	protected static IModele model;
	
	public Face(int[] tab,RGB rgb, IModele modelply) {
		this.tab = tab;
		this.rgb=rgb;
		Face.model = modelply;
	}
	
	public Face(int[] tab, IModele modelply) {
		this(tab, null, modelply);
	}

	public int[] getVertex() {
		return this.tab;
	}

	public RGB getRgb() {
		return rgb;
	}

	public void setRgb(RGB rgb) {
		this.rgb = rgb;
	}

	public double[] getXs() {
		double[] xs = new double[tab.length];
		for (int i = 0; i < tab.length; i++) {
			xs[i] = model.getVertex(tab[i]).getX();
		}
		return xs;
	}

	public double[] getYs() {
		double[] ys = new double[tab.length];
		for (int i = 0; i < tab.length; i++) {
			ys[i] = model.getVertex(tab[i]).getY();
		}
		return ys;
	}

	public double[] getZs() {
		double[] zs = new double[tab.length];
		for (int i = 0; i < tab.length; i++) {
			zs[i] = model.getVertex(tab[i]).getZ();
		}
		return zs;
	}

	public double maxXOfFace() {
		double max = this.getXs()[0];
		for (double x : this.getXs()) {
			max = (x > max) ? max = x : max;
		}
		return max;
	}

	public double maxYOfFace() {
		double max = this.getYs()[0];
		for (double y : this.getYs()) {
			max = (y > max) ? max = y : max;
		}
		return max;
	}

	public double maxZOfFace() {
		double max = this.getZs()[0];
		for (double z : this.getZs()) {
			max = (z > max) ? max = z : max;
		}
		return max;
	}
	
	public Vertex getCenter() {
		double x = 0, y = 0, z = 0;
		double[] xs = getXs(), ys = getYs(), zs = getZs();
		
		for(double x1: xs) {
			x += x1;
		}
		for(double y1: ys) {
			y += y1;
		}
		for(double z1: zs) {
			z += z1;
		}
		
		return new Vertex(x/xs.length, y/ys.length, z/zs.length);
	}

	public int compareTo(Face o) {
		return (int) (this.maxZOfFace() * 100 - o.maxZOfFace() * 100);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0; i < tab.length; i++) {
			sb.append(tab[i]);
		}
		return sb.toString();
	}
}
