package vue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import modele.comparators.ComparatorX;
import modele.comparators.ComparatorY;
import modele.comparators.ComparatorZ;
import modele.maths.MathUtils;
import modele.maths.Vector3D;
import modele.ply.Face;
import modele.ply.IModele;
import modele.ply.PlyReader;
import modele.ply.RGB;
import modele.ply.Vertex;
import modele.subject_observer.Observer;

public abstract class View extends Stage implements Observer {
	protected Scene scene;

	protected Controller controller;
	
	protected FXMLLoader loader = new FXMLLoader();
	protected Parent root;

	protected String title = "SuperMoteur3D";

	protected PlyReader plyReader;
	protected IModele model;

	protected Canvas canvasFace;
	
	protected double xBefore;
	protected double yBefore;
	
	protected Color strokesColor = Color.BLACK;
	protected RGB fillColor = new RGB(255, 0, 0);

	protected boolean stroke = true;
	protected boolean fill = true;
	protected boolean colors = true;
	protected boolean doLight = true;

	public View(File file) throws IOException {
		init(file);
	}
	
	public View(IModele modele) throws IOException {
		init(modele);
	}

	/** Méthode qui initialise un changement de modèle */
	public void init(File file) {
		if (file == null) {
			title = "SuperMoteur3D";
			return;
		}
		plyReader = new PlyReader(file);
		model = plyReader.getModelply();

		if (model.isNull() && Controller.getHorloge() != null) {
			Controller.getHorloge().setBlocked(true);
			title = "SuperMoteur3D";
			return;
		}

		if (model.getRgbType() == 'V')
			model.RgbPoints(model.getFaces());
		model.scaleVertex();
		model.attach(this);
		Controller.getHorloge().setBlocked(false);

		title = "SuperMoteur3D" + " | " + model.getName();

		if(scene != null) {
			this.initCanvas();
			draw();
		}
		
		this.setTitle(title);
	}

	/** Méthode qui initialise un changement de modèle */
	public void init(IModele model) {
		this.model = model;
		if (model.isNull()) {
			title = "SuperMoteur3D";
			return;
		}

		if (model.isNull() && Controller.getHorloge() != null) {
			Controller.getHorloge().setBlocked(true);
			title = "SuperMoteur3D";
			return;
		}

		if (model.getRgbType() == 'V')
			model.RgbPoints(model.getFaces());
		model.scaleVertex();
		model.attach(this);
		Controller.getHorloge().setBlocked(false);

		title = "SuperMoteur3D" + " | " + model.getName();

		if(scene != null) {
			this.initCanvas();
			draw();
		}

		this.setTitle(title);

	}
	
	/** Méthode par defaut qui initialise un canvas de face */
	protected void initCanvas() {
		canvasFace = (Canvas) scene.lookup("#canvasFace");
		configCanvas(canvasFace);
	}
	
	protected void draw() {
		drawFace(model, canvasFace);
	}

	protected void configCanvas(Canvas canvas) {
		clearCanvas(canvas);
		canvas.setOnMousePressed((MouseEvent e) -> {
			xBefore = e.getX();
			yBefore = e.getY();
			Controller.getHorloge().setBlocked(true);
		});
		canvas.setOnMouseReleased((MouseEvent e) -> {
			Controller.getHorloge().setBlocked(false);
		});
	}

	protected void clearCanvas(Canvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
	
	public static RGB toRGBCode(Color color){
		int r, g, b;
		r = (int) (color.getRed() * 255);
		g = (int) (color.getGreen() * 255);
		b = (int) (color.getBlue() * 255);
		return new RGB(r, g, b);
    }
	
	public void setStrokesColor(Color strokesColor) {
		this.strokesColor = strokesColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = toRGBCode(fillColor);
	}

	protected void drawFace(IModele model, Canvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();

		// Algorithme du peintre.
		Face[] faces = model.getFaces();
		Arrays.sort(faces, new ComparatorZ());

		for (int i = 0; i < faces.length; i++) {
			double alpha;
			if (doLight) {
				Vector3D ab = new Vector3D(model.getVertex(faces[i].getVertex()[0]), model.getVertex(faces[i].getVertex()[1]));
				Vector3D ac = new Vector3D(model.getVertex(faces[i].getVertex()[0]), model.getVertex(faces[i].getVertex()[2]));

				Vector3D normal = MathUtils.vectorProduct(ab, ac);
				Vector3D lightDir = new Vector3D(faces[i].getCenter(), new Vertex(1, 1, 1));

				normal.normalize();
				lightDir.normalize();

				alpha = MathUtils.scalarProduct(normal, lightDir);
				alpha *= -1;
				alpha = (alpha < 0d) ? 0 : alpha*2;
			} else {
				alpha = 1;
			}

			if (model.getFaces()[0].getRgb() != null && colors) {
				int red = (int) (model.getFaces()[i].getRgb().getRed() * alpha);
				int green = (int) (model.getFaces()[i].getRgb().getGreen() * alpha);
				int blue = (int) (model.getFaces()[i].getRgb().getBlue() * alpha);

				red = (red < 0) ? 0 : (red > 255) ? 255 : red;
				green = (green < 0) ? 0 : (green > 255) ? 255 : green;
				blue = (blue < 0) ? 0 : (blue > 255) ? 255 : blue;
				gc.setFill(Paint.valueOf(Color.rgb(red, green,
						blue, model.getFaces()[i].getRgb().getAlpha()).toString()));
			} else {
				int red = (int) (fillColor.getRed() * alpha);
				red = (red > 255) ? 255 : red;
				
				int green = (int) (fillColor.getGreen() * alpha);
				green = (green > 255) ? 255 : green;

				int blue = (int) (fillColor.getBlue()* alpha);
				blue = (blue > 255) ? 255 : blue;

				gc.setFill(Paint.valueOf(Color.rgb(red, green, blue, 1).toString()));
			}
			
			gc.setStroke(strokesColor);
			
			if (fill)
				gc.fillPolygon(faces[i].getXs(), faces[i].getYs(), faces[i].getXs().length);
			if (stroke)
				gc.strokePolygon(faces[i].getXs(), faces[i].getYs(), faces[i].getXs().length);
		}
	}

	protected void drawTop(IModele model, Canvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();

		// Algorithme du peintre.
		Face[] faces = model.getFaces();
		Arrays.sort(faces, new ComparatorY());

		for (int i = 0; i < faces.length; i++) {
			double alpha;
			if (doLight) {
				Vector3D ab = new Vector3D(model.getVertex(faces[i].getVertex()[0]), model.getVertex(faces[i].getVertex()[1]));
				Vector3D ac = new Vector3D(model.getVertex(faces[i].getVertex()[0]), model.getVertex(faces[i].getVertex()[2]));

				Vector3D normal = MathUtils.vectorProduct(ab, ac);
				Vector3D lightDir = new Vector3D(faces[i].getCenter(), new Vertex(1, 1, 1));

				normal.normalize();
				lightDir.normalize();

				alpha = MathUtils.scalarProduct(normal, lightDir);
				alpha *= -1;
				alpha = (alpha < 0d) ? 0 : alpha*2;
			} else {
				alpha = 1;
			}

			if (model.getFaces()[0].getRgb() != null && colors) {
				int red = (int) (model.getFaces()[i].getRgb().getRed() * alpha);
				int green = (int) (model.getFaces()[i].getRgb().getGreen() * alpha);
				int blue = (int) (model.getFaces()[i].getRgb().getBlue() * alpha);

				red = (red < 0) ? 0 : (red > 255) ? 255 : red;
				green = (green < 0) ? 0 : (green > 255) ? 255 : green;
				blue = (blue < 0) ? 0 : (blue > 255) ? 255 : blue;
				gc.setFill(Paint.valueOf(Color.rgb(red, green,
						blue, model.getFaces()[i].getRgb().getAlpha()).toString()));
			} else {
				int red = (int) (fillColor.getRed() * alpha);
				red = (red > 255) ? 255 : red;
				
				int green = (int) (fillColor.getGreen() * alpha);
				green = (green > 255) ? 255 : green;

				int blue = (int) (fillColor.getBlue()* alpha);
				blue = (blue > 255) ? 255 : blue;

				gc.setFill(Paint.valueOf(Color.rgb(red, green, blue, 1).toString()));
			}
			if (fill)
				gc.fillPolygon(faces[i].getXs(), faces[i].getZs(), faces[i].getXs().length);
			
			gc.setStroke(strokesColor);
			
			if (stroke)
				gc.strokePolygon(faces[i].getXs(), faces[i].getZs(), faces[i].getXs().length);
		}
	}

	protected void drawRight(IModele model, Canvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();

		// Algorithme du peintre.
		Face[] faces = model.getFaces();
		Arrays.sort(faces, new ComparatorX());

		for (int i = 0; i < faces.length; i++) {
			double alpha;
			if (doLight) {
				Vector3D ab = new Vector3D(model.getVertex(faces[i].getVertex()[0]), model.getVertex(faces[i].getVertex()[1]));
				Vector3D ac = new Vector3D(model.getVertex(faces[i].getVertex()[0]), model.getVertex(faces[i].getVertex()[2]));

				Vector3D normal = MathUtils.vectorProduct(ab, ac);
				Vector3D lightDir = new Vector3D(faces[i].getCenter(), new Vertex(1, 1, 1));

				normal.normalize();
				lightDir.normalize();

				alpha = MathUtils.scalarProduct(normal, lightDir);
				alpha *= -1;
				alpha = (alpha < 0d) ? 0 : alpha*2;
			} else {
				alpha = 1;
			}

			if (model.getFaces()[0].getRgb() != null && colors) {
				int red = (int) (model.getFaces()[i].getRgb().getRed() * alpha);
				int green = (int) (model.getFaces()[i].getRgb().getGreen() * alpha);
				int blue = (int) (model.getFaces()[i].getRgb().getBlue() * alpha);

				red = (red < 0) ? 0 : (red > 255) ? 255 : red;
				green = (green < 0) ? 0 : (green > 255) ? 255 : green;
				blue = (blue < 0) ? 0 : (blue > 255) ? 255 : blue;

				gc.setFill(Paint.valueOf(Color.rgb(red, green,
						blue, model.getFaces()[i].getRgb().getAlpha()).toString()));
			} else {
				int red = (int) (fillColor.getRed() * alpha);
				red = (red > 255) ? 255 : red;
				
				int green = (int) (fillColor.getGreen() * alpha);
				green = (green > 255) ? 255 : green;

				int blue = (int) (fillColor.getBlue()* alpha);
				blue = (blue > 255) ? 255 : blue;

				gc.setFill(Paint.valueOf(Color.rgb(red, green, blue, 1).toString()));
			}
			

			gc.setStroke(strokesColor);
			
			if (fill)
				gc.fillPolygon(faces[i].getZs(), faces[i].getYs(), faces[i].getZs().length);
			if (stroke)
				gc.strokePolygon(faces[i].getZs(), faces[i].getYs(), faces[i].getZs().length);
		}
	}

	public IModele getModel() {
		return this.model;
	}

	public Controller getController() {
		return this.controller;
	}

	public void setController(Controller c) {
		this.controller = c;
	}

	public void fillSwitch() {
		fill = !fill;
	}

	public void strokeSwitch() {
		stroke = !stroke;
	}

	public void colorsSwitch() {
		colors = !colors;
	}

	public void doLightSwitch() {
		doLight = !doLight;
	}
}
