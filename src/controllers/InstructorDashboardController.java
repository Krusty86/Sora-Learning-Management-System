package controllers;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.Course;
import models.CourseDAO;
import models.Details;
import models.EnrollmentDAO;
import models.InstructorCourseDAO;
import models.InstructorCourseDetailDAO;
import models.Details;

public class InstructorDashboardController implements Initializable{

	@FXML
    private Button btn_logout;
	
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
    private TableColumn<Details, Integer> colAssignments;

    @FXML
    private TableColumn<Details, Double> colGrade;

    @FXML
    private TableColumn<Details, String> colName;

    @FXML
    private TableColumn<Details, Integer> colStudents;

    @FXML
    private TableView<Details> tbl_course;

    @FXML
    private PieChart gradePieChart;

    @FXML
    private Label lbl_average;

    @FXML
    private Label lbl_courses;

    @FXML
    private Label lbl_review;

    @FXML
    private Label lbl_student;

    @FXML
    private BorderPane mainContent;

    @FXML
    private BarChart<String, Number> submissionChart;
    
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
    void toggleCourse(MouseEvent event) {

    }
    
    @FXML
    void showData(MouseEvent event) {

    }

         private Details course;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
    	
    	course=null;
    	lbl_courses.setText(String.valueOf(InstructorCourseDAO.getCourseCountByInstructor(UserControl.inst.getId())));
    	lbl_student.setText(String.valueOf(EnrollmentDAO.getStudentCountByInstructor(UserControl.inst.getId())));
    	colName.setCellValueFactory(new PropertyValueFactory<Details, String>("name"));
    	colStudents.setCellValueFactory(new PropertyValueFactory<Details, Integer>("stu_count"));
    	colAssignments.setCellValueFactory(new PropertyValueFactory<Details, Integer>("assign_count"));
    	colGrade.setCellValueFactory(new PropertyValueFactory<Details, Double>("avg_grade"));
    	
    	ArrayList<Details> detailLst=InstructorCourseDetailDAO.getInstructorStats(UserControl.inst.getId());
    	tbl_course.setItems(FXCollections.observableArrayList(detailLst));

        // --- POPULATE PIE CHART (Grades Distribution per Course) ---
        gradePieChart.getData().clear();
        for (Details d : detailLst) {
            // Adding course name and its average grade to the Pie Chart
            gradePieChart.getData().add(new PieChart.Data(d.getName(), d.getAvg_grade()));
        }

        // --- POPULATE BAR CHART (Average Grade per Course) ---
        submissionChart.getData().clear();
        XYChart.Series series = new XYChart.Series();
        series.setName("Average Grade");

        for (Details d : detailLst) {
            // X-axis: Course Name, Y-axis: Average Grade
            series.getData().add(new XYChart.Data(d.getName(), d.getAvg_grade()));
        }

        // Add series to the chart
        submissionChart.getData().add(series);

    }
}
