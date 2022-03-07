package modele.comparators;

import java.util.Comparator;

import modele.ply.Face;

/** Classe qui compare 2 faces en fonction de la coordonnée z */
public class ComparatorZ implements Comparator<Face> {

	@Override
	public int compare(Face f1, Face f2) {
		if (f1.maxZOfFace() < f2.maxZOfFace()) {
			return -1;
		} else if (f1.maxZOfFace() > f2.maxZOfFace()) {
			return 1;
		} else {
			return 0;
		}
	}

}
