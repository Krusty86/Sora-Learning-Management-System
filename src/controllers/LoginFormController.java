package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.UserControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.*;

public class LoginFormController implements Initializable{
    @FXML
    private TextField textField;
    @FXML
    private Button btn_signIn;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button showPasswordBtn;

    @FXML
    private TextField usernameField;
    @FXML
    void handleShowPassword(ActionEvent event) {
    	if (passwordField.isVisible()) {
            // Switch to Plain Text
            passwordField.setVisible(false);
            textField.setVisible(true);
        } else {
            // Switch back to Dots
            passwordField.setVisible(true);
            textField.setVisible(false);
        }
    }

    @FXML
    void handleSignIn(ActionEvent event) {
    	String userName = usernameField.getText();
    	String pwd = passwordField.getText();
    	Admin Account = AdminDAO.getUser(userName, pwd);
    	if(Account!=null) {
        	String userRole = Account.getRole();
            if (userRole != null) {
                String fxmlFile = "";
                switch (userRole.toLowerCase()) {
                    case "admin":
                        fxmlFile = "/fxml/admin-dashboard.fxml";
                        break;
                    case "student":
                        fxmlFile = "/fxml/student-dashboard.fxml";
                        break;
                    case "instructor":
                        fxmlFile = "/fxml/instructorDashboard.fxml";
                        break;
                    default:
                        System.out.println("Unknown Role");
                        return;
                }

        navigateToDashboard(event, fxmlFile, Account);
    }} else {
    	Alert alert = new Alert(AlertType.ERROR,"Wrong Login Credentials");
    	alert.show();
        System.out.println("Invalid credentials!");
    }
}

	private void navigateToDashboard(ActionEvent event, String fxmlPath, Admin account) {
	    try {
		    	FXMLLoader loader = new FXMLLoader();
		    	URL url = getClass().getResource(fxmlPath);
		    	
	    	if(account.getRole().equals("admin")) {
	    		
	    	}else {
	    		System.out.println("Inside Student or Instructor");
	    		UserControl.setUserData(account);
	    	}
	    	loader.setLocation(url);
	    	Parent root = loader.load();
		        Scene scene = new Scene(root);
		        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		        stage.setScene(scene);
		        stage.setMaximized(true);
		        stage.show();
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Error loading: " + fxmlPath);
	    }
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		textField.textProperty().bindBidirectional(passwordField.textProperty());

        // 2. Ensure the hidden field collapses its layout space
        textField.managedProperty().bind(textField.visibleProperty());
        passwordField.managedProperty().bind(passwordField.visibleProperty());

        // 3. Initial State: Dots visible, Plain text hidden
        passwordField.setVisible(true);
        textField.setVisible(false);
	}


}
