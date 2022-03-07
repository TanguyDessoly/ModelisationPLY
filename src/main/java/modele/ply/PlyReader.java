package modele.ply;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import modele.exception.WrongPlyPropertiesException;

/**
 * Classe qui lie le fichier ply pour creer le modele
 *
 */
public class PlyReader {

	protected String path;
	protected IModele modelply;

	private boolean isHeader = true;
	private boolean isInVertex;
	private boolean isInFace;

	private int cptVertex = 0;

	private int cptFace = 0;

	private int color = 0;
	private boolean isAlpha = false;

	public PlyReader(File file) {
		if (file.exists() && !file.isDirectory()) {
			this.modelply = new ModelePly(file.getName());
			this.read(file.getAbsolutePath());
		}
	}

	public IModele getModelply() {
		return modelply;
	}

	public int getCptVertex() {
		return cptVertex;
	}

	public int getCptFace() {
		return cptFace;
	}

	/**
	 * Methode qui lis ligne par ligne le fichier ply
	 * 
	 * @param path
	 */
	public void read(String path) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))){
			String line = "";
			while ((line = br.readLine()) != null) {
				this.transformLine(line);
			}
		} catch (Exception e) {
		}
	}

	/**
	 * Methode qui transforme chaque ligne soit en face, point ou information
	 * 
	 * @param line
	 * @throws Exception
	 */
	public void transformLine(String line) throws Exception {
		String[] words = line.split(" |\\n");
		if (isHeader) {
			if (words[0].equals("end_header"))
				isHeader = false;
			else verifyIfNotWrong(line, words);
			findIfRGB();
		} else if (!line.equals("")) {
			if (cptVertex < modelply.getNbVertex()) {
				modelply.addVertex(new Vertex(Double.parseDouble(words[0]), Double.parseDouble(words[1]),
						Double.parseDouble(words[2])));
				defineMinMax(words);
				defineRGB(words);
				cptVertex++;
			} else {
				modelply.addFace(createFace(line));
				cptFace++;
			}
		}
	}

	private void verifyIfNotWrong(String line, String[] words) throws Exception, WrongPlyPropertiesException {
		if (words[0].equals("element")) {
			if (words[1].equals("vertex")) {
				this.modelply.setNbVertex(Integer.parseInt(words[2]));
				isInVertex = true;
			} else if (words[1].equals("face")) {
				this.modelply.setNbFace(Integer.parseInt(words[2]));
				isInFace = true;
			}
		} else if (words[0].equals("property")) {
			if (words[1].equals("uchar")) {
				if (words[2].equals("red") || words[2].equals("green") || words[2].equals("blue")) {
					if (!isInVertex && !isInFace)
						throw new Exception();
					else if (isInVertex) {
						if (isInFace) {
							color++;
							if (color == 3)
								isInVertex = false;
						} else {
							color++;
						}
					}
				} else if (words[2].equals("alpha")) {
					isAlpha = true;
				} else {
					modelply = new NullModele();
					throw new WrongPlyPropertiesException(line);
				}
			} else if (words[1].equals("float") || words[1].equals("float32")) {
				if (!words[2].equals("x") && !words[2].equals("y") && !words[2].equals("z")
						&& !words[2].equals("nx") && !words[2].equals("ny") && !words[2].equals("nz")
						&& !words[2].equals("confidence") && !words[2].equals("intensity") && !words[2].equals("u")
						&& !words[2].equals("v")) {
					modelply = new NullModele();
					throw new WrongPlyPropertiesException(line);
				}
			} else if (words.length >= 5 && words[0].equals("property")) {
				if (!words[1].equals("list") || !words[4].equals("vertex_indices")) {
					modelply = new NullModele();
					throw new WrongPlyPropertiesException(line);
				}
				if (!words[2].equals("uchar") && !words[2].equals("uint8")
						|| !words[3].equals("int") && !words[3].equals("int32")) {
					modelply = new NullModele();
					throw new WrongPlyPropertiesException(line);
				}
			} else if (words[0].equals("format")) {
				if (words.length >= 3 && (!words[1].equals("ascii") || !words[2].equals("1.0"))) {
					modelply = new NullModele();
					throw new WrongPlyPropertiesException(line);
				}
			} else if (!words[0].equals("ply") || !words[0].equals("comment")) {
				modelply = new NullModele();
				throw new WrongPlyPropertiesException(line);
			}
		}
	}

	private void findIfRGB() {
		if ((!isInVertex && color == 3) || (isInVertex && isInFace && color == 6)) {
			if (isAlpha)
				modelply.setRgb(false, true, true);
			else
				modelply.setRgb(false, true, false);
		} else if (isInVertex && color == 3) {
			if (isAlpha)
				modelply.setRgb(true, false, true);
			else
				modelply.setRgb(true, false, false);
		}
	}

	private void defineRGB(String[] words) {
		if (modelply.getRgbType() == 'V') {
			if (isAlpha) {
				modelply.getVertex()[cptVertex].setRgb(new RGB(Integer.parseInt(words[3]),
						Integer.parseInt(words[4]), Integer.parseInt(words[5]), Double.parseDouble(words[6])));
			} else
				modelply.getVertex()[cptVertex].setRgb(new RGB(Integer.parseInt(words[3]),
						Integer.parseInt(words[4]), Integer.parseInt(words[5])));
		}
	}

	private void defineMinMax(String[] words) {
		if (modelply.getMin()[0] > Double.parseDouble(words[0]))
			modelply.getMin()[0] = Double.parseDouble(words[0]);

		if (modelply.getMin()[1] > Double.parseDouble(words[1]))
			modelply.getMin()[1] = Double.parseDouble(words[1]);

		if (modelply.getMin()[2] > Double.parseDouble(words[2]))
			modelply.getMin()[2] = Double.parseDouble(words[2]);

		if (modelply.getMax()[0] < Double.parseDouble(words[0]))
			modelply.getMax()[0] = Double.parseDouble(words[0]);

		if (modelply.getMax()[1] < Double.parseDouble(words[1]))
			modelply.getMax()[1] = Double.parseDouble(words[1]);

		if (modelply.getMax()[2] < Double.parseDouble(words[2]))
			modelply.getMax()[2] = Double.parseDouble(words[2]);
	}

	public Face createFace(String line) {
		String words[] = line.split(" ");
		int[] tab = new int[Integer.parseInt(words[0])];
		Face face = new Face(tab, modelply);
		for (int i = 0; i < Integer.parseInt(words[0]); i++)
			tab[i] = Integer.parseInt(words[i + 1]);
		if (modelply.getRgbType() == 'F') {
			if (isAlpha) {
				face.setRgb(new RGB(Integer.parseInt(words[Integer.parseInt(words[0]) + 1]),
						Integer.parseInt(words[Integer.parseInt(words[0]) + 2]),
						Integer.parseInt(words[Integer.parseInt(words[0]) + 3]),
						Double.parseDouble(words[Integer.parseInt(words[0]) + 4])));
			} else {
				face.setRgb(new RGB(Integer.parseInt(words[Integer.parseInt(words[0]) + 1]),
						Integer.parseInt(words[Integer.parseInt(words[0]) + 2]),
						Integer.parseInt(words[Integer.parseInt(words[0]) + 3])));
			}
		}

		return face;
	}

}
