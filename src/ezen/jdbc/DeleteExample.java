package ezen.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteExample {
	// DB 연결정보 상수
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:xe"; // Oracle
	private static final String user = "hr";
	private static final String password = "hr";

	public static void deleteDepartment(int departmentId) throws ClassNotFoundException {
		Class.forName(driver);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(url, user, password);
			StringBuilder sb = new StringBuilder(); // 자체편집 가능해서 이거 쓴다고 함. 뭐지? 왜??
			sb.append(" DELETE FROM departments")
       		  .append(" WHERE department_id = ?"); // ? : 바인딩 파라미터
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setInt(1, departmentId);
			int count = pstmt.executeUpdate();
			System.out.println(count + "개의 행이 삭제되었습니다.");
			pstmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();	
				if(con != null) con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		try {
			deleteDepartment(290);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
