package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.DBConnection;

public class EnrollmentDAO {
	public static int getStudentCountByInstructor(int instructorId) {
	    int count = 0;
	    // Link: Enrollment -> Course (via major_id) -> InstructorCourses (via course_id)
	    String query = "SELECT COUNT(DISTINCT e.student_id) AS student_count " +
	                   "FROM enrollments e " +
	                   "JOIN courses c ON e.major_id = c.major_id " +
	                   "JOIN instructorCourses ic ON c.id = ic.course_id " +
	                   "WHERE ic.instructor_id = ?";

	    try (Connection conn = DBConnection.connect();
	         PreparedStatement pstmt = conn.prepareStatement(query)) {
	        
	        pstmt.setInt(1, instructorId);
	        
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                count = rs.getInt("student_count");
	            }
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        System.err.println("Error fetching student count: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return count;
	}
	public static ArrayList<Enrollment> getEnrollmentDetailsByCourse(int courseId) {
	    ArrayList<Enrollment> list = new ArrayList<>();
	    String sql = "SELECT s.name, c.name, e.student_id, c.id, e.id, s.email, m.name, " +
	                 "(SELECT COUNT(*) FROM enrollments e2 WHERE e2.major_id = c.major_id) AS total_count " +
	                 "FROM courses c " +
	                 "JOIN enrollments e ON c.major_id = e.major_id join majors m on m.id = c.major_id " +
	                 "JOIN students s ON e.student_id = s.id " +
	                 "WHERE c.id = ?";

	    try (Connection con = DBConnection.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setInt(1, courseId);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Enrollment enroll = new Enrollment(
	                rs.getString(1), // student_name
	                rs.getString(2), // course_name
	                rs.getInt(3),    // student_id
	                rs.getInt(4),    // course_id
	                rs.getInt(5)    // enrollment_id
	            );
	            enroll.setMajor(rs.getString(7));
	            enroll.setEmail(rs.getString(6));
	            list.add(enroll);
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	public static ArrayList<Enrollment> getAllEnrollments() {
		ArrayList<Enrollment> eLst = new ArrayList<Enrollment>();
		
		try {
			Connection con = DBConnection.connect();
			String sql = "select * from enrollments";
			Statement ps = con.createStatement();
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()) {
				int id=rs.getInt("id");
				int student_id = rs.getInt("student_id");
				int year_id=rs.getInt("year_id");
				int major_id = rs.getInt("major_id");
				int class_id = rs.getInt("class_id");
				ArrayList<Student> stu = StudentDAO.getStudentById(student_id);
				String student = StudentDAO.getStudentById(student_id).get(0).getName();
				String year = YearDAO.getYearById(year_id).get(0).getName();
				String major = MajorDAO.getMajorById(major_id).get(0).getName();
				String className = ClassDAO.getClassById(class_id).get(0).getName();

				eLst.add(new Enrollment(id, student_id, year_id, major_id, class_id, student, year, major, className));
			}
			
			return eLst;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean addEnrollment(Enrollment obj) {
		try {
			Connection con = DBConnection.connect();
			String sql = "insert into enrollments (student_id, year_id, major_id, class_id) values (?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, obj.getStudent_id());
			ps.setInt(2, obj.getYear_id());
			ps.setInt(3, obj.getMajor_id());
			ps.setInt(4, obj.getClass_id());
			int rows = ps.executeUpdate();
			if(rows > 0) 
				return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;	
	}
	
	public static boolean updateEnrollment(Enrollment obj) {
		try {
			Connection con = DBConnection.connect();
			String sql = "update enrollments set student_id=?, year_id=?, major_id=?, class_id=? where (id=?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, obj.getStudent_id());
			ps.setInt(2, obj.getYear_id());
			ps.setInt(3, obj.getMajor_id());
			ps.setInt(4, obj.getClass_id());
			ps.setInt(5, obj.getId());
			int rows = ps.executeUpdate();
			if(rows > 0) 
				return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean deleteEnrollment(int id) {
		String sql = "delete from enrollments where(id=?)";
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
	
	public static Enrollment getEnrollmentByStudentId(int studentId) {
	    Enrollment eLst= new Enrollment();
	    
	    String sql = "SELECT e.id, e.student_id, e.year_id, e.major_id, e.class_id, " +
	                 "s.name AS student_name, y.name AS year_name, " +
	                 "m.name AS major_name, c.name AS class_name " +
	                 "FROM enrollments e " +
	                 "JOIN students s ON e.student_id = s.id " +
	                 "JOIN years y ON e.year_id = y.id " +
	                 "JOIN majors m ON e.major_id = m.id " +
	                 "JOIN classes c ON e.class_id = c.id " +
	                 "WHERE e.student_id = ?";

	    try (Connection con = DBConnection.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setInt(1, studentId);
	        ResultSet rs = ps.executeQuery();

	        while(rs.next()) {
	            int id = rs.getInt("id");
	            int sId = rs.getInt("student_id");
	            int yId = rs.getInt("year_id");
	            int mId = rs.getInt("major_id");
	            int cId = rs.getInt("class_id");
	            
	            
	            String studentName = rs.getString("student_name");
	            String yearName = rs.getString("year_name");
	            String majorName = rs.getString("major_name");
	            String className = rs.getString("class_name");

	            eLst=new Enrollment(id, sId, yId, mId, cId, studentName, yearName, majorName, className);
	        }
	        return eLst;

	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
//	student_id, year_id, major_id, class_id
	public static ArrayList<Enrollment> searchEnrollment(String keyword) {
	    ArrayList<Enrollment> gLst = new ArrayList<>();
	    
	    // SQL with JOINs to access name columns across different tables
	    String sql = "SELECT e.*, s.name as stu_name, y.name as year, m.name as mjr_name, c.name as className FROM Enrollments e " +
	                 "JOIN students s ON e.student_id = s.id " +
	                 "JOIN years y ON e.year_id = y.id " +
	                 "JOIN majors m ON e.major_id = m.id " +
	                 "JOIN classes c ON e.class_id = c.id " +
	                 "WHERE s.name LIKE ? " +  // Student Name
	                 "OR y.name LIKE ? " +    // Year Name
	                 "OR m.name LIKE ? " +    // Major Name
	                 "OR c.name LIKE ?";      // Class Name

	    try {
	        Connection con = DBConnection.connect();
	        if (con != null) {
	            PreparedStatement ps = con.prepareStatement(sql);
	            String fuzzyKeyword = "%" + keyword + "%";
	            
	            ps.setString(1, fuzzyKeyword);
	            ps.setString(2, fuzzyKeyword);
	            ps.setString(3, fuzzyKeyword);
	            ps.setString(4, fuzzyKeyword);
	            
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                int id = rs.getInt("id");
	                int student_id = rs.getInt("student_id");
	                int year_id = rs.getInt("year_id");
	                int major_id = rs.getInt("major_id");
	                int class_id = rs.getInt("class_id");
	                
	                Enrollment enroll = new  Enrollment(id, student_id, year_id, major_id, class_id);
	                enroll.setStudent(rs.getString("stu_name"));
	                enroll.setYear(rs.getString("year"));
	                enroll.setClass_name(rs.getString("className"));
	                enroll.setMajor(rs.getString("mjr_name"));
	                gLst.add(enroll);
	            }
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return gLst;
	}

	
}
