package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.Course;
import models.CourseDAO;
import models.Exam;
import models.ExamDAO;
import models.Grade;
import models.GradeDAO;
import models.InstructorStats;
import models.InstructorStatsDAO;

public class GradeController implements Initializable{
	private Grade gradeData;
    @FXML
    void handlePendingGrades(MouseEvent event) {

    }
	@FXML
    private AnchorPane anc_course1;

    @FXML
    private AnchorPane anc_course2;

    @FXML
    private AnchorPane anc_course3;
    
    @FXML
    private Button btn_logout;

    @FXML
    private Button btn_assignment;

    @FXML
    private Button btn_c1;

    @FXML
    private Button btn_c2;

    @FXML
    private Button btn_c3;

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
    private Button btn_create;
    
    @FXML
    private Button btn_update;
    
    @FXML
    private Button btn_search;

    @FXML
    private Button btn_delete;
    
    @FXML
    private TextField txt_search;
    
    @FXML
    private TextField txt_exam;

    @FXML
    private TextField txt_grade;

    @FXML
    private TextField txt_name;

    @FXML
    private Label lbl_course1;

    @FXML
    private Label lbl_course2;

    @FXML
    private Label lbl_course3;

    @FXML
    private BorderPane mainContent;

    @FXML
    private StackPane stack_;

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
    private TableView<Grade> tbl_grades;

    @FXML
    private Button btn_exit;

    @FXML
    void handleCourseFilter(ActionEvent event) {
    	refreshTable(GradeDAO.getGradesByInstructorIdAndFilter(UserControl.inst.getId(), cbl_courseFilter.getSelectionModel().getSelectedItem().getName()));
    }

    @FXML
    void handleExamFilter(ActionEvent event) {
    	refreshTable(GradeDAO.getGradesByInstructorIdAndFilter(UserControl.inst.getId(), cbl_examFilter.getSelectionModel().getSelectedItem().getName()));
    }
    @FXML
    void handleExit(ActionEvent event) {
    	hb_submitGrade.setVisible(false);
		hb_submitGrade.setManaged(false);
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		hb_submitGrade.setVisible(false);
		hb_submitGrade.setManaged(false);
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
		col_course.setCellValueFactory(new PropertyValueFactory<>("ex_name"));
		col_grade.setCellValueFactory(new PropertyValueFactory<>("result"));
		col_avg.setCellValueFactory(new PropertyValueFactory<>("examTypeName"));
		refreshTable(GradeDAO.getGradesByInstructorId(UserControl.inst.getId()));
		cbl_courseFilter.setItems(FXCollections.observableArrayList(CourseDAO.getCourseByInstructor(UserControl.inst.getId())));
		cbl_examFilter.setItems(FXCollections.observableArrayList(ExamDAO.getExamsByInstructor(UserControl.inst.getId())));
		
		
	}
	void refreshTable(ArrayList<Grade> gLst) {
		tbl_grades.setItems(FXCollections.observableArrayList(gLst));
	}

    @FXML
    private Button btn_gradeSubmit;
    @FXML
    private HBox hb_submitGrade;
    @FXML
    private Label lbl_examName;    
    @FXML
    private Label lbl_stuName;
    @FXML
    private TextField txt_gradeInput;
    
    @FXML
    void handleGradeSubmit(ActionEvent event) {
    	System.out.println("Inside grade submit");
    	LocalDate currentDate = LocalDate.now();
    	if(currentDate.isAfter(gradeData.getDate())) {
    		if(GradeDAO.updateGrade(gradeData.getId(), txt_gradeInput.getText())) {
        		System.out.println("Success");
        		refreshTable(GradeDAO.getGradesByInstructorId(UserControl.inst.getId()));
        		hb_submitGrade.setVisible(false);
        		hb_submitGrade.setManaged(false);
        		Alert alert = new Alert(AlertType.INFORMATION,"Graded Successfully");
        		alert.show();
        	}
    	}else {
    		Alert alert = new Alert(AlertType.ERROR,"You cannot start grading yet.");
    		alert.show();
    	}
    	
    	
    }
    
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
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/instructorInstructorInfo.fxml"));
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
    void getData(MouseEvent event) {
    	hb_submitGrade.setVisible(true);
		hb_submitGrade.setManaged(true);
    	gradeData = tbl_grades.getSelectionModel().getSelectedItem();
    	System.out.println("Inside Get Data Method");
    	lbl_stuName.setText(gradeData.getStu_name());
    	lbl_examName.setText(gradeData.getEx_name());
    	txt_grade.setText(String.valueOf(gradeData.getResult()));
    }
    @FXML
    void toggleCourse(MouseEvent event) {

    }

}
