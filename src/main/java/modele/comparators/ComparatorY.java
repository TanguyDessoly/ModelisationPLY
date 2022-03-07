package modele.comparators;

import java.util.Comparator;

import modele.ply.Face;

/** Classe qui compare 2 faces en fonction de la coordonnée y */
public class ComparatorY implements Comparator<Face> {

	@Override
	public int compare(Face f1, Face f2) {
		if (f1.maxYOfFace() < f2.maxYOfFace()) {
			return -1;
		} else if (f1.maxYOfFace() > f2.maxYOfFace()) {
			return 1;
		} else {
			return 0;
		}
	}

}
