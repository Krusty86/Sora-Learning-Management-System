package controllers;

import java.io.IOException;
import java.util.ArrayList;


import application.DBConnection;
import application.UserControl;
import models.Enrollment;
import models.EnrollmentDAO;
import models.Grade;
import models.GradeDAO;
import models.StudentDAO;
import models.Student;
import models.Slot;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.scene.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class StudentInfoController {

    @FXML
    private Button btn_assignments;

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
    private LineChart<String,Number> chart_grade;
    
    @FXML
    private TableView<Grade>table_marks;
    

    @FXML
    private TableColumn<Grade, Double> col_average;
    
    @FXML
    private TableColumn<Grade, String> col_course;

    @FXML
    private TableColumn<Grade, String> col_grade;

    @FXML
    private TableColumn<Grade, Integer> col_lower;

    @FXML
    private TableColumn<Grade, Integer> col_upper;
    
    @FXML
    private Label lbl_classno;

    @FXML
    private Label lbl_gpa;

    @FXML
    private Label lbl_major;

    @FXML
    private Label lbl_name;

    @FXML
    private Label lbl_year;

    @FXML
    private BorderPane mainContent;
    
    
    
    @FXML
    public void initialize() {
        
        col_course.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        col_grade.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));
        col_average.setCellValueFactory(new PropertyValueFactory<>("average"));

        chart_grade.getXAxis().setTickLabelRotation(45);
        loadStudentData(UserControl.getStu().getId());
       
    }

    	private void loadStudentData(int studentId) {
    	ArrayList<Student> stuList = StudentDAO.getStudentById(studentId);
        if (stuList == null || stuList.isEmpty()) return;
        Student stu = stuList.get(0);
        
        Enrollment enroll=EnrollmentDAO.getEnrollmentByStudentId(studentId);
        System.out.println(enroll.getStudent());
            lbl_name.setText(enroll.getStudent());
            lbl_year.setText(enroll.getYear());
            lbl_classno.setText(enroll.getClass_name());
            lbl_major.setText(enroll.getMajor());
            lbl_gpa.setText(String.format("%.2f", GradeDAO.calculateGPA(studentId)));
            
            ArrayList<Grade> grades = GradeDAO.getGradesByStudentID(stu); 
            ArrayList<Grade> avgGrades = GradeDAO.getStudentAveragesPerCourse(studentId);
            
//            table_marks.setItems(FXCollections.observableArrayList(avgGrades));
//            double gpaValue = GradeDAO.calculateGPA(studentId);
//            
         
           

        if (grades != null) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Semester Progress");

            for (Grade g : grades) {
                try {
                	
                    int score = Integer.parseInt(g.getResult());
                    g.setLetterGrade(calculateGradeLetter(score)); 
                   // String letter = calculateGradeLetter(score);
                    //g.setLetterGrade(letter);
                    for(Grade avgItem : avgGrades) {
                        if(avgItem.getCourse_name().equals(g.getCourse_name())) {
                            g.setAverage(avgItem.getAverage());
                        }
                    }
                    
                    
                   series.getData().add(new XYChart.Data<>(g.getCourse_name(), score)); 
                } catch (Exception e) {
                   g.setLetterGrade("F");
                }
            }
            
            table_marks.setItems(FXCollections.observableArrayList(grades));
            
            
            chart_grade.getData().clear();
            chart_grade.getData().add(series);
        }
    }

    
    private String calculateGradeLetter(int score) {
        if (score >= 80) return "A";
        if (score >= 70) return "B";
        if (score >= 60) return "C";
        if (score >= 50) return "D";
        return "F";
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
}
