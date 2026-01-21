package application;

import java.sql.*;

public class DBConnection {
	public static Connection connect() throws ClassNotFoundException, SQLException {
		Connection con = null;
		String dbname = "soralmssystem";
		String userName = "root";
		String password = "1234";
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/soralmssystem", userName, password);
		return con;
	}
}
