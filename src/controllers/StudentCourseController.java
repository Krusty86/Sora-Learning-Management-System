package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.UserControl;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Rectangle2D;

import java.net.URL;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.Course;
import models.CourseDAO;
import models.CourseDetailsDAO;
import models.GradeDAO;

public class StudentCourseController implements Initializable {

    @FXML
    private Button btn_assignment;

    @FXML
    private Button btn_course;

    @FXML
    private Button btn_grade;

    @FXML
    private Button btn_home;

    @FXML
    private Button btn_logout;

    @FXML
    private Button btn_schedule;

    @FXML
    private Button btn_studentinfo;

    @FXML
    private Label lbl_course1;

    @FXML
    private Label lbl_course2;

    @FXML
    private Label lbl_course3;

    @FXML
    private Label lbl_course4;

    @FXML
    private Label lbl_datedays;

    @FXML
    private HBox card_1;

    @FXML
    private HBox card_2;

    @FXML
    private HBox card_3;

    @FXML
    private HBox card_4;
    
    @FXML
    private BorderPane mainContent;
    
    private ArrayList<Course> studentCourseList = new ArrayList<>();
  
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	loadCourseData();
    	int totalExams = GradeDAO.getTotalCount();
        lbl_datedays.setText(totalExams + " Exams");
    }
    private void loadCourseData() {
    	studentCourseList = CourseDAO.getStudentCoursesObjects();
    	
    	if (studentCourseList != null ) {
            if (studentCourseList.size() >= 1) lbl_course1.setText(studentCourseList.get(0).getName()); 
            if (studentCourseList.size() >= 2) lbl_course2.setText(studentCourseList.get(1).getName());
            if (studentCourseList.size() >= 3) lbl_course3.setText(studentCourseList.get(2).getName());
            if (studentCourseList.size() >= 4) lbl_course4.setText(studentCourseList.get(3).getName());
              
//ArrayList<String> courses = CourseDAO.getStudentCoursesNames();
//        if (courses != null ) {
//            
//            if (courses.size() >= 1) lbl_course1.setText(courses.get(0)); 
//            if (courses.size() >= 2) lbl_course2.setText(courses.get(1));
//            if (courses.size() >= 3) lbl_course3.setText(courses.get(2));
//            if (courses.size() >= 4) lbl_course4.setText(courses.get(3));
//      studentCourseList = CourseDAO.getStudentCoursesObjects(); 
        
        
        }
    }
    	
    @FXML
    void handleAssignments(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/assignments.fxml"));
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
    void handleCourse(ActionEvent event)  throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/courses.fxml"));
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
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/grade.fxml"));
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
    void handleHome(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/student-dashboard.fxml"));
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
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/schedule.fxml"));
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
    void handleStudentInfo(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/student-info.fxml"));
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
    void handleLogout(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
        URL url = getClass().getResource("/fxml/Login-form.fxml");
        loader.setLocation(url);
        Parent root = loader.load();
		Scene scene = new Scene(root,1121,570);
		scene.getStylesheets().add(getClass().getResource("/application/login-form.css").toExternalForm());
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new Scene
        stage.setScene(scene);

        // Center on screen and show
        stage.centerOnScreen();
        stage.show();
    }
    
    @FXML
    void handleCourseClick(MouseEvent event) {
        Object source = event.getSource();
        
        if (studentCourseList == null || studentCourseList.isEmpty()) return;

        
        if (source == card_1 && studentCourseList.size() >= 1) {
            UserControl.selectedCourseId = studentCourseList.get(0).getId();
        } else if (source == card_2 && studentCourseList.size() >= 2) {
            UserControl.selectedCourseId = studentCourseList.get(1).getId();
        } else if (source == card_3 && studentCourseList.size() >= 3) {
            UserControl.selectedCourseId = studentCourseList.get(2).getId();
        } else if (source == card_4 && studentCourseList.size() >= 4) {
            UserControl.selectedCourseId = studentCourseList.get(3).getId();
        }

        
        try {
            BorderPane pane = FXMLLoader.load(getClass().getResource("/fxml/assignments.fxml"));
            mainContent.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
   
