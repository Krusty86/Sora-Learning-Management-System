package models;

import java.sql.*;
import java.util.ArrayList;

import application.DBConnection;

public class ExamTypeDAO {
	public static ArrayList<ExamType> getAll() {
	    ArrayList<ExamType> examTypeList = new ArrayList<>();
	    String query = "SELECT id, name FROM examTypes";
	    
	    // Using try-with-resources to automatically close ResultSet and PreparedStatement
	    try (Connection conn = DBConnection.connect();
	         PreparedStatement pstmt = conn.prepareStatement(query);
	         ResultSet rs = pstmt.executeQuery()) {
	        
	        while (rs.next()) {
	            // Create a new ExamType object for each row
	            int id = rs.getInt("id");
	            String name = rs.getString("name");
	            
	            ExamType type = new ExamType(name, id);
	            examTypeList.add(type);
	        }
	        
	    } catch (SQLException | ClassNotFoundException e) {
	        System.err.println("Error fetching exam types: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return examTypeList;
	}
}
