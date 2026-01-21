package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import models.Instructor;

public class RowController {
    @FXML private Label nameLabel, idLabel, emailLabel, addressLabel;

    public void setData(Instructor instructor) {
        nameLabel.setText(instructor.getName());
        idLabel.setText("ID: " + instructor.getId());
        emailLabel.setText(instructor.getEmail());
        addressLabel.setText(instructor.getAddress());
    }

    @FXML private void handleEdit() { /* Edit Logic */ }
    @FXML private void handleDetail() { /* Detail Logic */ }
}