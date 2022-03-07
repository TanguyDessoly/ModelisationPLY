package modele.ply;

import modele.maths.Matrix3D;
import modele.subject_observer.Observer;

public interface IModele {
	
	public String getInfos();
	public boolean isNull();
	
	public void negativeVertex();
	public void scaleVertex();
	
	public double[] getMin();
	public void setMin(double[] min);
	public void setMin(double min, int index);
	public double[] getMax();
	public void setMax(double[] max);
	public void setMax(double max, int index);
	public void totalMinMax();
	public void updateMinMax();

	public double[] getCenter();
	public void startCenteredModel();
	
	public void RgbPoints(Face[] face);	
	public void setRgb(boolean vertex, boolean face, boolean alpha);
	public char getRgbType();
	
	public void addVertex(Vertex vertex);
	public void setVertex(Vertex[] vertex);
	public Vertex getVertex(int index);
	public Vertex[] getVertex();
	public int getCptVertex();
	public void setNbVertex(int nbVertex);
	public int getNbVertex();
	
	public void addFace(Face face);
	public void addFace(int[] vertexIndexes);
	public void setFaces(Face[] faces);
	public Face getFace(int index);
	public Face[] getFaces();
	public void setNbFace(int nbFace);
	public int getNbFace();
	public int getCptFace();
	
	public boolean isAlpha();
	
	public double[] getTotal();
	
	public Matrix3D getMatrice();
	
	public void setName(String name);
	public String getName();
	

	public void attach(Observer observer);
	public void detach(Observer observer);
	public void notifyObservers();
}
