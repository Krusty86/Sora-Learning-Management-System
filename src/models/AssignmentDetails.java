package models;

public class AssignmentDetails {
	private String title, due_date, course_name;
	private int submitted, total;
	public AssignmentDetails(String title, String due_date, String course_name, int submitted, int total) {
		super();
		this.title = title;
		this.due_date = due_date;
		this.course_name = course_name;
		this.submitted = submitted;
		this.total = total;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDue_date() {
		return due_date;
	}
	public AssignmentDetails(String title, String due_date, String course_name) {
		super();
		this.title = title;
		this.due_date = due_date;
		this.course_name = course_name;
	}
	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public int getSubmitted() {
		return submitted;
	}
	public void setSubmitted(int submitted) {
		this.submitted = submitted;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
}
