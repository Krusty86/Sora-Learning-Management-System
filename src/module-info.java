module SoraLMSProject {
	requires javafx.controls;
	requires java.sql;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	
	
	opens application to javafx.graphics, javafx.fxml;
	opens controllers to javafx.fxml, javafx.base;
	opens models to javafx.base;
	exports controllers to javafx.fxml;

}
