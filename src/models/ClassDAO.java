package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.DBConnection;

public class ClassDAO {
	public static boolean addClass(Class obj) {
		try {
			Connection con = DBConnection.connect();
			String sql = "insert into classes (name, mjr_id) values (?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, obj.getName());
			ps.setInt(2, obj.getMajor_id());
			int rows = ps.executeUpdate();
			if(rows > 0) 
				return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static ArrayList<Class> getAllClasses() {
		ArrayList<Class> sLst = new ArrayList<Class>();
		
		try (Connection con = DBConnection.connect()){
			
			String sql = "select c.*, m.id as mjr_id, m.name as mjr_name from classes as c join majors as m on c.mjr_id = m.id";
			Statement ps = con.createStatement();
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()) {
				int id=rs.getInt("id");
				String name = rs.getString("name");
				String mjr_name = rs.getString("mjr_name");
				int mjr_id = rs.getInt("mjr_id");
				
				sLst.add(new Class(id, name, mjr_name, mjr_id));
			}
			return sLst;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static ArrayList<Class> getClassById(int id) {
		ArrayList<Class> eLst = getAllClasses();
		ArrayList<Class> result = new ArrayList<Class>();
		for(Class e:eLst) {
			String str_id = String.valueOf(e.getId());
			String str_id_1 = String.valueOf(id);
			if(str_id.contains(str_id_1)) {
				result.add(e);
			}
		}
		return result;
	}
	public static boolean updateClass(Class obj) {
        try (Connection con = DBConnection.connect()) {
            String sql = "UPDATE classes SET name = ?, mjr_id = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, obj.getName());
            ps.setInt(2, obj.getMajor_id());
            ps.setInt(3, obj.getId());
            
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteClass(int id) {
        try (Connection con = DBConnection.connect()) {
            String sql = "DELETE FROM classes WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static ArrayList<Class> getClassesByMajorId(int majorId) {
        ArrayList<Class> sLst = new ArrayList<Class>();
        // SQL uses a WHERE clause to filter by major_id
        String sql = "SELECT c.*, m.name as mjr_name " +
                     "FROM classes c " +
                     "JOIN majors m ON c.mjr_id = m.id " +
                     "WHERE c.mjr_id = ?";

        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, majorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String mjr_name = rs.getString("mjr_name");
                int mjr_id = rs.getInt("mjr_id");

                sLst.add(new Class(id, name, mjr_name, mjr_id));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return sLst;
    }
}
