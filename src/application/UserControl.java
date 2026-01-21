package application;

import models.*;

public class UserControl {
	public static int selectedCourseId;
	public static Student stu;
	public static Instructor inst;
	public static void setUserData(Admin obj) {
		if(obj.getRole().equals("instructor")){
			inst = UserInstructorDAO.getByUserID(obj.getId());
			System.out.println(UserInstructorDAO.getByUserID(obj.getId()).getName());
		}else {
			stu = UserStudentDAO.getByUserID(obj.getId());
		}
	}
	public static Student getStu() {
		return stu;
	}
	public static void setStu(Student stu) {
		UserControl.stu = stu;
	}
	public static Instructor getInst() {
		return inst;
	}
	public static void setInst(Instructor inst) {
		UserControl.inst = inst;
	}
}
