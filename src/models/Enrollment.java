package models;

public class Enrollment {
	private int id, student_id, year_id, major_id, class_id, course_id;
	private String email;
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCourse_id() {
		return course_id;
	}

	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}

	public Enrollment(String student, String course_name, int student_id, int course_id, int id) {
		super();
		this.student = student;
		this.course_name = course_name;
		this.student_id = student_id;
		this.course_id = course_id;
		this.id = id;
	}

	private String student, year, major, class_name, course_name;
	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	private int count;

	public String getStudent() {
		return student;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setStudent(String student) {
		this.student = student;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Enrollment(int student_id, int year_id, int major_id, int class_id, String student, String year,
			String major, String class_name) {
//		super();
		this.student_id = student_id;
		this.year_id = year_id;
		this.major_id = major_id;
		this.class_id = class_id;
		this.student = student;
		this.year = year;
		this.major = major;
		this.class_name = class_name;
	}

	public Enrollment(int id, int student_id, int year_id, int major_id, int class_id, String student, String year,
			String major, String class_name) {
//		super();
		this.id = id;
		this.student_id = student_id;
		this.year_id = year_id;
		this.major_id = major_id;
		this.class_id = class_id;
		this.student = student;
		this.year = year;
		this.major = major;
		this.class_name = class_name;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public Enrollment(String student, String year, String major, String class_name) {
		super();
		this.student = student;
		this.year = year;
		this.major = major;
		this.class_name = class_name;
	}

	public Enrollment(int id, int student_id, int year_id, int major_id, int class_id) {
//		super();
		this.id = id;
		this.student_id = student_id;
		this.year_id = year_id;
		this.major_id = major_id;
		this.class_id = class_id;
	}

	public Enrollment(int student_id, int year_id, int major_id, int class_id) {
//		super();
		this.student_id = student_id;
		this.year_id = year_id;
		this.major_id = major_id;
		this.class_id = class_id;
	}
	
	public Enrollment() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}

	public int getYear_id() {
		return year_id;
	}

	public void setYear_id(int year_id) {
		this.year_id = year_id;
	}

	public int getMajor_id() {
		return major_id;
	}

	public void setMajor_id(int major_id) {
		this.major_id = major_id;
	}

	public int getClass_id() {
		return class_id;
	}

	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}
	
}
