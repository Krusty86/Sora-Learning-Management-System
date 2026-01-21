package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.DBConnection;

public class CourseDAO {
	public static ArrayList<Course> getCourseByInstructor(int id) {
	      ArrayList<Course> list = new ArrayList<>();
	      
	      String sql = "SELECT * FROM courses c join instructorcourses ict on c.id = ict.course_id where ict.instructor_id = ?"; 
	      
	      try (Connection con = DBConnection.connect()){
	    		  PreparedStatement smt = con.prepareStatement(sql);
	    		  smt.setInt(1, id);
	    		  ResultSet rs = smt.executeQuery();
	          
	          while (rs.next()) {
	              
	              list.add(new Course(rs.getInt("id"), rs.getString("name"), rs.getInt("major_id")));
	          }
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	      return list;
	  }
	
	public static ArrayList<Course> getStudentCoursesObjects() {
	      ArrayList<Course> list = new ArrayList<>();
	      
	      String sql = "SELECT id, name, major_id FROM courses LIMIT 4"; 
	      
	      try (Connection con = DBConnection.connect();
	           Statement st = con.createStatement();
	           ResultSet rs = st.executeQuery(sql)) {
	          
	          while (rs.next()) {
	              
	              list.add(new Course(rs.getInt("id"), rs.getString("name"), rs.getInt("major_id")));
	          }
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	      return list;
	  }
	public static ArrayList<Course> getAllCourses() {
		ArrayList<Course> cLst = new ArrayList<Course>();
		
		try (Connection con = DBConnection.connect()){
			String sql = "select * from courses";
			Statement ps = con.createStatement();
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()) {
				int id=rs.getInt("id");
				String name = rs.getString("name");
				int major_id = rs.getInt("major_id");
				cLst.add(new Course(id, name, major_id));
			}
			return cLst;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static ArrayList<Course> getCoursesByMajorId(int majorId) {
	    ArrayList<Course> cLst = new ArrayList<Course>();
	    
	    String sql = "SELECT c.id, c.name, c.major_id, m.name AS major_name " +
	                 "FROM courses c " +
	                 "JOIN majors m ON c.major_id = m.id " +
	                 "WHERE c.major_id = ?";
	    
	    try (Connection con = DBConnection.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setInt(1, majorId);
	        ResultSet rs = ps.executeQuery();
	        
	        while(rs.next()) {
	            int id = rs.getInt("id");
	            String name = rs.getString("name");
	            int mId = rs.getInt("major_id");
	            String majorName = rs.getString("major_name");
	            cLst.add(new Course(id, name, mId, majorName));
	        }
	        return cLst;
	        
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	public static ArrayList<Course> getCoursesByMajor(String keyword) {
	    ArrayList<Course> cLst = new ArrayList<Course>();
	    int num1=3, num2=4;
		if(keyword.equals("SE")) {
			num1 = 1;
			num2 = 2;
		}
	    String sql = "SELECT c.id, c.name, c.major_id, m.name AS major_name " +
	                 "FROM courses c " +
	                 "JOIN majors m ON c.major_id = m.id " +
	                 "WHERE c.major_id = ? or c.major_id = ?";
	    
	    try (Connection con = DBConnection.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setInt(1, num1);
	        ps.setInt(2, num2);
	        ResultSet rs = ps.executeQuery();
	        
	        while(rs.next()) {
	            int id = rs.getInt("id");
	            String name = rs.getString("name");
	            int mId = rs.getInt("major_id");
	            String majorName = rs.getString("major_name");
	            cLst.add(new Course(id, name, mId, majorName));
	        }
	        return cLst;
	        
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	public static ArrayList<Course> getAssignedCoursesByClass(int classId) {
	    ArrayList<Course> list = new ArrayList<>();
	    // Join slots with courses to see what is actually scheduled for this class
	    String sql = "SELECT DISTINCT c.id, c.name, c.major_id " +
	                 "FROM courses c " +
	                 "JOIN slots s ON c.id = s.course_id " +
	                 "WHERE s.class_id = ?";
	    try (Connection con = DBConnection.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, classId);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            list.add(new Course(rs.getInt("id"), rs.getString("name"), rs.getInt("major_id")));
	        }
	    } catch (Exception e) { e.printStackTrace(); }
	    return list;
	}
	public static boolean addCourse(Course obj) {
		try {
			Connection con = DBConnection.connect();
			String sql = "insert into courses (name, major_id) values (?,?)";
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
	
	public static boolean updateCourse(Course obj) {
		try {
			Connection con = DBConnection.connect();
			String sql = "update courses set name=?, major_id=? where (id=?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, obj.getName());
			ps.setInt(2, obj.getMajor_id());
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
	
	public static boolean deleteCourse(int id) {
		String sql = "delete from courses where(id=?)";
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
	
	public static ArrayList<Course> getCourseById(int id) {
		ArrayList<Course> cLst = getAllCourses();
		ArrayList<Course> result = new ArrayList<Course>();
		for(Course c: cLst) {
			String str_id = String.valueOf(c.getId());
			String str_id_1 = String.valueOf(id);
			if(str_id.contains(str_id_1)) {
				result.add(c);
				}
			}
		return result;
	}
	
//	public static ArrayList<Course> searchCourse(String keyword){
//		ArrayList<Course> gLst = new ArrayList<>();
//		String sql = "select * from courses where name like ? or major_id like ?";
//		try {
//			Connection con = DBConnection.connect();
//			if(con!=null) {
//				PreparedStatement ps = con.prepareStatement(sql);
//				keyword="%"+keyword+"%";
//				ps.setString(1, keyword);
//				ps.setString(2, keyword);
//				ResultSet rs = ps.executeQuery();
//				while(rs.next()) {
//					String name = rs.getString("name");
//					int major_id = rs.getInt("major_id");
//					int id = rs.getInt("id");
//					Course g = new Course(id, name, major_id);
//					gLst.add(g);
//				}
//				return gLst;
//			}
//			
//		
//		} catch (ClassNotFoundException | SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return gLst;
//	}
	public static ArrayList<Course> searchCourse(String keyword) {
	    ArrayList<Course> gLst = new ArrayList<>();
	    
	    // Using JOINs to access names from related tables
	    String sql = "SELECT c.* FROM courses c " +
	                 "JOIN majors m ON c.major_id = m.id " +
	                 "JOIN instructors i ON c.instructor_id = i.id " +
	                 "WHERE c.name LIKE ? " +    // Course Name
	                 "OR m.name LIKE ? " +       // Major Name
	                 "OR i.name LIKE ? " +       // Instructor Name
	                 "OR c.year LIKE ?";         // Year (if it's a column in courses)

	    try {
	        Connection con = DBConnection.connect();
	        if (con != null) {
	            PreparedStatement ps = con.prepareStatement(sql);
	            String searchPattern = "%" + keyword + "%";
	            
	            // Set the same keyword for all 4 parameters
	            ps.setString(1, searchPattern);
	            ps.setString(2, searchPattern);
	            ps.setString(3, searchPattern);
	            ps.setString(4, searchPattern);

	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                gLst.add(new Course(
	                    rs.getInt("id"),
	                    rs.getString("name"),
	                    rs.getInt("major_id")
	                    // Add instructor_id or year here if your Course constructor supports it
	                ));
	            }
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	    return gLst;
	}
	
	

	
	
}
