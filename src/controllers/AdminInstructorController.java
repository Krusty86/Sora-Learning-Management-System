package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.Course;
import models.CourseDAO;
import models.Enrollment;
import models.Instructor;
import models.InstructorCourse;
import models.InstructorCourseDAO;
import models.InstructorCourseDetail;
import models.InstructorDAO;
import models.Student;
import models.StudentDAO;

public class AdminInstructorController implements Initializable{

    @FXML
    private AnchorPane anc_assign;

    @FXML
    private AnchorPane anc_viewAll;

    @FXML
    private Button assignCourse;

    @FXML
    private Button btn_account1;

    @FXML
    private Button btn_course1;

    @FXML
    private Button btn_create;

    @FXML
    private Button btn_create1;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_delete1;

    @FXML
    private Button btn_grade1;

    @FXML
    private Button btn_home1;

    @FXML
    private Button btn_instructor1;

    @FXML
    private Button btn_search;

    @FXML
    private Button btn_searchAssign;

    @FXML
    private Button btn_shedule1;

    @FXML
    private Button btn_student1;

    @FXML
    private Button btn_update;

    @FXML
    private Button btn_update1;

    @FXML
    private ComboBox<Course> cbl_courseA;

    @FXML
    private ComboBox<Course> cbl_courseB;

    @FXML
    private ComboBox<Course> cbl_courseC;

    @FXML
    private ComboBox<Instructor> cbl_name;

    @FXML
    private TableColumn<Instructor, String> col_address;

    @FXML
    private TableColumn<Instructor, Number> col_count;

    @FXML
    private TableColumn<InstructorCourseDetail, Number> col_count1;

    @FXML
    private TableColumn<InstructorCourseDetail, String> col_course1;

    @FXML
    private TableColumn<InstructorCourseDetail, String> col_course2;

    @FXML
    private TableColumn<InstructorCourseDetail, String> col_course3;

    @FXML
    private TableColumn<Instructor, String> col_dob;

    @FXML
    private TableColumn<Instructor, String> col_email;

    @FXML
    private TableColumn<InstructorCourseDetail, String> col_instName;

    @FXML
    private TableColumn<Instructor, String> col_name;

    @FXML
    private TableColumn<Instructor, String> col_phone;

    @FXML
    private BorderPane mainContent;

    @FXML
    private TableView<Instructor> tbl_instructor;

    @FXML
    private TableView<InstructorCourseDetail> tbl_AssignCourse;

    @FXML
    private TextField txt_address;

    @FXML
    private TextField txt_dob;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_phone;

    @FXML
    private TextField txt_search;

    @FXML
    private TextField txt_searchAssign;
    
    @FXML
    private TextField txt_nrc;

    @FXML
    private Button viewAllInstructors;

    @FXML
    private Button btn_logOut;

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
    void handleCreateAssign(ActionEvent event) {
    	int count = 0;
    	Instructor instructor = cbl_name.getSelectionModel().getSelectedItem();
    	Course c1 = cbl_courseA.getSelectionModel().getSelectedItem();
    	Course c2 = cbl_courseB.getSelectionModel().getSelectedItem();
    	Course c3 = cbl_courseC.getSelectionModel().getSelectedItem();
    	if(c1 == null && c2 == null & c3 == null) {
    		Alert alert = new Alert(AlertType.ERROR,"Please Selecte at least one course");alert.show();
    	}else {
    		if(c1.getId()==c2.getId() || c1.getId()==c3.getId() || c2.getId()==c3.getId()) {
        		Alert alert = new Alert(AlertType.ERROR,"Duplicate Course Selected!");alert.show();
        	}else if(checkDuplicate(instructor)) {
        		Alert alert = new Alert(AlertType.ERROR,"An Instructor with the same ID already exists!");alert.show();
        	}
    		else{
        		boolean status = InstructorCourseDAO.addInstructorCourse(new InstructorCourseDetail(instructor, c1, c2, c3));
        		if(status) {
        			Alert alert = new Alert(AlertType.INFORMATION,"Successfully Assigned");
        			alert.show();displayUpdateAssignCourse();
        		}else {
        			Alert alert = new Alert(AlertType.ERROR,"Failed to Assign");alert.show();
        		}
        	}
    	}
    	
    }    
    
    @FXML
    void handleUpdateAssign(ActionEvent event) {
    	Course c1 = cbl_courseA.getSelectionModel().getSelectedItem();
    	Course c2 = cbl_courseB.getSelectionModel().getSelectedItem();
    	Course c3 = cbl_courseC.getSelectionModel().getSelectedItem();
    	if(InstructorCourseDAO.updateInstructorCourse(inst, cbl_courseA.getSelectionModel().getSelectedItem(), cbl_courseB.getSelectionModel().getSelectedItem(), cbl_courseC.getSelectionModel().getSelectedItem())) {
    		Alert alert = new Alert(AlertType.INFORMATION,"Successfully Updated");
    		alert.show();
    		displayUpdateAssignCourse();
    	}
    	else {
    		Alert alert = new Alert(AlertType.ERROR,"Failed to update");alert.show();
    	}
    }

    @FXML
    void handleDeleteAssign(ActionEvent event) {

    }

    @FXML
    void handleCreate(ActionEvent event) {
    	Instructor inst = new Instructor(txt_name.getText(), txt_dob.getText(), txt_address.getText(), txt_phone.getText(), txt_email.getText(), txt_nrc.getText());
    	if(InstructorDAO.addInstructor(inst)) {
    		System.out.println("Inside Admin Instructor Create");
    		Alert alert = new Alert(AlertType.INFORMATION,"Successfully Added");
    		alert.show();
    		displayUpdate();
    	}else {
    		Alert alert = new Alert(AlertType.ERROR,"Failed to add");
    		alert.show();
    	}
    }

    @FXML
    void handleDelete(ActionEvent event) {
    	if(InstructorDAO.deleteInstructor(inst.getId())) {
    		Alert alert = new Alert(AlertType.INFORMATION,"Successfully Deleted");
    		alert.show();
    		displayUpdate();
    		
    	}
    	else {
    		Alert alert = new Alert(AlertType.ERROR,"Failed to Delete");
    		alert.show();
    	}
    	
    }

    @FXML
    void handleSearch(ActionEvent event) {
    	String keyword = txt_search.getText();
    	ArrayList<Instructor> sLst = InstructorDAO.searchInstructor(keyword);
    	System.out.println(sLst);
    	tbl_instructor.setItems(FXCollections.observableArrayList(sLst));
    }


    @FXML
    void handleUpdate(ActionEvent event) {
    	boolean status = InstructorDAO.updateInstructor(new Instructor(inst.getId(),txt_name.getText(), txt_dob.getText(), txt_address.getText(), txt_phone.getText(), txt_email.getText(),txt_nrc.getText()));
    	if(status) {
    		Alert alert = new Alert(AlertType.INFORMATION,"Successfully Updated");
    		alert.show();
    		displayUpdate();
    	}else {
    		Alert alert = new Alert(AlertType.ERROR,"Failed to Update");
    		alert.show();
    	}
    }


//    @FXML
//    void handleAssign(ActionEvent event) {
//    	anc_assign.toFront();
//    	setActiveStyle(assignCourse, viewAllInstructors);
//    }
//    @FXML
//    void handleViewAll(ActionEvent event) {
//        anc_viewAll.toFront();
//        viewAllInstructors.getStyleClass().setAll("tab-button-active");
//        assignCourse.getStyleClass().setAll("tab-button");
//    }
//
//    @FXML
//    void handleAssign(ActionEvent event) {
//        anc_assign.toFront();
//        assignCourse.getStyleClass().setAll("tab-button-active");
//        viewAllInstructors.getStyleClass().setAll("tab-button");
//    }
    
    @FXML 
    void handleViewAll(ActionEvent event) {
        anc_viewAll.toFront();
        
        // Set 'View All' as active
        updateButtonStyles(viewAllInstructors, assignCourse);
    }

    @FXML 
    void handleAssign(ActionEvent event) {
        anc_assign.toFront();
        
        // Set 'Assign' as active
        updateButtonStyles(assignCourse, viewAllInstructors);
    }

    private void updateButtonStyles(Button active, Button inactive) {
        // Reset both buttons to the base class
        active.getStyleClass().removeAll("tab-btn", "tab-btn-active");
        inactive.getStyleClass().removeAll("tab-btn", "tab-btn-active");
        
        // Apply appropriate classes
        active.getStyleClass().add("tab-btn-active");
        inactive.getStyleClass().add("tab-btn");
    }
    @FXML
    void showData(MouseEvent event) {
    	inst = tbl_instructor.getSelectionModel().getSelectedItem();
    	System.out.println("Selected Instructor: "+inst.getName());
    	txt_name.setText(inst.getName());
        txt_address.setText(inst.getAddress());
        txt_email.setText(inst.getEmail());
        txt_phone.setText(inst.getPh());
        txt_dob.setText(inst.getDob());
        txt_nrc.setText(inst.getNrc());
    }
    private InstructorCourseDetail instC = null;
    @FXML
    void showDataAssign(MouseEvent event) {
    	instC = tbl_AssignCourse.getSelectionModel().getSelectedItem();
    	System.out.println("Selected Instructor: "+instC.getInst());
    	inst=instC.getInst();
    	cbl_name.setValue(instC.getInst());
    	cbl_courseA.setValue(instC.getCourse_1());
    	cbl_courseB.setValue(instC.getCourse_2());
    	cbl_courseC.setValue(instC.getCourse_3());
    	
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

    private Instructor inst;
    private InstructorCourse instCourse;
    private InstructorCourseDetail instDetail;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
//		Instructor List
		inst = null;
		col_count.setCellFactory(col -> new TableCell<>() {
			 protected void updateItem(Number item, boolean empty) {
	                super.updateItem(item, empty);
	                setText(empty ? null : String.valueOf(getIndex() + 1));
	            }
		});
		col_name.setCellValueFactory(new PropertyValueFactory<Instructor, String>("name"));
		col_address.setCellValueFactory(new PropertyValueFactory<Instructor, String>("nrc"));
		col_email.setCellValueFactory(new PropertyValueFactory<Instructor, String>("email"));
		col_phone.setCellValueFactory(new PropertyValueFactory<Instructor, String>("ph"));
		col_dob.setCellValueFactory(new PropertyValueFactory<Instructor, String>("dob"));
		ArrayList<Instructor> iLst = InstructorDAO.getAllInstructors();
		tbl_instructor.setItems(FXCollections.observableArrayList(iLst));
		
		
//		Instructor Assign Course list
		instCourse = null;
		col_count1.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : String.valueOf(getIndex() + 1));
            }
        });
		col_instName.setCellValueFactory(new PropertyValueFactory<InstructorCourseDetail, String>("instName"));
		col_course1.setCellValueFactory(new PropertyValueFactory<InstructorCourseDetail, String>("c1Name"));
		col_course2.setCellValueFactory(new PropertyValueFactory<InstructorCourseDetail, String>("c2Name"));
		col_course3.setCellValueFactory(new PropertyValueFactory<InstructorCourseDetail, String>("c3Name"));
		ArrayList<InstructorCourseDetail> eLst = InstructorCourseDetail.splitCourse(InstructorCourseDAO.getInstructorCourseDetailsByInstructor());
		tbl_AssignCourse.setItems(FXCollections.observableArrayList(eLst));
		cbl_name.setItems(FXCollections.observableArrayList(InstructorDAO.getAllInstructors()));
		cbl_courseA.setItems(FXCollections.observableArrayList(CourseDAO.getAllCourses()));
		cbl_courseB.setItems(FXCollections.observableArrayList(CourseDAO.getAllCourses()));
		cbl_courseC.setItems(FXCollections.observableArrayList(CourseDAO.getAllCourses()));
		
	}
	void  displayUpdate() {
		ArrayList<Instructor> iLst= InstructorDAO.getAllInstructors();
		tbl_instructor.setItems(FXCollections.observableArrayList(iLst));
		txt_name.setText("");
		txt_address.setText("");
		txt_email.setText("");
		txt_phone.setText("");
		txt_dob.setText("");
		txt_nrc.setText("");
	}
	void displayUpdateAssignCourse() {
		ArrayList<InstructorCourseDetail> eLst = InstructorCourseDetail.splitCourse(InstructorCourseDAO.getInstructorCourseDetailsByInstructor());
		tbl_AssignCourse.setItems(FXCollections.observableArrayList(eLst));
		
	}
	boolean checkDuplicate(Instructor ins) {
		ArrayList<InstructorCourseDetail> tmpLst = InstructorCourseDetail.splitCourse(InstructorCourseDAO.getInstructorCourseDetailsByInstructor());
		System.out.println(tmpLst.get(0).getInst_id()+" Input ID: "+ins.getId());
		for(InstructorCourseDetail i: tmpLst) {
			if(i.getInst_id()==ins.getId())
				return false;
		}
		return true;
	}
}
