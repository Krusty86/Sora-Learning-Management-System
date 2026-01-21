package models;

public class Year {
	private int id;
	private String name;
	public Year(int id, String name) {
//		super();
		this.id = id;
		this.name = name;
	}
	public Year(String name) {
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
