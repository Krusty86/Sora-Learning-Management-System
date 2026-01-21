package models;

import java.sql.*;
import java.util.ArrayList;

import application.DBConnection;

public class InstructorCourseDetailDAO {

	public static ArrayList<Details> getInstructorCourseLoad(int instructorId) {
	    ArrayList<Details> list = new ArrayList<>();
	    String sql = "SELECT c.name, m.name as major_name, COUNT(e.id) AS student_count " +
	                 "FROM instructorcourses ic " +
	                 "JOIN courses c ON ic.course_id = c.id " +
	                 "JOIN majors m ON c.major_id = m.id " +
	                 "LEFT JOIN enrollments e ON m.id = e.major_id " +
	                 "WHERE ic.instructor_id = ? " +
	                 "GROUP BY c.id, c.name, m.name";

	    try (Connection con = DBConnection.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setInt(1, instructorId);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Details d = new Details();
	            d.setName(rs.getString("name"));
	            d.setMajor_name(rs.getString("major_name"));
	            d.setStu_count(rs.getInt("student_count"));
	        	list.add(d);
	            
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}

	public static ArrayList<Details> getInstructorStats(int instructorId) {
	    ArrayList<Details> list = new ArrayList<>();
	    
	    String sql = "SELECT c.name, " +
	                 "(SELECT COUNT(*) FROM enrollments e WHERE e.major_id = c.major_id) AS stu_count, " +
	                 "(SELECT COUNT(DISTINCT a.title) FROM assignments a WHERE a.course_id = c.id) AS assign_count, " +
	                 "ROUND(AVG(g.result), 2) AS avg_grade " +
	                 "FROM courses c " +
	                 "JOIN instructorcourses ic ON c.id = ic.course_id " +
	                 "LEFT JOIN exams ex ON c.id = ex.course_id " +
	                 "LEFT JOIN grades g ON ex.id = g.exam_id " +
	                 "WHERE ic.instructor_id = ? " +
	                 "GROUP BY c.id, c.name, c.major_id";

	    try (Connection con = DBConnection.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setInt(1, instructorId);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	        	Details obj = new Details(rs.getString("name"), rs.getInt("stu_count"),rs.getInt("assign_count"),rs.getDouble("avg_grade"));
	        	list.add(obj);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}
}

