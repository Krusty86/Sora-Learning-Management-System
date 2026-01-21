package models;

public class CourseDetails {
	private String course_name, instructor_name, major_name, year_name;
	private int student_count, course_id, major_id;
	public CourseDetails(String course_name, String instructor_name, String major_name, String year_name,
			int student_count, int course_id, int major_id) {
		super();
		this.course_name = course_name;
		this.instructor_name = instructor_name;
		this.major_name = major_name;
		this.year_name = year_name;
		this.student_count = student_count;
		this.course_id = course_id;
		this.major_id = major_id;
	}
	public int getMajor_id() {
		return major_id;
	}
	public void setMajor_id(int major_id) {
		this.major_id = major_id;
	}
	public CourseDetails(String course_name, String instructor_name, String major_name, String year_name,
			int student_count, int course_id) {
		super();
		this.course_name = course_name;
		this.instructor_name = instructor_name;
		this.major_name = major_name;
		this.year_name = year_name;
		this.student_count = student_count;
		this.course_id = course_id;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public String getInstructor_name() {
		return instructor_name;
	}
	public void setInstructor_name(String instructor_name) {
		this.instructor_name = instructor_name;
	}
	public String getMajor_name() {
		return major_name;
	}
	public void setMajor_name(String major_name) {
		this.major_name = major_name;
	}
	public String getYear_name() {
		return year_name;
	}
	public void setYear_name(String year_name) {
		this.year_name = year_name;
	}
	public int getStudent_count() {
		return student_count;
	}
	public void setStudent_count(int student_count) {
		this.student_count = student_count;
	}
	public int getCourse_id() {
		return course_id;
	}
	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}
	
	
}
