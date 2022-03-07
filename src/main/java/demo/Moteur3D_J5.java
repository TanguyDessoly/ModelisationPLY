package demo;

import java.io.File;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import modele.ply.NullModele;
import vue.CompleteView;
import vue.View;

/** Classe qui lance le projet */
public class Moteur3D_J5 extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Methode qui initialise l'affichage
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		@SuppressWarnings("unused")
		File defaultFile = new File(System.getProperty("user.dir") + "/exemples/hexa.ply");
		
		try {
			
			View view = new CompleteView(new NullModele());
//			CompleteView view = new CompleteView(defaultFile); si l'on veut lancer le moteur avec un fichier par défaut
			
			Controller control = new Controller();
			control.setStage(view);
			control.setView(view);
			control.setModel(view.getModel());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
