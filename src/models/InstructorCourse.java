package models;

public class InstructorCourse {
	private int id, instructor_id, course_id;
	private String instructor, course;
	private Instructor ins;
	private Course cor;
	public InstructorCourse(int id, Instructor ins, Course cor) {
		super();
		this.id = id;
		this.ins = ins;
		this.cor = cor;
		this.instructor = ins.getName();
		this.course = cor.getName();
		this.instructor_id = ins.getId();
	}

	public InstructorCourse(int id, int instructor_id, int course_id, String instructor, String course, Instructor ins,
			Course cor) {
		super();
		this.id = id;
		this.instructor_id = instructor_id;
		this.course_id = course_id;
		this.instructor = instructor;
		this.course = course;
		this.ins = ins;
		this.cor = cor;
	}

	public Instructor getIns() {
		return ins;
	}

	public void setIns(Instructor ins) {
		this.ins = ins;
	}

	public Course getCor() {
		return cor;
	}

	public void setCor(Course cor) {
		this.cor = cor;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public InstructorCourse(int instructor_id, int course_id, String instructor, String course) {
//		super();
		this.instructor_id = instructor_id;
		this.course_id = course_id;
		this.instructor = instructor;
		this.course = course;
	}

	public InstructorCourse(int id, int instructor_id, int course_id, String instructor, String course) {
//		super();
		this.id = id;
		this.instructor_id = instructor_id;
		this.course_id = course_id;
		this.instructor = instructor;
		this.course = course;
	}

	public InstructorCourse(int id, int instructor_id, int course_id) {
//		super();
		this.id = id;
		this.instructor_id = instructor_id;
		this.course_id = course_id;
	}

	public InstructorCourse(int instructor_id, int course_id) {
//		super();
		this.instructor_id = instructor_id;
		this.course_id = course_id;
	}

	public InstructorCourse() {
//		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInstructor_id() {
		return instructor_id;
	}

	public void setInstructor_id(int instructor_id) {
		this.instructor_id = instructor_id;
	}

	public int getCourse_id() {
		return course_id;
	}

	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}
	
}
