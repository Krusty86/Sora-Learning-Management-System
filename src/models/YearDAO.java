package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.DBConnection;

public class YearDAO {
	public static ArrayList<Year> getAllYears() {
		ArrayList<Year> sLst = new ArrayList<Year>();
		
		try {
			Connection con = DBConnection.connect();
			String sql = "select * from Years";
			Statement ps = con.createStatement();
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()) {
				int id=rs.getInt("id");
				String name = rs.getString("name");
				sLst.add(new Year(id, name));
			}
			return sLst;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static ArrayList<Year> getYearById(int id) {
		ArrayList<Year> eLst = getAllYears();
		ArrayList<Year> result = new ArrayList<Year>();
		for(Year e:eLst) {
			String str_id = String.valueOf(e.getId());
			String str_id_1 = String.valueOf(id);
			if(str_id.contains(str_id_1)) {
				result.add(e);
			}
		}
		return result;
	}
}
