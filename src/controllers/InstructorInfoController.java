package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.DBConnection;
import application.UserControl;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.Details;
import models.Instructor;
import models.InstructorCourseDetailDAO;
import models.InstructorDAO;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class InstructorInfoController implements Initializable{

    @FXML
    private Button btn_assignment;

    @FXML
    private Button btn_course;

    @FXML
    private Button btn_grade;

    @FXML
    private Button btn_home;

    @FXML
    private Button btn_instructorInfo;

    @FXML
    private Button btn_logout;

    @FXML
    private Button btn_schedule;

    @FXML
    private TableColumn<Details, String> col_courseName;

    @FXML
    private TableColumn<Details, String> col_major;

    @FXML
    private TableColumn<Details, Integer> col_studentCount;

    @FXML
    private Label instructorInfo;

    @FXML
    private Label lbl_courseCount;

    @FXML
    private Label lbl_email;

    @FXML
    private Label lbl_instShortName;

    @FXML
    private Label lbl_instructorName;

    @FXML
    private Label lbl_phone;

    @FXML
    private Label lbl_studentCount;

    @FXML
    private BorderPane mainContent;

    @FXML
    private TableView<Details> tbl_courses;
    
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
    void handleHome(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/instructorDashboard.fxml"));
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
    void handleCourse(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/instructorCourses.fxml"));
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
    void handleAssignment(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/instructorAssignments.fxml"));
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
    void handleGrade(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/instructorGrade.fxml"));
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
    void handleSchedule(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/instructorSchedule.fxml"));
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
    void handleInstructorInfo(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/instructorInstructorInfo.fxml.fxml"));
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
    void toggleCourse(MouseEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Instructor inst = InstructorDAO.getInstructorById(UserControl.inst.getId()).get(0);
		col_courseName.setCellValueFactory(new PropertyValueFactory<>("name"));
    	col_major.setCellValueFactory(new PropertyValueFactory<>("major_name"));
    	col_studentCount.setCellValueFactory(new PropertyValueFactory<>("stu_count"));
    	System.out.println(UserControl.inst.getId());
    	ArrayList<Details> data= InstructorCourseDetailDAO.getInstructorCourseLoad(UserControl.inst.getId());
    	for(Details d: data) {
    		System.out.println(d.getName()+" "+d.getStu_count());
    	}
    	tbl_courses.setItems(FXCollections.observableArrayList(data));
    	lbl_email.setText(inst.getEmail());
    	lbl_instructorName.setText(inst.getName());
    	lbl_phone.setText(inst.getPh());
//    	lbl_studentCount
    	
	}


	
}
