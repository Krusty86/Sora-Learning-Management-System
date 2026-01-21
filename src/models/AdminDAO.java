package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.DBConnection;

public class AdminDAO {
	public static ArrayList<Admin> getAllUsers() {
		ArrayList<Admin> uLst = new ArrayList<Admin>();
		try {
			Connection con = DBConnection.connect();
			String sql = "select * from users";
			Statement ps = con.createStatement();
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()) {
				int id=rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String role = rs.getString("role");
				uLst.add(new Admin(id, username, password, role));
			}
			return uLst;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean addUser(Admin obj) {
		try {
			Connection con = DBConnection.connect();
			String sql = "insert into users (username, password, role) values (?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, obj.getUsername());
			ps.setString(2, obj.getPassword());
			ps.setString(3, obj.getRole());
			int rows = ps.executeUpdate();
			if(rows > 0) 
				return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static boolean updateUser(Admin obj) {
		try {
			Connection con = DBConnection.connect();
			String sql = "update users set username=?, password=?, role=? where (id=?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, obj.getUsername());
			ps.setString(2, obj.getPassword());
			ps.setString(3, obj.getRole());
			ps.setInt(4, obj.getId());
			int rows = ps.executeUpdate();
			if(rows > 0) 
				return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static boolean deleteUser(int id) {
		String sql = "delete from users where(id=?)";
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
	public static ArrayList<Admin> getUserById(int id){
		ArrayList<Admin> uLst = getAllUsers();
		ArrayList<Admin> result = new ArrayList<Admin>();
		for(Admin u: uLst) {
			String str_id = String.valueOf(u.getId());
			String str_id_1 = String.valueOf(id);
			if(str_id.contains(str_id_1)) {
				result.add(u);
			}
		}
		return result;
	}
	public static Admin getUser(String username, String pwd) {
		try(Connection con = DBConnection.connect()){
			Admin result;
			String sql = "select * from users where username like ? and password like?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, pwd);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				int id=rs.getInt("id");
				String user = rs.getString("username");
				String password = rs.getString("password");
				String role = rs.getString("role");
				result = new Admin(id, username, password, role);
				return result;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}return null;
	}
	public static ArrayList<Admin> searchUsers(String keyword){
		ArrayList<Admin> gLst = new ArrayList<>();
		String sql = "select * from users where username like ? or password like ? or role like ?";
		try {
			Connection con = DBConnection.connect();
			if(con!=null) {
				PreparedStatement ps = con.prepareStatement(sql);
				
				ps.setString(1, keyword);
				ps.setString(2, keyword);
				ps.setString(3, keyword);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					int id = rs.getInt("id");
					String username = rs.getString("username");
					String password = rs.getString("password");
					String role = rs.getString("role");
					Admin a = new Admin(id, username, password, role);
					gLst.add(a);
				}
				return gLst;
			}
			
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return gLst;
	}
	public static Admin getUserByUserName(String keyword){
		String sql = "select * from users where username = ?";
		try {
			Connection con = DBConnection.connect();
			if(con!=null) {
				PreparedStatement ps = con.prepareStatement(sql);
				
				ps.setString(1, keyword);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					int id = rs.getInt("id");
					String username = rs.getString("username");
					String password = rs.getString("password");
					String role = rs.getString("role");
					Admin a = new Admin(id, username, password, role);
					return a;
				}
			}
			
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
}
