package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.DBConnection;

public class ExamDAO {
	public static ArrayList<Exam> getExamsByInstructor(int instructorId) {
	    ArrayList<Exam> examList = new ArrayList<>();
	    
	    String query = "SELECT e.*, c.name AS course_name, et.name as examType_name  " +
	                   "FROM exams e " +
	                   "JOIN courses c ON e.course_id = c.id " +
	                   "JOIN instructorCourses ic ON c.id = ic.course_id join examTypes et on et.id = e.examType_id " +
	                   "WHERE ic.instructor_id = ?";

	    try (Connection conn = DBConnection.connect();
	         PreparedStatement pstmt = conn.prepareStatement(query)) {
	        
	        pstmt.setInt(1, instructorId);
	        
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                Exam exam = new Exam();
	                exam.setId(rs.getInt("id"));
	                exam.setName(rs.getString("name"));
	                exam.setDate(rs.getString("date"));
	                exam.setExamType_id(rs.getInt("examType_id"));
	                exam.setCourse_id(rs.getInt("course_id"));
	                
	                // Populating the course name setter
	                exam.setCourse_name(rs.getString("course_name"));
	                exam.setExamType_name(rs.getString("examType_name"));
	                examList.add(exam);
	            }
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        System.err.println("Error fetching instructor exams: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return examList;
	}
	
	public static ArrayList<Exam> getExamsWithCourseNames() {
	    ArrayList<Exam> examList = new ArrayList<>();
	    
	    // Using an INNER JOIN to link the course_id to the courses table name
	    String query = "SELECT e.*, c.name AS course_display_name, et.name as examType_name " +
	                   "FROM exams e join examTypes as et on e.examType_id = et.id " +
	                   "JOIN courses c ON e.course_id = c.id";

	    try (Connection conn = DBConnection.connect();
	         PreparedStatement pstmt = conn.prepareStatement(query);
	         ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {
	            // 1. Create the Exam object using standard fields
	            Exam exam = new Exam();
	            exam.setId(rs.getInt("id"));
	            exam.setName(rs.getString("name"));
	            exam.setDate(rs.getString("date"));
	            exam.setExamType_id(rs.getInt("examType_id"));
	            exam.setCourse_id(rs.getInt("course_id"));
	            exam.setExamType_name(rs.getString("examType_name"));
	            // 2. Set the extra field from the JOIN
	            exam.setCourse_name(rs.getString("course_display_name"));
	            
	            examList.add(exam);
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        System.err.println("Error fetching exams with course names: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return examList;
	}
	
	public static boolean createExamAndInitializeGrades(Exam exam) {
	    String insertExamSQL = "INSERT INTO exams (name, date, examType_id, course_id) VALUES (?, ?, ?, ?)";
	    
	    String findStudentsSQL = "SELECT e.student_id FROM enrollments e " +
	                             "JOIN courses c ON e.major_id = c.major_id " +
	                             "JOIN majors m ON c.major_id = m.id " +
	                             "WHERE c.id = ? AND e.year_id = m.year_id";

	    String insertGradeSQL = "INSERT INTO grades (exam_id, result, student_id) VALUES (?, ?, ?)";

	    Connection conn = null;
	    
	    try {
	        conn = DBConnection.connect();
	        // 1. Start Transaction
	        conn.setAutoCommit(false);

	        // 2. Insert Exam
	        int generatedExamId = -1;
	        try (PreparedStatement pstmt = conn.prepareStatement(insertExamSQL, Statement.RETURN_GENERATED_KEYS)) {
	            pstmt.setString(1, exam.getName());
	            pstmt.setDate(2, Date.valueOf(exam.getDate()));
	            pstmt.setInt(3, exam.getExamType_id());
	            pstmt.setInt(4, exam.getCourse_id());
	            pstmt.executeUpdate();

	            ResultSet rs = pstmt.getGeneratedKeys();
	            if (rs.next()) {
	                generatedExamId = rs.getInt(1);
	            }
	        }

	        // 3. Get Enrolled Students for this course
	        ArrayList<Integer> studentIds = new ArrayList<>();
	        try (PreparedStatement pstmt = conn.prepareStatement(findStudentsSQL)) {
	            pstmt.setInt(1, exam.getCourse_id());
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                studentIds.add(rs.getInt("student_id"));
	            }
	        }

	        // 4. Batch Insert Grade Records
	        try (PreparedStatement pstmt = conn.prepareStatement(insertGradeSQL)) {
	            for (Integer studentId : studentIds) {
	                pstmt.setInt(1, generatedExamId);
	                pstmt.setInt(2, 0); // Default result is 0
	                pstmt.setInt(3, studentId);
	                pstmt.addBatch();
	            }
	            pstmt.executeBatch();
	        }

	        // 5. Commit Transaction
	        conn.commit();
	        System.out.println("Exam created and grades initialized for " + studentIds.size() + " students.");
	        return true;

	    } catch (SQLException | ClassNotFoundException e) {
	        System.err.println("Error occurred: " + e.getMessage());
	        // Attempt Rollback
	        if (conn != null) {
	            try {
	                conn.rollback();
	                System.err.println("Transaction rolled back successfully.");
	            } catch (SQLException rollbackEx) {
	                rollbackEx.printStackTrace();
	            }
	        }
	    } finally {
	        // Reset AutoCommit and Close Connection
	        if (conn != null) {
	            try {
	                conn.setAutoCommit(true);
	                conn.close();
	            } catch (SQLException finalEx) {
	                finalEx.printStackTrace();
	            }
	        }
	    }
	    return false;
	}
	
	public static ArrayList<String> getUpcomingExams() {
	      ArrayList<String> examList = new ArrayList<>();
	      
	      String sql = "SELECT name, date FROM exams ORDER BY date ASC LIMIT 4";
	      
	      try (Connection con = DBConnection.connect();
	           Statement st = con.createStatement();
	           ResultSet rs = st.executeQuery(sql)) {
	          
	          while (rs.next()) {
	            String examInfo = rs.getString("name") + " - (" + rs.getString("date") + ")";
	              examList.add(examInfo);
	          }
	      } catch (Exception e) {
	        e.printStackTrace();
	      }
	      return examList;
	  }
	public static ArrayList<Exam> getAllExams() {
		ArrayList<Exam> sLst = new ArrayList<Exam>();
		
		try {
			Connection con = DBConnection.connect();
			String sql = "select * from exams";
			Statement ps = con.createStatement();
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()) {
				int id=rs.getInt("id");
				String name = rs.getString("name");
				int examType_id = rs.getInt("examType_id");
				String date = rs.getString("date");
				int course_id = rs.getInt("course_id");
				sLst.add(new Exam(id, name, examType_id, date, course_id));
			}
			return sLst;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean addExam(Exam obj) {
		try {
			Connection con = DBConnection.connect();
			String sql = "insert into exams (name, examType_id, date, course_id) values (?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, obj.getName());
			ps.setInt(2, obj.getExamType_id());
			ps.setString(3, obj.getDate());
			ps.setInt(4, obj.getCourse_id());
			int rows = ps.executeUpdate();
			if(rows > 0) 
				return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static boolean updateExam(Exam obj) {
		try {
			Connection con = DBConnection.connect();
			String sql = "update exams set name=?, examType_id=?, date=?, course_id=? where (id=?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, obj.getName());
			ps.setInt(2, obj.getExamType_id());
			ps.setString(3, obj.getDate());
			ps.setInt(4, obj.getCourse_id());
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
	
	public static boolean deleteExam(int id) {
		String sql = "delete from exams where(id=?)";
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
	
	public static ArrayList<Exam> getExamById(int id) {
		ArrayList<Exam> eLst = getAllExams();
		ArrayList<Exam> result = new ArrayList<Exam>();
		for(Exam e:eLst) {
			String str_id = String.valueOf(e.getId());
			String str_id_1 = String.valueOf(id);
			if(str_id.contains(str_id_1)) {
				result.add(e);
			}
		}
		return result;
	}
//	name, examType_id, date, course_id
	public static ArrayList<Exam> searchExam(String keyword){
		ArrayList<Exam> gLst = new ArrayList<>();
		String sql = "select * from exams where name like ? or examType_id like ? or date like ? or course_id like ?";
		try {
			Connection con = DBConnection.connect();
			if(con!=null) {
				PreparedStatement ps = con.prepareStatement(sql);
				keyword="%"+keyword+"%";
				ps.setString(1, keyword);
				ps.setString(2, keyword);
				ps.setString(3, keyword);
				ps.setString(4, keyword);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					int id=rs.getInt("id");
					int examType_id = rs.getInt("examType_id");
					String name = rs.getString("name");
					String date = rs.getString("date");
					int course_id = rs.getInt("course_id");
					gLst.add(new Exam(id, name, examType_id, date, course_id));
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
