package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Course;
import models.CourseDAO;
import models.CourseDetails;
import models.Major;
import models.MajorDAO;

public class AdminCourseFormController implements Initializable{

    @FXML
    private Label courseNameError;

    @FXML
    private TextField courseNameField;

    @FXML
    private Button deleteButton;

    @FXML
    private ComboBox<Major> majorComboBox;

    @FXML
    private Label majorError;

    @FXML
    private Button submitButton;
    private CourseDetails selectedCourse;

    public void setMode(String mode, CourseDetails course) {
        this.selectedCourse = course;

        if ("update".equalsIgnoreCase(mode) && course != null) {
            submitButton.setText("Update Course");
            deleteButton.setVisible(true);
            deleteButton.setManaged(true);
            
            // Fill existing data
            courseNameField.setText(course.getCourse_name());
            
            // Set the correct Major in ComboBox
            for (Major m : majorComboBox.getItems()) {
                if (m.getId() == course.getMajor_id()) {
                    majorComboBox.setValue(m);
                    break;
                }
            }
        } else {
            // Create Mode
            this.selectedCourse = null;
            submitButton.setText("Create Course");
            deleteButton.setVisible(false);
            deleteButton.setManaged(false);
            courseNameField.clear();
            majorComboBox.setValue(null);
        }
    }

    @FXML
    private void handleSubmit() {
        if (!validateForm()) return;

        String name = courseNameField.getText();
        int majorId = majorComboBox.getValue().getId();

        if (selectedCourse == null) {
            // CREATE LOGIC
            Course newCourse = new Course(name, majorId);
            if (CourseDAO.addCourse(newCourse)) {
                closeWindow();
            }
        } else {
            // UPDATE LOGIC
            selectedCourse.setCourse_name(name);
            selectedCourse.setMajor_id(majorId);
            if (CourseDAO.updateCourse(new Course(selectedCourse.getCourse_id(),selectedCourse.getCourse_name(),selectedCourse.getMajor_id()))) {
                closeWindow();
            }
        }
    }

    @FXML
    private void handleDelete() {
        if (selectedCourse != null) {
            if (CourseDAO.deleteCourse(selectedCourse.getCourse_id())) {
                closeWindow();
            }
        }
    }

    private boolean validateForm() {
        boolean isValid = true;
        // Your existing validation logic...
        if (courseNameField.getText().trim().length() < 2) {
            courseNameError.setVisible(true);
            isValid = false;
        } else {
            courseNameError.setVisible(false);
        }
        
        if (majorComboBox.getValue() == null) {
            majorError.setVisible(true);
            isValid = false;
        } else {
            majorError.setVisible(false);
        }
        return isValid;
    }

    private void closeWindow() {
        submitButton.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        majorComboBox.setItems(FXCollections.observableArrayList(MajorDAO.getAllMajors()));
    }

}
