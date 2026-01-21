package controllers;

import java.io.IOException;
import java.util.ArrayList;

import application.UserControl;

import java.util.*;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Rectangle2D;

import java.net.URL;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.*;
import models.Class;

public class StudentScheduleController {

    @FXML
    private Button btn_assignments;

    @FXML
    private Button btn_course;

    @FXML
    private Button btn_grade;

    @FXML
    private Button btn_home;

    @FXML
    private Button btn_schedule;

    @FXML
    private Button btn_studentinfo;
    
    @FXML
    private Button btn_logout;

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
    private BorderPane mainContent;
    private Enrollment enrollInfo = EnrollmentDAO.getEnrollmentByStudentId(UserControl.getStu().getId());
    @FXML
    public void initialize() {
    	
    	
    	col_1.setCellValueFactory(new PropertyValueFactory<>("s1"));
    	col_2.setCellValueFactory(new PropertyValueFactory<>("s2"));
    	col_3.setCellValueFactory(new PropertyValueFactory<>("s3"));
    	col_4.setCellValueFactory(new PropertyValueFactory<>("s4"));
    	
    	tbl_schedule.getSelectionModel().setCellSelectionEnabled(true);
        tbl_schedule.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        System.out.println("ID: "+enrollInfo.getClass_id());
//    	populateTable(enrollInfo.getClass_id(), filterSchedule(SlotDAO.getAllSlots()));
        populateTable(2, filterSchedule(2));
        System.out.println(filterSchedule(2).size());
        
    }
    
    
    @FXML
    void handleAssignments(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/assignments.fxml"));
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
    void handleCourse(ActionEvent event)  throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/courses.fxml"));
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
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/grade.fxml"));
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
    void handleHome(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/student-dashboard.fxml"));
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
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/schedule.fxml"));
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
    void handleStudentInfo(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/student-info.fxml"));
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
    void handleLogout(ActionEvent event) throws IOException {
       
        FXMLLoader loader = new FXMLLoader();
        URL url = getClass().getResource("/fxml/Login-form.fxml");
        loader.setLocation(url);
        Parent root = loader.load();
		Scene scene = new Scene(root,1121,570);
		scene.getStylesheets().add(getClass().getResource("/application/login-form.css").toExternalForm());
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new Scene
        stage.setScene(scene);

        // Center on screen and show
        stage.centerOnScreen();
        stage.show();
    }
    
    void populateTable(Integer id, HashMap<Integer,ArrayList<ArrayList<Slot>>> schedule) {
        ArrayList<ScheduleDetails> resultLst = new ArrayList<>();
        schedule.forEach((classId, weekData) -> {
            System.out.println("Outputting data for Class: " + classId);
            weekData.forEach(day -> {
                System.out.println("Day Slots: " + day.get(0).getClass_name());
            });
        });
//        schedule.keySet()
        for(ArrayList<Slot> sLst: schedule.get(id)) {
          resultLst.add(new ScheduleDetails(id, sLst.get(0),sLst.get(1),sLst.get(2),sLst.get(3)));
        }
        tbl_schedule.setItems(FXCollections.observableArrayList(resultLst));
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
          this.s1 = slot1.getCourse_name();
          this.s2 = slot2.getCourse_name();
          this.s3 = slot3.getCourse_name();
          this.s4 = slot4.getCourse_name();
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
      
      public HashMap<Integer, ArrayList<ArrayList<Slot>>> filterSchedule(int classId) {
    	    ArrayList<Slot> sLst = SlotDAO.getSlotByClassId(classId);
    	    System.out.println(sLst.get(0).getCourse_name());
    	    HashMap<Integer, ArrayList<ArrayList<Slot>>> resultMap = new HashMap<>();
    	    ArrayList<ArrayList<Slot>> outer = new ArrayList<>();

    	    Slot[] MON = new Slot[4];
    	    Slot[] TUE = new Slot[4];
    	    Slot[] WED = new Slot[4];
    	    Slot[] THU = new Slot[4];
    	    Slot[] FRI = new Slot[4];

    	    for (Slot s : sLst) {
    	        // Update the slot data (Course name or Course object)
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


    	    outer.add(new ArrayList<>(Arrays.asList(MON)));
    	    outer.add(new ArrayList<>(Arrays.asList(TUE)));
    	    outer.add(new ArrayList<>(Arrays.asList(WED)));
    	    outer.add(new ArrayList<>(Arrays.asList(THU)));
    	    outer.add(new ArrayList<>(Arrays.asList(FRI)));

    	    resultMap.put(classId, outer);
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
}
