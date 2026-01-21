package models;

public class Assignment {
	private int id;
	private String title, description, due_date, status;
	private int course_id, student_id;
	private String course, student;
	private Course cour;
	private Student stu;
	private String grade;
	private String submit;
	private String file_path;
	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	private String submit_date;
	public String getSubmit_date() {
		return submit_date;
	}

	public void setSubmit_date(String submit_date) {
		this.submit_date = submit_date;
	}

	public String getGrade() {
		return grade;
	}

	public Assignment(int id, String title, String description, String due_date) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.due_date = due_date;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Assignment(int id, String title, String description, String due_date, String status, Course cour,
			Student stu) {
//		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.due_date = due_date;
		this.status = status;
		this.cour = cour;
		this.stu = stu;
		this.course = cour.getName();
		this.course_id = cour.getId();
		this.student = stu.getName();
		this.student_id = stu.getId();
	}
	
	public Assignment(String title, String description, String due_date, String status, int course_id, int student_id,
			String grade) {
		super();
		this.title = title;
		this.description = description;
		this.due_date = due_date;
		this.status = status;
		this.course_id = course_id;
		this.student_id = student_id;
		this.grade = grade;
	}

	public Assignment(int id, String title, String description, String due_date, String status, int course_id,
			int student_id, String grade) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.due_date = due_date;
		this.status = status;
		this.course_id = course_id;
		this.student_id = student_id;
		this.grade = grade;
	}

	public Assignment(String title, String description, String due_date, String status, int course_id, int student_id,
			String course, String student, Course cour, Student stu, String grade) {
		super();
		this.title = title;
		this.description = description;
		this.due_date = due_date;
		this.status = status;
		this.course_id = course_id;
		this.student_id = student_id;
		this.course = course;
		this.student = student;
		this.cour = cour;
		this.stu = stu;
		this.grade = grade;
	}

	public Assignment(int id, String title, String description, String due_date, String status, int course_id,
			int student_id, String course, String student, Course cour, Student stu, String grade) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.due_date = due_date;
		this.status = status;
		this.course_id = course_id;
		this.student_id = student_id;
		this.course = course;
		this.student = student;
		this.cour = cour;
		this.stu = stu;
		this.grade = grade;
	}

	public Assignment(int id, String title, String description, String due_date, String status, int course_id) {
//		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.due_date = due_date;
		this.status = status;
		this.course_id = course_id;
	}

	public Assignment(String title, String description, String due_date, String status, Course cour, Student stu) {
//		super();
		this.title = title;
		this.description = description;
		this.due_date = due_date;
		this.status = status;
		this.cour = cour;
		this.stu = stu;
		this.course = cour.getName();
		this.course_id = cour.getId();
		this.student = stu.getName();
		this.student_id = stu.getId();
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getStudent() {
		return student;
	}
	public void setStudent(String student) {
		this.student = student;
	}
	public Course getCour() {
		return cour;
	}
	public void setCour(Course cour) {
		this.cour = cour;
	}
	public Student getStu() {
		return stu;
	}
	public void setStu(Student stu) {
		this.stu = stu;
	}
	public Assignment(String title, String description, String due_date, String status, int course_id, int student_id,
			String course, String student, Course cour, Student stu) {
//		super();
		this.title = title;
		this.description = description;
		this.due_date = due_date;
		this.status = status;
		this.course_id = course_id;
		this.student_id = student_id;
		this.course = course;
		this.student = student;
		this.cour = cour;
		this.stu = stu;
	}
	public Assignment(int id, String title, String description, String due_date, String status, int course_id,
			int student_id, String course, String student, Course cour, Student stu) {
//		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.due_date = due_date;
		this.status = status;
		this.course_id = course_id;
		this.student_id = student_id;
		this.course = course;
		this.student = student;
		this.cour = cour;
		this.stu = stu;
	}
	public Assignment(int id, String title, String description, String due_date, String status, int course_id,
			int student_id) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.due_date = due_date;
		this.status = status;
		this.course_id = course_id;
		this.student_id = student_id;
	}
	public Assignment(String title, String description, String due_date, String status, int course_id, int student_id) {
		this.title = title;
		this.description = description;
		this.due_date = due_date;
		this.status = status;
		this.course_id = course_id;
		this.student_id = student_id;
	}
	public Assignment() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDue_date() {
		return due_date;
	}
	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getCourse_id() {
		return course_id;
	}
	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}
	public int getStudent_id() {
		return student_id;
	}
	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}

	@Override
	public String toString() {
		return getTitle();
	}
	
	
	
}
