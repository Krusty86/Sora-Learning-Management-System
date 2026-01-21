package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.*;
public class AdminClassFormController implements Initializable {

    @FXML
    private TableView<models.Class> tbl_class;
    @FXML
    private TableColumn<models.Class, String> col_class;
    @FXML
    private TableColumn<models.Class, String> col_major;

    @FXML
    private TextField courseNameField; // Assuming this is the Class Name input
    @FXML
    private ComboBox<Major> majorComboBox;
    
    @FXML
    private Label courseNameError;
    @FXML
    private Label majorError;

    @FXML
    private Button submitButton;
    @FXML
    private Button deleteButton;

    // Track the current selected object for Update/Delete
    private models.Class selectedClass;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Setup Table Columns
        col_class.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_major.setCellValueFactory(new PropertyValueFactory<>("major_name"));
        
        refreshTable();
        majorComboBox.setItems(FXCollections.observableArrayList(MajorDAO.getAllMajors()));

        // Listen for table selection changes
        tbl_class.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
            }
        });
        
        // Hide delete button by default (Create mode)
        deleteButton.setVisible(false);
    }

    private void refreshTable() {
        tbl_class.setItems(FXCollections.observableArrayList(ClassDAO.getAllClasses()));
    }

    private void populateFields(models.Class targetClass) {
        this.selectedClass = targetClass;
        courseNameField.setText(targetClass.getName());
        
        // Set ComboBox value
        for (Major m : majorComboBox.getItems()) {
            if (m.getId() == targetClass.getMajor_id()) {
                majorComboBox.setValue(m);
                break;
            }
        }

        submitButton.setText("Update Class");
        deleteButton.setVisible(true);
    }

    @FXML
    void handleSubmit(ActionEvent event) {
        if (!validateForm()) return;

        String name = courseNameField.getText().trim();
        int majorId = majorComboBox.getValue().getId();

        if (selectedClass == null) {
            // CREATE LOGIC
            models.Class newClass = new models.Class(name, majorId);
            if (ClassDAO.addClass(newClass)) {
                resetForm();
                refreshTable();
            }
        } else {
            // UPDATE LOGIC
            selectedClass.setName(name);
            selectedClass.setMajor_id(majorId);
            if (ClassDAO.updateClass(selectedClass)) {
                resetForm();
                refreshTable();
            }
        }
    }

    @FXML
    void handleDelete(ActionEvent event) {
        if (selectedClass != null) {
            // Assuming ClassDAO has a delete method by ID
            if (ClassDAO.deleteClass(selectedClass.getId())) {
                resetForm();
                refreshTable();
            }
        }
    }

    private boolean validateForm() {
        boolean isValid = true;
        if (courseNameField.getText().trim().isEmpty()) {
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

    private void resetForm() {
        selectedClass = null;
        courseNameField.clear();
        majorComboBox.setValue(null);
        submitButton.setText("Create Class");
        deleteButton.setVisible(false);
        tbl_class.getSelectionModel().clearSelection();
    }

    private void closeWindow() {
        submitButton.getScene().getWindow().hide();
    }
}