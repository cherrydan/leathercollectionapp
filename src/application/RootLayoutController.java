package application;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;


public class RootLayoutController {

	private Main main;

	public void setMain(Main main) {
		this.main = main;
	}

	/*
	 * Создаем пустой файл с информацией о юбках
	 */
	@FXML
	private void handleNew() {
		main.setSkirtsFilePath(null);
		main.getSkirtsData().clear();
	}

	/*
	 * Открытие файла, используем встроенный в JavaFX FileChooser->OpenDialog
	 */
	@FXML
	private void handleOpen() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");

		fileChooser.getExtensionFilters().add(extFilter);

		File file = fileChooser.showOpenDialog(Main.getPrimaryStage());

		if(file != null) {
			main.loadSkirtsDataFromFile(file);
		}
	}

	@FXML
	private void handleSave() {
		File skirtsFile = main.getSkirtsFilePath();
		if(skirtsFile != null) {
			main.saveSkirtsDataToFile(skirtsFile);
		}
		else {
			handleSaveAs();
		}
	}

	/*
	 * Сохранить как, используем FileChooser->SaveDialog
	 */

	@FXML
	private void handleSaveAs() {
		// TODO Auto-generated method stub
		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(Main.getPrimaryStage());

        if(file != null) {
        	if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
        	main.saveSkirtsDataToFile(file);
        }


	}

	@FXML
	private void handleClose() {
		System.exit(0);
	}

	@FXML
    private void handleAbout(){
	Alert alert = new Alert(AlertType.INFORMATION);
	alert.setTitle("Leather Skirts Collection App");
	alert.setHeaderText("About...");
	alert.setContentText("This is leather-mini skirts collection\n in my wife's wardrobe.\n(c) D. Tcherepuknin, 2018");
	alert.showAndWait();
    }


}
