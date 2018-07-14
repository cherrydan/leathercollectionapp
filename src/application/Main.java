package application;

import java.io.File;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

			//Связываем контроллер таблицы и главное приложение
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

			RootLayoutController controller = loader.getController();

			controller.setMain(this);

			File file = getSkirtsFilePath();
			if(file != null) {
				loadSkirtsDataFromFile(file);
			}


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


	/*
	 * Возвращает ссылку на последний открытый пользователем файл, сохраненного в Preferences
	 */
	public File getSkirtsFilePath() {

		Preferences prefs = Preferences.userNodeForPackage(Main.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {
			return new File(filePath);
		}
		else {
			return null;
		}
	}

	/*
	 * Устанавливает путь в Prefenerces последнего открытого пользователем файла (получая ссылку на файл)
	 */
	public void setSkirtsFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(Main.class);
		if(file != null) {
			prefs.put("filePath", file.getPath());
			primaryStage.setTitle("Leather Skirts App - " + file.getName());
		}
		else {
			prefs.remove("filePath");
			primaryStage.setTitle("Leather Skirts App");
		}

	}

	/*
	 * Загружает данные о юбках из файла и обновляет таблицу
	 */
	public void loadSkirtsDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(SkirtsModelWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			SkirtsModelWrapper wrapper = (SkirtsModelWrapper) um.unmarshal(file);

			leatherSkirtsData.clear();
			leatherSkirtsData.addAll(wrapper.getModels());

			setSkirtsFilePath(file);
		}
		catch (Exception e) {
			// TODO: handle exception
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not load data");
	        alert.setContentText("Could not load data from file:\n" + file.getPath());
	        e.printStackTrace();
	        alert.showAndWait();
		}
	}

	/*
	 * Сохраняет данные о юбках из таблицы в файл
	 */
	public void saveSkirtsDataToFile(File file) {
		try {
		JAXBContext context = JAXBContext.newInstance(SkirtsModelWrapper.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		SkirtsModelWrapper wrapper = new SkirtsModelWrapper();
		wrapper.setModels(leatherSkirtsData);

		m.marshal(wrapper, file);

		setSkirtsFilePath(file);

		}
		catch (Exception e) {
			// TODO: handle exception
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not save data");
	        alert.setContentText("Could not save data to file:\n" + file.getPath());

	        alert.showAndWait();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
