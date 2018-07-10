package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditSkirtsInfoDialogController {

	private Stage dialogStage;

	private LeatherSkirtModel model;

	private Boolean okClicked = false;


    @FXML
    private TextField txtBrandName;

    @FXML
    private TextField txtWaistLength;

    @FXML
    private TextField txtSkirtLength;

    @FXML
    private TextField txtNoteBene;

    @FXML
    private Button btnOk;

    @FXML
    private Button btnCancel;

    @FXML
    void initialize() {

    }

    public void setDialogStage(Stage dialogStage) {
    	this.dialogStage = dialogStage;
    }

    public void setModel(LeatherSkirtModel model) {
    	this.model = model;
    	txtBrandName.setText(model.getBrandName());
    	txtWaistLength.setText(Double.toString(model.getWaist()));
    	txtSkirtLength.setText(Double.toString(model.getLength()));
    	txtNoteBene.setText(model.getNotaBene());

    }

    public Boolean isOkClicked() {
    	return okClicked;
    }

    @FXML
    public void handleOk() {
    	if(isInputValid()) {
    		model.setBrandName(txtBrandName.getText());
    		model.setWaist(Double.parseDouble(txtWaistLength.getText()));
    		model.setLength(Double.parseDouble(txtSkirtLength.getText()));
    		model.setNotaBene(txtNoteBene.getText());

    		okClicked = true;
    		dialogStage.close();
    	}

    }

    @FXML
    public void handleCancel() {
    	dialogStage.close();
    }

	private boolean isInputValid() {
		String errorMessage = "";

		if(txtBrandName.getText() == null || txtBrandName.getText().length() == 0) {
			errorMessage += "No valid brandname!\n";
		}

		if(txtWaistLength.getText() == null || txtWaistLength.getText().length() == 0) {
			errorMessage += "No valid waist length!\n";
		}
		else {
            // try to parse the waist length to double
			try {
			Double.parseDouble(txtWaistLength.getText());
			}
			catch (NumberFormatException e) {
				// TODO: handle exception
				errorMessage += "No valid number format!\n";

			}
		}

		if(txtSkirtLength.getText() == null || txtSkirtLength.getText().length() == 0) {
			errorMessage += "No valid skirt length!\n";
		}
		else {
			//try to parse the skirt length to double
			try {
				Double.parseDouble(txtSkirtLength.getText());
			}
			catch (NumberFormatException e) {
				// TODO: handle exception
				errorMessage += "No valid number format!\n";
			}
		}

		if(txtNoteBene.getText() == null || txtNoteBene.getText().length() == 0) {
			errorMessage += "No valid notabene field!";
		}


		if(errorMessage.length() == 0) {
			return true;
		}
		else {
			// Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
		}

	}


}
