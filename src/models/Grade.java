package models;

import java.time.LocalDate;

public class Grade {
	private int id, exam_id;
	private String examTypeName;
	public String getExamTypeName() {
		return examTypeName;
	}
	public void setExamTypeName(String examTypeName) {
		this.examTypeName = examTypeName;
	}
	private String result;
	private int student_id, course_id,  class_id, year_id, major_id;
	private double average;
	private String mjr_name;
	private LocalDate date;
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	private String LetterGrade;
	public String getLetterGrade() {
		return LetterGrade;
	}
	public String getMjr_name() {
		return mjr_name;
	}
	public void setMjr_name(String mjr_name) {
		this.mjr_name = mjr_name;
	}
	
	public void setLetterGrade(String letterGrade) {
		LetterGrade = letterGrade;
	}
	public double getAverage() {
		return average;
	}
	public void setAverage(double average) {
		this.average = average;
	}
	public int getMajor_id() {
		return major_id;
	}
	public Grade(String course_name, String stu_name, String ex_name, String result, double average) {
		super();
		this.course_name = course_name;
		this.stu_name = stu_name;
		this.ex_name = ex_name;
		this.result = result;
		this.average = average;
	}
	public void setMajor_id(int major_id) {
		this.major_id = major_id;
	}
	public int getCourse_id() {
		return course_id;
	}
	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}
	
	public Grade(String stu_name, String course_name, String ex_name, int id, int year_id, String result) {
//		super();
		this.stu_name = stu_name;
		this.course_name = course_name;
		this.ex_name = ex_name;
		this.id = id;
		this.year_id = year_id;
		this.result = result;
	}
	public Grade(String stu_name, String course_name, String ex_name, int student_id, int course_id,
			int exam_id, int class_id, int year_id, int major_id, String result) {
//		super();
		this.stu_name = stu_name;
		this.course_name = course_name;
		this.ex_name = ex_name;
		this.student_id = student_id;
		this.course_id = course_id;
		this.exam_id = exam_id;
		this.class_id = class_id;
		this.year_id = year_id;
		this.major_id = major_id;
		this.result = result;
	}

	public Grade(int id, String stu_name, String course_name, String ex_name, int student_id, int course_id,
			int exam_id, int class_id, int year_id, int major_id, String result) {
//		super();
		this.id = id;
		this.stu_name = stu_name;
		this.course_name = course_name;
		this.ex_name = ex_name;
		this.student_id = student_id;
		this.course_id = course_id;
		this.exam_id = exam_id;
		this.class_id = class_id;
		this.year_id = year_id;
		this.major_id = major_id;
		this.result = result;
	}
	public Grade(int id, String stu_name, String course_name, String ex_name, int student_id, int course_id,
			int exam_id, String result, int major_id) {
		super();
		this.id = id;
		this.stu_name = stu_name;
		this.course_name = course_name;
		this.ex_name = ex_name;
		this.student_id = student_id;
		this.course_id = course_id;
		this.exam_id = exam_id;
		this.result = result;
		this.major_id = major_id;
	}
	public int getClass_id() {
		return class_id;
	}
	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}
	public int getYear_id() {
		return year_id;
	}
	public void setYear_id(int year_id) {
		this.year_id = year_id;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	private Student stu;
	private Exam ex;
	private String stu_name, ex_name, course_name;
	public String getStu_name() {
		return stu_name;
	}
	public void setStu_name(String stu_name) {
		this.stu_name = stu_name;
	}
	public String getEx_name() {
		return ex_name;
	}
	public void setEx_name(String ex_name) {
		this.ex_name = ex_name;
	}
	public Student getStu() {
		return stu;
	}
	public Grade(int id, int exam_id, String result, int student_id, String stu_name, String ex_name) {
//		super();
		this.id = id;
		this.exam_id = exam_id;
		this.result = result;
		this.student_id = student_id;
		this.stu_name = stu_name;
		this.ex_name = ex_name;
	}
	public Grade(int exam_id, String result, int student_id, Student stu, Exam ex, String stu_name, String ex_name) {
//		super();
		this.exam_id = exam_id;
		this.result = result;
		this.student_id = student_id;
		this.stu = stu;
		this.ex = ex;
		this.stu_name = stu_name;
		this.ex_name = ex_name;
	}
	public Grade(int id, int exam_id, String result, int student_id, Student stu, Exam ex, String stu_name,
			String ex_name) {
//		super();
		this.id = id;
		this.exam_id = exam_id;
		this.result = result;
		this.student_id = student_id;
		this.stu = stu;
		this.ex = ex;
		this.stu_name = stu_name;
		this.ex_name = ex_name;
	}
	public void setStu(Student stu) {
		this.stu = stu;
	}
	public Exam getEx() {
		return ex;
	}
	public void setEx(Exam ex) {
		this.ex = ex;
	}
	public Grade(String result, Student stu, Exam ex) {
//		super();
		this.result = result;
		this.stu = stu;
		this.ex = ex;
		this.stu_name = stu.getName();
		this.ex_name = ex.getName();
	}
	public Grade(int id, String result, Student stu, Exam ex) {
		super();
		this.id = id;
		this.result = result;
		this.stu = stu;
		this.ex = ex;
		this.stu_name = stu.getName();
		this.ex_name = ex.getName();
	}
	public Grade(int id, int exam_id, String result, int student_id) {
		this.id = id;
		this.exam_id = exam_id;
		this.result = result;
		this.student_id = student_id;
	}
	public Grade(int exam_id, String result, int student_id) {
		this.exam_id = exam_id;
		this.result = result;
		this.student_id = student_id;
	}
	public Grade() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getExam_id() {
		return exam_id;
	}
	public void setExam_id(int exam_id) {
		this.exam_id = exam_id;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getStudent_id() {
		return student_id;
	}
	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}
	
	
}
