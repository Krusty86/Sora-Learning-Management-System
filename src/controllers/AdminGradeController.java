package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.ClassDAO;
import models.Course;
import models.CourseDAO;
import models.Exam;
import models.ExamDAO;
import models.ExamType;
import models.ExamTypeDAO;
import models.Grade;
import models.GradeDAO;
import models.Major;
import models.Year;
import models.YearDAO;

public class AdminGradeController implements Initializable{

	@FXML
    private Button btnYear1;

    @FXML
    private Button btnYear2;

    @FXML
    private Button btn_Sem1;

    @FXML
    private Button btn_Sem2;

    @FXML
    private Button btn_account;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_course;

    @FXML
    private Button btn_grade;

    @FXML
    private Button btn_home;

    @FXML
    private Button btn_ITMajor;

    @FXML
    private Button btn_SEMajor;

    @FXML
    private Button btn_instructor;

    @FXML
    private Button btn_shedule;

    @FXML
    private Button btn_student;

    @FXML
    private Button btn_update;


    @FXML
    private ComboBox<Course> cbl_course;

    @FXML
    private ComboBox<ExamType> cbl_exam;

    @FXML
    private RadioButton toggle_chart;
    @FXML
    private ComboBox<models.Class> cbl_class;

    @FXML
    private TableColumn<Grade, String> col_course;

    @FXML
    private TableColumn<Grade, String> col_exam;

    @FXML
    private TableColumn<Grade, String> col_name;

    @FXML
    private TableColumn<Grade, String> col_status;

    @FXML
    private Label lbl_classAvg;

    @FXML
    private Label lbl_passRate;

    @FXML
    private BorderPane mainContent;

    @FXML
    private BarChart<String, Number> performanceChart;
    @FXML
    private TableView<Grade> tbl_grades;
    @FXML
    private Button btn_logOut;

    @FXML
    private Button btn_gradeDelete;

    @FXML
    private Button btn_gradeSubmit;

    @FXML
    private Button btn_gradeUpdate;
    @FXML
    private ComboBox<ExamType> cbl_examType;

    @FXML
    private ComboBox<Course> cbl_gradeCourse;

    @FXML
    private TableColumn<Exam, String> col_examType;

    @FXML
    private TableColumn<Exam, String> col_courseName;

    @FXML
    private TableColumn<Exam, String> col_examDate;

    @FXML
    private TableColumn<Exam, String> col_examName;

    @FXML
    private DatePicker examDate;

    @FXML
    private HBox hb_submitGrade;

    @FXML
    private TableView<Exam> tbl_Exams;

    @FXML
    private ComboBox<Year> cbl_semFilter;
    @FXML
    private TextField txt_examName;

    @FXML
    private Button btn_Exam;

    @FXML
    private Button btn_Grade;
    @FXML
    private AnchorPane anc_Grade;
    @FXML
    private AnchorPane anc_Exam;
    @FXML
    private VBox hb_performance;
    @FXML
    void handle_gradeCourseFilter(ActionEvent event) {
    	Course c = cbl_course.getSelectionModel().getSelectedItem();
    	tbl_grades.setItems(FXCollections.observableArrayList(GradeDAO.getGradesByCourse(c)));
    	updateChartData();
    }

    @FXML
    void handle_gradeExamFilter(ActionEvent event) {
    	System.out.println("Inside grade exam type filter");
    	ExamType et = cbl_exam.getSelectionModel().getSelectedItem();
    	System.out.println(et.getName());
    	tbl_grades.setItems(FXCollections.observableArrayList(GradeDAO.getGradesByExamType(et)));
    	updateChartData();
    }
    @FXML
    void togglePerformanceChart(ActionEvent event) {
        // Toggle the visibility: if it's true, make it false, and vice versa
        boolean isCurrentlyVisible = hb_performance.isVisible();
        
        hb_performance.setVisible(!isCurrentlyVisible);
        hb_performance.setManaged(!isCurrentlyVisible);
        
        // Optionally update the button text if the event source is a Button
        if (event.getSource() instanceof Button) {
            ((Button) event.getSource()).setText(!isCurrentlyVisible ? "Hide Chart" : "Show Chart");
        }
    }
    @FXML
    void handleExam(ActionEvent event) {
		anc_Exam.toFront();
    	setActiveStyle(btn_Exam, btn_Grade);
    }

    @FXML
    void handleGrade(ActionEvent event) {
    	anc_Grade.toFront();
    	setActiveStyle(btn_Grade, btn_Exam);

    }
    @FXML
    void handleSemesterFilter(ActionEvent event) {
    	updateGradeTable();
    }
    @FXML
    void handleGradeSubmit(ActionEvent event) {
    	String examName = txt_examName.getText();
    	int course_id = cbl_gradeCourse.getSelectionModel().getSelectedItem().getId();
    	String date = examDate.getValue().toString();
    	int examTypeId = cbl_examType.getSelectionModel().getSelectedItem().getId();
    	Exam ex = new Exam();
    	ex.setName(examName);
    	ex.setCourse_id(course_id);
    	ex.setDate(date);
    	ex.setExamType_id(examTypeId);
    	if(ExamDAO.createExamAndInitializeGrades(ex)) {
    		Alert alert = new Alert(AlertType.INFORMATION,"Successfully Added");
    		alert.show();
    		refreshExamTable();
		}else {
			Alert alert = new Alert(AlertType.ERROR,"Failed to add");
    		alert.show();
		}
    	}
    @FXML
    void handleGradeDelete(ActionEvent event) {
        Exam selectedEx = tbl_Exams.getSelectionModel().getSelectedItem();

        if (selectedEx == null) {
            new Alert(AlertType.WARNING, "Please select an exam to delete").show();
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + selectedEx.getName() + "?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (ExamDAO.deleteExam(selectedEx.getId())) {
                    new Alert(AlertType.INFORMATION, "Deleted Successfully").show();
                    refreshExamTable();
                    // Clear fields after deletion
                    txt_examName.clear();
                    examDate.setValue(null);
                }
            }
        });
    }

    @FXML
    void handleGradeUpdate(ActionEvent event) {
        Exam selectedEx = tbl_Exams.getSelectionModel().getSelectedItem();

        if (selectedEx == null) {
            new Alert(AlertType.WARNING, "Please select an exam from the table first").show();
            return;
        }

        // Update the object with new values from the UI
        selectedEx.setName(txt_examName.getText());
        selectedEx.setDate(examDate.getValue().toString());
        selectedEx.setCourse_id(cbl_gradeCourse.getSelectionModel().getSelectedItem().getId());
        selectedEx.setExamType_id(cbl_examType.getSelectionModel().getSelectedItem().getId());

        if (ExamDAO.updateExam(selectedEx)) {
            new Alert(AlertType.INFORMATION, "Exam Updated Successfully").show();
            refreshExamTable();
        } else {
            new Alert(AlertType.ERROR, "Update Failed").show();
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
    models.Class selectedClass;
    private String selectedMajor = "SE"; // Default
    private void updateClassComboBox() {
        ArrayList<models.Class> allClass = ClassDAO.getAllClasses();
        ArrayList<models.Class> filteredList = new ArrayList<>();

        // Define the valid class names for each major
        List<String> validNames;

        if ("SE".equalsIgnoreCase(selectedMajor)) {
            validNames = Arrays.asList("A", "B", "C", "D");
        } else if ("IT".equalsIgnoreCase(selectedMajor)) {
            validNames = Arrays.asList("E", "F", "G", "H");
        } else {
            validNames = new ArrayList<>(); // Empty if no major selected
        }

        // Filter the list from the database
        for (models.Class c : allClass) {
            // Check if the current class name is in our allowed list
            if (validNames.contains(c.getName().toUpperCase())) {
                filteredList.add(c);
            }
        }

        cbl_class.setItems(FXCollections.observableArrayList(filteredList));
        
        // Selection logic
        if (!filteredList.isEmpty()) {
            cbl_class.getSelectionModel().selectFirst();
            selectedClass = filteredList.get(0);
        } else {
            selectedClass = null;
        }
    }
    @FXML
    void showExamData(MouseEvent event) {
        // 1. Get the selected exam from the table
        Exam selectedEx = tbl_Exams.getSelectionModel().getSelectedItem();

        if (selectedEx != null) {
            // 2. Set the text field
            txt_examName.setText(selectedEx.getName());

            // 3. Set the DatePicker (assumes date string is in YYYY-MM-DD format)
            if (selectedEx.getDate() != null) {
                examDate.setValue(java.time.LocalDate.parse(selectedEx.getDate()));
            }

            // 4. Set the Course ComboBox
            for (Course c : cbl_gradeCourse.getItems()) {
                if (c.getId() == selectedEx.getCourse_id()) {
                    cbl_gradeCourse.getSelectionModel().select(c);
                    break;
                }
            }

            // 5. Set the ExamType ComboBox
            for (ExamType et : cbl_examType.getItems()) {
                if (et.getId() == selectedEx.getExamType_id()) {
                    cbl_examType.getSelectionModel().select(et);
                    break;
                }
            }
        }
    }
    @FXML
    void handle_Class(ActionEvent event) {
    	updateGradeTable();
    }


    // Add these for your Major buttons
    @FXML
    void handleSEMajor(ActionEvent event) {
        selectedMajor = "SE";
        // Update major button styles here
        setActiveStyle(btn_SEMajor, btn_ITMajor);
        updateClassComboBox();
        updateGradeTable();
    }

    @FXML
    void handleITMajor(ActionEvent event) {
        selectedMajor = "IT";
        // Update major button styles here
        setActiveStyle(btn_ITMajor, btn_SEMajor);
        updateClassComboBox();
        updateGradeTable();
        
    }

    @FXML
    void showPopUp(MouseEvent event) {

    }
    private void updateChartData() {
        // 1. Clear old data
        performanceChart.getData().clear();

        // 2. Create the Series
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Grade Distribution");

        // 3. Initialize counters
        int countA = 0, countB = 0, countC = 0, countD = 0, countF = 0;

        // 4. Categorize integer results into grade brackets
        for (Grade g : tbl_grades.getItems()) {
            int score = Integer.parseInt(g.getResult()); // Now treating it as an integer

            if (score >= 80) {
                countA++;
            } else if (score >= 70) {
                countB++;
            } else if (score >= 60) {
                countC++;
            } else if (score >= 50) {
                countD++;
            } else {
                countF++;
            }
        }

        // 5. Add data points to the series
        // We use Strings for the X-Axis categories
        series.getData().add(new XYChart.Data<>("80-100 (A)", countA));
        series.getData().add(new XYChart.Data<>("70-79 (B)", countB));
        series.getData().add(new XYChart.Data<>("60-69 (C)", countC));
        series.getData().add(new XYChart.Data<>("50-59 (D)", countD));
        series.getData().add(new XYChart.Data<>("0-49 (F)", countF));

        // 6. Add the series to the BarChart
        performanceChart.getData().add(series);
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
    
    private void updateGradeTable() {
        selectedClass = cbl_class.getValue();
        ArrayList<Grade> gradeList;
        gradeList = GradeDAO.getGradeByClass(selectedClass);
        tbl_grades.setItems(FXCollections.observableArrayList(gradeList));
        updateChartData();
        calculateStats();
    }

    private void calculateStats() {
        var items = tbl_grades.getItems();
        
        // 1. Handle empty state
        if (items == null || items.isEmpty()) {
            performanceChart.getData().clear();
            lbl_passRate.setText("0%");
            return;
        }

        int passCount = 0;
        int failCount = 0;
        for (Grade g : items) {
            if (Integer.parseInt(g.getResult()) >= 60) {
                passCount++;
            } else {
                failCount++;
            }
        }

        // 3. Update the UI Labels
        double passRate = ((double) passCount / items.size()) * 100;
        lbl_passRate.setText(String.format("%.1f%%", passRate));
    }
    private void setActiveStyle(Button active, Button inactive) {
        active.getStyleClass().removeAll("tab-button", "tab-button-active");
        inactive.getStyleClass().removeAll("tab-button", "tab-button-active");
        
        active.getStyleClass().add("tab-button-active");
        inactive.getStyleClass().add("tab-button");
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	    cbl_class.setItems(FXCollections.observableArrayList(ClassDAO.getAllClasses()));
	    col_name.setCellValueFactory(new PropertyValueFactory<>("stu_name"));
	    col_course.setCellValueFactory(new PropertyValueFactory<>("course_name"));
	    col_exam.setCellValueFactory(new PropertyValueFactory<>("ex_name"));
	    col_status.setCellValueFactory(new PropertyValueFactory<>("result"));
	    tbl_grades.setItems(FXCollections.observableArrayList(GradeDAO.getAllGrades()));
		cbl_class.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
		    if (newVal != null) {
		        updateGradeTable();
		    }
		});
		cbl_course.setItems(FXCollections.observableArrayList(CourseDAO.getCoursesByMajor(selectedMajor)));
		cbl_exam.setItems(FXCollections.observableArrayList(ExamTypeDAO.getAll()));
		cbl_gradeCourse.setItems(FXCollections.observableArrayList(CourseDAO.getCoursesByMajorId(1)));
		cbl_semFilter.setItems(FXCollections.observableArrayList(YearDAO.getAllYears()));
		cbl_examType.setItems(FXCollections.observableArrayList(ExamTypeDAO.getAll()));
	    col_examName.setCellValueFactory(new PropertyValueFactory<>("name"));
	    col_examDate.setCellValueFactory(new PropertyValueFactory<>("date"));
	    col_courseName.setCellValueFactory(new PropertyValueFactory<>("course_name"));
	    col_examType.setCellValueFactory(new PropertyValueFactory<>("examType_name"));
	    cbl_gradeCourse.setItems(FXCollections.observableArrayList(CourseDAO.getAllCourses()));
		refreshExamTable();
		// Add this inside your initialize() method
		tbl_Exams.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
		    if (newVal != null) {
		        txt_examName.setText(newVal.getName());
		        examDate.setValue(java.time.LocalDate.parse(newVal.getDate()));
		        
		        // Find and select the matching course in ComboBox
		        for (Course c : cbl_gradeCourse.getItems()) {
		            if (c.getId() == newVal.getCourse_id()) {
		                cbl_gradeCourse.getSelectionModel().select(c);
		                break;
		            }
		        }
		        
		        // Find and select the matching ExamType
		        for (ExamType et : cbl_examType.getItems()) {
		            if (et.getId() == newVal.getExamType_id()) {
		                cbl_examType.getSelectionModel().select(et);
		                break;
		            }
		        }
		    }
		});
	}
	void refreshExamTable() {
		tbl_Exams.setItems(FXCollections.observableArrayList(ExamDAO.getExamsWithCourseNames()));
	}

}
