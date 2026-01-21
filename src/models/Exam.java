package models;

public class Exam {
	private int id;
	private String name;
	private int examType_id;
	private String date;
	private int course_id;
	private String course_name;
	private String examType_name;
	public String getExamType_name() {
		return examType_name;
	}
	public void setExamType_name(String examType_name) {
		this.examType_name = examType_name;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public Exam(int id, String name, int examType_id, String date, int course_id) {
		this.id = id;
		this.name = name;
		this.examType_id = examType_id;
		this.date = date;
		this.course_id = course_id;
	}
	public Exam(String name, int examType_id, String date, int course_id) {
		this.name = name;
		this.examType_id = examType_id;
		this.date = date;
		this.course_id = course_id;
	}
	public Exam() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getExamType_id() {
		return examType_id;
	}
	public void setExamType_id(int examType_id) {
		this.examType_id = examType_id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getCourse_id() {
		return course_id;
	}
	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}
	@Override
	public String toString() {
		return this.name;
	}
	
	
}
