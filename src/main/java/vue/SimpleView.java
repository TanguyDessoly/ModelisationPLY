package vue;

import java.io.File;
import java.io.IOException;

import javafx.scene.Scene;
import modele.subject_observer.Subject;

public class SimpleView extends View {

	// Deprecated
	public SimpleView(File file) throws IOException {
		super(file);
		loader.setLocation(getClass().getResource("/simpleView.fxml")); // was removed due to massive changes and lack
																		// of time (it was an interface with only one
																		// canvas)
		root = loader.load();
		scene = new Scene(root, getMaxWidth(), getMaxHeight());

		if (file != null)
			initCanvas();

		// Set and shows the stage
		this.setScene(scene);
		this.setTitle("SuperMoteur3D");
		this.show();
	}

	public SimpleView() throws IOException {
		this(null);
	}

	@Override
	public void update(Subject subject) {
	}

}