package controller.comparators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Comparator;

import controller.Explorer;

public class faceComparator implements Comparator<File> {

	@Override
	public int compare(File f1, File f2) {
		if (!Explorer.isPly(f1) && !Explorer.isPly(f2))
			return 0;

		if (nbFace(f1) < nbFace(f2))
			return -1;
		else if (nbFace(f1) > nbFace(f2))
			return 1;
		return 0;
	}

	private int findNbFace(String line) throws Exception {
		int nbFace = 0;
		String[] words = line.split(" |\\n");
		if (words[0].equals("element")) {
			if (words[1].equals("face")) {
				nbFace = Integer.parseInt(words[2]);
			}
		}
		return nbFace;
	}

	private int nbFace(File f) {

		int nbFace = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()))) {
			String line = "";
			while ((line = br.readLine()) != null && (nbFace == 0)) {
				nbFace = this.findNbFace(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nbFace;
	}

}
