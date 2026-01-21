package models;

import java.sql.*;
import java.util.*;

import application.DBConnection;

public class CourseDetailsDAO {
	public static ArrayList<CourseDetails> getCourseStats() {
	    ArrayList<CourseDetails> list = new ArrayList<>();
	    
	    // Updated SQL: added c.id to the GROUP BY clause
	    String sql = "SELECT c.name, i.name, m.name, y.name,m.id,  COUNT(e.id) AS total, c.id " +
	                 "FROM courses c " +
	                 "JOIN instructorcourses ic ON c.id = ic.course_id " +
	                 "JOIN instructors i ON ic.instructor_id = i.id " +
	                 "JOIN majors m ON c.major_id = m.id " +
	                 "JOIN years y ON m.year_id = y.id " +
	                 "LEFT JOIN enrollments e ON e.major_id = m.id " +
	                 "GROUP BY c.id, c.name, i.name, m.name, m.id, y.name"; // Included c.id here

	    try (Connection con = DBConnection.connect();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            list.add(new CourseDetails(
	                rs.getString(1), // course_name
	                rs.getString(2), // instructor_name
	                rs.getString(3), // major_name
	                rs.getString(4), // year_name
	                rs.getInt(6),    // student_count
	                rs.getInt(7),     // course_id
	                rs.getInt(5)
	            ));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	public static ArrayList<CourseDetails> getFilteredCourseDetails(String keyword) {
	    ArrayList<CourseDetails> list = new ArrayList<>();
	    
	    // We use the same JOIN logic as getCourseStats but add a WHERE clause
	    String sql = "SELECT c.name, i.name, m.name, y.name, m.id, COUNT(e.id) AS total, c.id " +
	                 "FROM courses c " +
	                 "JOIN instructorcourses ic ON c.id = ic.course_id " +
	                 "JOIN instructors i ON ic.instructor_id = i.id " +
	                 "JOIN majors m ON c.major_id = m.id " +
	                 "JOIN years y ON m.year_id = y.id " +
	                 "LEFT JOIN enrollments e ON e.major_id = m.id " +
	                 "WHERE c.name LIKE ? OR i.name LIKE ? OR m.name LIKE ? OR y.name LIKE ? " +
	                 "GROUP BY c.id, c.name, i.name, m.name, m.id, y.name";

	    try (Connection con = DBConnection.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        String pattern = "%" + keyword + "%";
	        ps.setString(1, pattern);
	        ps.setString(2, pattern);
	        ps.setString(3, pattern);
	        ps.setString(4, pattern);

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            list.add(new CourseDetails(
	                rs.getString(1), // course_name
	                rs.getString(2), // instructor_name
	                rs.getString(3), // major_name
	                rs.getString(4), // year_name
	                rs.getInt(6),    // student_count
	                rs.getInt(7),    // course_id
	                rs.getInt(5)     // major_id
	            ));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}
}
