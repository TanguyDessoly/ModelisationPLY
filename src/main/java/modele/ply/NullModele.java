package modele.ply;

import modele.maths.Matrix3D;
import modele.subject_observer.Observer;
import modele.subject_observer.Subject;
import vue.View;

public class NullModele extends Subject implements IModele{
		

	protected Face[] faces=new Face[] {new Face(new int[]{0},this)};
	protected int cptFace=0;
	protected int nbFace=0;
	
	protected Vertex[] vertex=new Vertex[] {new Vertex(0,0,0)};
	protected int cptVertex=0;
	protected int nbVertex=0;

	private char rgbType = ' ';
	protected boolean alpha = false;
	
	protected String name="";
	
	protected double[] min = new double[3];
	protected double[] max = new double[3];
	protected double[] total = new double[2];

	
	public String getInfos() {return "Modèle corrompu !";}
	public boolean isNull() {return true;}
	public void negativeVertex() {}
	public void totalMinMax() {}
	public void scaleVertex() {}
	public void updateMinMax() {}
	public void startCenteredModel() {}
	public void RgbPoints(Face[] face) {}
	public void addVertex(Vertex vertex) {}
	public void addFace(Face face) {}
	public void addFace(int[] vertexIndexes) {}
	public void setFaces(Face[] faces) {}
	public boolean isAlpha() {return alpha;}
	public double[] getTotal() {return total;}
	public void setVertex(Vertex[] vertex) {}
	public Vertex getVertex(int index) {return vertex[index];}
	public Vertex[] getVertex() {	return vertex;}
	public int getCptFace() {return cptFace;}
	public int getCptVertex() {return cptVertex;}
	public double[] getCenter() {return new double[] { 0 , 0 , 0 };}
	public Face getFace(int index) {return faces[index];}
	public Face[] getFaces() {return faces;}	
	public void setNbFace(int nbFace) {}
	public int getNbFace() {return nbFace;}
	public void setNbVertex(int nbVertex) {}
	public int getNbVertex() {return nbVertex;}
	public void setName(String name) {}
	public String getName() {return name;}
	public double[] getMin() {return min;}
	public void setMin(double[] min) {}
	public double[] getMax() {return max;}
	public void setMin(double min, int index) {}
	public void setMax(double[] max) {}
	public void setMax(double max, int index) {}
	public void setRgb(boolean vertex, boolean face, boolean alpha) {}
	public char getRgbType() {return rgbType;}
	public void attach(View view) {}
	public void attach(Observer observer) {}
	public void detach(Observer observer) {}
	public void notifyObservers() {}
	public void notifyObservers(Object data) {}
	public Matrix3D getMatrice() { return new Matrix3D(new Double[0][0]); }
}
