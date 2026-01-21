package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.DBConnection;

public class InstructorCourseDAO {
	public static int getCourseCountByInstructor(int instructorId) {
	    int count = 0;
	    String query = "SELECT COUNT(course_id) AS course_count FROM instructorCourses WHERE instructor_id = ?";

	    try (Connection conn = DBConnection.connect();
	         PreparedStatement pstmt = conn.prepareStatement(query)) {
	        
	        pstmt.setInt(1, instructorId);
	        
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                count = rs.getInt("course_count");
	            }
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        System.err.println("Error fetching course count: " + e.getMessage());
	        // We don't throw the exception here as per your previous preference
	    }
	    
	    return count;
	}
	public static ArrayList<InstructorCourse> getAllInstructorCourse() {
		ArrayList<InstructorCourse> gLst = new ArrayList<InstructorCourse>();
		try {
			Connection con = DBConnection.connect();
			String sql = "select * from instructorcourses";
			Statement ps = con.createStatement();
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()) {
				int id=rs.getInt("id");
				int instructor_id = rs.getInt("instructor_id");
				int course_id = rs.getInt("course_id");
				gLst.add(new InstructorCourse(id, instructor_id, course_id));
			}
			return gLst;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean addInstructorCourse(InstructorCourseDetail obj) {
		try {
			Connection con = DBConnection.connect();
			String sql = "insert into instructorcourses (instructor_id, course_id) values (?,?)";
			ArrayList<Course> cLst = new ArrayList<>();
			cLst.add(obj.getCourse_1());
			cLst.add(obj.getCourse_2());
			cLst.add(obj.getCourse_3());
			
			try(PreparedStatement insertSql = con.prepareStatement(sql)){
					for(Course c: cLst) {
						if(c!=null) {
							insertSql.setInt(1, obj.getInst().getId());
							insertSql.setInt(2, c.getId());
							insertSql.addBatch();
						}
					}
					insertSql.executeBatch();
				}
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
		
	}
	public static boolean updateInstructorCourse(Instructor i, Course old, Course n) {
		try {
			Connection con = DBConnection.connect();
			String sql = "update instructorcourses set instructor_id=?, course_id=? where instructor_id=? and course_id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, i.getId());
			ps.setInt(2, n.getId());
			ps.setInt(3, i.getId());
			ps.setInt(4, old.getId());
			int rows = ps.executeUpdate();
			if(rows > 0) 
				return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;	
	}
	public static boolean updateInstructorCourse(Instructor i, Course old, Course n,Course old1, Course n1) {
		try {
			Connection con = DBConnection.connect();
			String sql = "update instructorcourses set instructor_id=?, course_id=? where instructor_id=? and course_id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, i.getId());
			ps.setInt(2, n.getId());
			ps.setInt(3, i.getId());
			ps.setInt(4, old.getId());
			int rows = ps.executeUpdate();
			if(rows > 0) 
				return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;	
	}
	public static boolean updateInstructorCourse(Instructor i, Course n, Course n1, Course n2) {
		ArrayList<Course> cLst = new ArrayList<>();
		cLst.add(n);
		cLst.add(n1);
		cLst.add(n2);

		Connection con=null;
		
		try {

			con = DBConnection.connect();
			con.setAutoCommit(false);
			String sql = "DELETE FROM instructorcourses WHERE instructor_id = ? ";
			try(PreparedStatement ps = con.prepareStatement(sql)){
				ps.setInt(1, i.getId());
				ps.executeUpdate();
			}
			String sql1 ="INSERT INTO instructorcourses (instructor_id, course_id) VALUES (?, ?)";
			try(PreparedStatement insertSql = con.prepareStatement(sql1)){
					for(Course c: cLst) {
						if(c!=null) {
							insertSql.setInt(1, i.getId());
							insertSql.setInt(2, c.getId());
							insertSql.addBatch();
						}
					}
					insertSql.executeBatch();
				}
			con.commit();
			System.out.println("Update Successful");
			return true;
			}
				
		 catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			 if(con!=null) {
				 try {
					 con.rollback();
					 System.err.println("Transaction rolled back due to error: "+e.getMessage());
				 }
				 catch(SQLException ex) {
					 ex.printStackTrace();
				 }
			 }
		}finally {
			try {
				if(con!=null) {
					con.setAutoCommit(true);
					con.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		return false;	
	}

	
	public static boolean deleteInstructorCourse(int id) {
		String sql = "delete from instructorcourses where(id=?)";
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
	
	public static ArrayList<InstructorCourse> getInstructorCourseById(int id) {
		ArrayList<InstructorCourse> gLst = getAllInstructorCourse();
		ArrayList<InstructorCourse> result = new ArrayList<InstructorCourse>();	
		for(InstructorCourse e: gLst) {
			String str_id = String.valueOf(e.getId());
			String str_id_1 = String.valueOf(id);
			if(str_id.contains(str_id_1)) {
				result.add(e);
			}
		}
		return result;
	}
//	instructor_id, course_id
	public static ArrayList<InstructorCourseDetail> getInstructorCourseDetailsByInstructor(){
		ArrayList<InstructorCourseDetail> gLst = new ArrayList<>();
		String sql = "SELECT "
				+ "    i.id, "
				+ "    GROUP_CONCAT(c.id SEPARATOR ',') AS all_courses "
				+ "FROM instructors i "
				+ "JOIN instructorcourses ic ON i.id = ic.instructor_id "
				+ "JOIN courses c ON ic.course_id = c.id "
				+ "GROUP BY i.id;";
		try {
			Connection con = DBConnection.connect();
			if(con!=null) {
				Statement sm = con.createStatement();
				ResultSet rs = sm.executeQuery(sql);
				while(rs.next()) {
					int instructor_id = rs.getInt("id");
					String courses = rs.getString("all_courses");
					gLst.add(new InstructorCourseDetail(instructor_id, courses));
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
