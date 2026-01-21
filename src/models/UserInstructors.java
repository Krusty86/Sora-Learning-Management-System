package models;

public class UserInstructors {
	private int id, inst_id, user_id;
	private Instructor instructor;
	private Admin user;
	private String name, username, pwd, role="instructor", email;
	public UserInstructors(int id,String username, String pwd) {
//		super();
		this.id = id;
		this.username = username;
		this.pwd = pwd;
	}
	public UserInstructors(String username, String pwd) {
//		super();
		this.username = username;
		this.pwd = pwd;
	}
	public UserInstructors(int id, int inst_id, int user_id, Instructor instructor, Admin user) {
//		super();
		this.id = id;
		this.inst_id = inst_id;
		this.user_id = user_id;
		this.instructor = instructor;
		this.user = user;
		this.name = instructor.getName();
		this.username = user.getUsername();
		this.pwd = user.getPassword();
		this.email = instructor.getEmail();
	}
	public UserInstructors(int inst_id, int user_id, Instructor instructor, Admin user) {
//		super();
		this.inst_id = inst_id;
		this.user_id = user_id;
		this.instructor = instructor;
		this.user = user;
		this.name = instructor.getName();
		this.username = user.getUsername();
		this.pwd = user.getPassword();
		this.email = instructor.getEmail();
	}
	
	public UserInstructors(int inst_id, String name, String username, String pwd) {
//		super();
		this.inst_id = inst_id;
		this.name = name;
		this.username = username;
		this.pwd = pwd;
		this.email = instructor.getEmail();
	}
	public UserInstructors(int id, int inst_id, String name, String username, String pwd) {
//		super();
		this.id = id;
		this.inst_id = inst_id;
		this.name = name;
		this.username = username;
		this.pwd = pwd;
		this.email = instructor.getEmail();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public void setUser(Admin user) {
		this.user = user;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInst_id() {
		return inst_id;
	}
	public void setInst_id(int inst_id) {
		this.inst_id = inst_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public Instructor getInstructor() {
		return instructor;
	}
	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}
	public Admin getUser() {
		return user;
	}
	public void setuser(Admin user) {
		this.user = user;
	}
	
	
	
	
}
