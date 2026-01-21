 package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.DBConnection;

public class AssignmentDAO {
	public static ArrayList<Assignment> getAssignmentsWithCourse(int studentId) {
        ArrayList<Assignment> list = new ArrayList<>();
        String sql = "SELECT id, course_id, title, due_date, status, grade FROM assignments WHERE student_id = ?";
//        

        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Using a constructor that includes the course name
                Assignment asgn = new Assignment();
                asgn.setId(rs.getInt("id"));
                //asgn.setCourse(rs.getString("name"));
                asgn.setTitle(rs.getString("title"));
                asgn.setDue_date(rs.getString("due_date"));
                asgn.setStatus(rs.getString("status"));
                asgn.setGrade(rs.getString("grade"));
                asgn.setCourse_id(rs.getInt("course_id"));
                
                list.add(asgn);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
	public static ArrayList<Assignment> getAssignmentsByInstructorAndCourse(int instructorId, int courseId) {
	    ArrayList<Assignment> assignments = new ArrayList<>();
	    
	    // Updated Query: Filter by both instructor (via join) and specific course_id
	    String query = "SELECT a.id, a.title, a.description, a.due_date, a.status, s.name, " +
	                   "a.course_id, a.student_id, a.submit_date, a.file_path, a.grade " +
	                   "FROM assignments a " +
	                   "JOIN instructorcourses ic ON a.course_id = ic.course_id join students s on s.id = a.student_id " +
	                   "WHERE ic.instructor_id = ? AND a.course_id = ?";

	    try (Connection conn = DBConnection.connect(); 
	         PreparedStatement pstmt = conn.prepareStatement(query)) {

	        pstmt.setInt(1, instructorId);
	        pstmt.setInt(2, courseId);

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                Assignment assignment = new Assignment();
	                assignment.setId(rs.getInt("id"));
	                assignment.setTitle(rs.getString("title"));
	                assignment.setDescription(rs.getString("description"));
	                assignment.setDue_date(rs.getString("due_date"));
	                assignment.setStatus(rs.getString("status"));
	                assignment.setCourse_id(rs.getInt("course_id"));
	                assignment.setStudent_id(rs.getInt("student_id"));
	                assignment.setSubmit_date(rs.getString("submit_date"));
	                assignment.setFile_path(rs.getString("file_path"));
	                assignment.setGrade(rs.getString("grade"));
	                assignment.setStudent(rs.getString("name"));
	                assignments.add(assignment);
	            }
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return assignments;
	}
	public static ArrayList<Assignment> getAssignmentsByInstructorAndTitle(int instructorId, String searchTitle) {
	    ArrayList<Assignment> assignments = new ArrayList<>();
	    
	    // Updated Query: Added filter for assignment title
	    String query = "SELECT a.id, a.title, a.description, a.due_date, a.status, s.name, " +
	                   "a.course_id, a.student_id, a.submit_date, a.file_path, a.grade " +
	                   "FROM assignments a " +
	                   "JOIN instructorcourses ic ON a.course_id = ic.course_id join students s on a.student_id = s.id " +
	                   "WHERE ic.instructor_id = ? AND a.title LIKE ?";

	    try (Connection conn = DBConnection.connect(); 
	         PreparedStatement pstmt = conn.prepareStatement(query)) {

	        // Bind Instructor ID
	        pstmt.setInt(1, instructorId);
	        
	        // Bind Title with wildcards (%) for partial matching (e.g., "Lab" finds "Lab 1")
	        pstmt.setString(2, "%" + searchTitle + "%");

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                Assignment assignment = new Assignment();
	                assignment.setId(rs.getInt("id"));
	                assignment.setTitle(rs.getString("title"));
	                assignment.setDescription(rs.getString("description"));
	                assignment.setDue_date(rs.getString("due_date"));
	                assignment.setStatus(rs.getString("status"));
	                assignment.setCourse_id(rs.getInt("course_id"));
	                assignment.setStudent_id(rs.getInt("student_id"));
	                assignment.setSubmit_date(rs.getString("submit_date"));
	                assignment.setFile_path(rs.getString("file_path"));
	                assignment.setGrade(rs.getString("grade"));
	                assignment.setStudent(rs.getString("name"));
	                assignments.add(assignment);
	            }
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return assignments;
	}
	public static ArrayList<Assignment> getAssignmentsByInstructor(int instructorId) {
        ArrayList<Assignment> assignments = new ArrayList<>();
        String query = "SELECT a.id, a.title, a.description, a.due_date, a.status, s.name, " +
                       "a.course_id, a.student_id, a.submit_date, a.file_path, a.grade " +
                       "FROM assignments a " +
                       "JOIN instructorcourses ic ON a.course_id = ic.course_id join students s on s.id = a.student_id " +
                       "WHERE ic.instructor_id = ?";

        try (Connection conn = DBConnection.connect(); 
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, instructorId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Assignment assignment = new Assignment();
                    assignment.setId(rs.getInt("id"));
                    assignment.setTitle(rs.getString("title"));
                    assignment.setDescription(rs.getString("description"));
                    assignment.setDue_date(rs.getString("due_date"));
                    assignment.setStatus(rs.getString("status"));
                    assignment.setCourse_id(rs.getInt("course_id"));
                    assignment.setStudent_id(rs.getInt("student_id"));
                    assignment.setSubmit_date(rs.getString("submit_date"));
                    assignment.setFile_path(rs.getString("file_path"));
                    assignment.setGrade(rs.getString("grade"));
                    assignment.setStudent(rs.getString("name"));
                    assignments.add(assignment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // In a real app, consider logging this or throwing a custom exception
        } catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        return assignments;
    }
	public static boolean submitAssignment(Assignment obj, String file_path, String submit_date) {
	    Connection con;
	    try {
	      con = DBConnection.connect();
	      if(con!=null) {
	        String sql = "update assignments set submit_date = ?, file_path = ?, status = 'true' where (id = ?)";
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, submit_date);
	        ps.setString(2, file_path);
	        ps.setInt(3, obj.getId());
	        int rows = ps.executeUpdate();
	        if(rows>0)
	          return true;
	      }
	    } catch (ClassNotFoundException | SQLException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	    
	    return false;
	  }
	public static ArrayList<Assignment> getUniqueAssignmentsByCourse(int courseId) {
	    ArrayList<Assignment> list = new ArrayList<>();
	    String sql = "SELECT a.id, a.title, a.due_date, a.description, a.submit_date, " +
	                 "SUM(CASE WHEN a.status = 'true' THEN 1 ELSE 0 END) AS submitted, " +
	                 "COUNT(*) AS total " +
	                 "FROM assignments a " +
	                 "JOIN courses c ON a.course_id = c.id " +
	                 "WHERE a.course_id = ? " +
	                 "GROUP BY a.id,a.title, a.due_date, a.description, a.submit_date";

	    try (Connection con = DBConnection.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setInt(1, courseId);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	        	Assignment assign =  new Assignment(
	            		rs.getInt("id"),
		                rs.getString("title"),
		                rs.getString("description"),
		                rs.getString("due_date")
		            );
	        	assign.setSubmit_date(rs.getString("submit_date"));
	            list.add(assign);
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	public static boolean updateGrade(int assignmentId, String grade) {
	    String sql = "UPDATE assignments SET grade = ? WHERE id = ?";
	    
	    try (Connection con = DBConnection.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setString(1, grade);
	        ps.setInt(2, assignmentId);
	        
	        int rows = ps.executeUpdate();
	        return rows > 0;
	        
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	public static boolean addAssignmentToAllEnrolled(Assignment obj) {
	    
		String sql = "INSERT INTO assignments (title, description, due_date, status, course_id, student_id) " +
	                 "SELECT ?, ?, ?, ?, c.id, e.student_id " +
	                 "FROM courses c " +
	                 "JOIN enrollments e ON c.major_id = e.major_id " +
	                 "WHERE c.id = ?";

	    try (Connection con = DBConnection.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setString(1, obj.getTitle());
	        ps.setString(2, obj.getDescription());
	        ps.setString(3, obj.getDue_date());
	        ps.setString(4,"false"); 
	        ps.setInt(5, obj.getCourse_id()); 
	        
	        int rows = ps.executeUpdate();
	        
	        return rows > 0;
	        
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	public static ArrayList<Assignment> getAssignmentByStudentId(int studentId) {
	    ArrayList<Assignment> iLst = new ArrayList<Assignment>(); 
	    String sql = "SELECT id, title, description, due_date, status, course_id FROM assignments WHERE student_id = ?";
	    
	    try (Connection con = DBConnection.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setInt(1, studentId);
	        
	        ResultSet rs = ps.executeQuery();
	        
	        while(rs.next()) {
	            int id = rs.getInt("id");
	            String title = rs.getString("title");
	            String description = rs.getString("description");
	            String due_date = rs.getString("due_date");
	            String status = rs.getString("status");
	            int course_id = rs.getInt("course_id");
	            iLst.add(new Assignment(id, title, description, due_date, status, course_id));
	        }
	        return iLst;
	        
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	public static ArrayList<Assignment> getAllAssignments() {
		ArrayList<Assignment> iLst = new ArrayList<Assignment>();
		
		try {
			Connection con = DBConnection.connect();
			String sql = "select * from assignments";
			Statement ps = con.createStatement();
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()) {
				int id=rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("description");
				String due_date = rs.getString("due_date");
				String status = rs.getString("status");
				int course_id = rs.getInt("course_id");
				int student_id = rs.getInt("student_id");
				String submit_date = rs.getString("submit_date");
				Assignment assign = new Assignment(id, title, description, due_date,status, course_id, student_id);
				assign.setSubmit_date(submit_date);
				iLst.add(assign);
			}
			return iLst;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static boolean addAssignment(Assignment obj) {
		try {
			Connection con = DBConnection.connect();
			String sql = "insert into assignments (title, description, due_date, status, course_id, student_id) values (?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, obj.getTitle());
			ps.setString(2, obj.getDescription());
			ps.setString(3, obj.getDue_date());
			ps.setString(4, obj.getStatus());
			ps.setInt(5, obj.getCourse_id());
			ps.setInt(6, obj.getStudent_id());
			int rows = ps.executeUpdate();
			if(rows > 0) 
				return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	public static boolean updateAssignment(Assignment obj) {
		try {
			Connection con = DBConnection.connect();
			String sql = "update assignments set title=?, description=?, due_date=?, status=?, course_id=? where (id=?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, obj.getTitle());
			ps.setString(2, obj.getDescription());
			ps.setString(3, obj.getDue_date());
			ps.setString(4, obj.getStatus());
			ps.setInt(5, obj.getCourse_id());
			ps.setInt(6, obj.getId());
			int rows = ps.executeUpdate();
			if(rows > 0) 
				return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public static boolean deleteAssignment(int id) {
		String sql = "delete from assignments where(id=?)";
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
	
	public static ArrayList<Assignment> getAssignmentById(int id) {
		ArrayList<Assignment> iLst = getAllAssignments();
		ArrayList<Assignment> result = new ArrayList<Assignment>();
		for(Assignment i: iLst) {
			String str_id = String.valueOf(i.getId());
			String str_id_1 = String.valueOf(id);
			if(str_id.contains(str_id_1)) {
				result.add(i);
			}
		}
		return result;
	}
	public static ArrayList<Assignment> searchAssignment(String keyword){
		ArrayList<Assignment> gLst = new ArrayList<>();
		String sql = "select * from assignments where title like ? or description like ? or due_date like ? or status like ? or course_id like ? or student_id like ? or";
		try {
			Connection con = DBConnection.connect();
			if(con!=null) {
				PreparedStatement ps = con.prepareStatement(sql);
				keyword="%"+keyword+"%";
				ps.setString(1, keyword);
				ps.setString(2, keyword);
				ps.setString(3, keyword);
				ps.setString(4, keyword);
				ps.setString(5, keyword);
				ps.setString(6, keyword);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					int id= rs.getInt("id");
					String title = rs.getString("title");
					String description = rs.getString("description");
					String due_date = rs.getString("due_date");
					String status = rs.getString("status");
					int course_id = rs.getInt("course_id");
					int student_id = rs.getInt("student_id");
					Assignment g = new Assignment(id, title, description, due_date, status, course_id, student_id);
					gLst.add(g);
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
