package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.Student;
import models.StudentDAO;
import models.Year;

public class AdminStudentController implements Initializable{

    @FXML
    private TableColumn<Student, String> col_phone;

    @FXML
    private Button btn_create;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_search;

    @FXML
    private Button btn_stuEnroll;

    @FXML
    private Button btn_logOut;
    @FXML
    private Button btn_update;

    @FXML
    private Button btn_viewAll;

    @FXML
    private ComboBox<Integer> cbl_year;

    @FXML
    private TableColumn<?, ?> col_actions;

    @FXML
    private TableColumn<Student, String> col_address;

    @FXML
    private TableColumn<Student, Integer> col_count;

    @FXML
    private TableColumn<Student, String> col_dob;

    @FXML
    private TableColumn<Student, String> col_email;

    @FXML
    private TableColumn<Student, String> col_name;

    @FXML
    private TableColumn<Year, String> col_year;

    @FXML
    private TableView<Student> tbl_student;

    @FXML
    private TextField txt_address;

    @FXML
    private TextField txt_dob;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_search;

    @FXML
    void handleLogOut(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader();
	    	URL url = getClass().getResource("/fxml/Login-form.fxml");
	    	loader.setLocation(url);
	    	Parent root;
			root = loader.load();
			Scene scene = new Scene(root,1121,570);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @FXML
    void handleUpdate(ActionEvent event) {

    }
    
    @FXML
    void handleCreate(ActionEvent event) {

    }

    @FXML
    void handleDelete(ActionEvent event) {
    	
    }

    @FXML
    void handleSearch(ActionEvent event) {
    	
    }

    @FXML
    private StackPane content;
    @FXML
    void handleViewAll(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/admin-student-1.fxml"));
			content.getChildren().removeAll();
			content.getChildren().setAll(root);
			updateButtonStyles(btn_viewAll, btn_stuEnroll);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void handleEnroll(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/admin-student-2.fxml"));
			content.getChildren().removeAll();
			content.getChildren().setAll(root);
			updateButtonStyles(btn_stuEnroll, btn_viewAll);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    private void updateButtonStyles(Button active, Button inactive) {
        // Reset both buttons to the base class
        active.getStyleClass().removeAll("tab-btn", "tab-btn-active");
        inactive.getStyleClass().removeAll("tab-btn", "tab-btn-active");
        
        // Apply appropriate classes
        active.getStyleClass().add("tab-btn-active");
        inactive.getStyleClass().add("tab-btn");
    }
    
    @FXML
    private BorderPane mainContent;
    
    @FXML
    void handleCourse(ActionEvent event)  throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/admin-courses.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
        double wd = bounds.getMaxX();
        double hgh = bounds.getMaxY();
        Scene scene = new Scene(root, wd, hgh);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handleHome(ActionEvent event)  throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/admin-dashboard.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
        double wd = bounds.getMaxX();
        double hgh = bounds.getMaxY();
        Scene scene = new Scene(root, wd, hgh);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handleStudent(ActionEvent event)  throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/admin-student.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
        double wd = bounds.getMaxX();
        double hgh = bounds.getMaxY();
        Scene scene = new Scene(root, wd, hgh);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handle_account(ActionEvent event)  throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/admin-account.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
        double wd = bounds.getMaxX();
        double hgh = bounds.getMaxY();
        Scene scene = new Scene(root, wd, hgh);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handle_grade(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/admin-grades.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
        double wd = bounds.getMaxX();
        double hgh = bounds.getMaxY();
        Scene scene = new Scene(root, wd, hgh);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handle_instructor(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/admin-instructor.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
        double wd = bounds.getMaxX();
        double hgh = bounds.getMaxY();
        Scene scene = new Scene(root, wd, hgh);stage.setScene(scene);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handle_schedule(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/admin-schedule.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
        double wd = bounds.getMaxX();
        double hgh = bounds.getMaxY();
        Scene scene = new Scene(root, wd, hgh);
        stage.setScene(scene);
        stage.show();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	private void setActiveStyle(Button active, Button inactive) {
	    // Apply Active Style
	    active.setStyle("-fx-background-color: #20a7db; -fx-text-fill: white; -fx-border-color: #20a7db; -fx-border-width: 2;");
	    
	    // Apply Inactive Style
	    inactive.setStyle("-fx-background-color: white; -fx-text-fill: #20a7db; -fx-border-color: #20a7db; -fx-border-width: 2;");
	}
    
    
    
}
