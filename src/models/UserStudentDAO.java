package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.DBConnection;

public class UserStudentDAO {
	public static ArrayList<UserStudents> getAllUserStudents() {
		ArrayList<UserStudents> uLst = new ArrayList<UserStudents>();
		try {
			Connection con = DBConnection.connect();
			String sql = "select * from UserStudents";
			Statement ps = con.createStatement();
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()) {
				int id=rs.getInt("id");
				int stu_id = rs.getInt("stu_id");
				int user_id = rs.getInt("user_id");
				Student student = StudentDAO.getStudentById(stu_id).get(0);
				Admin user = AdminDAO.getUserById(user_id).get(0);
				uLst.add(new UserStudents(id, stu_id, user_id, student, user));
			}
			return uLst;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static Student getByUserID(int id) {
		try(Connection con = DBConnection.connect()){
			String sql = "select stu_id from userstudents where user_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			Student result = new Student();
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int inst_id = rs.getInt("stu_id");
				result.setId(inst_id);
			}
			return result;
		}catch(Exception e) {
			e.printStackTrace();
		}return null;
	}
	public static boolean addUserStudents(UserStudents obj) {
		try {
			Connection con = DBConnection.connect();
			Admin userObj = new Admin(obj.getUsername(), obj.getPwd(), obj.getRole());
			if(AdminDAO.addUser(userObj)) {
				userObj = AdminDAO.getUserByUserName(userObj.getUsername());
				System.out.println("Inside userstudent creation "+userObj.getId()+"\nUsername"+ userObj.getUsername());
				if(userObj.getUsername().equals(obj.getUsername()) && userObj.getPassword().equals(obj.getPwd())) {
					String sql = "insert into userstudents (stu_id, user_id) values (?,?)";
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setInt(1,obj.getStu_id());
					ps.setInt(2, userObj.getId());
					int rows = ps.executeUpdate();
					if(rows > 0) 
						return true;
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static boolean updateUserStudents(UserStudents obj) {
		try(Connection con = DBConnection.connect()) {
			
			String sql = "UPDATE users u "
					+ "JOIN userstudents ui ON u.id = ui.user_id "
					+ "SET u.username = ?, "
					+ "    u.password = ? "
					+ "WHERE ui.id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,obj.getUsername());
			ps.setString(2,obj.getPwd());
			ps.setInt(3, obj.getId());
			int rows = ps.executeUpdate();
			if(rows > 0) 
				return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static boolean deleteUserStudents(int id) {
		String sql = "delete from UserStudents where(id=?)";
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
	public static ArrayList<UserStudents> getUserById(int id){
		ArrayList<UserStudents> uLst = getAllUserStudents();
		ArrayList<UserStudents> result = new ArrayList<UserStudents>();
		for(UserStudents u: uLst) {
			String str_id = String.valueOf(u.getId());
			String str_id_1 = String.valueOf(id);
			if(str_id.contains(str_id_1)) {
				result.add(u);
			}
		}
		return result;
	}
	public static ArrayList<UserStudents> searchUserStudents(String keyword){
		ArrayList<UserStudents> gLst = new ArrayList<>();
		String sql = "select * from UserStudents where user_id like ? or Stu_id like";
		try {
			Connection con = DBConnection.connect();
			if(con!=null) {
				PreparedStatement ps = con.prepareStatement(sql);
				keyword="%"+keyword+"%";
				ps.setString(1, keyword);
				ps.setString(2, keyword);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					int id = rs.getInt("id");
					int stu_id = rs.getInt("stu_id");
					int user_id = rs.getInt("user_id");
					Student student = StudentDAO.getStudentById(stu_id).get(0);
					Admin user = AdminDAO.getUserById(user_id).get(0);
					gLst.add(new UserStudents(id, stu_id, user_id, student, user));
					
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
