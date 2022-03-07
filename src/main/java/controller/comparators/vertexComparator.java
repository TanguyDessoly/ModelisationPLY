package controller.comparators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Comparator;

import controller.Explorer;

public class vertexComparator implements Comparator<File> {

	@Override
	public int compare(File f1, File f2) {
		if (!Explorer.isPly(f1) && !Explorer.isPly(f2))
			return 0;

		if (nbVertex(f1) < nbVertex(f2))
			return -1;
		else if (nbVertex(f1) > nbVertex(f2))
			return 1;
		return 0;
	}

	private int findNbVertex(String line) throws Exception {
		int nbVertex = 0;
		String[] words = line.split(" |\\n");
		if (words[0].equals("element")) {
			if (words[1].equals("vertex")) {
				nbVertex = Integer.parseInt(words[2]);
			}
		}
		return nbVertex;
	}

	private int nbVertex(File f) {
		int nbVertex = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()))) {
			String line = "";
			while ((line = br.readLine()) != null && (nbVertex == 0)) {
				nbVertex = this.findNbVertex(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nbVertex;
	}

}
