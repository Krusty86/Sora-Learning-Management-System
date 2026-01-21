package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.*;
import models.Class;

public class AdminScheduleController implements Initializable{
	ArrayList<Slot> sLst = SlotDAO.getAllSlots();
	ArrayList<models.Class> cLst = getClassList(sLst);
	HashMap<Integer,ArrayList<ArrayList<Slot>>> schedule = filterSchedule(sLst);
	private static int currentPage = 1;
	
	  @FXML
	    private Button btn_logOut;
	    @FXML
	    private Button btn_account1;

	    @FXML
	    private Button btn_course1;

	    @FXML
	    private Button btn_create;

	    @FXML
	    private Button btn_delete;

	    @FXML
	    private Button btn_grade1;

	    @FXML
	    private Button btn_home1;

	    @FXML
	    private Button btn_instructor1;
	    @FXML
	    private Button btn_shedule1;

	    @FXML
	    private ComboBox<Class> cbl_classFilter;
	    @FXML
	    private ComboBox<Major> cbl_majorFilter;
	    @FXML
	    private Button btn_student1;

	    @FXML
	    private Button btn_addClass;
	    @FXML
	    private Button btn_update;

	    @FXML
	    private ComboBox<models.Class> cbl_class;

	    @FXML
	    private ComboBox<Course> cbl_courseName;

	    @FXML
	    private ComboBox<String> cbl_day;

	    @FXML
	    private ComboBox<String> cbl_endTime;

	    @FXML
	    private ComboBox<String> cbl_startTime;

	    @FXML
	    private TableColumn<ScheduleDetails, String> col_1;

	    @FXML
	    private TableColumn<ScheduleDetails, String> col_2;

	    @FXML
	    private TableColumn<ScheduleDetails, String> col_3;

	    @FXML
	    private TableColumn<ScheduleDetails, String> col_4;

	    @FXML
	    private BorderPane mainContent;

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
				stage.setResizable(false);
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    }
	    @FXML
	    void handleClassAddition(ActionEvent event) {
	    	try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/classForm.fxml"));
	            Parent root = loader.load();
	            AdminClassFormController controller = loader.getController();
	            Stage stage = new Stage();
	            stage.setScene(new Scene(root));
	            stage.showAndWait();
	            
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    @FXML
	    void handleClassFilter(ActionEvent event) {
	    	int class_id = cbl_classFilter.getSelectionModel().getSelectedItem().getId();
	    	cbl_courseName.setItems(FXCollections.observableArrayList(CourseDAO.getAssignedCoursesByClass(class_id)));
	    	populateTable(class_id, schedule);
	    }
	    @FXML
	    void handleMajorFilter(ActionEvent event) {
	    	int major_id = cbl_majorFilter.getSelectionModel().getSelectedItem().getId();
	    	ArrayList<Class> classLst = ClassDAO.getClassesByMajorId(major_id);
	    	cbl_classFilter.setItems(FXCollections.observableArrayList(classLst));
	    	populateTable(classLst.get(0).getId(), schedule);
	    	cbl_courseName.setItems(FXCollections.observableArrayList(CourseDAO.getAssignedCoursesByClass(classLst.get(0).getId())));
	    }
	       Slot cellData;
	    @FXML
	    void handleCreate(ActionEvent event) {
	    	if(SlotDAO.addSlot(new Slot(cbl_courseName.getSelectionModel().getSelectedItem().getId(), cbl_day.getSelectionModel().getSelectedItem(),cbl_startTime.getSelectionModel().getSelectedItem(), cbl_endTime.getSelectionModel().getSelectedItem(),cbl_class.getSelectionModel().getSelectedItem().getId()))) {
	    		Alert alert = new Alert(AlertType.INFORMATION,"Schedule Added Successfully");
	    		alert.show();displayUpdate();
	    	}else {
	    		Alert alert = new Alert(AlertType.ERROR,"Schedule Adding Failed");
	    		alert.show();
	    	}
	    }

	    private void displayUpdate() {
	        // 1. Re-fetch all slots from the database to get the latest changes
	        this.sLst = SlotDAO.getAllSlots();
	        
	        // 2. Re-filter the schedule HashMap with the new data
	        this.schedule = filterSchedule(this.sLst);
	        
	        // 3. Determine which class ID is currently being viewed
	        // If a class is selected in the filter, use that; otherwise default to the first class
	        int currentClassId;
	        if (cbl_classFilter.getSelectionModel().getSelectedItem() != null) {
	            currentClassId = cbl_classFilter.getSelectionModel().getSelectedItem().getId();
	        } else {
	            currentClassId = 1; // Default fallback
	        }
	        
	        // 4. Re-populate the table
	        populateTable(currentClassId, this.schedule);
	        
	        // 5. Clear selection and reset buttons to a neutral state
	        tbl_schedule.getSelectionModel().clearSelection();
	        btn_create.setDisable(false);
	        btn_update.setDisable(true);
	        btn_delete.setDisable(true);
	    }

		@FXML
	    void handleDelete(ActionEvent event) {
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setTitle("Delete Confirmation");
	    	alert.setHeaderText("You are about to Delete this");
	    	Optional<ButtonType> result = alert.showAndWait();
	    	if (result.isPresent() && result.get() == ButtonType.OK) {
	            // User clicked OK - Perform the delete logic here
	            System.out.println("Item Deleted");
	        } else {
	            // User clicked Cancel or closed the window
	            System.out.println("Action Cancelled");
	        }
	    }
	  
	    @FXML
	    void handleUpdate(ActionEvent event) {
	    	Slot dataSlot = new Slot(cellData.getId(), cbl_courseName.getSelectionModel().getSelectedItem().getId(), cbl_day.getValue(), cbl_startTime.getValue(), cbl_endTime.getValue(), cbl_class.getSelectionModel().getSelectedItem().getId());
	    	if(SlotDAO.updateSlot(dataSlot)){
	    		Alert alert = new Alert(AlertType.INFORMATION,"Schedule Added Successfully");
	    		alert.show();
	    	}else {
	    		Alert alert = new Alert(AlertType.ERROR,"Schedule Adding Failed");
	    		alert.show();
	    	}
	    }
	    	    
//	    @FXML
//	    void showData(MouseEvent event) {
////	    	get coordinates of the selected cell
//	    	
//	    	TablePosition pos = tbl_schedule.getSelectionModel().getSelectedCells().get(0);
//	    	int row = pos.getRow();
//	    	int col = pos.getColumn();
//	    	System.out.println(pos.getColumn());
//	    	cellData = schedule.get(cLst.get(0).getId()).get(row).get(col);
//	    	System.out.println("Selected Cell: "+cellData.getCourse_name());
//	    	cbl_class.setValue(ClassDAO.getClassById(cellData.getClass_id()).get(0));
//	    	cbl_courseName.setValue(CourseDAO.getCourseById(cellData.getCourse_id()).get(0));
//	    	cbl_day.setValue(cellData.getDay());
//	    	cbl_startTime.setValue(cellData.getStart_time());
//	    	cbl_endTime.setValue(cellData.getEnd_time());
//	    	
//	    }   
	    @FXML
	    void showData(MouseEvent event) {
	        if (tbl_schedule.getSelectionModel().getSelectedCells().isEmpty()) return;

	        TablePosition pos = tbl_schedule.getSelectionModel().getSelectedCells().get(0);
	        int row = pos.getRow(); // Day (0=Mon, 1=Tue...)
	        int col = pos.getColumn(); // Time Slot (0=9:00, 1=10:30...)

	        // Use the currently selected class from the filter, not just index 0
	        int currentClassId = cbl_classFilter.getValue() != null ? 
	                             cbl_classFilter.getValue().getId() : cLst.get(0).getId();

	        cellData = schedule.get(currentClassId).get(row).get(col);

	        if (cellData != null) {
	            System.out.println("Selected: " + cellData.getCourse_name());
	            cbl_class.setValue(ClassDAO.getClassById(cellData.getClass_id()).get(0));
	            cbl_courseName.setValue(CourseDAO.getCourseById(cellData.getCourse_id()).get(0));
	            cbl_day.setValue(cellData.getDay());
	            cbl_startTime.setValue(cellData.getStart_time());
	            cbl_endTime.setValue(cellData.getEnd_time());
	            
	            // Change button mode
	            btn_create.setDisable(true);
	            btn_update.setDisable(false);
	            btn_delete.setDisable(false);
	        } else {
	            // Handle clicking an empty slot
	            System.out.println("Empty Slot Selected");
	            btn_create.setDisable(false);
	            btn_update.setDisable(true);
	        }
	    }
	    
	    
	    @FXML
	    void handleCourse(ActionEvent event)  throws IOException {
	    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/admin-courses.fxml"));
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
	        Scene scene = new Scene(root, wd, hgh);stage.setScene(scene);
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
		cbl_day.setItems(FXCollections.observableArrayList("MON", "TUE", "WED", "THU", "FRI"));
	    cbl_startTime.setItems(FXCollections.observableArrayList("09:00", "10:30", "13:00", "14:30"));
	    cbl_endTime.setItems(FXCollections.observableArrayList("10:30", "12:00", "14:30", "16:00"));
	    cbl_class.setItems(FXCollections.observableArrayList(ClassDAO.getAllClasses()));
	    cbl_courseName.setItems(FXCollections.observableArrayList(CourseDAO.getAssignedCoursesByClass(1)));
	    
	    cbl_day.getSelectionModel().selectFirst();
		col_1.setCellValueFactory(new PropertyValueFactory<>("s1"));
	    col_2.setCellValueFactory(new PropertyValueFactory<>("s2"));
	    col_3.setCellValueFactory(new PropertyValueFactory<>("s3"));
	    col_4.setCellValueFactory(new PropertyValueFactory<>("s4"));
		tbl_schedule.getSelectionModel().setCellSelectionEnabled(true);
		tbl_schedule.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		System.out.println(cLst.get(0)+" "+schedule.keySet());
		populateTable(1,schedule);
		cbl_classFilter.setItems(FXCollections.observableArrayList(ClassDAO.getClassesByMajorId(1)));
		cbl_majorFilter.setItems(FXCollections.observableArrayList(MajorDAO.getAllMajors()));
	}

	void populateTable(Integer id, HashMap<Integer, ArrayList<ArrayList<Slot>>> schedule) {
	    ArrayList<ScheduleDetails> resultLst = new ArrayList<>();

	    // 1. Check if schedule is null or if the specific class ID exists
	    if (schedule != null && schedule.containsKey(id)) {
	        ArrayList<ArrayList<Slot>> days = schedule.get(id);

	        if (days != null) {
	            for (ArrayList<Slot> daySlots : days) {
	                // Ensure the list has 4 slots before adding to prevent IndexOutOfBounds
	                if (daySlots != null && daySlots.size() >= 4) {
	                    resultLst.add(new ScheduleDetails(
	                        id, 
	                        daySlots.get(0), 
	                        daySlots.get(1), 
	                        daySlots.get(2), 
	                        daySlots.get(3)
	                    ));
	                }
	            }
	        }
	    }

	    // 2. Set items (if resultLst is empty, the table will automatically show "No content in table")
	    tbl_schedule.setItems(FXCollections.observableArrayList(resultLst));
	}
	public class ScheduleDetails {
		private models.Class cl;
		private int cid;
		private Slot slot1, slot2, slot3, slot4;
		private String s1, s2, s3, s4;
		
		public ScheduleDetails(int cid, Slot slot1, Slot slot2, Slot slot3, Slot slot4) {
		    this.cid = cid;
		    this.slot1 = slot1;
		    this.slot2 = slot2;
		    this.slot3 = slot3;
		    this.slot4 = slot4;
		    
		    // Ternary checks prevent NullPointerException
		    this.s1 = (slot1 != null) ? slot1.getCourse_name() : "Free";
		    this.s2 = (slot2 != null) ? slot2.getCourse_name() : "Free";
		    this.s3 = (slot3 != null) ? slot3.getCourse_name() : "Free";
		    this.s4 = (slot4 != null) ? slot4.getCourse_name() : "Free";
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
//			super();
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
	
	public HashMap<Integer,ArrayList<ArrayList<Slot>>> filterSchedule(ArrayList<Slot> sLst){
		ArrayList<models.Class> cLst = getClassList(sLst);
		HashMap<Integer,ArrayList<ArrayList<Slot>>> resultMap= new HashMap<>();
		for(models.Class c: cLst) {
			ArrayList<ArrayList<Slot>> outer = new ArrayList<>();
			ArrayList<Slot> inner = new ArrayList<>();
			Slot[] MON = new Slot[4];
			Slot[] TUE = new Slot[4];
			Slot[] WED = new Slot[4];
			Slot[] THU = new Slot[4];
			Slot[] FRI = new Slot[4];
			for(Slot s: sLst) {
				if(c.getId()==s.getClass_id()) {
					Course tmp = CourseDAO.getCourseById(s.getCourse_id()).get(0);
					switch(s.getDay()) {
					case "MON": MON[filterSlot(s)]=s.getCourse_id()==0? update(s): update2(s);break;
					case "TUE": TUE[filterSlot(s)]=s.getCourse_id()==0? update(s): update2(s);break;
					case "WED": WED[filterSlot(s)]=s.getCourse_id()==0? update(s): update2(s);break;
					case "THU": THU[filterSlot(s)]=s.getCourse_id()==0? update(s): update2(s);break;
					case "FRI": FRI[filterSlot(s)]=s.getCourse_id()==0? update(s): update2(s);break;
					}
				}
			}
			outer.add(new ArrayList<>(Arrays.asList(MON)));
	        outer.add(new ArrayList<>(Arrays.asList(TUE)));
	        outer.add(new ArrayList<>(Arrays.asList(WED)));
	        outer.add(new ArrayList<>(Arrays.asList(THU)));
	        outer.add(new ArrayList<>(Arrays.asList(FRI)));
			resultMap.put(c.getId(), outer);
		}
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
//			System.out.println(s.getStart_time());
			switch(s.getStart_time()) {
				case "09:00": result=0;break; 
				case "10:30": result = 1; break;
				case "13:00": result = 2;break;
				case "14:30": result = 3;break;
			}
			return result;
		
	}
	
	public ArrayList<Class> getClassList(ArrayList<Slot> obj) {
		ArrayList<models.Class> resultLst = new ArrayList<>();
		int count = 0;
		resultLst.add(ClassDAO.getClassById(obj.get(0).getClass_id()).get(0));
		for(Slot s: obj) {
			for(models.Class c:resultLst) {
				if(s.getClass_id()==c.getId()) {
					count+=1;
				}
			}if(count==0) {
				resultLst.add(ClassDAO.getClassById(s.getClass_id()).get(0));
			}
			count=0;
		}
		return resultLst;
	}
    
}
