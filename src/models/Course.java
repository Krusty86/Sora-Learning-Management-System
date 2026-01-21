package models;

public class Course {
	private int id;
	private String name;
	private int major_id;
	private String major;
	public Course(String name, int major_id, String major) {
		super();
		this.name = name;
		this.major_id = major_id;
		this.major = major;
	}
	public Course(int id, String name, int major_id, String major) {
		super();
		this.id = id;
		this.name = name;
		this.major_id = major_id;
		this.major = major;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public Course(int id, String name, int major_id) {
		this.id = id;
		this.name = name;
		this.major_id = major_id;
	}
	public Course(String name, int major_id) {
		this.name = name;
		this.major_id = major_id;
	}
	public Course() {
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
	public int getMajor_id() {
		return major_id;
	}
	public void setMajor_id(int major_id) {
		this.major_id = major_id;
	}
	@Override
	public String toString() {
		return getName();
	}
	
	
}
