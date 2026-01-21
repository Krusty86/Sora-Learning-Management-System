package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Rectangle2D;

import java.net.URL;
import java.time.LocalDate;

import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.Assignment;
import models.AssignmentDAO;
import javafx.event.ActionEvent;
import javafx.stage.Window;

public class StudentAssignmentController {
	
	@FXML
    private BorderPane mainContent;
    
	@FXML
    private TableView<Assignment>tableAssignments;
  
    @FXML
    private TableColumn<Assignment, Integer> col_course;

    @FXML
    private TableColumn<Assignment, String> col_assignment;

    @FXML
    private TableColumn<Assignment, String> col_duedate;

    @FXML
    private TableColumn<Assignment, String> col_status;
    
    @FXML
    private TableColumn<Assignment, String> col_grade;
    
    @FXML
    private TableColumn<Assignment, String> col_action;

    @FXML
    private Button btn_studentinfo;

    @FXML
    private Button btn_course;

    @FXML
    private Button btn_grade;

    @FXML
    private Button btn_home;
    Assignment selected;
    @FXML
    private Button btn_assignments;

    @FXML
    private Button btn_schedule;
    
    @FXML
    private Button btn_logout;
    
    @FXML
    private ComboBox<String> combo_subject; 

    @FXML
    private TextField txt_filepath; 

    @FXML
    private Button btn_browse; 

    @FXML
    private Button btn_submit; 
    
    private ObservableList<Assignment> assignmentList =FXCollections.observableArrayList();
    
   

    private java.io.File selectedFile;
            
    public void initialize() {
    	col_course.setCellValueFactory(new PropertyValueFactory<>("course_id"));
    	col_assignment.setCellValueFactory(new PropertyValueFactory<>("title"));
    	col_duedate.setCellValueFactory(new PropertyValueFactory<>("due_date"));
    	col_status.setCellValueFactory(new PropertyValueFactory<>("submit"));
    	col_grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
    	if(selected == null) {
    		btn_browse.setDisable(true);
    	}
    	loadAssignmentsFromDB();
    	
    	ObservableList<String> options = FXCollections.observableArrayList("All Assignments", "Submitted", "Not Yet");
        combo_subject.setItems(options);
        combo_subject.getSelectionModel().selectFirst(); 

        
        combo_subject.setOnAction(event -> {
            String selected = combo_subject.getSelectionModel().getSelectedItem();
            
            if (selected == null || selected.equals("All Assignments")) {
                tableAssignments.setItems(assignmentList); 
            } else {
                ObservableList<Assignment> filteredList = FXCollections.observableArrayList();
                for (Assignment a : assignmentList) {
                    if (a.getSubmit().equals(selected)) {
                        filteredList.add(a);
                    }
                }
                tableAssignments.setItems(filteredList); 
            }
        });
//    	ObservableList<String> titles = FXCollections.observableArrayList();
//        for (Assignment a : assignmentList) {
//            titles.add(a.getTitle());
//        }
//        combo_subject.setItems(titles);

        
//        tableAssignments.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
//            if (newVal != null) {
//                combo_subject.setValue(newVal.getTitle()); // getCourse_id() အစား getTitle() သုံးပါ
//            }
//        });
    }
   	
//    	ObservableList<String> subjects =FXCollections.observableArrayList("JAVA Programming" , "Database Systems","Web Development");
//    	combo_subject.setItems(subjects);
//    	
//    	tableAssignments.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
//            if (newVal != null) {
//                combo_subject.setValue("Course " + newVal.getCourse_id());
//            }
//        });
//    
//    	
//    	loadAssignmentsFromDB();
//    }
    
    private void loadAssignmentsFromDB() {
    	int currentStudentId = application.UserControl.getStu().getId();
    	ArrayList<Assignment> list = AssignmentDAO.getAssignmentsWithCourse(currentStudentId);
    	
    	if (list != null) {
    		assignmentList.clear();
    		
            for(Assignment a : list) {
                if(a.getStatus().equals("true")) {
                    a.setSubmit("Submitted");
                } else {
                    a.setSubmit("Not Yet");
                }
            }
            assignmentList.setAll(list);
            tableAssignments.setItems(assignmentList);
        }
    }

    	
		
	

	

    @FXML
    void handleAssignments(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/assignments.fxml"));
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
    void handleCourse(ActionEvent event)  throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/courses.fxml"));
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
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/grade.fxml"));
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
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/student-dashboard.fxml"));
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
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/schedule.fxml"));
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
    void handleStudentInfo(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/student-info.fxml"));
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
    void handleLogout(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
        URL url = getClass().getResource("/fxml/Login-form.fxml");
        loader.setLocation(url);
        Parent root = loader.load();
		Scene scene = new Scene(root,1121,570);
		scene.getStylesheets().add(getClass().getResource("/application/login-form.css").toExternalForm());
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new Scene
        stage.setScene(scene);

        // Center on screen and show
        stage.centerOnScreen();
        stage.show();

    }
    
    @FXML
    void handleBrowse(ActionEvent event) {
    	    try {
                System.out.println("Browse button clicked!");
                
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select Assignment File");
                
                // Use the Window from the button that was clicked
                Window stage = ((Node) event.getSource()).getScene().getWindow();
                
                selectedFile = fileChooser.showOpenDialog(stage);
               
                if (selectedFile != null) {
                    txt_filepath.setText(selectedFile.getName());
                    System.out.println("File selected: " + selectedFile.getAbsolutePath());
                } else {
                    System.out.println("No file selected.");
                }
            } catch (Exception e) {
                System.out.println("Error opening FileChooser: " + e.getMessage());
                e.printStackTrace();
            }
    }
    @FXML
    void handleSubmit(ActionEvent event) {
        Assignment selected = tableAssignments.getSelectionModel().getSelectedItem();
        if (selected != null && selectedFile != null) {
            selected.setStatus("Submitted");
            selected.setFile_path(selectedFile.getAbsolutePath());
            
            selected.setSubmit_date(java.time.LocalDate.now().toString());

            if (AssignmentDAO.submitAssignment(selected, selected.getFile_path(),selected.getSubmit_date())) {
                loadAssignmentsFromDB();
//            	tableAssignments.refresh(); 
                showAlert("Success", "Assignment Submitted!");
                btn_browse.setDisable(true);
                btn_submit.setDisable(true);
                txt_filepath.clear();
                selectedFile = null;
            }
        }
    }
    @FXML
    void showData(MouseEvent event) {
        selected = tableAssignments.getSelectionModel().getSelectedItem();
        
        if (selected != null) {
        	//combo_subject.setValue(selected.getTitle());
            //combo_subject.setValue("Course " + selected.getCourse_id());
            System.out.println("Row Clicked: " + selected.getTitle());
        }
        LocalDate dueDate = LocalDate.parse(selected.getDue_date());
        LocalDate currentDate = LocalDate.now();
        if(dueDate.isBefore(currentDate)) {
        	btn_submit.setDisable(true);
        	btn_browse.setDisable(true);
        }else {
        	btn_submit.setDisable(false);
        	btn_browse.setDisable(false);
        }
    }
    
   

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

