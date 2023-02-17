package ezen.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC API를 이용한 RDBMS(Oracle) 연동
 * 
 * @Author 김재훈
 * @Date 2023. 2. 17.
 */
public class JDBCExample2 {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe"; // Oracle
		String user = "hr";
		String password = "hr";
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, user, password);
		String sql = "SELECT employee_id, last_name FROM employees";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery(sql); // 결과 조회하고 동시에 ResultSet rs에 담는 것.
		while (rs.next()) {
			int employeeId = rs.getInt("employee_id"); // getInt 오라클 Number를 int로 형변환 메소드.
			String lastName = rs.getString("last_name");
			System.out.println(employeeId + ", " + lastName);
		}
		con.close();

	}

}
