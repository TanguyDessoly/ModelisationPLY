package controller;

import javafx.fxml.FXML;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import modele.maths.Vector3D;
import modele.ply.IModele;
import modele.ply.ModelTransforming;
import modele.subject_observer.Subject;
import vue.View;

/** Classe qui implemente les actions du Scene Builder */
public class Controller {

	protected static Stage stage;
	protected static View view;
	protected static IModele model;
	protected static Horloge horloge;
	protected static Timer timer;
	
	/**
	 * Initilialise l'ecran d'affichage
	 * 
	 * @param primaryStage , Le premier ecran d'affichage
	 */
	public void setStage(Stage primaryStage) {
		stage = primaryStage;
	}

	/**
	 * Initialise la vue
	 * 
	 * @param vue , Une simple vue
	 */
	public void setView(View vue) {
		view = vue;
		view.setController(this);
	}

	public static View getView() {
		return view;
	}

	/**
	 * Initialise le modele
	 * 
	 * @param model , Le modele
	 */
	public void setModel(IModele modele) {
		model = modele;
		if (horloge != null) {
			horloge.fullStop();
		}
		horloge = new Horloge(modele);
		timer = horloge.getTimer();
		if(modele.isNull()) horloge.setBlocked(true);
		else horloge.setBlocked(false);
	}

	public static Horloge getHorloge() {
		return Controller.horloge;
	}

	@FXML
	public void mouseMove() {
		horloge.stop();
	}

	/** Methode permettant de déplacer vers la gauche l'affichage d'une vue */
	@FXML
	public void leftClicked() {
		ModelTransforming.translate(model, new Vector3D(-5d, 0d, 0d));
		horloge.stop();
	}

	/** Methode permettant de déplacer vers la droite l'affichage d'une vue */
	@FXML
	public void rightClicked() {
		ModelTransforming.translate(model, new Vector3D(5d, 0d, 0d));
		horloge.stop();
	}

	/** Methode permettant de déplacer vers le haut l'affichage d'une vue */
	@FXML
	public void topClicked() {
		ModelTransforming.translate(model, new Vector3D(0d, 5d, 0d));
		horloge.stop();
	}

	/** Methode permettant de déplacer vers le bas l'affichage d'une vue */
	@FXML
	public void botClicked() {
		ModelTransforming.translate(model, new Vector3D(0d, -5d, 0d));
		horloge.stop();
	}

	/** Methode permettant de zoomer sur l'affichage d'une vue */
	@FXML
	public void plusClicked() {
		ModelTransforming.zoom(model, 1.05);
		horloge.stop();
	}

	/** Methode permettant de dézoomer sur l'affichage d'une vue */
	@FXML
	public void minusClicked() {
		ModelTransforming.zoom(model, 1 / 1.05);
		horloge.stop();
	}

	/** Methode permettant de faire une rotation gauche sur l'affichage d'une vue */
	@FXML
	public void rotateXLeft() {		
		ModelTransforming.rotateOnYAxis(model, Math.PI/16);
		horloge.stop();
	}

	/** Methode permettant de faire une rotation gauche sur l'affichage d'une vue */
	@FXML
	public void rotateXRight() {
		ModelTransforming.rotateOnYAxis(model, -Math.PI/16);
		horloge.stop();
	}

	/** Methode permettant de faire une rotation gauche sur l'affichage d'une vue */
	@FXML
	public void rotateYLeft() {
		ModelTransforming.rotateOnXAxis(model, -Math.PI/16);
		horloge.stop();
	}

	/** Methode permettant de faire une rotation gauche sur l'affichage d'une vue */
	@FXML
	public void rotateYRight() {
		ModelTransforming.rotateOnXAxis(model, Math.PI/16);
		horloge.stop();
	}

	/** Methode permettant de faire une rotation gauche sur l'affichage d'une vue */
	@FXML
	public void rotateZLeft() {
		ModelTransforming.rotateOnZAxis(model, Math.PI/16);
		horloge.stop();
	}

	/** Methode permettant de faire une rotation gauche sur l'affichage d'une vue */
	@FXML
	public void rotateZRight() {
		ModelTransforming.rotateOnZAxis(model, -Math.PI/16);
		horloge.stop();
	}

	@FXML
	public void key_pressed(KeyEvent event) {
		horloge.stop();
		switch (event.getCode()) {
		case Z:
			topClicked();
			break;
		case S:
			botClicked();
			break;
		case Q:
			leftClicked();
			break;
		case D:
			rightClicked();
			break;
		case J:
			rotateXLeft();
			break;
		case L:
			rotateXRight();
			break;
		case I:
			rotateYLeft();
			break;
		case K:
			rotateYRight();
			break;
		case U:
			rotateZLeft();
			break;
		case O:
			rotateZRight();
			break;
		case ADD:
			plusClicked();
			break;
		case SUBTRACT:
			minusClicked();
			break;
		case T:
			fill();
			break;
		case G:
			stroke();
			break;
		case B:
			colors();
			break;
		case H:
			horloge.blocked = horloge.isBlocked() ? false : true;
			break;
		default:
			break;
		}
	}

	@FXML
	public void onScroll(ScrollEvent e) {
		double zoomFactor = 1.03;
		double deltaY = e.getDeltaY();
		if (deltaY < 0) {
			double reverseZoomFactor = 1.945;
			zoomFactor = reverseZoomFactor - zoomFactor;
		}
		ModelTransforming.zoom(model, zoomFactor);
	}

	/** Change le paramètre de remplissage (on/off) **/
	@FXML
	public void fill() {
		view.fillSwitch();
		view.update((Subject) model);
	}

	/** Change le paramètre de présence des contours (on/off) **/
	@FXML
	public void stroke() {
		view.strokeSwitch();
		view.update((Subject) model);
	}

	/** Change le paramètre de présence des contours (on/off) **/
	@FXML
	public void colors() {
		view.colorsSwitch();
		view.update((Subject) model);
	}

	@FXML
	public void light() {
		view.doLightSwitch();
		view.update((Subject) model);
	}

	@FXML
	public void centerModel() {
		model.startCenteredModel();
	}

	/** Methode ouvrant un fichier ply en le droppant sur le programme via un explorateur externe */
	@FXML
	public void onDragDropped(DragEvent event){
		Dragboard db = event.getDragboard();
		boolean success = false;
		if (db.hasFiles()) {
			view.init(db.getFiles().get(0));
			view.getController().setModel(view.getModel());
			success = true;
		}
		event.setDropCompleted(success);

		event.consume();
	}

	@FXML
	public void onDragOver(DragEvent event){
		Dragboard db = event.getDragboard();
		if (db.hasFiles()) {
			event.acceptTransferModes(TransferMode.COPY);
		} else {
			event.consume();
		}
	}

}
