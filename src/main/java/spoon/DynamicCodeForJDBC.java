package spoon;

import java.sql.*;

public class DynamicCodeForJDBC {
	static final String DB_URL = "jdbc:mysql://localhost/TUTORIALSPOINT";
	static final String USER = "guest";
	static final String PASS = "guest123";

	static final String QUERY2 = "FROM Employees";
	static final String QUERY3 = "id,first,last";
	static final String QUERY1 = "SELECT";
	

	public void getJdbcQuery() {
		StringBuffer sb = new StringBuffer(); 
		sb.append(QUERY1);
		sb.append(QUERY2);
		sb.append(QUERY3);
		String dynamic_query = sb.toString();
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(dynamic_query);) {
			while (rs.next()) {
				System.out.print("ID: " + rs.getInt("id"));
				System.out.print(", Age: " + rs.getInt("age"));
				System.out.print(", First: " + rs.getString("first"));
				System.out.println(", Last: " + rs.getString("last"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
//	public void getJdbcQuery1() {
//		System.out.println("Body of method getJdbcQuery1");
//	}
}