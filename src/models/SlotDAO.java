package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.DBConnection;

public class SlotDAO {
	
	public static ArrayList<Slot> getSlotsByInstructorId(int instructorId) {
	    String sql = "SELECT s.*, c.name FROM slots s " +
	                 "JOIN instructorcourses ic ON s.course_id = ic.course_id "+
	                 "JOIN classes c on s.class_id = c.id "+
	                 "WHERE ic.instructor_id = ?";
	    ArrayList<Slot> sLst = new ArrayList<>();
	    
	    try (Connection con = DBConnection.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setInt(1, instructorId);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	            	Slot s = new Slot(
		                    rs.getInt("id"),
		                    rs.getInt("course_id"),
		                    rs.getString("day"),
		                    rs.getString("start_time"),
		                    rs.getString("end_time"),
		                    rs.getInt("class_id"));
	            	s.setClass_name(rs.getString("name"));
	            	sLst.add(s);
	            }
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	    return sLst; 
	}
	public static ArrayList<Slot> getAllSlots() {
		ArrayList<Slot> sLst = new ArrayList<Slot>();
		
		try {
			Connection con = DBConnection.connect();
			if(con!=null) {
				String sql = "select * from slots";
				Statement ps = con.createStatement();
				ResultSet rs = ps.executeQuery(sql);
				while(rs.next()) {
					int id=rs.getInt("id");
					int course_id=rs.getInt("course_id");
					String day = rs.getString("day");
					String start_time = rs.getString("start_time");
					String end_time = rs.getString("end_time");
					int class_id = rs.getInt("class_id");
					
					sLst.add(new Slot(id, course_id, day, start_time, end_time, class_id));
				}
				return sLst;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean addSlot(Slot obj) {
		try {
			Connection con = DBConnection.connect();
			if(con!=null) {
			
				// 1. First, Check if the record already exists
				String checkSql = "SELECT COUNT(*) FROM slots WHERE course_id = ? AND start_time = ? AND day = ?";
				PreparedStatement checkPs = con.prepareStatement(checkSql);
				checkPs.setInt(1, obj.getCourse_id());
				checkPs.setString(2, obj.getStart_time());
				checkPs.setString(3, obj.getDay());

				ResultSet rs = checkPs.executeQuery();
				if (rs.next() && rs.getInt(1) > 0) {
				    // Record already exists!
				    System.out.println("Conflict detected: This course is already scheduled at this time.");
				    return false; 
				}

				// 2. If no conflict, proceed with the INSERT
				String sql = "INSERT INTO slots (course_id, day, start_time, end_time, class_id) VALUES (?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, obj.getCourse_id());
				ps.setString(2, obj.getDay());
				ps.setString(3, obj.getStart_time());
				ps.setString(4, obj.getEnd_time());
				ps.setInt(5, obj.getClass_id());

				int rows = ps.executeUpdate();
				return rows > 0;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static boolean updateSlot(Slot obj) {
		try {
			Connection con = DBConnection.connect();
			if(con!=null) {
				String sql = "update slots set course_id=?, day=?, start_time=?, end_time=?, class_id=? where (id=?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, obj.getCourse_id());
				ps.setString(2, obj.getDay());
				ps.setString(3, obj.getStart_time());
				ps.setString(4, obj.getEnd_time());
				ps.setInt(5, obj.getClass_id());
				ps.setInt(6, obj.getId());
				int rows = ps.executeUpdate();
				if(rows > 0) 
					return true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean deleteSlot(int id) {
		String sql = "delete from slots where(id=?)";
		try {
			Connection con = DBConnection.connect();
			if(con!=null) {
				PreparedStatement ps=con.prepareStatement(sql);
				ps.setInt(1, id);
				int rows = ps.executeUpdate();
				if(rows > 0)
					return true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static ArrayList<Slot> getSlotById(int id) {
		ArrayList<Slot> sLst = getAllSlots();
		ArrayList<Slot> result = new ArrayList<Slot>();	
		for(Slot s: sLst) {
			String str_id = String.valueOf(s.getId());
			String str_id_1 = String.valueOf(id);
			if(str_id.contains(str_id_1)) {
				result.add(s);
			}
		}
		return result;
	}
	
//	course_id, day, start_time, end_time, class_id
	public static ArrayList<Slot> searchSlot(String keyword){
		ArrayList<Slot> gLst = new ArrayList<>();
		String sql = "select * from slots where course_id like ? or day like ? or start_time like ? or end_time like ? or class_id like ?";
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
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					int id=rs.getInt("id");
					int course_id=rs.getInt("course_id");
					String day = rs.getString("day");
					String start_time = rs.getString("start_time");
					String end_time = rs.getString("end_time");
					int class_id = rs.getInt("class_id");
					gLst.add(new Slot(id, course_id, day, start_time, end_time, class_id));
				}
				return gLst;
			}
			
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return gLst;
	
	}
	public static ArrayList<Slot> getSlotByClassId(int cid) {
	    String sql = "select * from slots where class_id=?";
	    ArrayList<Slot> sLst = new ArrayList<>();
	    
	    try (Connection con = DBConnection.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        if (con != null) {
	            ps.setInt(1, cid);
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    int id = rs.getInt("id");
	                    int course_id = rs.getInt("course_id");
	                    String day = rs.getString("day");
	                    String start_time = rs.getString("start_time");
	                    String end_time = rs.getString("end_time");
	                    int class_id = rs.getInt("class_id");

	                        sLst.add(new Slot(id, course_id, day, start_time, end_time, class_id));
	                    
	                }
	                return sLst;
	            }
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	    return sLst; 
	}
}