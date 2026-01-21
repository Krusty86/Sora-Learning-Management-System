package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Rectangle2D;

import java.net.URL;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.CourseDetailsDAO;
import models.Grade;
import models.GradeDAO;
import models.InstructorCourseDAO;
import models.InstructorCourseDetailDAO;

public class StudentGradeController {

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
    private Button btn_enterExam;

    @FXML
    private Button btn_enterYear;

    @FXML
    private ComboBox<String> cbl_exam;

    @FXML
    private ComboBox<String> cbl_year;
    
    @FXML
    private TableView<Grade> tableGrade;
    
    @FXML
    private TableColumn<Grade, String> col_average;

    @FXML
    private TableColumn<Grade, String> col_course;

    @FXML
    private TableColumn<Grade, String> col_final;

    @FXML
    private TableColumn<Grade, String> col_grade;
    
   
    @FXML
    private BorderPane mainContent;
    
    @FXML
    public void initialize() {
        
        col_course.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        col_grade.setCellValueFactory(new PropertyValueFactory<>("result"));
        col_average.setCellValueFactory(new PropertyValueFactory<>("ex_name"));
        col_final.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));
        
        cbl_year.setItems(FXCollections.observableArrayList("Year 1", "Year 2"));
        
        ArrayList<String> examList = GradeDAO.getExamTypes(); 
        if (examList != null) {
            ObservableList<String> options = FXCollections.observableArrayList();
            options.add("All Exams"); 
            options.addAll(examList);
            cbl_exam.setItems(options);
        }
//        ArrayList<String> examList = GradeDAO.getExamNames();
//        if (examList != null) {
//            cbl_exam.setItems(FXCollections.observableArrayList(examList));
//        }
//        
        if (application.UserControl.getStu() != null) {
            loadGradeData(application.UserControl.getStu().getId());
        }
        //loadGradeData(1); 
        int stuId = application.UserControl.getStu().getId();
            loadGradeData(stuId); 
            
            double finalGPA = GradeDAO.calculateGPA(stuId); 
            lbl_gpa.setText(String.format("%.2f", finalGPA));
    
} 
    @FXML
    private Label lbl_gpa;
    private void loadGradeData(int studentId) {
        ArrayList<Grade> grades = GradeDAO.getGradesByStudent(studentId);
        if (grades != null) {
            for (Grade g : grades) {
                processGradeData(g);
            }
            System.out.println(grades.size());
            tableGrade.setItems(FXCollections.observableArrayList(grades)); 
        }
    }
   
   
    private void processGradeData(Grade g) {
        if (g.getResult() != null && !g.getResult().isEmpty()) {
            try {
                int score = Integer.parseInt(g.getResult().trim());
                g.setAverage(score); 
                g.setLetterGrade(calculateGradeLetter(score)); 
            } catch (Exception e) {
                g.setLetterGrade("N/A");
            }
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
    

    
//    @FXML
//    void handleFilter(ActionEvent event) {
//        String selectedYear = cbl_year.getValue(); 
//        if (selectedYear != null) {
//            int yearId = selectedYear.equals("Year 1") ? 1 : 2;
//            loadGradeByYear(yearId); 
//        } else {
//            System.out.println("Please choose a year");
//        }
//    }
//    
    private void loadGradeByYear(int yearId) {
        try {
            int studentId = application.UserControl.getStu().getId(); 
            ArrayList<Grade> grades = GradeDAO.getGradesByStudent(studentId);
            
            if (grades != null) {
                ObservableList<Grade> filteredList = FXCollections.observableArrayList();
                for (Grade g : grades) {
                    if (g.getYear_id() == yearId) {
                       
                        if (g.getResult() != null && !g.getResult().isEmpty()) {
                            int score = Integer.parseInt(g.getResult().trim());
                            g.setAverage(score); 
                            g.setLetterGrade(calculateGradeLetter(score)); 
                        }
                        filteredList.add(g);
                    }
                }
                tableGrade.setItems(filteredList);
            }
        } catch (Exception e) {
            System.err.println("Error in loadGradeByYear: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    void handleExamFilter(ActionEvent event) {
        String selected = cbl_exam.getValue(); 
        int studentId = application.UserControl.getStu().getId();
        ArrayList<Grade> allGrades = GradeDAO.getGradesByStudent(studentId);
        System.out.println(allGrades.size());
        if (allGrades != null) {
            ObservableList<Grade> filtered = FXCollections.observableArrayList();
            
            for (Grade g : allGrades) {
                if (selected == null || selected.equals("All Exams")) {
                    processGradeData(g);
                    filtered.add(g);
                } 
                else if (g.getEx_name() != null && g.getEx_name().equals(selected)) {
                    processGradeData(g);
                    filtered.add(g);
                }
            }
            tableGrade.setItems(filtered);
        }
    }
    @FXML
    void handleYearFilter(ActionEvent event) {
        String selectedYear = cbl_year.getValue();    
        if (selectedYear != null) {
                   int yearId = selectedYear.equals("Year 1") ? 1 : 2;
            
                    loadGradeByYear(yearId); 
        } else {
            System.out.println("Please choose a year");
        }
    }
    
//    @FXML
//    void handleExamFilter(ActionEvent event) {
//        String selectedExam = cbl_exam.getValue();
//        
//        if (selectedExam != null) {
//            loadGradeByExam(selectedExam);
//        } else {
//            System.out.println("Please choose an exam type");
//        }
//    }
    
//    private void loadGradeByExam(String examName) {
//        try {
//            int studentId = application.UserControl.getStu().getId();
//            ArrayList<Grade> grades = GradeDAO.getGradesByStudent(studentId);
//            
//            if (grades != null) {
//                ObservableList<Grade> filteredList = FXCollections.observableArrayList();
//                for (Grade g : grades) {
//                    
//                    if (g.getEx_name() != null && g.getEx_name().equals(examName)) {
//                        processGradeData(g); 
//                        filteredList.add(g);
//                    }
//                }
//                tableGrade.setItems(filteredList);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    private String calculateGradeLetter(int score) {
        if (score >= 80) return "A";
        if (score >= 70) return "B";
        if (score >= 60) return "C";
        if (score >= 50) return "D";
        return "F";
    }
}
