package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class RootLayoutController {

	@FXML
	public void handleClose() {
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
