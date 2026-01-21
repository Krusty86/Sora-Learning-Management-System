package models;

public class Details{
	private String name;
	private int stu_count, assign_count;
	private double avg_grade;
	private String major_name;
	public Details() {
		super();
	}
	public String getMajor_name() {
		return major_name;
	}
	public void setMajor_name(String major_name) {
		this.major_name = major_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStu_count() {
		return stu_count;
	}
	public void setStu_count(int stu_count) {
		this.stu_count = stu_count;
	}
	public int getAssign_count() {
		return assign_count;
	}
	public void setAssign_count(int assign_count) {
		this.assign_count = assign_count;
	}
	public double getAvg_grade() {
		return avg_grade;
	}
	public void setAvg_grade(double avg_grade) {
		this.avg_grade = avg_grade;
	}
	public Details(String name, int stu_count, int assign_count, double avg_grade) {
		super();
		this.name = name;
		this.stu_count = stu_count;
		this.assign_count = assign_count;
		this.avg_grade = avg_grade;
	}
	
}
