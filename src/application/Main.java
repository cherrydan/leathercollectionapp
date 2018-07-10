package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {

	private BorderPane rootLayout;
	private static Stage primaryStage;

	//Данные о юбках
	private ObservableList<LeatherSkirtModel> leatherSkirtsData = FXCollections.observableArrayList();

	/*
	 * Конструктор класса Main - в нем мы заполним leatherSkirtsData данными о некоторых юбках, возможно ошибочные
	 *
	 */

	public Main() {
		leatherSkirtsData.add(new LeatherSkirtModel("Moschino", 72, 29.5, "Real Leather - straight"));
		leatherSkirtsData.add(new LeatherSkirtModel("Alice and Olivia", 64, 37.3, "Real Leather - pleated"));
		leatherSkirtsData.add(new LeatherSkirtModel("Mango", 66, 36.2, "Real Leather - straight"));
		leatherSkirtsData.add(new LeatherSkirtModel("Anastassia Kuchugova", 64, 36.3, "Real Leather - straight, orange"));
		leatherSkirtsData.add(new LeatherSkirtModel("Miss Yu", 60, 35, "Real Leather - skater mini skirt"));

	}


	/*
	 * Возвращает данные в виде наблюдаемого списка данных о юбках
	 * @return
	 */

	public ObservableList<LeatherSkirtModel> getSkirtsData() {
		return leatherSkirtsData;
	}


	@SuppressWarnings("static-access")
	@Override
	public void start(Stage primaryStage) {

		try {
			this.primaryStage = primaryStage;
			this.primaryStage.setResizable(true);
			this.primaryStage.setTitle("Leather Skirts App");
			this.primaryStage.getIcons().add(new Image("file:resources/images/skirt.png"));
			initRootLayout();
			showTableLayout();


		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Показывает таблицу с информацией о юбках в рамках главной (корневой) формы
	 */

	public void showTableLayout() {
		// TODO Auto-generated method stub
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("TableLayout.fxml"));
			AnchorPane skirtsOverview = (AnchorPane) loader.load();

			//Помещаем дочернюю форму с информацией по центру корневой формы
			rootLayout.setCenter(skirtsOverview);

			TableLayoutController controller = loader.getController();
			controller.setMain(this);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}


	/*
	 * Инициализируем главную (корневую) форму
	 */


	public void initRootLayout() {
		// TODO Auto-generated method stub
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			//Показываем сцену, содержащую главную (корневую) форму
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			primaryStage.show();

		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	/*
	 * Возвращает главную сцену
	 */
	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	/*
	 * Показывает диалоговое окно добавления или редактирования записи о юбках
	 */

	public boolean showEditSkirtsInfoDialog(LeatherSkirtModel model) {
		try {
			//Загружаем из файла FXML
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("EditSkirtsInfoDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			//Создаем сцену модального диалогового окна
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Skirts Info");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			//Соединяемся с класс-контроллером
			EditSkirtsInfoDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setModel(model);

			//Показыааем диалоговое окно и ждем
			dialogStage.showAndWait();
			return controller.isOkClicked();
		}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return false;
			}

	}


	public static void main(String[] args) {
		launch(args);
	}
}
