package models;

import java.sql.*;

import application.DBConnection;

public class InstructorStatsDAO {
	 public static InstructorStats getInstructorDashboardStats(int instructorId) {
	        InstructorStats stats = new InstructorStats();
	        String sql = "SELECT " +
	                     "  (SELECT COUNT(DISTINCT e.student_id) " +
	                     "   FROM enrollments e " +
	                     "   JOIN courses c ON e.major_id = c.major_id " +
	                     "   JOIN instructorcourses ic2 ON c.id = ic2.course_id " +
	                     "   WHERE ic2.instructor_id = ic.instructor_id) AS total_students, " +
	                     "  COUNT(DISTINCT ic.course_id) AS total_courses, " +
	                     "  (SELECT COUNT(*) " +
	                     "   FROM exams ex " +
	                     "   JOIN instructorcourses ic3 ON ex.course_id = ic3.course_id " +
	                     "   LEFT JOIN grades gr ON ex.id = gr.exam_id " +
	                     "   WHERE ic3.instructor_id = ic.instructor_id AND gr.id IS NULL) AS ungraded_count " +
	                     "FROM instructorcourses ic " +
	                     "WHERE ic.instructor_id = ? " +
	                     "GROUP BY ic.instructor_id";

	        try (Connection con = DBConnection.connect();
	             PreparedStatement ps = con.prepareStatement(sql)) {
	            
	            ps.setInt(1, instructorId);
	            ResultSet rs = ps.executeQuery();
	            
	            if (rs.next()) {
	                stats.setStu_count(rs.getInt("total_students"));
	                stats.setCourse_count(rs.getInt("total_courses"));
	                stats.setUngraded_count(rs.getInt("ungraded_count"));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return stats;
	    }
}
