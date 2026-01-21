package models;

public class Slot {
	private int id, course_id;
	private String day, start_time, end_time;
	private int class_id;
	private String course_name, class_name;
	private Class cObj;
	private Course courseObj;
	public Slot(int id, String day, String start_time, String end_time, Class cObj, Course courseObj) {
//		super();
		this.id = id;
		this.day = day;
		this.start_time = start_time;
		this.end_time = end_time;
		this.cObj = cObj;
		this.courseObj = courseObj;
		this.course_name = courseObj.getName();
		this.course_id = courseObj.getId();
		this.class_id = cObj.getId();
		this.class_name = cObj.getName();
	}
	public Class getcObj() {
		return cObj;
	}
	public void setcObj(Class cObj) {
		this.cObj = cObj;
	}
	public Course getCourseObj() {
		return courseObj;
	}
	public void setCourseObj(Course courseObj) {
		this.courseObj = courseObj;
		this.course_name = courseObj.getName();
	}
	public Slot(int id, int course_id, String day, String start_time, String end_time, int class_id, String course_name,
			String class_name, Class cObj, Course courseObj) {
//		super();
		this.id = id;
		this.course_id = course_id;
		this.day = day;
		this.start_time = start_time;
		this.end_time = end_time;
		this.class_id = class_id;
		this.course_name = course_name;
		this.class_name = class_name;
		this.cObj = cObj;
		this.courseObj = courseObj;
	}
	public Slot(int id, int course_id, String day, String start_time, String end_time, int class_id) {
		this.id = id;
		this.course_id = course_id;
		this.day = day;
		this.start_time = start_time;
		this.end_time = end_time;
		this.class_id = class_id;
	}
	public Slot(String day, String start_time, String end_time, Class cObj, Course courseObj) {
//		super();
		this.day = day;
		this.start_time = start_time;
		this.end_time = end_time;
		this.cObj = cObj;
		this.courseObj = courseObj;
		this.course_name = courseObj.getName();
		this.course_id = courseObj.getId();
		this.class_id = cObj.getId();
		this.class_name = cObj.getName();
	}
	public Slot(int course_id, String day, String start_time, String end_time, int class_id) {
		this.course_id = course_id;
		this.day = day;
		this.start_time = start_time;
		this.end_time = end_time;
		this.class_id = class_id;
	}
	public Slot() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCourse_id() {
		return course_id;
	}
	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public int getClass_id() {
		return class_id;
	}
	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public String getClass_name() {
		return class_name;
	}
	public Slot(String day, String start_time, String end_time, String course_name, Class cObj) {
		super();
		this.day = day;
		this.start_time = start_time;
		this.end_time = end_time;
		this.course_name = course_name;
		this.cObj = cObj;
	}
	public Slot(int id, String day, String start_time, String end_time, String course_name, Class cObj) {
		super();
		this.id = id;
		this.day = day;
		this.start_time = start_time;
		this.end_time = end_time;
		this.course_name = course_name;
		this.cObj = cObj;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public Slot(String course_name) {
		super();
		this.course_name = course_name;
	}
	
	
}
