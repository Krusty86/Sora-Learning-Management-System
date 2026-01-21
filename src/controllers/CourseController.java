package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.Course;
import models.CourseDAO;
import models.CourseDetailsDAO;
import models.Enrollment;
import models.EnrollmentDAO;
import models.InstructorCourseDetail;


public class CourseController implements Initializable{
	
	
	
	 @FXML
	    void showData(MouseEvent event) {
		  
	    }
	 
	 @FXML
	 private Button btn_logout;
	 
	@FXML
    private ComboBox<Course> cb_course;
	
	@FXML
	private Button btn_confirm;

    @FXML
    private TableColumn<Enrollment, String> col_email;

    @FXML
    private TableColumn<Enrollment, String> col_major;

    @FXML
    private TableColumn<Enrollment, Number> col_no;

    @FXML
    private TableColumn<Enrollment, String> col_stName;

    
    @FXML
    private TableView<Enrollment> tbl_enrollment;
    
    @FXML
    private BorderPane mainContent;

    @FXML
    void handleCourseFilter(ActionEvent event) {
    	tbl_enrollment.setItems(FXCollections.observableArrayList(EnrollmentDAO.getEnrollmentDetailsByCourse(cb_course.getSelectionModel().getSelectedItem().getId())));
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
    void handle_confirm(ActionEvent event) {
    	Course selectedCourse=cb_course.getSelectionModel().getSelectedItem();
    	if(selectedCourse==null) {
    		return;
    	}
    	int courseId=selectedCourse.getId();
    	
    	ArrayList<Enrollment> selectLst=EnrollmentDAO.getEnrollmentDetailsByCourse(courseId);
    	tbl_enrollment.setItems(
    			FXCollections.observableArrayList(selectLst)
    			);
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
    void toggleCourse(MouseEvent event) {
    	

    }

    private Enrollment enrol;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		enrol=null;
		
		col_no.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : String.valueOf(getIndex() + 1));
            }
        });
		col_stName.setCellValueFactory(new PropertyValueFactory<Enrollment, String>("student"));
		col_email.setCellValueFactory(new PropertyValueFactory<Enrollment, String>("email"));
		col_major.setCellValueFactory(new PropertyValueFactory<Enrollment, String>("major"));
		
		ArrayList<Enrollment> enrolLst;
		enrolLst=EnrollmentDAO.getEnrollmentDetailsByCourse(UserControl.inst.getId());
//		System.out.println(enrolLst.get(0).getStudent());
//		System.out.println(enrolLst.get(0).getMajor());
		tbl_enrollment.setItems(FXCollections.observableArrayList(enrolLst));
		cb_course.setItems(FXCollections.observableArrayList(CourseDAO.getCourseByInstructor(UserControl.inst.getId())));
	
		
	}

}   