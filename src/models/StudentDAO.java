package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.DBConnection;

public class StudentDAO {
	
	public static ArrayList<Student> getAllStudents() {
		ArrayList<Student> sLst = new ArrayList<Student>();
		
		try {
			Connection con = DBConnection.connect();
			String sql = "select * from students";
			Statement ps = con.createStatement();
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()) {
				int id=rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String dob = rs.getString("dob");
				String address = rs.getString("address");
				String ph = rs.getString("ph");
				String email = rs.getString("email");
				String nrc = rs.getString("nrc");
				sLst.add(new Student(id, name, age, dob, address, ph, email,nrc));
			}
			return sLst;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean addStudent(Student obj) {
		try {
			Connection con = DBConnection.connect();
			String sql = "insert into students (name, age, dob, address, ph, email,nrc) values (?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, obj.getName());
			ps.setInt(2, obj.getAge());
			ps.setString(3, obj.getDob());
			ps.setString(4, obj.getAddress());
			ps.setString(5, obj.getPh());
			ps.setString(6, obj.getEmail());
			ps.setString(7, obj.getNrc());
			int rows = ps.executeUpdate();
			if(rows > 0) 
				return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static boolean updateStudent(Student obj) {
		try {
			Connection con = DBConnection.connect();
			String sql = "update students set name=?, age=?, address=?, ph=?, email=?, nrc=? where (id=?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, obj.getName());
			ps.setInt(2, obj.getAge());
			ps.setString(3, obj.getAddress());
			ps.setString(4, obj.getPh());
			ps.setString(5, obj.getEmail());
			ps.setString(6, obj.getNrc());
			ps.setInt(7, obj.getId());
			int rows = ps.executeUpdate();
			if(rows > 0) 
				return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean deleteStudent(int id) {
		String sql = "delete from students where(id=?)";
		try {
			Connection con = DBConnection.connect();
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, id);
			int rows = ps.executeUpdate();
			if(rows > 0)
				return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static ArrayList<Student> getStudentById(int id) {
		ArrayList<Student> gLst = getAllStudents();
		ArrayList<Student> result = new ArrayList<>();	
		for(Student e: gLst) {
			String str_id = String.valueOf(e.getId());
			String str_id_1 = String.valueOf(id);
			if(str_id.contains(str_id_1)) {
				result.add(e);
			}
		}
		return result;
	}
	
//	name, age, dob, address, ph, email
	public static ArrayList<Student> searchStudent(String keyword){
		ArrayList<Student> gLst = new ArrayList<>();
		String sql = "select * from Students where name like ? or age like ? or dob like ? or address like ? or ph like ? or email like ? or nrc like ?";
		try {
			Connection con = DBConnection.connect();
			if(con!=null) {
				PreparedStatement ps = con.prepareStatement(sql);
				keyword="%"+keyword+"%";
				ps.setString(1, keyword);
				ps.setString(2, keyword);
				ps.setString(3, keyword);
				ps.setString(4, keyword);
				ps.setString(5, keyword);
				ps.setString(6, keyword);
				ps.setString(7, keyword);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					int id=rs.getInt("id");
					String name = rs.getString("name");
					int age = rs.getInt("age");
					String dob = rs.getString("dob");
					String address = rs.getString("address");
					String ph = rs.getString("ph");
					String email = rs.getString("email");
					String nrc = rs.getString("nrc");
					gLst.add(new Student(id, name, age, dob, address, ph, email, nrc));
				}
				return gLst;
			}
			
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return gLst;
	}
}
