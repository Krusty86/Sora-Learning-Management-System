package models;

import java.sql.*;
import java.util.*;

import application.DBConnection;
import models.*;

public class AssignmentDetailsDAO {
	public static ArrayList<AssignmentDetails> getFilteredAssignmentDetails(String keyword) {
	    ArrayList<AssignmentDetails> list = new ArrayList<>();
	    
	    // SQL joins assignments with courses to access course names
	    String sql = "SELECT a.id, a.title, a.due_date, c.name AS course_name, a.course_id " +
	                 "FROM assignments a " +
	                 "JOIN courses c ON a.course_id = c.id " +
	                 "WHERE a.title LIKE ? OR a.due_date LIKE ? OR c.name LIKE ? " +
	                 "ORDER BY a.due_date ASC";

	    try (Connection con = DBConnection.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        String pattern = "%" + keyword + "%";
	        ps.setString(1, pattern);
	        ps.setString(2, pattern);
	        ps.setString(3, pattern);

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            list.add(new AssignmentDetails(
	                rs.getString("title"),
	                rs.getString("due_date"),
	                rs.getString("course_name")
	            ));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	public static ArrayList<AssignmentDetails> getAllAssignmentReports() {
	    ArrayList<AssignmentDetails> list = new ArrayList<>();
	    // Removed WHERE clause to show all courses
	    String sql = "SELECT a.title, a.due_date, c.name, " +
	                 "SUM(CASE WHEN a.status = 'true' THEN 1 ELSE 0 END) AS submitted, " +
	                 "COUNT(*) AS total " +
	                 "FROM assignments a " +
	                 "JOIN courses c ON a.course_id = c.id " +
	                 "GROUP BY c.id, a.title, a.due_date, c.name " + 
	                 "ORDER BY c.name ASC, a.due_date ASC"; 
	    try (Connection con = DBConnection.connect();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) { 

	        while (rs.next()) {
	            list.add(new AssignmentDetails(
	                rs.getString("title"),
	                rs.getString("due_date"),
	                rs.getString("name"),
	                rs.getInt("submitted"),
	                rs.getInt("total")
	            ));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	public static ArrayList<Assignment> getAssignmentsWithCourse(int studentId) {
	    ArrayList<Assignment> list = new ArrayList<>();
	    String sql = "SELECT a.id, c.name, a.title, a.due_date, a.status, a.grade " +
	                 "FROM assignments a " +
	                 "JOIN courses c ON a.course_id = c.id " +
	                 "WHERE a.student_id = ?";

	    try (Connection con = DBConnection.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setInt(1, studentId);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Assignment asgn = new Assignment();
	            asgn.setId(rs.getInt("id"));
	            asgn.setCourse(rs.getString("name"));
	            asgn.setTitle(rs.getString("title"));
	            asgn.setDue_date(rs.getString("due_date"));
	            asgn.setStatus(rs.getString("status"));
	            asgn.setGrade(rs.getString("grade"));
	            
	            list.add(asgn);
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	public static ArrayList<AssignmentDetails> getAssignmentReport(int courseId) {
	    ArrayList<AssignmentDetails> list = new ArrayList<>();
	    String sql = "SELECT a.title, a.due_date, c.name, " +
	                 "SUM(CASE WHEN a.status = 'true' THEN 1 ELSE 0 END) AS submitted, " +
	                 "COUNT(*) AS total " +
	                 "FROM assignments a " +
	                 "JOIN courses c ON a.course_id = c.id " +
	                 "WHERE a.course_id = ? " +
	                 "GROUP BY a.title, a.due_date, c.name, c.id";

	    try (Connection con = DBConnection.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setInt(1, courseId);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            list.add(new AssignmentDetails(
	                rs.getString("title"),
	                rs.getString("due_date"),
	                rs.getString("name"),
	                rs.getInt("submitted"),
	                rs.getInt("total")
	            ));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}
}
