package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

import application.UserControl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.Class;
import models.CourseDAO;
import models.Instructor;
import models.Slot;
import models.SlotDAO;

public class ScheduleController implements Initializable{
	private Instructor inst= UserControl.inst;
	@FXML
    private Button btn_logout;
	
    @FXML
    private Button btn_assignment;

    @FXML
    private Button btn_course;

    @FXML
    private Button btn_grade;

    @FXML
    private Button btn_home;

    @FXML
    private Button btn_schedule;

    @FXML
    private Button btn_studentInfo;

    @FXML
    private BorderPane mainContent;
    @FXML
    private TableColumn<ScheduleDetails, String> col_1;

    @FXML
    private TableColumn<ScheduleDetails, String> col_2;

    @FXML
    private TableColumn<ScheduleDetails, String> col_3;

    @FXML
    private TableColumn<ScheduleDetails, String> col_4;

    @FXML
    private TableView<ScheduleDetails> tbl_schedule;
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
    void handleHome(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/instructorDashboard.fxml"));
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
    void handleCourse(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/instructorCourses.fxml"));
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
    void handleAssignment(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/instructorAssignments.fxml"));
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
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/instructorGrade.fxml"));
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
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/instructorSchedule.fxml"));
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
    void handleInstructorInfo(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/instructorInstructorInfo.fxml"));
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
    void toggleCourse(MouseEvent event) {

    }
    public class ScheduleDetails {
        private models.Class cl;
        private int cid;
        private Slot slot1, slot2, slot3, slot4;
        private String s1, s2, s3, s4;
        
        public ScheduleDetails(int cid, Slot slot1, Slot slot2, Slot slot3, Slot slot4) {
          super();
          this.cid = cid;
          this.slot1 = slot1;
          this.slot2 = slot2;
          this.slot3 = slot3;
          this.slot4 = slot4;
          this.s1 = slot1.getCourse_name() + (slot1.getClass_name()==null? "\nFree": "\nClass: " + slot1.getClass_name());
          this.s2 = slot2.getCourse_name() + (slot2.getClass_name()==null? "\nFree": "\nClass: " + slot2.getClass_name());
          this.s3 = slot3.getCourse_name() + (slot3.getClass_name()==null? "\nFree": "\nClass: " + slot3.getClass_name());
          this.s4 = slot4.getCourse_name() + (slot4.getClass_name()==null? "\nFree": "\nClass: " + slot4.getClass_name());
        }
        public int getCid() {
          return cid;
        }
        public void setCid(int cid) {
          this.cid = cid;
        }
        public String getS1() {
          return s1;
        }
        public void setS1(String s1) {
          this.s1 = s1;
        }
        public String getS2() {
          return s2;
        }
        public void setS2(String s2) {
          this.s2 = s2;
        }
        public String getS3() {
          return s3;
        }
        public void setS3(String s3) {
          this.s3 = s3;
        }
        public String getS4() {
          return s4;
        }
        public void setS4(String s4) {
          this.s4 = s4;
        }
        public ScheduleDetails(Class cl, Slot slot1, Slot slot2, Slot slot3, Slot slot4) {
//          super();
          this.cl = cl;
          this.slot1 = slot1;
          this.slot2 = slot2;
          this.slot3 = slot3;
          this.slot4 = slot4;
          this.s1 = slot1.getCourse_name();
          this.s2 = slot2.getCourse_name();
          this.s3 = slot3.getCourse_name();
          this.s4 = slot4.getCourse_name();
        }
        public models.Class getCl() {
          return cl;
        }
        public void setCl(models.Class cl) {
          this.cl = cl;
        }
        public Slot getSlot1() {
          return slot1;
        }
        public void setSlot1(Slot slot1) {
          this.slot1 = slot1;
        }
        public Slot getSlot2() {
          return slot2;
        }
        public void setSlot2(Slot slot2) {
          this.slot2 = slot2;
        }
        public Slot getSlot3() {
          return slot3;
        }
        public void setSlot3(Slot slot3) {
          this.slot3 = slot3;
        }
        public Slot getSlot4() {
          return slot4;
        }
        public void setSlot4(Slot slot4) {
          this.slot4 = slot4;
        }
        
        
      }
      
    public HashMap<Integer, ArrayList<ArrayList<Slot>>> filterScheduleByInstructor(int instructorId) {
        // 1. Fetch only slots assigned to this instructor
        ArrayList<Slot> sLst = SlotDAO.getSlotsByInstructorId(instructorId);
        
        HashMap<Integer, ArrayList<ArrayList<Slot>>> resultMap = new HashMap<>();
        ArrayList<ArrayList<Slot>> outer = new ArrayList<>();

        // Initialize day arrays
        Slot[] MON = new Slot[4];
        Slot[] TUE = new Slot[4];
        Slot[] WED = new Slot[4];
        Slot[] THU = new Slot[4];
        Slot[] FRI = new Slot[4];

        for (Slot s : sLst) {
            Slot updatedSlot = (s.getCourse_id() == 0) ? update(s) : update2(s);
            int slotIndex = filterSlot(s);

            switch (s.getDay().toUpperCase()) {
                case "MON": MON[slotIndex] = updatedSlot; break;
                case "TUE": TUE[slotIndex] = updatedSlot; break;
                case "WED": WED[slotIndex] = updatedSlot; break;
                case "THU": THU[slotIndex] = updatedSlot; break;
                case "FRI": FRI[slotIndex] = updatedSlot; break;
            }
        }

        fillEmptySlots(MON);
        fillEmptySlots(TUE);
        fillEmptySlots(WED);
        fillEmptySlots(THU);
        fillEmptySlots(FRI);

        outer.addAll(Arrays.asList(new ArrayList<>(Arrays.asList(MON)), 
                                   new ArrayList<>(Arrays.asList(TUE)), 
                                   new ArrayList<>(Arrays.asList(WED)), 
                                   new ArrayList<>(Arrays.asList(THU)), 
                                   new ArrayList<>(Arrays.asList(FRI))));

        resultMap.put(instructorId, outer);
        return resultMap;
    }
    Slot update(Slot s) {
          s.setCourse_name("Study");
          return s;
        }
        Slot update2(Slot s) {
          s.setCourseObj(CourseDAO.getCourseById(s.getCourse_id()).get(0));
          return s;
        }
        int filterSlot(Slot s) {
          int result = 0;
          Slot[] tmpLst = new Slot[4];
//          System.out.println(s.getStart_time());
          switch(s.getStart_time()) {
            case "09:00": result=0;break; 
            case "10:30": result = 1; break;
            case "13:00": result = 2;break;
            case "14:30": result = 3;break;
          }
          return result;
        
      }

    	private void fillEmptySlots(Slot[] day) {
    	    for (int i = 0; i < day.length; i++) {
    	        if (day[i] == null) {
    	            Slot empty = new Slot();
    	            empty.setCourse_name("Study");
    	            day[i] = empty;
    	        }
    	    }
    	}
      
      @FXML
      void showData(MouseEvent event) {
          ScheduleDetails selected = tbl_schedule.getSelectionModel().getSelectedItem();

          if (selected != null) {
              System.out.println("Monday Class: " + selected.getS1());
          }
      }

	  @Override
	  public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		  col_1.setCellValueFactory(new PropertyValueFactory<>("s1"));
	    	col_2.setCellValueFactory(new PropertyValueFactory<>("s2"));
	    	col_3.setCellValueFactory(new PropertyValueFactory<>("s3"));
	    	col_4.setCellValueFactory(new PropertyValueFactory<>("s4"));
	    	
	    	tbl_schedule.getSelectionModel().setCellSelectionEnabled(true);
	        tbl_schedule.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	        
	        HashMap<Integer, ArrayList<ArrayList<Slot>>> data = filterScheduleByInstructor(inst.getId());
	        populateTable(UserControl.inst.getId(), data);
	  }
	  void populateTable(Integer instructorId, HashMap<Integer, ArrayList<ArrayList<Slot>>> schedule) {
		    ArrayList<ScheduleDetails> resultLst = new ArrayList<>();
		    
		    // Check if the instructor has any data in the map
		    if (schedule != null && schedule.containsKey(instructorId)) {
		        ArrayList<ArrayList<Slot>> weekData = schedule.get(instructorId);
		        
		        // Loop through the 5 days (Monday to Friday)
		        for (ArrayList<Slot> daySlots : weekData) {
		            // Ensure the day has exactly 4 slots to prevent IndexOutOfBounds
		            if (daySlots.size() >= 4) {
		                resultLst.add(new ScheduleDetails(
		                    instructorId, 
		                    daySlots.get(0), // 09:00 - 10:30
		                    daySlots.get(1), // 10:30 - 12:00
		                    daySlots.get(2), // 01:00 - 02:30
		                    daySlots.get(3)  // 02:30 - 04:00
		                ));
		            }
		        }
		    }
		    
		    // Update the JavaFX TableView
		    tbl_schedule.setItems(FXCollections.observableArrayList(resultLst));
		}

}
