package application;

import application.Main;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

import java.util.Date;

import application.LeatherSkirtModel;

public class TableLayoutController {

	@FXML
	private TableView<LeatherSkirtModel> leatherSkirtTable;

	@FXML
	private TableColumn<LeatherSkirtModel, String> brandNameColumn;

	@FXML
	private TableColumn<LeatherSkirtModel, Double> waistLengthColumn;

	@FXML
	private TableColumn<LeatherSkirtModel, Double> skirtLengthColumn;

	@FXML
	private TableColumn<LeatherSkirtModel,String> skirtNotaBeneColumn;

	//Ссылка на главное приложение
	private Main main;


	//Пустой контроллер
	public TableLayoutController() {

	}

	//Инициализируем контроллер
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
	private void initialize() {
		brandNameColumn.setCellValueFactory(cellData-> cellData.getValue().brandNameProperty());
		waistLengthColumn.setCellValueFactory(cellData -> (ObservableValue)cellData.getValue().waistProperty());
		skirtLengthColumn.setCellValueFactory(cellData -> (ObservableValue) cellData.getValue().lengthProperty());
		skirtNotaBeneColumn.setCellValueFactory(cellData -> cellData.getValue().notaBeneProperty());

		showSkirtsInfoInTable(null);

		//leatherSkirtTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showSkirtsInfoInTable(newValue));



	}





	private void showSkirtsInfoInTable(LeatherSkirtModel model) {
		// TODO Auto-generated method stub
		if(model != null)
		leatherSkirtTable.getItems().add(model);

	}

	/*
	 * Пользователь нажал кнопку Clear в главном окне
	 */

	@FXML
	private void handleClear() {
		int selectedIndex = leatherSkirtTable.getSelectionModel().getSelectedIndex();

		if(selectedIndex >= 0)
		leatherSkirtTable.getItems().remove(selectedIndex);

		//Ничего не выбрано в таблице
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(Main.getPrimaryStage());
			alert.setTitle("No Selection");
	        alert.setHeaderText("No skirt seletion");
	        alert.setContentText("Please select a skirt in the table.");

	        alert.showAndWait();
		}
	}

	/*
	 * Пользователь нажал кнопку Add в главном окне
	 */
	@FXML
	private void handleAdd() {
		LeatherSkirtModel tempModel = new LeatherSkirtModel("",0,0,"");
		boolean okClicked = main.showEditSkirtsInfoDialog(tempModel);
		if(okClicked) {
			main.getSkirtsData().add(tempModel);
		}

	}

	/*
	 * Пользователь нажал кнопку Edit в главном окне
	 */
	@FXML
	private void handleEdit() {
		//Получаем текущую выбранную юбку из таблицы
		LeatherSkirtModel selectedModel = leatherSkirtTable.getSelectionModel().getSelectedItem();
		if(selectedModel != null) {
			boolean okClicked = main.showEditSkirtsInfoDialog(selectedModel);
			if(okClicked) {

			}
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(Main.getPrimaryStage());
	        alert.setTitle("No Selection");
	        alert.setHeaderText("No Person Selected");
	        alert.setContentText("Please select a person in the table.");

	        alert.showAndWait();
		}
	}

	Date lastClickTime;
	LeatherSkirtModel tempModel;
	/*
	 * Пользователь дважды щелкнул на ряду в таблице, мы берем эти данные из ряда и запускаем EditSkirtsInfoDialog
	 *
	 * Человек придумал алгоритм, засечь время между двумя кликами, и если оно меньше 300 миллисекунд, это двойной клик, и
	 * можно запускать какой-либо процесс по нему
	 */
	@FXML
	private void handleRowSelect() {

		LeatherSkirtModel selectedModel = leatherSkirtTable.getSelectionModel().getSelectedItem();

		if(selectedModel == null) return;
		if(selectedModel != tempModel) {
			tempModel = selectedModel;
			lastClickTime = new Date();

		} else if(selectedModel == tempModel) {
			Date now = new Date();
			long diff = now.getTime() - lastClickTime.getTime();
			if(diff < 300) {
				boolean okClicked = main.showEditSkirtsInfoDialog(selectedModel);
				if(okClicked) {}
			}
			else {
				lastClickTime = new Date();
			}
		}

	}

	public void setMain(Main main) {
		this.main = main;
		leatherSkirtTable.setItems(main.getSkirtsData());
	}



}
