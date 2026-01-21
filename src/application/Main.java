package application;


import java.net.URL;
import java.sql.Connection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception{
		
		try {

//			BorderPane root = new BorderPane();
			FXMLLoader loader = new FXMLLoader();
//			URL url = getClass().getResource("/fxml/admin-student.fxml");
//			URL url = getClass().getResource("/fxml/admin-instructor.fxml");
//			URL url = getClass().getResource("/fxml/admin-grades.fxml");
//			URL url = getClass().getResource("/fxml/admin-courses.fxml");			
//			URL url = getClass().getResource("/fxml/add-instructor-form.fxml");
			URL url = getClass().getResource("/fxml/login-form.fxml");
			loader.setLocation(url);
			Parent root = loader.load();
			Scene scene = new Scene(root,1121,570);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Connection con = DBConnection.connect();
			if(con!=null)
				System.out.println("Success");
			else
				System.out.println("Failed");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
