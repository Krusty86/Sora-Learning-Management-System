package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.util.Duration;

import application.DBConnection;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AdminDashboardController implements Initializable{

	@FXML
    private Button btn_account;

    @FXML
    private Button btn_course;

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
    private Label lbl_instructorCount;

    @FXML
    private Label lbl_stuCount;

    @FXML
    private BorderPane mainContent;
    @FXML
    private GridPane calendarGrid;
    @FXML
    private Label lbl_courseCount;
    @FXML private Label monthYearLabel;
    @FXML private VBox noticeListContainer;
    private LocalDate calendarDate;
    @FXML
    private ProgressBar progress_courses;

    @FXML
    private ProgressBar progress_instructors;

    @FXML
    private ProgressBar progress_students;

    private void drawCalendar() {
        // Clear previous days (but keep the headers if they are at row 0)
        calendarGrid.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);

        // Update Header (e.g., "JANUARY 2026")
        monthYearLabel.setText(calendarDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) 
                                + " " + calendarDate.getYear());

        YearMonth yearMonth = YearMonth.from(calendarDate);
        int daysInMonth = yearMonth.lengthOfMonth();
        
        // Find which day of the week the 1st starts on (1=Mon, 7=Sun)
        // Adjusting to 0=Sun for the grid
        int firstDayOfWeek = yearMonth.atDay(1).getDayOfWeek().getValue() % 7;

        int row = 1; // Start below the headers
        for (int day = 1; day <= daysInMonth; day++) {
            int column = (day + firstDayOfWeek - 1) % 7;
            
            Label dayLabel = new Label(String.valueOf(day));
            dayLabel.setPrefSize(30, 30);
            dayLabel.setAlignment(javafx.geometry.Pos.CENTER);
            dayLabel.getStyleClass().add("calendar-day");
            
            // Highlight today
            if (day == LocalDate.now().getDayOfMonth() && 
                calendarDate.getMonth() == LocalDate.now().getMonth() &&
                calendarDate.getYear() == LocalDate.now().getYear()) {
                dayLabel.setStyle("-fx-background-color: #4FBBE3; -fx-text-fill: white; -fx-background-radius: 5; -fx-font-weight: bold;");
            } else {
                dayLabel.setStyle("-fx-text-fill: #4B5563;");
            }

            calendarGrid.add(dayLabel, column, row);

            if (column == 6) {
                row++;
            }
        }
    }

    @FXML
    private void prevMonth() {
        calendarDate = calendarDate.minusMonths(1);
        drawCalendar();
    }

    @FXML
    private void nextMonth() {
        calendarDate = calendarDate.plusMonths(1);
        drawCalendar();
    }
    @FXML
    private void loadStudentNotices() {
        noticeListContainer.getChildren().clear();
        addNotice("Spring Semester Starts", "The spring semester begins on Feb 1st.", "Jan 25");
        addNotice("Course Registration", "Register for spring courses today.", "Jan 23");
    }

    private void addNotice(String title, String content, String date) {
        VBox notice = new VBox(5);
        notice.getStyleClass().add("notice-item");
        notice.setPadding(new javafx.geometry.Insets(10));
        
        Label titleLbl = new Label(title);
        titleLbl.setStyle("-fx-font-weight: bold;");
        
        Label contentLbl = new Label(content);
        contentLbl.getStyleClass().add("subtitle-text");
        
        notice.getChildren().addAll(titleLbl, contentLbl);
        noticeListContainer.getChildren().add(notice);
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
    	Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
        double wd = bounds.getMaxX();
        double hgh = bounds.getMaxY();
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/admin-courses.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
        Scene scene = new Scene(root, wd, hgh);
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
        Calendar cal = Calendar.getInstance();
        calendarDate = LocalDate.now();
        drawCalendar();
        String[] monthNames = {"January", "February", "March", "April", "May", "June", 
                               "July", "August", "September", "October", "November", "December"};
        monthYearLabel.setText(monthNames[cal.get(Calendar.MONTH)] + " " + cal.get(Calendar.YEAR));
        updateDashboardStats();
        loadStudentNotices();
	}
	
	private final double STUDENT_CAPACITY = 500.0;
	private final double INSTRUCTOR_GOAL = 50.0;
	private void updateDashboardStats() {
	    String query = "SELECT (SELECT COUNT(*) FROM enrollments), " +
	                  "(SELECT COUNT(*) FROM instructorcourses),"
	                  + "(SELECT COUNT(*) FROM courses)";
	    
	    try (Connection conn = DBConnection.connect(); 
	         PreparedStatement pstmt = conn.prepareStatement(query);
	         ResultSet rs = pstmt.executeQuery()) {
	        
	        if (rs.next()) {
	            int studentCount = rs.getInt(1);
	            int instructorCount = rs.getInt(2);
	            int courseCount = rs.getInt(3);
	            Timeline timeline = new Timeline();
	            
	            KeyValue kv = new KeyValue(progress_students.progressProperty(), studentCount / STUDENT_CAPACITY);

	            KeyValue kv1 = new KeyValue(progress_students.progressProperty(), instructorCount / INSTRUCTOR_GOAL);
	            KeyFrame kf = new KeyFrame(Duration.millis(1000), kv);
	            KeyFrame kf1 = new KeyFrame(Duration.millis(1000), kv1);
	            
	            timeline.getKeyFrames().add(kf);
	            timeline.getKeyFrames().add(kf1);
	            
	            timeline.play();
	            lbl_stuCount.setText(String.valueOf(studentCount));
	            lbl_instructorCount.setText(String.valueOf(instructorCount));
	            lbl_courseCount.setText(String.valueOf(courseCount));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
