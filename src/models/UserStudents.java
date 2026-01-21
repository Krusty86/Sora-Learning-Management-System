package models;

public class UserStudents {
	private int id, stu_id, user_id;
	private Student student;
	private Admin user;
	private String name, username, pwd, role="student", email;
	public UserStudents(int id, String username, String pwd) {
		super();
		this.id = id;
		this.username = username;
		this.pwd = pwd;
	}
	public UserStudents(String username, String pwd) {
		super();
		this.username = username;
		this.pwd = pwd;
		this.role = "student";
	}
	public UserStudents(int id, int stu_id, int user_id, Student student, Admin user) {
//		super();
		this.id = id;
		this.stu_id = stu_id;
		this.user_id = user_id;
		this.student = student;
		this.user = user;
		this.name = student.getName();
		this.username = user.getUsername();
		this.pwd = user.getPassword();
		this.email = student.getEmail();
	}
	public UserStudents(int stu_id, int user_id, Student student, Admin user) {
//		super();
		this.stu_id = stu_id;
		this.user_id = user_id;
		this.student = student;
		this.user = user;
		this.name = student.getName();
		this.username = user.getUsername();
		this.pwd = user.getPassword();
		this.email = student.getEmail();
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
	public UserStudents(int stu_id, String name, String username, String pwd) {
//		super();
		this.stu_id = stu_id;
		this.name = name;
		this.username = username;
		this.pwd = pwd;
		this.email = student.getEmail();
	}
	public UserStudents(int id, int stu_id, String name, String username, String pwd) {
//		super();
		this.id = id;
		this.stu_id = stu_id;
		this.name = name;
		this.username = username;
		this.pwd = pwd;
		this.email = student.getEmail();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStu_id() {
		return stu_id;
	}
	public void setStu_id(int stu_id) {
		this.stu_id = stu_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Admin getUser() {
		return user;
	}
	public void setuser(Admin user) {
		this.user = user;
	}
	
	
	
	
}
