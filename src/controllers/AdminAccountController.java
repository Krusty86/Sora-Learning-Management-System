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
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.Admin;
import models.AdminDAO;
import models.EnrollmentDAO;
import models.Instructor;
import models.InstructorDAO;
import models.Student;
import models.StudentDAO;
import models.UserInstructorDAO;
import models.UserInstructors;
import models.UserStudentDAO;
import models.UserStudents;

public class AdminAccountController implements Initializable{

	  @FXML
	    private AnchorPane anc_stu;

	    @FXML
	    private AnchorPane anc_tch;
	  @FXML
	    private Button btn_account;

	    @FXML
	    private Button btn_course;

	    @FXML
	    private Button btn_create;

	    @FXML
	    private Button btn_delete;

	    @FXML
	    private Button btn_grade;

	    @FXML
	    private Button btn_home;

	    @FXML
	    private Button btn_instructor;

	    @FXML
	    private Button btn_shedule;

	    @FXML
	    private Button btn_stuCreate;

	    @FXML
	    private Button btn_stuDelete;

	    @FXML
	    private Button btn_stuUpdate;

	    @FXML
	    private Button btn_student;

	    @FXML
	    private Button btn_update;


	    @FXML
	    private ComboBox<Student> cbl_student;

	    @FXML
	    private ComboBox<Instructor> cbl_teacher;

	    @FXML
	    private TableColumn<UserInstructors, String> col_TName;

	    @FXML
	    private TableColumn<UserInstructors, String> col_TPwd;

	    @FXML
	    private TableColumn<UserInstructors, String> col_TRole;

	    @FXML
	    private TableColumn<UserInstructors, String> col_TUsername;
	    @FXML
	    private TableColumn<UserInstructors, String> col_Temail;

	    @FXML
	    private TableColumn<UserStudents, Number> col_stuCount;

	    @FXML
	    private TableColumn<UserStudents, String> col_stuName;

	    @FXML
	    private TableColumn<UserStudents, String> col_stuPwd;
	    @FXML
	    private TableColumn<UserStudents, String> col_stuEmail;

	    @FXML
	    private TableColumn<UserStudents, String> col_stuRole;

	    @FXML
	    private TableColumn<UserStudents, String> col_stuUsername;

	    @FXML
	    private TableColumn<UserStudents, Number> col_tCount;

	    @FXML
	    private BorderPane mainContent;

	    @FXML
	    private TableView<UserStudents> tbl_stuAccount;

	    @FXML
	    private TableView<UserInstructors> tbl_teacherAccount;

	    @FXML
	    private TextField txt_pwd;

	    @FXML
	    private TextField txt_stuID;

	    @FXML
	    private TextField txt_stuPwd;

	    @FXML
	    private TextField txt_stuUsername;

	    @FXML
	    private TextField txt_username;
	    @FXML
	    private Label txt_stuAcc;
	    @FXML
	    private Label txt_TchAcc;
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
	    void stu_btn(MouseEvent event) {
	    	anc_stu.toFront();
	    	setActiveStyle(txt_stuAcc, txt_TchAcc);
	    }

	    @FXML
	    void tch_btn(MouseEvent event) {
	    	anc_tch.toFront();
	    	setActiveStyle(txt_TchAcc ,txt_stuAcc);
	    }
	    private void setActiveStyle(Label active, Label inactive) {
		    // Apply Active Style
		    active.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
		    
		    // Apply Inactive Style
		    inactive.setStyle("-fx-background-color: transparent; -fx-text-fill:  blue;");
		}
	    

//    Teacher Account
    @FXML
    void handleCreate(ActionEvent event) {
    	int count = 0;
    	String username = txt_username.getText();
    	String pwd = txt_pwd.getText();
    	if(cbl_teacher.getSelectionModel().getSelectedItem()==null) {
    		Alert alert = new Alert(AlertType.ERROR,"Account Already Exists!!");
    		alert.show();
    	}else {    	
    		UserInstructors ustu = new UserInstructors(username, pwd);
    		ustu.setInst_id(cbl_teacher.getSelectionModel().getSelectedItem().getId());
    		if(UserInstructorDAO.addUserInstructors(ustu)) {
    			Alert alert = new Alert(AlertType.INFORMATION,"Successfully Added");
	    		alert.show();
	    		displayUpdateInst();
    		}else {
    			Alert alert = new Alert(AlertType.ERROR,"Failed to add");
	    		alert.show();
	}
    }	
	}

    @FXML
    void handleDelete(ActionEvent event) {
    	boolean status = UserInstructorDAO.deleteUserInstructors(userInst.getId());
    	if(status) {
    		Alert alert = new Alert(AlertType.INFORMATION,"Successfully Deleted");
    		alert.show();
    		displayUpdateInst();
    	}else {
    		Alert alert = new Alert(AlertType.ERROR,"Failed to Delete");
    		alert.show();
    	}
    }
    
    @FXML
    void handleUpdate(ActionEvent event) {
    	boolean status = UserInstructorDAO.updateUserInstructors(new UserInstructors(userInst.getId(),txt_username.getText(),txt_pwd.getText()));
    	if(status) {
    		Alert alert = new Alert(AlertType.INFORMATION,"Successfully Updated");
    		alert.show();
    		displayUpdateInst();
    	}else {
    		Alert alert = new Alert(AlertType.ERROR,"Failed to Update");
    		alert.show();
    	}
    }
    
//    Student Accounts
    @FXML
    void handleStuCreate(ActionEvent event) {
    	int count = 0;
    	String username = txt_stuUsername.getText();
    	String pwd = txt_stuPwd.getText();
    	if(cbl_student.getSelectionModel().getSelectedItem()==null) {
    		Alert alert = new Alert(AlertType.ERROR,"Please select a student");
    		alert.show();
    	}else {
    		UserStudents ustu = new UserStudents(username, pwd);
    		ustu.setStu_id(cbl_student.getSelectionModel().getSelectedItem().getId());
    		if(UserStudentDAO.addUserStudents(ustu)) {
    			Alert alert = new Alert(AlertType.INFORMATION,"Successfully Added");
	    		alert.show();
	    		displayUpdateStu();
    		}else {
    			Alert alert = new Alert(AlertType.ERROR,"Failed to add");
	    		alert.show();
    		}
	
    	}    	
    }

    @FXML
    void handleStuDelete(ActionEvent event) {
    	boolean status = UserStudentDAO.deleteUserStudents(userStu.getId());
    	if(status) {
    		Alert alert = new Alert(AlertType.INFORMATION,"Successfully Deleted");
    		alert.show();
    		displayUpdateStu();
    	}else {
    		Alert alert = new Alert(AlertType.ERROR,"Failed to Delete");
    		alert.show();
    	}
    }

    @FXML
    void handleStuUpdate(ActionEvent event) {
    	boolean status = UserStudentDAO.updateUserStudents(new UserStudents(userStu.getId(),txt_stuUsername.getText(),txt_stuPwd.getText()));
    	if(status) {
    		Alert alert = new Alert(AlertType.INFORMATION,"Successfully Updated");
    		alert.show();
    		displayUpdateStu();
    	}else {
    		Alert alert = new Alert(AlertType.ERROR,"Failed to Update");
    		alert.show();
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
    
    private UserInstructors userInst=null;
    private UserStudents userStu=null;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
//    	Teacher Table
		col_tCount.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : String.valueOf(getIndex() + 1));
            }
        });
		col_TName.setCellValueFactory(new PropertyValueFactory<UserInstructors, String>("name"));
		col_TUsername.setCellValueFactory(new PropertyValueFactory<UserInstructors, String>("username"));
		cbl_teacher.setItems(FXCollections.observableArrayList(InstructorDAO.getAllInstructors()));
		col_Temail.setCellValueFactory(new PropertyValueFactory<UserInstructors, String>("email"));
		ArrayList<UserInstructors> uiLst;
		
		try {
			uiLst = UserInstructorDAO.getAllUserInstructors();
			tbl_teacherAccount.setItems(FXCollections.observableArrayList(uiLst));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
//		Student table
		col_stuCount.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : String.valueOf(getIndex() + 1));
            }
        });
		col_stuName.setCellValueFactory(new PropertyValueFactory<UserStudents, String>("name"));
		col_stuUsername.setCellValueFactory(new PropertyValueFactory<UserStudents, String>("username"));
		cbl_student.setItems(FXCollections.observableArrayList(StudentDAO.getAllStudents()));
		col_stuEmail.setCellValueFactory(new PropertyValueFactory<UserStudents, String>("email"));
		ArrayList<UserStudents> usLst;
		
		try {
			usLst = UserStudentDAO.getAllUserStudents();
			tbl_stuAccount.setItems(FXCollections.observableArrayList(usLst));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	
	}
    @FXML
    void showDataTeacher(MouseEvent event) {  
	  userInst = tbl_teacherAccount.getSelectionModel().getSelectedItem();
      System.out.println("selected teacher: " + userInst.getId());
      txt_username.setText(userInst.getUsername());
      txt_pwd.setText(userInst.getPwd());
      cbl_teacher.setValue(InstructorDAO.getInstructorById(userInst.getInst_id()).get(0));
      
    }
    @FXML
    void showDataStudent(MouseEvent event) {
  	  userStu = tbl_stuAccount.getSelectionModel().getSelectedItem();
      System.out.println("selected student: " + userStu.getId());
      txt_stuUsername.setText(userStu.getUsername());
      txt_stuPwd.setText(userStu.getPwd());
      cbl_student.setValue(StudentDAO.getStudentById(userStu.getStu_id()).get(0));
    }
	  void displayUpdateStu() {
		  ArrayList<UserStudents> sLst = UserStudentDAO.getAllUserStudents();
			tbl_stuAccount.setItems(FXCollections.observableArrayList(sLst));
			txt_stuUsername.setText("");
		    txt_stuPwd.setText("");
		    cbl_student.setPromptText("Student");
	  }
	  void displayUpdateInst() {
		  ArrayList<UserInstructors> sLst = UserInstructorDAO.getAllUserInstructors();
			tbl_teacherAccount.setItems(FXCollections.observableArrayList(sLst));
			txt_username.setText("");
		    txt_pwd.setText("");
		    cbl_teacher.setPromptText("Instructor");
	  }
	  
	  
}
