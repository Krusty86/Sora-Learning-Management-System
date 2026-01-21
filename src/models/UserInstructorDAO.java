package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.DBConnection;

public class UserInstructorDAO {
	public static ArrayList<UserInstructors> getAllUserInstructors() {
		ArrayList<UserInstructors> uLst = new ArrayList<UserInstructors>();
		try {
			Connection con = DBConnection.connect();
			String sql = "select * from UserInstructors";
			Statement ps = con.createStatement();
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()) {
				int id=rs.getInt("id");
				int inst_id = rs.getInt("inst_id");
				int user_id = rs.getInt("user_id");
				Instructor instructor = InstructorDAO.getInstructorById(inst_id).get(0);
				Admin user = AdminDAO.getUserById(user_id).get(0);
				uLst.add(new UserInstructors(id, inst_id, user_id, instructor, user));
			}
			return uLst;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean addUserInstructors(UserInstructors obj) {
		try {
			Connection con = DBConnection.connect();
			Admin userObj = new Admin(obj.getUsername(), obj.getPwd(), obj.getRole());
			if(AdminDAO.addUser(userObj)) {
				userObj = AdminDAO.getUserByUserName(userObj.getUsername());
				if(userObj.getUsername().equals(obj.getUsername()) && userObj.getPassword().equals(obj.getPwd())) {
					String sql = "insert into UserInstructors (inst_id, user_id) values (?,?)";
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setInt(1,obj.getInst_id());
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
	
	public static boolean updateUserInstructors(UserInstructors obj) {
		try(Connection con = DBConnection.connect()) {
			
			String sql = "UPDATE users u "
					+ "JOIN userInstructors ui ON u.id = ui.user_id "
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
	
	public static boolean deleteUserInstructors(int id) {
		String sql = "delete from UserInstructors where(id=?)";
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
	public static ArrayList<UserInstructors> getUserById(int id){
		ArrayList<UserInstructors> uLst = getAllUserInstructors();
		ArrayList<UserInstructors> result = new ArrayList<UserInstructors>();
		for(UserInstructors u: uLst) {
			String str_id = String.valueOf(u.getId());
			String str_id_1 = String.valueOf(id);
			if(str_id.contains(str_id_1)) {
				result.add(u);
			}
		}
		return result;
	}
	public static Instructor getByUserID(int id) {
		try(Connection con = DBConnection.connect()){
			String sql = "select inst_id from userinstructors where user_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			Instructor result = new Instructor();
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int inst_id = rs.getInt("inst_id");
				result.setId(inst_id);
			}
			return result;
		}catch(Exception e) {
			e.printStackTrace();
		}return null;
	}
	public static ArrayList<UserInstructors> searchUserInstructors(String keyword){
		ArrayList<UserInstructors> gLst = new ArrayList<>();
		String sql = "select ui.id, ui.user_id, ui.inst_id from userInstructors ui "+
					"JOIN users u ON ui.user_id = u.id JOIN instructors i ON ui.inst_id = i.id"
				+"where u.username Like ? or i.name like ? i.email like ?"; 
				
				
		try (Connection con = DBConnection.connect()){
			
			if(con!=null) {
				PreparedStatement ps = con.prepareStatement(sql);
				keyword="%"+keyword+"%";
				ps.setString(1, keyword);
				ps.setString(2, keyword);
				ps.setString(2, keyword);
				
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					int id = rs.getInt("id");
					int inst_id = rs.getInt("inst_id");
					int user_id = rs.getInt("user_id");
					Instructor instructor = InstructorDAO.getInstructorById(inst_id).get(0);
					Admin user = AdminDAO.getUserById(user_id).get(0);
					gLst.add(new UserInstructors(id, inst_id, user_id, instructor, user));
					
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
