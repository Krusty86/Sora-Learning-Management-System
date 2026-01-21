package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.*;

public class AssignmentController implements Initializable{
	
	 private static final String ArrayLis = null;

	  @FXML
	    private Button btn_submit;



	 @FXML
	 private BorderPane mainContent;
	 
	 @FXML
     void showData(MouseEvent event) {       
	       assign = tbl_AssignmentList.getSelectionModel().getSelectedItem();
	       if(assign==null){
	    	   return;
	       }
	       System.out.println("selected assignment: " + assign.getId());
	       cb_courses.setValue(CourseDAO.getCourseById(assign.getCourse_id()).get(0));
	       txt_title.setText(assign.getTitle());
	       txt_des.setText(assign.getDescription());
	       
	       txt_dd.setText(assign.getDue_date());
	       
     }
	 @FXML
     void showDataSubmitted(MouseEvent event) {       
		 Assignment selectedAssignment = tbl_AssignmentList1.getSelectionModel().getSelectedItem();
		 txt_grade.setText(selectedAssignment.getGrade());
		 LocalDate dueDate = LocalDate.parse(selectedAssignment.getDue_date());
	        LocalDate currentDate = LocalDate.now();
	        System.out.println("Assignment Status "+selectedAssignment.getStatus());
	        System.out.println("Due data: "+dueDate);
	        System.out.println("Current Date: "+ currentDate);
	       if(dueDate.isBefore(currentDate) || selectedAssignment.getStatus().equalsIgnoreCase("true")) {
	    	   btn_submit.setDisable(true);
	    	   txt_grade.setDisable(true);
	       }else {
	    	   btn_submit.setDisable(false);
	    	   txt_grade.setDisable(false);
	       }
	 }
	 @FXML
	    private TextField txt_search;
	 
	 @FXML
	    private TextField txt_search1;

	 
	 
	 @FXML
	    private ComboBox<Course> cb_courses;

	 @FXML
	    private ComboBox<Student> cb_student;
	 
	 @FXML
	    private TableView<Assignment> tbl_AssignmentList;

	 @FXML
	    private ComboBox<Course> cb_course;

	 @FXML
	    private ComboBox<Assignment> cb_assignmentTitle;
	    @FXML
	    private ComboBox<Course> cb_courses1;
	    @FXML
	    private TableColumn<Assignment, String> col_dueDate;

	    @FXML
	    private TableColumn<Assignment, String> col_status;

	    @FXML
	    private TableColumn<Assignment, String> col_stuName;

	    @FXML
	    private TableView<Assignment> tbl_AssignmentList1;
	    
	    @FXML
	    private TableColumn<Assignment, String> col_submitDate;

	    @FXML
	    private AnchorPane anc_assign;

	    @FXML
	    private AnchorPane anc_submitted;
	    
	    
	    @FXML
	    private Button btn_search1;
	    
	    @FXML
	    private Button btn_logout;

	    @FXML
	    private Button btn_selectEnter;
	    
	    @FXML
	    private Button btn_confirm;

	    @FXML
	    private Button btn_Grade;

	    @FXML
	    private Button btn_assign;

	    @FXML
	    private Button btn_assignment;

	    @FXML
	    private Button btn_course;

	    @FXML
	    private Button btn_create;

	    @FXML
	    private Button btn_delete;

	    @FXML
	    private Button btn_home;

	    @FXML
	    private Button btn_schedule;

	    @FXML
	    private Button btn_instructorInfo;


	    @FXML
	    private Button btn_update;
	    
	    @FXML
	    private Button btn_search;

    @FXML
    private TableColumn<Assignment, String> col_action;

    @FXML
    private TableColumn<Assignment, Date> col_date;

    @FXML
    private TableColumn<Assignment, String> col_des;

    @FXML
    private TableColumn<Assignment, Integer> col_sub;

    @FXML
    private TableColumn<Assignment, String> col_title;
    
    @FXML
    private TableColumn<Assignment, String> col_course;
    
    @FXML
    private TableColumn<Assignment, String> col_student;

    @FXML
    private TextField txt_dd;

    @FXML
    private TextField txt_des;

    @FXML
    private TextField txt_grade;

    @FXML
    private TextField txt_title;
    
    private Assignment assignment;
    
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
    void handleGradeSubmit(ActionEvent event) {
        // 1. Get the selected assignment from the TableView (Pane 2/anc_submitted)
        Assignment selectedAssignment = tbl_AssignmentList1.getSelectionModel().getSelectedItem();
        if (selectedAssignment == null) {
            Alert alert = new Alert(AlertType.WARNING, "Please select an assignment from the table first.");
            alert.show();
            return;
        }
        
        // 2. Get the grade from the text field (Assuming txt_grade or txt_des is used for grading)
        // Note: You should ensure you have a dedicated txt_grade field in your FXML
        String newGrade = txt_grade.getText(); 

        if (newGrade == null || newGrade.trim().isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING, "Please enter a grade.");
            alert.show();
            return;
        }

        // 3. Call the DAO method to update the grade in the database
        boolean isUpdated = AssignmentDAO.updateGrade(selectedAssignment.getId(), newGrade);

        if (isUpdated) {
            Alert alert = new Alert(AlertType.INFORMATION, "Grade submitted successfully!");
            alert.show();
            
            // 4. Refresh the TableView to show the new grade
            refreshSubmittedTable();
            
            // 5. Clear the input field
            txt_grade.clear();
        } else {
            Alert alert = new Alert(AlertType.ERROR, "Failed to update grade. Please try again.");
            alert.show();
        }
    }

    // Helper method to keep the table in sync
    private void refreshSubmittedTable() {
        ArrayList<Assignment> updatedList = AssignmentDAO.getAssignmentsByInstructor(UserControl.inst.getId());
        tbl_AssignmentList1.setItems(FXCollections.observableArrayList(updatedList));
    }
    @FXML
    void handleSearch(ActionEvent event) {
    	String keyword = txt_search.getText();
    	System.out.println(keyword);
    	ArrayList<Assignment> aLst = AssignmentDAO.searchAssignment(keyword);
    	System.out.println(aLst);
    	tbl_AssignmentList.setItems(FXCollections.observableArrayList(aLst));

    }
    @FXML
    void handleCourseFilterSubmit(ActionEvent event) {
    	System.out.println("Inside Course Filter in Assignment List");
    	tbl_AssignmentList.setItems(FXCollections.observableArrayList(AssignmentDAO.getAssignmentsByInstructorAndCourse(UserControl.inst.getId(), cb_course.getSelectionModel().getSelectedItem().getId())));
    }
    @FXML
    void handeAssignmentFilter(ActionEvent event) {
    	tbl_AssignmentList1.setItems(FXCollections.observableArrayList(AssignmentDAO.getAssignmentsByInstructorAndTitle(UserControl.inst.getId(), cb_assignmentTitle.getSelectionModel().getSelectedItem().getTitle())));
    }
    @FXML
    void handleCourseFilter(ActionEvent event) {
    	tbl_AssignmentList1.setItems(FXCollections.observableArrayList(AssignmentDAO.getAssignmentsByInstructorAndCourse(UserControl.inst.getId(), cb_courses1.getSelectionModel().getSelectedItem().getId())));
    }
    
    
    @FXML
    void handleAssignList(ActionEvent event) {
    	anc_assign.toFront();
    }

    @FXML
    void handleSubmittedStu(ActionEvent event) {
    	anc_submitted.toFront();
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
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/instructorAssignment.fxml"));
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
    void handleCreate(ActionEvent event) {
    	String title = txt_title.getText();
    	String description = txt_des.getText();
    	String dd = txt_dd.getText();
    	String sub = "false";
    	
    	Course cname=cb_courses.getSelectionModel().getSelectedItem();
    	
    	int cid=cname.getId();
    	
    	    	
    	Assignment assignment = new Assignment();
    	assignment.setTitle(title);
    	assignment.setDescription(description);
    	assignment.setDue_date(dd);
    	assignment.setStatus(sub);
    	assignment.setCourse_id(cid);
    	
        boolean status = AssignmentDAO.addAssignmentToAllEnrolled(assignment);
        if(status) {
            System.out.println("Inside Admin Assignment Create Controller");
            Alert alert = new Alert(AlertType.INFORMATION,"Successfully Added");
            alert.show();
            ArrayList<Assignment> AssignmentLst = AssignmentDAO.getAssignmentsByInstructor(UserControl.inst.getId());            
            tbl_AssignmentList.setItems(FXCollections.observableArrayList(AssignmentLst));
            
        }else {
            Alert alert = new Alert(AlertType.ERROR,"Failed to add");
            alert.show();
        }
        
    
    }
   

    @FXML
    void handleDelete(ActionEvent event) {
    	String title = txt_title.getText();
    	String des = txt_des.getText();
    	String dueDate = txt_dd.getText();
    	
    	boolean status = AssignmentDAO.deleteAssignment(assign.getId());
    	Alert alert = new Alert(AlertType.INFORMATION,"Successfully Deleted");
		alert.show();
        ArrayList<Assignment> AssignmentLst = AssignmentDAO.getAssignmentsByInstructor(UserControl.inst.getId());
		tbl_AssignmentList.setItems(FXCollections.observableArrayList(AssignmentLst));
		txt_title.setText("");
		txt_des.setText("");
		txt_dd.setText("");
    }
    

    @FXML
    void handleUpdate(ActionEvent event) {
    	String title = txt_title.getText();
    	String des = txt_des.getText();
    	String dueDate = txt_dd.getText();
    	String sub="false";
    	Course cname=cb_courses.getSelectionModel().getSelectedItem();
    	int cid=cname.getId();
    	boolean status = AssignmentDAO.updateAssignment(new Assignment(assign.getId(),title,des, dueDate, sub, cid));
    	Alert alert = new Alert(AlertType.INFORMATION,"Successfully Added");
		alert.show();
        ArrayList<Assignment> AssignmentLst = AssignmentDAO.getAssignmentsByInstructor(UserControl.inst.getId());
		tbl_AssignmentList.setItems(FXCollections.observableArrayList(AssignmentLst));
		txt_des.setText("");
		txt_dd.setText("");
		

    }
    
    @FXML
    void handle_CourseConfirm(ActionEvent event) {
    	Course selectedCourse = cb_course.getSelectionModel().getSelectedItem();
        if (selectedCourse == null) {
            return;
        }

        int courseId = selectedCourse.getId();

        ArrayList<Assignment> confirmLst = AssignmentDAO.getUniqueAssignmentsByCourse(courseId);
        tbl_AssignmentList.setItems(
            FXCollections.observableArrayList(confirmLst)
        );
    }
    
    @FXML
    void handleSearch1(ActionEvent event) {
    	String keyword1=txt_search1.getText();
    	System.out.println(keyword1);
    	ArrayList<Assignment> alst1=AssignmentDAO.searchAssignment(keyword1);
    	tbl_AssignmentList1.setItems(FXCollections.observableArrayList(alst1));

    }

    @FXML
    void handleSelectEnter(ActionEvent event) {
    	System.out.println("---"+cb_courses1.getSelectionModel().getSelectedItem()+"-----"+cb_assignmentTitle.getSelectionModel().getSelectedItem()+"---");
    	
    	Course c_select=cb_courses1.getSelectionModel().getSelectedItem();
    	Assignment a_select=cb_assignmentTitle.getSelectionModel().getSelectedItem();
    	if(c_select==null || a_select==null)
    	{
    		return;
    	}
    	
    	int courseId=c_select.getId();
    	int assignmentId=a_select.getId();

        ArrayList<Assignment> AssignmentLst = AssignmentDAO.getAssignmentsByInstructor(UserControl.inst.getId());
    	ArrayList<Assignment> resultAssignment=new ArrayList<>();
    	
    	for(Assignment a:AssignmentLst)
    		if(a.getCourse_id()==courseId && a.getId()==assignmentId)
    		{
    			resultAssignment.add(a);
    		}
    	tbl_AssignmentList1.setItems(FXCollections.observableArrayList(resultAssignment));
    	
    }
    
    private Assignment assign;
    private Assignment submittedStu;
    private Assignment updateassign;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
    	
    	//pane 1
        assign = null;
       cb_assignmentTitle.setItems(FXCollections.observableArrayList(AssignmentDAO.getAssignmentsByInstructor(UserControl.inst.getId())));
//       	col_stuName.setCellValueFactory(new PropertyValueFactory<>("student"));
        col_title.setCellValueFactory(new PropertyValueFactory<Assignment, String>("title"));
        col_des.setCellValueFactory(new PropertyValueFactory<Assignment, String>("description"));
        col_date.setCellValueFactory(new PropertyValueFactory<Assignment, Date>("due_date"));
        
//        col_sub.setCellValueFactory(new PropertyValueFactory<Assignment, Integer>("count"));
//        col_course.setCellValueFactory(new PropertyValueFactory<Assignment, String>("course"));
//        col_student.setCellValueFactory(new PropertyValueFactory<Assignment, String>("student"));
        ArrayList<Assignment> assignLst;
        ArrayList<Course> cLst = CourseDAO.getCourseByInstructor(UserControl.inst.getId());
        for(Course c: cLst) {
        	System.out.println(c.getName());
        }
        System.out.println(cLst.size());
        try {

            assignLst = AssignmentDAO.getAssignmentsByInstructor(UserControl.inst.getId());
            //System.out.println(assignLst.size());
            //System.out.println(assignLst);
            
            tbl_AssignmentList.setItems(FXCollections.observableArrayList(assignLst));
           
            //System.out.println(cb_course.getSelectionModel().getSelectedItem());
            
            cb_courses.setItems(FXCollections.observableArrayList(cLst));
            cb_course.setItems(FXCollections.observableArrayList(cLst));
            //System.out.println(cb_course.getValue());
            
            
        }catch(Exception e) {
            e.printStackTrace();
        }
        // Submitted Assignment List
        submittedStu = null;
        
        col_stuName.setCellValueFactory(new PropertyValueFactory<Assignment, String>("student"));
        col_status.setCellValueFactory(new PropertyValueFactory<Assignment, String>("grade"));
        col_dueDate.setCellValueFactory(new PropertyValueFactory<Assignment, String>("due_date"));
        col_submitDate.setCellValueFactory(new PropertyValueFactory<Assignment, String>("submit_date"));
//      
        ArrayList<Assignment> submittedLst;
        try {
            submittedLst = AssignmentDAO.getAssignmentsByInstructor(UserControl.inst.getId());
            System.out.println(submittedLst.get(0).getSubmit());
            tbl_AssignmentList1.setItems(FXCollections.observableArrayList(submittedLst));
            
            cb_courses1.setItems(FXCollections.observableArrayList(cLst));
            if(cb_courses1.getSelectionModel().getSelectedItem()==null)
            {
            	
            }
            else {
                cb_assignmentTitle.setItems(FXCollections.observableArrayList(AssignmentDAO.getUniqueAssignmentsByCourse(cb_courses1.getSelectionModel().getSelectedItem().getId())));

            }
            
            
            
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        
        
   
    

    }
}
