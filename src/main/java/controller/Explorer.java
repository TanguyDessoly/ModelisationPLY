package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;

import controller.comparators.ddcComparator;
import controller.comparators.faceComparator;
import controller.comparators.sizeComparator;
import controller.comparators.vertexComparator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import vue.View;

public class Explorer extends Parent {

	protected static Explorer instance;

	protected enum Sort {
		NAME, VERTEX, FACE, SIZE, DATE
	};


	protected File path = new File(System.getProperty("user.dir") + "/exemples/");
	protected TextField pathText = new TextField(path.getAbsolutePath());

	protected final ObservableList<Sort> sortsType = FXCollections.observableArrayList(Sort.NAME, Sort.VERTEX,
			Sort.FACE, Sort.SIZE, Sort.DATE);
	protected final ChoiceBox<Sort> sortChoiceBox = new ChoiceBox<Sort>(sortsType);
	protected Sort currentSort = Sort.NAME;

	protected File file;
	protected ArrayList<File> filelist = getChildren(path);
	protected ListView<File> list = new ListView<File>();

	protected HBox menu = new HBox();
	protected Button parent = new Button("↑");
	protected static TextArea infosArea = new TextArea();
	protected Label label = new Label("Aucune selection");

	protected final Image dirPng = new Image(
			"file://" + System.getProperty("user.dir") + "/src/main/resources/icons/folder.png");

	protected final Image filePng = new Image(
			"file://" + System.getProperty("user.dir") + "/src/main/resources/icons/file2.png");

	protected final Image plyPng = new Image(
			"file://" + System.getProperty("user.dir") + "/src/main/resources/icons/script.png");

	private int nbVertex = 0;
	private int nbFace = 0;

	public Explorer(View view) throws IOException {
		initPathText();
		initSort();
		initParentButton();
		initList(view);
		initInfosArea();

		VBox root = initVBox();
		this.getChildren().add(root);
	}

	class ExplorerListChangeListener implements ListChangeListener<File> {
		public void onChanged(ListChangeListener.Change<? extends File> c) {
			label.setText("Selection de " + c.getList());
			if (c.getList().isEmpty())
				return;

			File f = c.getList().get(0); // multiple-selection is not supported (not intuitive)
			if (f.isFile() && isPly(f)) {
				file = f;
				updateFileInfo(file);
			}
		}
	}

	class CellDisplay extends ListCell<File> {
		@Override
		public void updateItem(File item, boolean empty) {
			super.updateItem(item, empty);

			Canvas canvas = new Canvas(500, 25);
			GraphicsContext gc = canvas.getGraphicsContext2D();

			if (!empty) {
				if (item.isDirectory()) {
					gc.drawImage(dirPng, 0, 0, 26, 30);
				} else if (item.isFile()) {
					if (isPly(item))
						gc.drawImage(plyPng, 0, 0, 26, 30);
					else
						gc.drawImage(filePng, 0, 0, 26, 30);
				}
				gc.fillText(item.getName(), 30, 17);
			}

			setGraphic(canvas);
		}
	}

	private void initPathText() {
		final int LENGTH = 520;
		pathText.setPrefWidth(LENGTH);
		pathText.textProperty().addListener((observable, oldValue, newValue) -> {
			// This is needed because you can't update UI elements from a non-main thread.
			// One way to do this is make a Runnable and schedule it on the main thread by
			// runLater.
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					if (!new File(newValue).exists())
						return; // clear works when return
					path = new File(newValue);
					// filelist.clear();
					filelist = getChildren(path);
					if (filelist == null) {
						infosArea.setText("Impossible d'ouvrir l'élement");
						return;
					}
					list.getItems().clear(); // this is the one that triggers it
					infosArea.clear();
					list.setCellFactory(new Callback<ListView<File>, ListCell<File>>() {
						@Override
						public ListCell<File> call(ListView<File> list) {
							return new CellDisplay();
						}
					});
					list.getItems().addAll(filelist);
				}
			});
		});
	}

	private void initSort() {
		sortChoiceBox.getSelectionModel().select(Sort.NAME);
		sortChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null)
				currentSort = newValue;
			refreshList();
		});
	}

	private void initParentButton() {
		parent.setOnAction(e -> {
			if (path.getParentFile() == null)
				return;
			path = path.getParentFile();
			pathText.setText(path.getAbsolutePath());
		});
	}

	private void initList(View view) {
		list.setPrefHeight(200);
		list.getItems().addAll(filelist);
		list.getSelectionModel().getSelectedItems().addListener(new ExplorerListChangeListener());
		list.setOnMouseClicked(doubleClickHandler(view, this));
		list.setCellFactory(new Callback<ListView<File>, ListCell<File>>() {
			@Override
			public ListCell<File> call(ListView<File> list) {
				return new CellDisplay();
			}
		});
	}

	private void refreshList() {
		filelist = getChildren(path);
		list.getItems().clear();
		list.getItems().addAll(filelist);
	}

	private void initInfosArea() {
		infosArea.setPrefHeight(90);
	}

	public static TextArea getInfosArea() {
		return infosArea;
	}

	private VBox initVBox() {
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER_LEFT);
		root.setSpacing(10.0);
		root.setPadding(new Insets(3, 3, 3, 3));
		menu.getChildren().addAll(parent, pathText, sortChoiceBox);
		root.getChildren().addAll(menu, list, infosArea, label);
		return root;
	}

	private EventHandler<MouseEvent> doubleClickHandler(View view, Explorer explorer) {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent click) {
				File f = list.getSelectionModel().getSelectedItem();
				if (f == null)
					return; // in case the user select a ply, then double click where there is no file/folder
				if (click.getClickCount() >= 2) {
					if (f.isFile() && isPly(f)) {
						view.init(f);
						view.getController().setModel(view.getModel());
					} else {
						infosArea.setText(
								"Mauvais type de fichier.\nLe programme ne prend en compte uniquement les fichiers PLY(.ply).");
					}
					if (f.isDirectory()) {
						path = f.getAbsoluteFile();
						pathText.setText(path.getAbsolutePath());
					}
				}
			}
		};
	}

	private void updateFileInfo(File f) {
		infosArea.setText(getInfos(f));
		if (infosArea.isFocused()) {
			infosArea.setFocusTraversable(false);
		}
	}

	private ArrayList<File> getChildren(File path) {

		String[] liste = path.list();
		if (liste == null)
			return null;

		ArrayList<String> al = new ArrayList<String>();
		for (String current : liste) {
			al.add(current);
		}

		Collections.sort(al);

		ArrayList<File> fileList = new ArrayList<File>();

		for (String current : al) {
			File f = new File(path.getAbsolutePath() + "/" + current);
			if (f.isDirectory())
				fileList.add(new File(path.getAbsolutePath() + "/" + current));
		}

		Collections.sort(fileList);

		for (String current : al) {
			File f = new File(path.getAbsolutePath() + "/" + current);
			if (f.isFile())
				fileList.add(new File(path.getAbsolutePath() + "/" + current));
		}

		switch (currentSort) {
		case VERTEX:
			Collections.sort(fileList, new vertexComparator());
			break;

		case FACE:
			Collections.sort(fileList, new faceComparator());
			break;

		case SIZE:
			Collections.sort(fileList, new sizeComparator());
			break;

		case DATE:
			Collections.sort(fileList, new ddcComparator());
			break;

		default:
			break;
		}

		pathText.setText(path.getAbsolutePath());

		return fileList;
	}

	private String getInfos(File f) {
		StringBuilder builder = new StringBuilder("Nom du fichier : ");
		builder.append(f.getName() + "\n");

		try (BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()))) {
			String line = "";
			while ((line = br.readLine()) != null && (nbFace == 0 || nbVertex == 0)) {
				this.findInfos(line);
			}

			String ddc = Files.getLastModifiedTime(f.toPath()).toString();
			ddc = ddc.substring(0, ddc.indexOf("T"));

			builder.append("Taille du fichier : " + f.length() / 1000 + "kb\n" + "Date de création : " + ddc);
			builder.append("\nNombre de points : " + nbVertex + "\n");
			builder.append("Nombre de faces : " + nbFace);

			nbFace = 0;
			nbVertex = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	private void findInfos(String line) throws Exception {
		String[] words = line.split(" |\\n");
		if (words[0].equals("element")) {
			if (words[1].equals("vertex")) {
				nbVertex = Integer.parseInt(words[2]);
			} else if (words[1].equals("face")) {
				nbFace = Integer.parseInt(words[2]);
			}
		}
	}

	private static String getExtension(File f) {
		String filePath = f.getName();
		int i = filePath.lastIndexOf('.');
		if (i > 0) {
			return filePath.substring(i + 1);
		}
		return "";
	}

	public static boolean isPly(File f) {
		return getExtension(f).equals("ply") || getExtension(f).equals("PLY");
	}

}