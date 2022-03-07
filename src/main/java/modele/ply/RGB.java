package modele.ply;

/** Classe employant les couleurs */
public class RGB {
	
	private int red;
	private int green;
	private int blue;
	private double alpha;

	public RGB(int r, int g, int b, double a) {
		red = r;
		green = g;
		blue = b;
		alpha = a;
	}
	
	public RGB(int r, int g, int b) {
		this(r, g, b, 1.0);
	}
	
	public RGB() {
		this(0, 0, 0);
	}

	public int getRed() {
		return red;
	}

	public int getGreen() {
		return green;
	}

	public int getBlue() {
		return blue;
	}

	/**
	 * Getter de l'opacite
	 * 
	 * @return l'opacite
	 */
	public double getAlpha() {
		return alpha;
	}
	
	@Override
	public String toString() {
		return "R:"+red+"G:"+green+"B:"+blue;
	}

}
