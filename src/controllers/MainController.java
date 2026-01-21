package controllers;
import models.*;

import java.util.*;

import javafx.fxml.*;
import javafx.scene.layout.*;

public class MainController {
	    @FXML private VBox rowsContainer;

	    public void initialize() {
	        loadDataFromDatabase();
	    }

	    private void loadDataFromDatabase() {
	        // Mock data - replace this with your actual DB fetch logic
	        List<Instructor> instructors = InstructorDAO.getAllInstructors(); 

	        for (Instructor instructor : instructors) {
	            try {
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("RowItem.fxml"));
	                HBox row = loader.load();

	                // Pass data to the row's controller
	                RowController controller = loader.getController();
	                controller.setData(instructor);

	                rowsContainer.getChildren().add(row);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }}
