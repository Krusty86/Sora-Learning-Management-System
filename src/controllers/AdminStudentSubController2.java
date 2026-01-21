package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import models.Class;
import models.ClassDAO;
import models.Enrollment;
import models.EnrollmentDAO;
import models.Major;
import models.MajorDAO;
import models.Student;
import models.StudentDAO;
import models.Year;
import models.YearDAO;

public class AdminStudentSubController2 implements Initializable{
	private AdminStudentSubController subCon;
    @FXML
    private Button btn_create;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_search;

    @FXML
    private Button btn_update;

    @FXML
    private ComboBox<models.Class> cbl_class;

    @FXML
    private ComboBox<Major> cbl_major;

    @FXML
    private ComboBox<Year> cbl_year;

    @FXML
    private TableColumn<Enrollment, String> col_class;

    @FXML
    private TableColumn<?, ?> col_enrollActions;

    @FXML
    private TableColumn<Enrollment, Number> col_enrollCount;

    @FXML
    private TableColumn<Enrollment, String> col_major;

    @FXML
    private TableColumn<Enrollment, String> col_stuName;

    @FXML
    private TableColumn<Enrollment, String> col_year;

    @FXML
    private StackPane content;

    @FXML
    private TableView<Enrollment> tbl_enrollment;
    
    @FXML
    private TextField txt_stuId;

    @FXML
    private TextField txt_enrollID;

    @FXML
    private TextField txt_search;

    @FXML
    private TextField txt_stuName;
    @FXML
    private ComboBox<Student> cbl_stuName;

    @FXML
    void handleCreate(ActionEvent event) {
    	
    	Student stu = cbl_stuName.getSelectionModel().getSelectedItem();
    	Major mjr = cbl_major.getSelectionModel().getSelectedItem();
    	Year yrs = cbl_year.getSelectionModel().getSelectedItem();
    	Class cls = cbl_class.getSelectionModel().getSelectedItem();
    	int mjr_id = mjr.getId();
    	int yrs_id = yrs.getId();
    	int clc_id = cls.getId();
    	int st_id = stu.getId();
    
    	
    	Enrollment enroll = new Enrollment(st_id, yrs_id, mjr_id,clc_id);
    	boolean status = EnrollmentDAO.addEnrollment(enroll);
    	if(status) {
    		System.out.println("Inside Admin Enrollment Create Controller");
    		Alert alert = new Alert(AlertType.INFORMATION,"Successfully Added");
    		alert.show();
    		ArrayList<Enrollment> eLst = EnrollmentDAO.getAllEnrollments();
    		tbl_enrollment.setItems(FXCollections.observableArrayList(eLst));
    		cbl_class.setPromptText("Class");
    		cbl_major.setPromptText("Major");
    		cbl_stuName.setPromptText("Student");
    		cbl_year.setPromptText("Year");
    		
    	}else {
    		Alert alert = new Alert(AlertType.ERROR,"Failed to add");
    		alert.show();
    	}
    }

    @FXML
    void handleDelete(ActionEvent event) {
    	boolean status = EnrollmentDAO.deleteEnrollment(enroll.getId());
    	if(status) {
    		Alert alert = new Alert(AlertType.INFORMATION,"Successfully Deleted");
    		alert.show();
    		displayUpdate();
    	}
    }

    @FXML
    void handleSearch(ActionEvent event) {
    	String keyword = txt_search.getText();
    	ArrayList<Enrollment> elst = EnrollmentDAO.searchEnrollment(keyword);
    	System.out.println(elst.size());
    	tbl_enrollment.setItems(FXCollections.observableArrayList(elst));
    }

    @FXML
    void handleUpdate(ActionEvent event) {
    	Student stu = cbl_stuName.getSelectionModel().getSelectedItem();
    	Major mjr = cbl_major.getSelectionModel().getSelectedItem();
    	Year yrs = cbl_year.getSelectionModel().getSelectedItem();
    	Class cls = cbl_class.getSelectionModel().getSelectedItem();
    	int mjr_id = mjr.getId();
    	int yrs_id = yrs.getId();
    	int clc_id = cls.getId();
    	int st_id = stu.getId();
    	if(EnrollmentDAO.updateEnrollment(new Enrollment(enroll.getId(), st_id, yrs_id,mjr_id, clc_id))) {
    		Alert alert = new Alert(AlertType.INFORMATION,"Successfully Updated");
    		alert.show();
    		displayUpdate();
    	}else {
    		Alert alert = new Alert(AlertType.ERROR,"Failed to Update");
    		alert.show();
    	}
    }

	    private Enrollment enroll;
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
			enroll = null;
			col_enrollCount.setCellFactory(col -> new TableCell<>() {
	            @Override
	            protected void updateItem(Number item, boolean empty) {
	                super.updateItem(item, empty);
	                setText(empty ? null : String.valueOf(getIndex() + 1));
	            }
	        });
			col_stuName.setCellValueFactory(new PropertyValueFactory<Enrollment, String>("student"));
			col_major.setCellValueFactory(new PropertyValueFactory<Enrollment, String>("major"));
			col_year.setCellValueFactory(new PropertyValueFactory<Enrollment, String>("year"));
			col_class.setCellValueFactory(new PropertyValueFactory<Enrollment, String>("class_name"));
			ArrayList<Enrollment> eLst;
			try {
				eLst = EnrollmentDAO.getAllEnrollments();
				System.out.println(eLst.size());
				tbl_enrollment.setItems(FXCollections.observableArrayList(eLst));
				ArrayList<Student> stLst = StudentDAO.getAllStudents();
				ArrayList<Major> mLst = MajorDAO.getAllMajors();
		    	ArrayList<Class> cLst = ClassDAO.getAllClasses();
		    	ArrayList<Year> yLst =YearDAO.getAllYears();
		    	cbl_stuName.setItems(FXCollections.observableArrayList(stLst));
		    	cbl_major.setItems(FXCollections.observableArrayList(mLst));
		    	cbl_class.setItems(FXCollections.observableArrayList(cLst));
		    	cbl_year.setItems(FXCollections.observableArrayList(yLst));
		    	
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		
		}
		
		@FXML
	    void showData(MouseEvent event) {
		  
		  enroll = tbl_enrollment.getSelectionModel().getSelectedItem();
          System.out.println("selected enrollment: " + enroll.getId());
          cbl_stuName.setValue(StudentDAO.getStudentById(enroll.getStudent_id()).get(0));
          cbl_major.setValue(MajorDAO.getMajorById(enroll.getMajor_id()).get(0));
          cbl_year.setValue(YearDAO.getYearById(enroll.getYear_id()).get(0));
          cbl_class.setValue(ClassDAO.getClassById(enroll.getClass_id()).get(0));
          
	    }
		
		void displayUpdate() {
			ArrayList<Enrollment> eLst = EnrollmentDAO.getAllEnrollments();
    		tbl_enrollment.setItems(FXCollections.observableArrayList(eLst));
    		cbl_class.setPromptText("Class");
    		cbl_major.setPromptText("Major");
    		cbl_stuName.setPromptText("Student");
    		cbl_year.setPromptText("Year");
		}

	}

