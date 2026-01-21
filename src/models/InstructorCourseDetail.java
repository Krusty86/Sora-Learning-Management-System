package models;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class InstructorCourseDetail {
	private Instructor inst;
	private Course course_1, course_2, course_3;
	private String c1Name, c2Name, c3Name, instName;
	private int inst_id;
	private String courses;
	private int stuCountCourse_1, stuCountCourse_2, stuCountCourse_3;
	private int assignCountCourse_1, assignCountCourse_2, assignCountCourse_3;
	private double gradeAvergeCourse_1, gradeAvergeCourse_2, gradeAvergeCourse_3;

	public int getStuCountCourse_1() {
		return stuCountCourse_1;
	}

	public void setStuCountCourse_1(int stuCountCourse_1) {
		this.stuCountCourse_1 = stuCountCourse_1;
	}

	public int getStuCountCourse_2() {
		return stuCountCourse_2;
	}

	public InstructorCourseDetail(String c1Name, String c2Name, String c3Name, int stuCountCourse_1,
			int stuCountCourse_2, int stuCountCourse_3, int assignCountCourse_1, int assignCountCourse_2,
			int assignCountCourse_3, double gradeAvergeCourse_1, double gradeAvergeCourse_2,
			double gradeAvergeCourse_3) {
		super();
		this.c1Name = c1Name;
		this.c2Name = c2Name;
		this.c3Name = c3Name;
		this.stuCountCourse_1 = stuCountCourse_1;
		this.stuCountCourse_2 = stuCountCourse_2;
		this.stuCountCourse_3 = stuCountCourse_3;
		this.assignCountCourse_1 = assignCountCourse_1;
		this.assignCountCourse_2 = assignCountCourse_2;
		this.assignCountCourse_3 = assignCountCourse_3;
		this.gradeAvergeCourse_1 = gradeAvergeCourse_1;
		this.gradeAvergeCourse_2 = gradeAvergeCourse_2;
		this.gradeAvergeCourse_3 = gradeAvergeCourse_3;
	}

	public InstructorCourseDetail() {
		super();
	}

	public void setStuCountCourse_2(int stuCountCourse_2) {
		this.stuCountCourse_2 = stuCountCourse_2;
	}

	public int getStuCountCourse_3() {
		return stuCountCourse_3;
	}

	public void setStuCountCourse_3(int stuCountCourse_3) {
		this.stuCountCourse_3 = stuCountCourse_3;
	}

	public int getAssignCountCourse_1() {
		return assignCountCourse_1;
	}

	public void setAssignCountCourse_1(int assignCountCourse_1) {
		this.assignCountCourse_1 = assignCountCourse_1;
	}

	public int getAssignCountCourse_2() {
		return assignCountCourse_2;
	}

	public void setAssignCountCourse_2(int assignCountCourse_2) {
		this.assignCountCourse_2 = assignCountCourse_2;
	}

	public int getAssignCountCourse_3() {
		return assignCountCourse_3;
	}

	public void setAssignCountCourse_3(int assignCountCourse_3) {
		this.assignCountCourse_3 = assignCountCourse_3;
	}

	public double getGradeAvergeCourse_1() {
		return gradeAvergeCourse_1;
	}

	public void setGradeAvergeCourse_1(double gradeAvergeCourse_1) {
		this.gradeAvergeCourse_1 = gradeAvergeCourse_1;
	}

	public double getGradeAvergeCourse_2() {
		return gradeAvergeCourse_2;
	}

	public void setGradeAvergeCourse_2(double gradeAvergeCourse_2) {
		this.gradeAvergeCourse_2 = gradeAvergeCourse_2;
	}

	public double getGradeAvergeCourse_3() {
		return gradeAvergeCourse_3;
	}

	public void setGradeAvergeCourse_3(double gradeAvergeCourse_3) {
		this.gradeAvergeCourse_3 = gradeAvergeCourse_3;
	}

	public int getInst_id() {
		return inst_id;
	}

	public void setInst_id(int inst_id) {
		this.inst_id = inst_id;
	}

	public String getCourses() {
		return courses;
	}

	public void setCourses(String courses) {
		this.courses = courses;
	}

	public InstructorCourseDetail(int inst_id, String courses) {
//		super();
		this.inst_id = inst_id;
		this.courses = courses;
	}

	public InstructorCourseDetail(Instructor inst, Course course_1, Course course_2, Course course_3) {
		super();
		this.inst = inst;
		this.course_1 = course_1;
		this.course_2 = course_2;
		this.course_3 = course_3;
	}

	public InstructorCourseDetail(Course course_1, Course course_2) {
//		super();
		this.course_1 = course_1;
		this.course_2 = course_2;
	}

	public InstructorCourseDetail(Course course_1) {
//		super();
		this.course_1 = course_1;
	}

	public InstructorCourseDetail(Course course_1, Course course_2, Course course_3) {
//		super();
		this.course_1 = course_1;
		this.course_2 = course_2;
		this.course_3 = course_3;
	}

	public Course getCourse_1() {
		return course_1;
	}

	public void setCourse_1(Course course_1) {
		this.course_1 = course_1;
		this.c1Name = course_1.getName();
	}

	public Course getCourse_2() {
		return course_2;
	}

	public void setCourse_2(Course course_2) {
		this.course_2 = course_2;
		this.c2Name = course_2.getName();
	}

	public Course getCourse_3() {
		return course_3;
	}

	public Instructor getInst() {
		return inst;
	}

	public void setInst(Instructor inst) {
		this.inst = inst;
		this.instName = inst.getName();
	}

	public String getC1Name() {
		return c1Name;
	}

	public void setC1Name(String c1Name) {
		this.c1Name = c1Name;
	}

	public String getC2Name() {
		return c2Name;
	}

	public void setC2Name(String c2Name) {
		this.c2Name = c2Name;
	}

	public String getC3Name() {
		return c3Name;
	}

	public void setC3Name(String c3Name) {
		this.c3Name = c3Name;
	}

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	public void setCourse_3(Course course_3) {
		this.course_3 = course_3;
		this.c3Name = course_3.getName();
	}
	public InstructorCourseDetail(Instructor inst) {
		super();
		this.inst = inst;
		this.instName = inst.getName();
	}

	public static ArrayList<InstructorCourseDetail> splitCourse(ArrayList<InstructorCourseDetail> elst){
		ArrayList<InstructorCourseDetail> resultlst = new ArrayList<>();
		String[] cLst = new String[4];
		int count;
		for(InstructorCourseDetail e: elst) {
			Instructor ins = InstructorDAO.getInstructorById(e.getInst_id()).get(0);
			InstructorCourseDetail icdTmp = new InstructorCourseDetail(ins);
			String[] tmp = e.getCourses().split(",");
			System.out.println(tmp);
			count=1;
			for(String s: tmp) {
				System.out.println(s);
				Course c = CourseDAO.getCourseById(Integer.parseInt(s)).get(0);
				String methodName = "setCourse_"+count;
				try {
					Method method = icdTmp.getClass().getMethod(methodName, Course.class);
					method.invoke(icdTmp, c);
					count+=1;
				}catch(Exception ex) {
					break;
				}
			}resultlst.add(icdTmp);
			
		}
		
		return resultlst;
		
	}
	
}
