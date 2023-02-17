package ezen.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertExample {
	// DB 연결정보 상수
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:xe"; // Oracle
	private static final String user = "hr";
	private static final String password = "hr";

	public static void addDepartment(String departmentName, int managerId) throws ClassNotFoundException, SQLException {

		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, user, password);
		StringBuilder sb = new StringBuilder(); // 자체편집 가능해서 이거 쓴다고 함. 뭐지? 왜??
		sb.append(" INSERT INTO departments(department_id, department_name, manager_id)")
//		.append(" VALUES(departments_seq.NEXTVAL, '"+departmentName+"')");// 문법에러 안 나게 한 칸 띄우기
				.append(" VALUES(departments_seq.NEXTVAL, ?, ?)"); // ? : 바인딩 파라미터
//		String sql = "INSERT INTO departments(department_id, department_name)\r\n"
//				+ "VALUES(departments_seq.NEXTVAL, '개발팀')";

		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		pstmt.setString(1, departmentName);
		pstmt.setInt(2, managerId);

		int count = pstmt.executeUpdate(); // 서버에 데이터 전송.
		System.out.println(count + "개의 행이 추가되었습니다.");
		con.close();
	}

	// 더 깔끔하게 DML처리하는 기본
	public static void addDepartmentV2(String departmentName, int managerId) throws ClassNotFoundException {

		Class.forName(driver);
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(url, user, password);
			con.setAutoCommit(false); // 디폴트 true로 되어있음. 그러면 익큐업뎃 날리면 바로 커밋까지 되어버림
			StringBuilder sb = new StringBuilder(); // 자체편집 가능해서 이거 쓴다고 함. 뭐지? 왜??
			sb.append(" INSERT INTO departments(department_id, department_name, manager_id)")
//			.append(" VALUES(departments_seq.NEXTVAL, '"+departmentName+"')");// 문법에러 안 나게 한 칸 띄우기
					.append(" VALUES(departments_seq.NEXTVAL, ?, ?)"); // ? : 바인딩 파라미터
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, departmentName);
			pstmt.setInt(2, managerId);
			int count = pstmt.executeUpdate();
			System.out.println(count + "개의 행이 추가되었습니다.");
			pstmt.close();
			// 여기까지가 인서트
			
			// 트랜잭션 연습을 위한 UPDATE SQL 실행
			StringBuilder sb2 = new StringBuilder();
			sb2.append(" UPDATE departments")
			   .append (" SET location_id = ?")
			   .append(" WHERE department_id = ?");
			pstmt = con.prepareStatement(sb2.toString());
			pstmt.setInt(1, 1000);
			pstmt.setInt(2, 310);
			count = pstmt.executeUpdate();
			System.out.println(count + "개의 행이 변경되었습니다.");
			con.commit();
			
		} catch (SQLException e) {
//			e.printStackTrace();
			try {
				if(con !=null) con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
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
//			addDepartment("점심팀", 100);
			addDepartmentV2("트랜잭션팀", 200);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
