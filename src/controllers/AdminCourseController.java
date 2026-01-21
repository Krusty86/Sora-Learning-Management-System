package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import application.DBConnection;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.AssignmentDetails;
import models.AssignmentDetailsDAO;
import models.Course;
import models.CourseDAO;
import models.CourseDetails;
import models.CourseDetailsDAO;
import models.Instructor;

public class AdminCourseController implements Initializable{

	    @FXML
	    private AnchorPane anc_assignment;

	    @FXML
	    private AnchorPane anc_course;

	    @FXML
	    private TextField assignmentSearchField;

	    @FXML
	    private TableView<AssignmentDetails> assignmentTable;

	    @FXML
	    private Button btnAssignments;

	    @FXML
	    private Button btnCourses;

	    @FXML
	    private Button btn_account;

	    @FXML
	    private Button btn_addCourse;

	    @FXML
	    private Button btn_course;

	    @FXML
	    private Button btn_filter;

	    @FXML
	    private Button btn_grade;

	    @FXML
	    private Button btn_home;

	    @FXML
	    private Button btn_instructor;

	    @FXML
	    private Button btn_logOut;

	    @FXML
	    private Button btn_shedule;

	    @FXML
	    private Button btn_student;

	    @FXML
	    private Button btn_unFilter;

	    @FXML
	    private ComboBox<Course> cbl_courseFilter;

	    @FXML
	    private TableColumn<AssignmentDetails, String> colCourse;

	    @FXML
	    private TableColumn<AssignmentDetails, Date> colDueDate;

	    @FXML
	    private TableColumn<AssignmentDetails, String> colStatus;

	    @FXML
	    private TableColumn<AssignmentDetails, Integer> colSubmissions;

	    @FXML
	    private TableColumn<AssignmentDetails, String> colTitle;

	    @FXML
	    private TableColumn<CourseDetails, String> col_instructor;

	    @FXML
	    private TableColumn<CourseDetails, String> col_major;

	    @FXML
	    private TableColumn<CourseDetails, String> col_name;

	    @FXML
	    private TableColumn<CourseDetails, Integer> col_students;

	    @FXML
	    private TableColumn<CourseDetails, String> col_year;

	    @FXML
	    private TableView<CourseDetails> courseTable;

	    @FXML
	    private Label lbl_title;
	    
	    @FXML
	    private Label lbl_activeSem;

	    @FXML
	    private Label lbl_assignmentCompletionAvg;

	    @FXML
	    private Label lbl_assignmentCount;

	    @FXML
	    private Label lbl_courseCount;

	    @FXML
	    private Label lbl_instructorCount;

	    @FXML
	    private Label lbl_stuCount;

	    @FXML
	    private Label lbl_ungradedAssignment;

	    @FXML
	    private BorderPane mainContent;
	    @FXML
	    private Button filter;

	    @FXML
	    private TextField txt_search_course;
	    @FXML
	    void handleFilter(ActionEvent event) {
	    	refreshCourseTable(CourseDetailsDAO.getFilteredCourseDetails(txt_search_course.getText()));
	    	
	    }

	    @FXML
	    void handleSwitchView(ActionEvent event) {
	    	if (event.getSource() == btnCourses) {
	            anc_course.toFront();
	            updateUI("Course Management");
	            setActiveTab(btnCourses, btnAssignments);
	            refreshCourseTable();
	            
	        } else if (event.getSource() == btnAssignments) {
	            anc_assignment.toFront();
	            updateUI("Assignment Management");
	            setActiveTab(btnAssignments, btnCourses);
	            refreshAssignmentTable();
	        }
	    }
	    private void updateUI(String title) {
	        lbl_title.setText(title);
	    }

	    private void setActiveTab(Button active, Button inactive) {
	        active.getStyleClass().remove("tab-button-inactive");
	        active.getStyleClass().add("tab-button-active");
	        
	        inactive.getStyleClass().remove("tab-button-active");
	        inactive.getStyleClass().add("tab-button-inactive");
	    }
	    @FXML
	    void handleCourseFilter(ActionEvent event) {
	    	refreshAssignmentTable(cbl_courseFilter.getSelectionModel().getSelectedItem().getId());
	    }
	    private Integer selectedCourseId = null;
	    private Integer selectedMajorId = null;

	    @FXML
	    void showData(MouseEvent event) {
	        // Get the selected item from the TableView
	        CourseDetails selected = courseTable.getSelectionModel().getSelectedItem();
	        
	        if (selected != null) {
	            selectedCourseId = selected.getCourse_id(); 
	            selectedMajorId = selected.getMajor_id();
	            btn_addCourse.setText("Update Course");
	        }
	    }

	    @FXML
	    void handleAddCourse(ActionEvent event) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/courseForm.fxml"));
	            Parent root = loader.load();
	            AdminCourseFormController controller = loader.getController();

	            if (selectedCourseId != null) {
	                // Retrieve the full Course object from your Table or DAO
	                CourseDetails courseToEdit = courseTable.getSelectionModel().getSelectedItem();
	                controller.setMode("update", courseToEdit);
	            } else {
	                controller.setMode("create", null);
	            }

	            Stage stage = new Stage();
	            stage.setScene(new Scene(root));
	            stage.setResizable(false);
	            stage.showAndWait();
	            
	            // Refresh your table here after the form closes
	            refreshCourseTable(); 
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    @FXML
	    void handleAssignmentFilter(ActionEvent event) {
	    	
	    }

	    @FXML
	    void handleAssignmentUnfilter(ActionEvent event) {
	    	refreshAssignmentTable();
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
	    ArrayList<Course> cLst;
	    Course course;
	    
	    private void refreshCourseTable() {
	    	btn_addCourse.setVisible(true);
	        ArrayList<CourseDetails> cdLst = CourseDetailsDAO.getCourseStats();
	        courseTable.setItems(FXCollections.observableArrayList(cdLst));
	    }
	    private void refreshCourseTable(ArrayList<CourseDetails> obj) {
	        courseTable.setItems(FXCollections.observableArrayList(obj));
	    }

	    private void refreshAssignmentTable() {
	    	btn_addCourse.setVisible(false);
	        ArrayList<AssignmentDetails> adLst = AssignmentDetailsDAO.getAllAssignmentReports();
	        assignmentTable.setItems(FXCollections.observableArrayList(adLst));
	    }
	    private void refreshAssignmentTable(int course_id) {
	    	btn_addCourse.setVisible(false);
	        ArrayList<AssignmentDetails> adLst = AssignmentDetailsDAO.getAssignmentReport(course_id);
	        assignmentTable.setItems(FXCollections.observableArrayList(adLst));
	    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
//		Course Section
		col_name.setCellValueFactory(new PropertyValueFactory<>("course_name"));
		col_instructor.setCellValueFactory(new PropertyValueFactory<>("instructor_name"));
		col_major.setCellValueFactory(new PropertyValueFactory<>("major_name"));
		col_year.setCellValueFactory(new PropertyValueFactory<>("year_name"));
		col_students.setCellValueFactory(new PropertyValueFactory<>("student_count"));
		updateDashboardStats();
		
		
//		Assignment Section
		colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
	    colDueDate.setCellValueFactory(new PropertyValueFactory<>("due_date"));
	    colCourse.setCellValueFactory(new PropertyValueFactory<>("course_name"));
	    colSubmissions.setCellValueFactory(new PropertyValueFactory<>("submitted"));
	    colStatus.setCellValueFactory(new PropertyValueFactory<>("total"));

    	cbl_courseFilter.setItems(FXCollections.observableArrayList(CourseDAO.getAllCourses()));
	    refreshCourseTable();
	    updateDashboardStats();
		
	}
//	private void updateDashboardStats() {
//	    String query = "SELECT (SELECT COUNT(*) FROM enrollments), " +
//	                  "(SELECT COUNT(*) FROM instructorcourses),"
//	                  + "(SELECT COUNT(*) FROM courses)";
//	    
//	    try (Connection conn = DBConnection.connect(); 
//	         PreparedStatement pstmt = conn.prepareStatement(query);
//	         ResultSet rs = pstmt.executeQuery()) {
//	        
//	        if (rs.next()) {
//	            int studentCount = rs.getInt(1);
//	            int instructorCount = rs.getInt(2);
//	            int courseCount = rs.getInt(3);
//
//	            lbl_stuCount.setText(String.valueOf(studentCount));
//	            lbl_instructorCount.setText(String.valueOf(instructorCount));
//	            lbl_courseCount.setText(String.valueOf(courseCount));
//	        }
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    } catch (ClassNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	}
	private void updateDashboardStats() {
	    // Extended query to fetch Assignment stats alongside Course/Student/Instructor stats
	    String query = "SELECT " +
	                  "(SELECT COUNT(*) FROM enrollments) AS stu_count, " +
	                  "(SELECT COUNT(*) FROM instructorcourses) AS ins_count, " +
	                  "(SELECT COUNT(*) FROM courses) AS course_count, " +
	                  "(SELECT COUNT(DISTINCT course_id) FROM assignments) AS assign_count, " +
	                  "(SELECT COUNT(*) FROM assignments WHERE status = 'false') AS ungraded_count, " +
	                  "(SELECT ROUND(AVG(CASE WHEN status = 'true' THEN 1 ELSE 0 END) * 100, 1) FROM assignments) AS completion_avg";
	    
	    try (Connection conn = DBConnection.connect(); 
	         PreparedStatement pstmt = conn.prepareStatement(query);
	         ResultSet rs = pstmt.executeQuery()) {
	        
	        if (rs.next()) {
	            // General Stats
	            lbl_stuCount.setText(String.valueOf(rs.getInt("stu_count")));
	            lbl_instructorCount.setText(String.valueOf(rs.getInt("ins_count")));
	            lbl_courseCount.setText(String.valueOf(rs.getInt("course_count")));

	            // Assignment Specific Stats
	            lbl_assignmentCount.setText(String.valueOf(rs.getInt("assign_count")));
	            lbl_ungradedAssignment.setText(String.valueOf(rs.getInt("ungraded_count")));
	            
	            // Completion Average (e.g., "75.5%")
	            double avg = rs.getDouble("completion_avg");
	            lbl_assignmentCompletionAvg.setText(avg + "%");
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}

}
