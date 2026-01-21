package models;

public class Class {
	private int id;
	private String name;
	private String major_name;
	private int major_id;

	public Class(int id, String name, String major_name, int major_id) {
		super();
		this.id = id;
		this.name = name;
		this.major_name = major_name;
		this.major_id = major_id;
	}

	public String getMajor_name() {
		return major_name;
	}

	public Class(String name, int major_id) {
		super();
		this.name = name;
		this.major_id = major_id;
	}

	public void setMajor_name(String major_name) {
		this.major_name = major_name;
	}

	public int getMajor_id() {
		return major_id;
	}

	public void setMajor_id(int major_id) {
		this.major_id = major_id;
	}

	public Class(int id, String name) {
//		super();
		this.id = id;
		this.name = name;
	}

	public Class() {
		
	}

	public Class(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return getName();
	}
	
	
}
