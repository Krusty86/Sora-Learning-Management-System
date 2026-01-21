package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.DBConnection;

public class GradeDAO {
	public static boolean updateGrade(int id, String mark) {
		try(Connection con = DBConnection.connect()){
			String sql = "update grades set result = ? where id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(mark));
			ps.setInt(2, id);
			if(ps.executeUpdate()>0)
				return true;
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}return false;
	}
	public static int getTotalCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM exams";
        try (Connection con = DBConnection.connect();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
	public static ArrayList<Grade> getGradesByInstructorId(int id){
		ArrayList<Grade> glst = new ArrayList<>();
		try(Connection con = DBConnection.connect()){
			String sql="""
					select s.name, e.name exam_name, e.date, c.name c_name, g.result, et.name as etname, 
					g.id, c.id c_id, s.id s_id, e.id e_id, mjr.name mjr_name, mjr.id mjr_id from grades g
					join exams e on e.id = g.exam_id
					join courses c on c.id = e.course_id
					join majors mjr on mjr.id = c.major_id
					join examtypes et on e.examType_id = et.id
					join enrollments erol on erol.major_id = mjr.id
					join students s on s.id = erol.student_id
					join instructorcourses ic on c.id = ic.course_id
					join instructors ins on ins.id = ic.instructor_id
					where ic.instructor_id = ?
					""";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Grade g = new Grade(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getString("c_name"),
						rs.getString("exam_name"),
						rs.getInt("s_id"),
						rs.getInt("c_id"),
						rs.getInt("e_id"),
						rs.getString("result"),
						rs.getInt("mjr_id")
						);
				g.setDate(rs.getDate("date").toLocalDate());
				g.setMjr_name(rs.getString("mjr_name"));
				g.setExamTypeName(rs.getString("etname"));
				
				glst.add(g);
			}
			return glst;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static ArrayList<Grade> getGradesByInstructorIdAndFilter(int id, String keyword){
		ArrayList<Grade> glst = new ArrayList<>();
		try(Connection con = DBConnection.connect()){
			String sql="""
					select s.name, e.name exam_name, e.date, c.name c_name, g.result, et.name as etname, 
					g.id, c.id c_id, s.id s_id, e.id e_id, mjr.name mjr_name, mjr.id mjr_id from grades g
					join exams e on e.id = g.exam_id
					join courses c on c.id = e.course_id
					join majors mjr on mjr.id = c.major_id
					join enrollments erol on erol.major_id = mjr.id
					join students s on s.id = erol.student_id
					join instructorcourses ic on c.id = ic.course_id
					join instructors ins on ins.id = ic.instructor_id
					join examtypes as et on e.examType_id = et.id
					where ic.instructor_id = ? and ( c.name like ? or e.name like ?)
					""";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			keyword = "%"+keyword+"%";
			ps.setString(2, keyword);
			ps.setString(3, keyword);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Grade g = new Grade(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getString("c_name"),
						rs.getString("exam_name"),
						rs.getInt("s_id"),
						rs.getInt("c_id"),
						rs.getInt("e_id"),
						rs.getString("result"),
						rs.getInt("mjr_id")
						);
				g.setDate(rs.getDate("date").toLocalDate());
				g.setMjr_name(rs.getString("mjr_name"));
				g.setExamTypeName(rs.getString("etname"));
				glst.add(g);
			}
			return glst;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    public static ArrayList<Grade> getLatestScores(int studentId) {
        ArrayList<Grade> gLst = new ArrayList<>();
        
        String sql = "SELECT g.result, c.name AS course_name " +
                     "FROM grades g " +
                     "JOIN exams e ON g.exam_id = e.id " +
                     "JOIN courses c ON e.course_id = c.id " +
                     "WHERE g.student_id = ? " +
                     "GROUP BY g.id, g.result, c.name " + 
                     "ORDER BY g.id DESC LIMIT 4";
        try (Connection con = DBConnection.connect();
                PreparedStatement ps = con.prepareStatement(sql)) {
               
               ps.setInt(1, studentId);
               ResultSet rs = ps.executeQuery();
               
               while(rs.next()) {
                   Grade g = new Grade();
                   g.setResult(rs.getString("result"));
                   g.setCourse_name(rs.getString("course_name")); 
                   gLst.add(g);
               }
           } catch (Exception e) { 
               e.printStackTrace(); 
           }
           return gLst;
       }
    public static ArrayList<Grade> getGradesByStudent(int studentId) {
        ArrayList<Grade> gLst = new ArrayList<>();
        String sql = "SELECT g.*, et.name AS exam_type_name, c.name AS course_display_name, en.year_id " +
                "FROM grades g " +
                "JOIN examtypes et ON g.exam_id = et.id " +
                "JOIN enrollments en ON g.student_id = en.student_id " +
                "JOIN courses c ON en.major_id = c.major_id " +
                "WHERE g.student_id = ?";
        
        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Grade g = new Grade(
                    rs.getInt("id"),
                    rs.getInt("exam_id"),
                    rs.getString("result"),
                    rs.getInt("student_id")
                );
              g.setEx_name(rs.getString("exam_type_name"));
              g.setCourse_name(rs.getString("course_display_name"));
              g.setYear_id(rs.getInt("year_id"));
                gLst.add(g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gLst;
    }
    public static ArrayList<String> getExamTypes() {
        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT et.name FROM examtypes et " +
                "JOIN grades g ON et.id = g.exam_id";
        try {
            Connection con = DBConnection.connect();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                list.add(rs.getString("name")); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
	public static ArrayList<Grade> getDetailedPerformance(int instructorId) {
	    ArrayList<Grade> list = new ArrayList<>();
	    
	    String sql = "SELECT c.name AS course_name, s.name AS student_name, g.result, e.name AS exam_name, " +
	                 "ROUND(AVG(g.result) OVER(PARTITION BY c.id), 2) AS course_avg " +
	                 "FROM instructorcourses ic " +
	                 "JOIN courses c ON ic.course_id = c.id " +
	                 "JOIN exams e ON c.id = e.course_id " +
	                 "JOIN grades g ON e.id = g.exam_id " +
	                 "JOIN students s ON g.student_id = s.id " +
	                 "WHERE ic.instructor_id = ?";

	    try (Connection con = DBConnection.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setInt(1, instructorId);
	        
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            list.add(new Grade(
	                rs.getString("course_name"),
	                rs.getString("student_name"),
	                rs.getString("exam_name"),
	                rs.getString("result"),
	                rs.getDouble("course_avg")
	            ));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	public static ArrayList<Grade> getAllGrades(){
		ArrayList<Grade> gLst = new ArrayList<>();
		try(Connection con = DBConnection.connect()){
			String sql = "SELECT \r\n g.id,"
					+ "    s.name AS student_name, \r\n"
					+ "    c.name AS course_name, \r\n"
					+ "    et.name AS exam_name,\r\n"
					+ "    s.id AS student_id, \r\n"
					+ "    c.id AS course_id, \r\n"
					+ "    et.id AS exam_id,\r\n"
					+ "    e.class_id, \r\n"
					+ "    e.year_id, \r\n"
					+ "    e.major_id,\r\n"
					+ "    g.result\r\n"
					+ "FROM students s\r\n"
					+ "JOIN enrollments e ON s.id = e.student_id\r\n"
					+ "JOIN courses c ON e.major_id = c.major_id\r\n"
					+ "JOIN grades g ON s.id = g.student_id\r\n"
					+ "JOIN examtypes et ON g.exam_id = et.id";
			Statement sm = con.createStatement();
			ResultSet rs = sm.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String stu_name = rs.getString("student_name");
				String course_name = rs.getString("course_name");
				String exam_name = rs.getString("exam_name");
				int stu_id = rs.getInt("student_id");
				int course_id = rs.getInt("course_id");
				int exam_id = rs.getInt("exam_id");
				int class_id = rs.getInt("class_id");
				int year_id = rs.getInt("year_id");
				int major_id = rs.getInt("major_id");
				String result = rs.getString("result");
				gLst.add(new Grade(stu_id, stu_name, course_name, exam_name, id, course_id, exam_id, class_id, year_id, major_id, result));
			}
			return gLst;
		}catch(Exception e) {
			e.printStackTrace();
		}return null;
	}
	public static ArrayList<Grade> getStudentAveragesPerCourse(int studentId) {
	    ArrayList<Grade> list = new ArrayList<>();
	    
	    String sql = "SELECT c.name AS course_name, " +
	                 "ROUND(AVG(g.result), 2) AS course_average " +
	                 "FROM grades g " +
	                 "JOIN exams e ON g.exam_id = e.id " +
	                 "JOIN courses c ON e.course_id = c.id " +
	                 "WHERE g.student_id = ? " +
	                 "GROUP BY c.id, c.name";

	    try (Connection con = DBConnection.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setInt(1, studentId);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Grade item = new Grade();
	            item.setCourse_name(rs.getString("course_name"));
	            item.setAverage(rs.getDouble("course_average"));
	            list.add(item);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	public double getCourseAverage(int courseId) {
	    double totalScores = 0;
	    int gradeCount = 0;
	    
	    String sql = "SELECT g.result FROM grades g " +
	                 "JOIN Exams e ON g.exam_id = e.id " +
	                 "WHERE e.course_id = ?";
	    
	    try (Connection con = DBConnection.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setInt(1, courseId);
	        ResultSet rs = ps.executeQuery();
	        
	        while (rs.next()) {
	            totalScores += rs.getDouble("result");
	            gradeCount++;
	        }
	    } catch (Exception e) { 
	        e.printStackTrace(); 
	    }
	    
	    if (gradeCount == 0) return 0.0;
	    double avg = totalScores / gradeCount;
	    return Math.round(avg * 100.0) / 100.0; 
	}
	public static double calculateGPA(int studentId) {
	    double totalPoints = 0;
	    int courseCount = 0;
	    
	    String sql = "SELECT result FROM grades WHERE student_id = ?";
	    
	    try (Connection con = DBConnection.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, studentId);
	        ResultSet rs = ps.executeQuery();
	        
	        while (rs.next()) {
	            int score = rs.getInt("result");
	            if (score >= 90) totalPoints += 4.0;
	            else if (score >= 80) totalPoints += 3.0;
	            else if (score >= 70) totalPoints += 2.0;
	            else if (score >= 60) totalPoints += 1.0;
	            else totalPoints += 0.0;
	            
	            courseCount++;
	        }
	    } catch (Exception e) { e.printStackTrace(); }
	    
	    return (courseCount == 0) ? 0.0 : totalPoints / courseCount;
	}
	public static ArrayList<Grade> getGradeByClass(Class obj){
		ArrayList<Grade> gLst = new ArrayList<>();
		try(Connection con = DBConnection.connect()){
			String sql = "SELECT g.id,\r\n"
					+ "    s.name AS student_name, \r\n"
					+ "    c.name AS course_name, \r\n"
					+ "    et.name AS exam_name,\r\n"
					+ "    s.id AS student_id, \r\n"
					+ "    c.id AS course_id, \r\n"
					+ "    et.id AS exam_id,\r\n"
					+ "    e.class_id, \r\n"
					+ "    e.year_id, \r\n"
					+ "    e.major_id,\r\n"
					+ "    g.result\r\n"
					+ "FROM students s\r\n"
					+ "JOIN enrollments e ON s.id = e.student_id\r\n"
					+ "JOIN courses c ON e.major_id = c.major_id\r\n"
					+ "JOIN grades g ON s.id = g.student_id\r\n"
					+ "JOIN examtypes et ON g.exam_id = et.id\r\n"
					+ "WHERE e.class_id = ?";
			PreparedStatement sm = con.prepareStatement(sql);
			sm.setInt(1, obj.getId());
			ResultSet rs = sm.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String stu_name = rs.getString("student_name");
				String course_name = rs.getString("course_name");
				String exam_name = rs.getString("exam_name");
				int stu_id = rs.getInt("student_id");
				int course_id = rs.getInt("course_id");
				int exam_id = rs.getInt("exam_id");
				int class_id = rs.getInt("class_id");
				int year_id = rs.getInt("year_id");
				int major_id = rs.getInt("major_id");
				String result = rs.getString("result");
				gLst.add(new Grade(stu_id, stu_name, course_name, exam_name, id, course_id, exam_id, class_id, year_id, major_id, result));
			}
			return gLst;
		}catch(Exception e) {
			e.printStackTrace();
		}return null;
	}
	
	public static ArrayList<Grade> getGradeByYear(Year obj){
		ArrayList<Grade> gLst = new ArrayList<>();
		try(Connection con = DBConnection.connect()){
			String sql = "SELECT g.id,\r\n"
					+ "    s.name AS student_name, \r\n"
					+ "    c.name AS course_name, \r\n"
					+ "    et.name AS exam_name,\r\n"
					+ "    s.id AS student_id, \r\n"
					+ "    c.id AS course_id, \r\n"
					+ "    et.id AS exam_id,\r\n"
					+ "    e.class_id, \r\n"
					+ "    e.year_id, \r\n"
					+ "    e.major_id,\r\n"
					+ "    g.result\r\n"
					+ "FROM students s\r\n"
					+ "JOIN enrollments e ON s.id = e.student_id\r\n"
					+ "JOIN courses c ON e.major_id = c.major_id\r\n"
					+ "JOIN grades g ON s.id = g.student_id\r\n"
					+ "JOIN examtypes et ON g.exam_id = et.id\r\n"
					+ "JOIN majors mjr on c.major_id = mjr.id WHERE mjr.year_id = ?";
			PreparedStatement sm = con.prepareStatement(sql);
			sm.setInt(1, obj.getId());
			ResultSet rs = sm.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String stu_name = rs.getString("student_name");
				String course_name = rs.getString("course_name");
				String exam_name = rs.getString("exam_name");
				int stu_id = rs.getInt("student_id");
				int course_id = rs.getInt("course_id");
				int exam_id = rs.getInt("exam_id");
				int class_id = rs.getInt("class_id");
				int year_id = rs.getInt("year_id");
				int major_id = rs.getInt("major_id");
				String result = rs.getString("result");
				gLst.add(new Grade(stu_id, stu_name, course_name, exam_name, id, course_id, exam_id, class_id, year_id, major_id, result));
			}
			return gLst;
		}catch(Exception e) {
			e.printStackTrace();
		}return null;
	}
	
	public static ArrayList<Grade> getGradesByStudentID(Student stu){
		ArrayList<Grade> gLst = new ArrayList<>();
		try(Connection con = DBConnection.connect()){
			String sql = "SELECT \r\n g.id,"
					+ "    s.name AS student_name,\r\n"
					+ "    c.name AS course_name,\r\n"
					+ "    et.name AS exam_name,\r\n"
					+ "    g.result,\r\n"
					+ "    e.year_id,\r\n"
					+ "    cl.name AS class_name\r\n"
					+ "FROM students s\r\n"
					+ "JOIN enrollments e ON s.id = e.student_id\r\n"
					+ "JOIN classes cl ON e.class_id = cl.id\r\n"
					+ "JOIN courses c ON e.major_id = c.major_id\r\n"
					+ "JOIN grades g ON s.id = g.student_id\r\n"
					+ "JOIN examtypes et ON g.exam_id = et.id\r\n"
					+ "WHERE s.id = ?";
			PreparedStatement sm = con.prepareStatement(sql);
			sm.setInt(1, stu.getId());
			ResultSet rs = sm.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String stu_name = rs.getString("student_name");
				String course_name = rs.getString("course_name");
				String exam_name = rs.getString("exam_name");
				String class_name = rs.getString("class_name");
				int year_id = rs.getInt("year_id");
				String result = rs.getString("result");
				gLst.add(new Grade(stu_name, course_name, exam_name, id, year_id, result));
			}
			return gLst;
		}catch(Exception e) {
			e.printStackTrace();
		}return null;
	}
	public static ArrayList<Grade> getGradesByCourse(Course co){
		ArrayList<Grade> gLst = new ArrayList<>();
		try(Connection con = DBConnection.connect()){
			String sql = "SELECT \r\n g.id, et.name as examType,"
					+ "    s.name AS student_name,\r\n"
					+ "    c.name AS course_name,\r\n"
					+ "    et.name AS exam_name,\r\n"
					+ "    g.result,\r\n"
					+ "    e.year_id,\r\n"
					+ "    cl.name AS class_name\r\n"
					+ "FROM students s\r\n"
					+ "JOIN enrollments e ON s.id = e.student_id\r\n"
					+ "JOIN classes cl ON e.class_id = cl.id\r\n"
					+ "JOIN courses c ON e.major_id = c.major_id\r\n"
					+ "JOIN grades g ON s.id = g.student_id\r\n"
					+ "JOIN examtypes et ON g.exam_id = et.id\r\n"
					+ "WHERE c.id = ?";
			PreparedStatement sm = con.prepareStatement(sql);
			sm.setInt(1, co.getId());
			ResultSet rs = sm.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String stu_name = rs.getString("student_name");
				String course_name = rs.getString("course_name");
				String exam_name = rs.getString("exam_name");
				String class_name = rs.getString("class_name");
				int year_id = rs.getInt("year_id");
				String result = rs.getString("result");
				Grade g = new Grade(stu_name, course_name, exam_name, id, year_id, result);
				g.setExamTypeName(rs.getString("examType"));
				gLst.add(g);
			}
			return gLst;
		}catch(Exception e) {
			e.printStackTrace();
		}return null;
	}
	
	public static ArrayList<Grade> getGradesByExamType(ExamType et){
		ArrayList<Grade> gLst = new ArrayList<>();
		try(Connection con = DBConnection.connect()){
			String sql = "SELECT \r\n g.id, et.name as examType,"
					+ "    s.name AS student_name,\r\n"
					+ "    c.name AS course_name,\r\n"
					+ "    et.name AS exam_name,\r\n"
					+ "    g.result,\r\n"
					+ "    e.year_id,\r\n"
					+ "    cl.name AS class_name\r\n"
					+ "FROM students s\r\n"
					+ "JOIN enrollments e ON s.id = e.student_id\r\n"
					+ "JOIN classes cl ON e.class_id = cl.id\r\n"
					+ "JOIN courses c ON e.major_id = c.major_id\r\n"
					+ "JOIN grades g ON s.id = g.student_id\r\n"
					+ "JOIN examtypes et ON g.exam_id = et.id\r\n"
					+ "WHERE et.id = ?";
			PreparedStatement sm = con.prepareStatement(sql);
			sm.setInt(1, et.getId());
			ResultSet rs = sm.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String stu_name = rs.getString("student_name");
				String course_name = rs.getString("course_name");
				String exam_name = rs.getString("exam_name");
				String class_name = rs.getString("class_name");
				int year_id = rs.getInt("year_id");
				String result = rs.getString("result");
				Grade g = new Grade(stu_name, course_name, exam_name, id, year_id, result);
				g.setExamTypeName(rs.getString("examType"));
				gLst.add(g);
			}
			return gLst;
		}catch(Exception e) {
			e.printStackTrace();
		}return null;
	}
	public static ArrayList<Grade> getGradesByMajor(String keyword){
		int num1=3, num2=4;
		if(keyword.equals("SE")) {
			num1 = 1;
			num2 = 2;
		}
		ArrayList<Grade> gLst = new ArrayList<>();
		try(Connection con = DBConnection.connect()){
			String sql = "SELECT \r\n g.id, et.name as examType,"
					+ "    s.name AS student_name,\r\n"
					+ "    c.name AS course_name,\r\n"
					+ "    et.name AS exam_name,\r\n"
					+ "    g.result,\r\n"
					+ "    e.year_id,\r\n"
					+ "    cl.name AS class_name\r\n"
					+ "FROM students s\r\n"
					+ "JOIN enrollments e ON s.id = e.student_id\r\n"
					+ "JOIN classes cl ON e.class_id = cl.id\r\n"
					+ "JOIN courses c ON e.major_id = c.major_id\r\n"
					+ "JOIN grades g ON s.id = g.student_id\r\n"
					+ "JOIN examtypes et ON g.exam_id = et.id\r\n"
					+ "WHERE c.major_id = ? or c.major_id = ?";
			PreparedStatement sm = con.prepareStatement(sql);
			sm.setInt(1, num1);
			sm.setInt(2, num2);
			ResultSet rs = sm.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String stu_name = rs.getString("student_name");
				String course_name = rs.getString("course_name");
				String exam_name = rs.getString("exam_name");
				String class_name = rs.getString("class_name");
				int year_id = rs.getInt("year_id");
				String result = rs.getString("result");
				Grade g = new Grade(stu_name, course_name, exam_name, id, year_id, result);
				g.setExamTypeName(rs.getString("examType"));
				gLst.add(g);
			}
			return gLst;
		}catch(Exception e) {
			e.printStackTrace();
		}return null;
	}

//	public static ArrayList<Grade> getAllGrades() {
//		ArrayList<Grade> gLst = new ArrayList<Grade>();
//		try {
//			Connection con = DBConnection.connect();
//			String sql = "select * from grades";
//			Statement ps = con.createStatement();
//			ResultSet rs = ps.executeQuery(sql);
//			while(rs.next()) {
//				int id=rs.getInt("id");
//				int exam_id = rs.getInt("exam_id");
//				String result = rs.getString("result");
//				int student_id = rs.getInt("student_id");
//				gLst.add(new Grade(id, exam_id, result, student_id));
//			}
//			return gLst;
//		} catch (ClassNotFoundException | SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	
	public static boolean addGrade(Grade obj) {
		try {
			Connection con = DBConnection.connect();
			String sql = "insert into grades (exam_id, result, student_id) values (?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, obj.getExam_id());
			ps.setString(2, obj.getResult());
			ps.setInt(3, obj.getStudent_id());
			int rows = ps.executeUpdate();
			if(rows > 0) 
				return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static boolean updateGrade(Grade obj) {
		try {
			Connection con = DBConnection.connect();
			String sql = "update grades exam_id=?, result=?, student_id=? where (id=?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, obj.getExam_id());
			ps.setString(2, obj.getResult());
			ps.setInt(3, obj.getStudent_id());
			ps.setInt(4, obj.getId());
			int rows = ps.executeUpdate();
			if(rows > 0) 
				return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static boolean deleteGrade(int id) {
		String sql = "delete from grades where(id=?)";
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
	
	
	public static ArrayList<Grade> getGradeById(int id) {
		ArrayList<Grade> gLst = getAllGrades();
		ArrayList<Grade> result = new ArrayList<Grade>();	
		for(Grade g: gLst) {
			String str_id = String.valueOf(g.getId());
			String str_id_1 = String.valueOf(id);
			if(str_id.contains(str_id_1)) {
				result.add(g);
			}
		}
		return result;
	}
	
	public static ArrayList<Grade> searchGrade(String keyword){
		ArrayList<Grade> gLst = new ArrayList<>();
		String sql = "select * from grades where exam_id like ? or result like ? or student_id like ?";
		try {
			Connection con = DBConnection.connect();
			if(con!=null) {
				PreparedStatement ps = con.prepareStatement(sql);
				keyword="%"+"keyword"+"%";
				ps.setString(1, keyword);
				ps.setString(2, keyword);
				ps.setString(3, keyword);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					int exam_id = rs.getInt("exam_id");
					String result = rs.getString("result");
					int student_id = rs.getInt("student_id");
					int id = rs.getInt("id");
					Grade g = new Grade(id, exam_id, result, student_id);
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
