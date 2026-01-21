package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ManagementController {

    @FXML private Button btnCourses, btnAssignments;
    @FXML private AnchorPane coursesView, assignmentsView;
    @FXML private Label titleLabel, descriptionLabel, breadcrumbLabel;

    @FXML
    private void handleSwitchView(ActionEvent event) {
        if (event.getSource() == btnCourses) {
            // Logic for Course Management
            coursesView.toFront();
            updateUI("Course Management", "Manage your curriculum, instructors, and student enrollment.", "Manage Courses");
            setActiveTab(btnCourses, btnAssignments);
            
        } else if (event.getSource() == btnAssignments) {
            // Logic for Assignment Management
            assignmentsView.toFront();
            updateUI("Assignment Management", "Track assignments, submissions, and grading progress.", "Manage Assignments");
            setActiveTab(btnAssignments, btnCourses);
        }
    }

    private void updateUI(String title, String desc, String breadcrumb) {
        titleLabel.setText(title);
        descriptionLabel.setText(desc);
        breadcrumbLabel.setText(breadcrumb);
    }

    private void setActiveTab(Button active, Button inactive) {
        active.getStyleClass().remove("tab-button-inactive");
        active.getStyleClass().add("tab-button-active");
        
        inactive.getStyleClass().remove("tab-button-active");
        inactive.getStyleClass().add("tab-button-inactive");
    }
}