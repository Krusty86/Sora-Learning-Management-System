package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Rectangle2D;

import java.net.URL;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.ExamDAO;
import models.Grade;
import models.GradeDAO;

public class StudentDashboardController {

    @FXML
    private Button btn_assignments;

    @FXML
    private Button btn_course;

    @FXML
    private Button btn_grade;
    @FXML
    void handleViewProgress(ActionEvent event) throws IOException {
        handleGrade(event);
        System.out.println("error?");
    }
    @FXML
    private Button btn_home;

    @FXML
    private Button btn_logout;

    @FXML
    private Button btn_schedule;

    @FXML
    private Button btn_studentinfo;

    @FXML
    private Label lbl_class1;

    @FXML
    private Label lbl_class2;

    @FXML
    private Label lbl_class3;

    @FXML
    private Label lbl_class4;

    @FXML
    private Label lbl_score1;

    @FXML
    private Label lbl_score2;

    @FXML
    private Label lbl_score3;

    @FXML
    private Label lbl_score4;

    
    @FXML
    private BorderPane mainContent;
    
    //a
    @FXML
    public void initialize() {
        int studentId = application.UserControl.getStu().getId(); 
        ArrayList<Grade> latestGrades = GradeDAO.getLatestScores(studentId); 

        if (latestGrades != null && !latestGrades.isEmpty()) {
            Label[] scoreLabels = {lbl_score1, lbl_score2, lbl_score3, lbl_score4};

           
            for (int i = 0; i < latestGrades.size() && i < scoreLabels.length; i++) {
                Grade g = latestGrades.get(i); 
                scoreLabels[i].setText(g.getCourse_name() + ": " + g.getResult());
                
                scoreLabels[i].setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold;");
            }
        }

        ArrayList<String> upcomingExams = ExamDAO.getUpcomingExams();
        if (upcomingExams != null && !upcomingExams.isEmpty()) {
            Label[] classLabels = {lbl_class1, lbl_class2, lbl_class3, lbl_class4};
            for (int i = 0; i < upcomingExams.size() && i < classLabels.length; i++) {
                classLabels[i].setText(upcomingExams.get(i));
            }
        }
    }
//        ArrayList<Grade> gradeList = GradeDAO.getGradesByStudent(1);
//        
//       
//        if (gradeList != null && !gradeList.isEmpty()) {
//            if (gradeList.size() >= 1) lbl_score1.setText(gradeList.get(0).getCourse_name() + ": " + gradeList.get(0).getResult());
//            if (gradeList.size() >= 2) lbl_score2.setText(gradeList.get(1).getCourse_name() + ": " + gradeList.get(1).getResult());
//            if (gradeList.size() >= 3) lbl_score3.setText(gradeList.get(2).getCourse_name() + ": " + gradeList.get(2).getResult());
//            if (gradeList.size() >= 4) lbl_score4.setText(gradeList.get(3).getCourse_name() + ": " + gradeList.get(3).getResult());
//        }
//        ArrayList<String> upcomingExams = ExamDAO.getUpcomingExams();
//        if (upcomingExams != null && !upcomingExams.isEmpty()) {
//            if (upcomingExams.size() >= 1) lbl_class1.setText(upcomingExams.get(0));
//            if (upcomingExams.size() >= 2) lbl_class2.setText(upcomingExams.get(1));
//            if (upcomingExams.size() >= 3) lbl_class3.setText(upcomingExams.get(2));
//            if (upcomingExams.size() >= 4) lbl_class4.setText(upcomingExams.get(3));
//            
//        }
//    }
    
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
