package controllers;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import models.Student;
import models.StudentDAO;

public class AdminStudentSubController implements Initializable{

	    @FXML
	    private Button btn_create;

	    @FXML
	    private Button btn_delete;

	    @FXML
	    private Button btn_search;

	    @FXML
	    private Button btn_update;

	    @FXML
	    private TableColumn<Student, Void> col_actions;

	    @FXML
	    private TableColumn<Student, String> col_address;

	    @FXML
	    private TableColumn<Student, Number> col_count;

	    @FXML
	    private TableColumn<Student, String> col_dob;

	    @FXML
	    private TableColumn<Student, String> col_email;

	    @FXML
	    private TableColumn<Student, String> col_name;

	    @FXML
	    private TableColumn<Student, String> col_phone;

	    @FXML
	    private StackPane content;

	    @FXML
	    private TableView<Student> tbl_student;

	    @FXML
	    private TextField txt_address;

	    @FXML
	    private TextField txt_dob;

	    @FXML
	    private TextField txt_email;

	    @FXML
	    private TextField txt_phone;
	    
	    @FXML
	    private TextField txt_id;

	    @FXML
	    private TextField txt_name;

	    @FXML
	    private TextField txt_search;
	    
	    @FXML
	    private TextField txt_nrc;
	    @FXML
	    private Button btn_logOut;

	    @FXML
	    void handleLogOut(ActionEvent event) {

	    }
	    

	    @FXML
	    void handleCreate(ActionEvent event) {
	    	String name = txt_name.getText();
	    	String address = txt_address.getText();
	    	String email = txt_email.getText();
	    	String phone = txt_phone.getText();
	    	String dob = txt_dob.getText();
	    	String nrc = txt_nrc.getText();
	    	Student stu = new Student(name,dob, address, phone, email, nrc);
	    	stu.setAge(stu.calAge(dob));
	    	boolean status = StudentDAO.addStudent(stu);
	    	if(status) {
	    		System.out.println("Inside Admin Student Create Controller");
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
	    	boolean status = StudentDAO.deleteStudent(stu.getId());
	    	if(status) {
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
	    	ArrayList<Student> sLst = StudentDAO.searchStudent(keyword);
	    	System.out.println(sLst);
	    	tbl_student.setItems(FXCollections.observableArrayList(sLst));
	    	
	    }

	    @FXML
	    void handleUpdate(ActionEvent event) {
	    	String name = txt_name.getText();
	    	String address = txt_address.getText();
	    	String email = txt_email.getText();
	    	String phone = txt_phone.getText();
	    	String dob = txt_dob.getText();
	    	String nrc = txt_nrc.getText();
	    	boolean status = StudentDAO.updateStudent(new Student(stu.getId(),name, dob, address, phone, email, nrc));
	    	if(status) {
	    		Alert alert = new Alert(AlertType.INFORMATION,"Successfully Updated");
	    		alert.show();
	    		displayUpdate();
	    	}else {
	    		Alert alert = new Alert(AlertType.ERROR,"Failed to Update");
	    		alert.show();
	    	}
	    }
	    private Student stu;
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
			stu = null;
			col_count.setCellFactory(col -> new TableCell<>() {
	            @Override
	            protected void updateItem(Number item, boolean empty) {
	                super.updateItem(item, empty);
	                setText(empty ? null : String.valueOf(getIndex() + 1));
	            }
	        });
			col_name.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
			col_address.setCellValueFactory(new PropertyValueFactory<Student, String>("nrc"));
			col_email.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
			col_phone.setCellValueFactory(new PropertyValueFactory<Student, String>("ph"));
			col_dob.setCellValueFactory(new PropertyValueFactory<Student, String>("dob"));
//			addButtonToTable();
			ArrayList<Student> sLst;
			
			try {
				sLst = StudentDAO.getAllStudents();
				tbl_student.setItems(FXCollections.observableArrayList(sLst));
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
		
		}
		  @FXML
		    void showData(MouseEvent event) {
			  
			  stu = tbl_student.getSelectionModel().getSelectedItem();
              System.out.println("selected student: " + stu.getId());
              txt_name.setText(stu.getName());
              txt_address.setText(stu.getAddress());
              txt_email.setText(stu.getEmail());
              txt_phone.setText(stu.getPh());
              txt_dob.setText(stu.getDob());
              txt_nrc.setText(stu.getNrc());
		    }
		  void displayUpdate() {
			  ArrayList<Student> sLst = StudentDAO.getAllStudents();
	    		tbl_student.setItems(FXCollections.observableArrayList(sLst));
	    		txt_name.setText("");
	    		txt_address.setText("");
	    		txt_email.setText("");
	    		txt_phone.setText("");
	    		txt_dob.setText("");
	    		txt_nrc.setText("");
		  }
	}

