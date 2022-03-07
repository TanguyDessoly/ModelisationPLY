package vue;

import java.io.File;
import java.io.IOException;

import controller.Explorer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import modele.maths.Vector3D;
import modele.ply.IModele;
import modele.ply.ModelTransforming;
import modele.ply.ModelePly;
import modele.subject_observer.Subject;

/** Classe de l'ensemble des vues */
public class CompleteView extends View {

	protected Canvas canvasFace, canvasTop, canvasRight;
	protected ListView<File> listeModeles;

	/** Constructeur qui intialise une vue */
	public CompleteView(File file) throws IOException {
		super(file);
		loader.setLocation(getClass().getResource("/fxmlBeau.fxml"));
		root = loader.load();
		//		scene = new Scene(root, getMaxWidth(), getMaxHeight()); si le fxml ne détermine pas la taille
		scene = new Scene(root);

		initColorPickers();
		initSubscene(new Explorer(this));

		// Set and shows the stage
		this.setScene(scene);
		this.setResizable(false);
		this.setTitle(title);
		this.show();
	}

	public CompleteView(IModele modele) throws IOException {
		super(modele);
		loader.setLocation(getClass().getResource("/fxmlBeau.fxml"));
		root = loader.load();
//		scene = new Scene(root, getMaxWidth(), getMaxHeight()); si le fxml ne détermine pas la taille
		scene = new Scene(root);
		initCanvas();
		draw();
		model.attach(this);

		initColorPickers();
		initSubscene(new Explorer(this));

		this.setScene(scene);
		this.setResizable(false);
		this.setTitle(title);
		this.show();
	}

	private void initColorPickers() {
		ColorPicker colorPickerStrokes = (ColorPicker) scene.lookup("#colorPickerStrokes");
		ColorPicker colorPickerFill = (ColorPicker) scene.lookup("#colorPickerFill");

		colorPickerFill.setValue(Color.RED);
		colorPickerStrokes.setValue(Color.BLACK);

		colorPickerStrokes.valueProperty().addListener((observable, oldValue, newValue) -> {setStrokesColor(newValue); update((Subject) model);});
		colorPickerFill.valueProperty().addListener((observable, oldValue, newValue) -> {setFillColor(newValue); update((Subject) model);});
	}

	private void initSubscene(Parent parent) {
		SubScene sub = (SubScene) scene.lookup("#subscene");
		sub.setRoot(parent);
	}

	/** Methode qui initialise un canvas */
	@Override
	protected void initCanvas() {
		canvasFace = (Canvas) scene.lookup("#canvasFace");
		canvasFace.setScaleY(-1);
		configCanvas(canvasFace);
		configFace(canvasFace);

		canvasTop = (Canvas) scene.lookup("#canvasTop");
		configCanvas(canvasTop);
		configTop(canvasTop);

		canvasRight = (Canvas) scene.lookup("#canvasRight");
		canvasRight.setScaleY(-1);
		canvasRight.setScaleX(-1);
		configCanvas(canvasRight);
		configRight(canvasRight);
	}
	
	protected void draw() {
		if(!model.isNull()) {
			drawFace(model, canvasFace);
			drawTop(model, canvasTop);
			drawRight(model, canvasRight);
		}
	}
	
	private void configFace(Canvas canvas) {
		if (model.isNull()) return; 
		// Defines the vector and redraws the model
		canvas.setOnMouseDragged((MouseEvent e) -> {
			double xDelta = e.getX() - xBefore;
			double yDelta = e.getY() - yBefore;

			double xDeltaInverse = xBefore - e.getX();
			double yDeltaInverse = yBefore - e.getY();
			if (e.isPrimaryButtonDown())
				ModelTransforming.translate(model, new Vector3D(xDelta, yDelta, 0d));
			else if (e.isSecondaryButtonDown()) {
				if (xDeltaInverse != 0) {
					ModelTransforming.rotateOnYAxis(model, (Math.PI * xDeltaInverse) / 255);
				}
				if (yDeltaInverse != 0) {
					ModelTransforming.rotateOnXAxis(model, (Math.PI * yDeltaInverse) / 255);
				}
			}
			xBefore = e.getX();
			yBefore = e.getY();
			clearCanvas(canvas);
			drawFace(model, canvas);
		});
	}

	private void configTop(Canvas canvas) {
		if (model.isNull()) return; 
		// Defines the vector and redraws the model
		canvas.setOnMouseDragged((MouseEvent e) -> {
			double xDelta = e.getX() - xBefore;
			double yDelta = e.getY() - yBefore;

			double xDeltaInverse = xBefore - e.getX();
			double yDeltaInverse = yBefore - e.getY();
			if (e.isPrimaryButtonDown())
				ModelTransforming.translate(model, new Vector3D(xDelta, 0d, yDelta));
			else if (e.isSecondaryButtonDown()) {
				if (xDeltaInverse != 0) {
					ModelTransforming.rotateOnYAxis(model, (Math.PI * xDeltaInverse) / 255);
				}
				if (yDeltaInverse != 0) {
					ModelTransforming.rotateOnXAxis(model, (Math.PI * yDeltaInverse) / 255);
				}
			}
			xBefore = e.getX();
			yBefore = e.getY();
			clearCanvas(canvas);
			drawTop(model, canvas);
		});
	}

	private void configRight(Canvas canvas) {
		if (model.isNull()) return; 
		// Defines the vector and redraws the model
		canvas.setOnMouseDragged((MouseEvent e) -> {
			double xDelta = e.getX() - xBefore;
			double yDelta = e.getY() - yBefore;

			double xDeltaInverse = xBefore - e.getX();
			double yDeltaInverse = yBefore - e.getY();
			if (e.isPrimaryButtonDown())
				ModelTransforming.translate(model, new Vector3D(0d, yDelta, xDelta));
			else if (e.isSecondaryButtonDown()) {
				if (xDeltaInverse != 0) {
					ModelTransforming.rotateOnYAxis(model, (Math.PI * xDeltaInverse) / 255);
				}
				if (yDeltaInverse != 0) {
					ModelTransforming.rotateOnXAxis(model, (Math.PI * yDeltaInverse) / 255);
				}
			}
			xBefore = e.getX();
			yBefore = e.getY();
			clearCanvas(canvas);
			drawRight(model, canvas);
		});
	}

	public void clearAll() {
		clearCanvas(canvasFace);
		clearCanvas(canvasTop);
		clearCanvas(canvasRight);
	}

	@Override
	public void update(Subject subject) {
		clearAll();

		drawFace((ModelePly) subject, canvasFace);
		drawTop((ModelePly) subject, canvasTop);
		drawRight((ModelePly) subject, canvasRight);
	}

	public void update(Subject subject, Object data) {}

}