package controllers;
import models.*;

import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;

import application.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class InstructorGradeController implements Initializable{

    @FXML
    private Button btn_assignment;

    @FXML
    private Button btn_course;

    @FXML
    private Button btn_grade;

    @FXML
    private Button btn_home;

    @FXML
    private Button btn_schedule;

    @FXML
    private Button btn_studentInfo;

    @FXML
    private ComboBox<Course> cbl_courseFilter;

    @FXML
    private ComboBox<Exam> cbl_examFilter;

    @FXML
    private TableColumn<Grade, Double> col_avg;

    @FXML
    private TableColumn<Grade, Number> col_count;

    @FXML
    private TableColumn<Grade, String> col_course;

    @FXML
    private TableColumn<Grade, String> col_grade;

    @FXML
    private TableColumn<Grade, String> col_stuName;

    @FXML
    private Label lbl_activeCourses;

    @FXML
    private Label lbl_classAverage;

    @FXML
    private Label lbl_currentSem;

    @FXML
    private Label lbl_stuCount;

    @FXML
    private Label lbl_ungraded;

    @FXML
    private Label lbl_welcome;

    @FXML
    private BorderPane mainContent;

    @FXML
    private TableView<Grade> tbl_grades;
    @FXML
    private Button btn_exit;
    @FXML
    private TextField txt_search;

    @FXML
    void handleAssignment(ActionEvent event) {

    }

    @FXML
    void handleCourse(ActionEvent event) {

    }

    @FXML
    void handleCourseFilter(ActionEvent event) {

    }

    @FXML
    void handleExamFilter(ActionEvent event) {

    }

    @FXML
    void handleGrade(ActionEvent event) {

    }

    @FXML
    void handleHome(ActionEvent event) {

    }

    @FXML
    void handleSchedule(ActionEvent event) {

    }

    @FXML
    void handleInstructorInfo(ActionEvent event) {

    }

    @FXML
    void toggleCourse(MouseEvent event) {

    }
    @FXML
    void handleExit(ActionEvent event) {
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		InstructorStats inslst = InstructorStatsDAO.getInstructorDashboardStats(UserControl.inst.getId());
		lbl_stuCount.setText(String.valueOf(inslst.getStu_count()));
		lbl_ungraded.setText(String.valueOf(inslst.getUngraded_count()));
		lbl_activeCourses.setText(String.valueOf(inslst.getCourse_count()));
		col_count.setCellFactory(col -> new TableCell<>() {
			 protected void updateItem(Number item, boolean empty) {
	                super.updateItem(item, empty);
	                setText(empty ? null : String.valueOf(getIndex() + 1));
	            }
		});
		col_stuName.setCellValueFactory(new PropertyValueFactory<>("stu_name"));
		col_course.setCellValueFactory(new PropertyValueFactory<>("course_name"));
		col_grade.setCellValueFactory(new PropertyValueFactory<>("result"));
		col_avg.setCellValueFactory(new PropertyValueFactory<>("average"));
		tbl_grades.setItems(FXCollections.observableArrayList(GradeDAO.getDetailedPerformance(UserControl.inst.getId())));
	}

   
    
}
