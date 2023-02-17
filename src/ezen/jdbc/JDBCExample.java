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
 * @Author 김재훈
 * @Date 2023. 2. 17.
 */
public class JDBCExample {

	public static void main(String[] args) {
//		Socket socket = new Socket("localhost", 1521);
//		System.out.println("오라클 서버와 연결됨.");
		// 오라클이 어떤 응용 프로토콜을 이용하여 데이터 송수신을 하는지 알 수가 없다.
		
		// JDBC 표준 API를 이용한 연결
		// #1. 오라클 드라이버 메모리 로드, 드라이버 객체 생성 및 DriverManager에 생성된 드라이버 객체 등록.
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe"; //Oracle
		String user = "hr";
		String password = "hr";
		
		try {
//			Driver driver = new OracleDriver(); Driver는 자바에서 만든거 OracleDriver는 오라클이 만든 드라이버 객체. 
			// 원래 객체 내가 직접 만드는건데 그렇게 안 하고 OracleDriver 클래스 안에서 만듦. 드라이버가 로드될 때 이렇게 되는 것.
			// "oracle.jdbc.driver.OracleDriver" 요것을 통해 드라이버 불러오면서 그렇게 하는듯.
			Class.forName(driver);

			System.out.println("오라클 드라이버 로딩 완료.");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("오라클 드라이버를 찾을 수 없습니다.");
		}
		
		// #2. 오라클 서버 연결
		Connection con = null;
		try {
			// 이 코드 안에서 유일한 클래스는 이것. 나머지는 인터페이스로 동작. 
			con = DriverManager.getConnection(url, user, password); 
			System.out.println("오라클 서버와 연결됨.");
			System.out.println("반환받은 커넥션 객체: " + con); //T4CConnection도 클래스 이름.
		} catch (SQLException e) {
			System.out.println("오라클 서버와 연결할 수 없습니다.");
		}
		
		// #3. SQL 전송 및 결과집합 수신
		String sql = "SELECT employee_id, last_name FROM employees";
		try {
//			Statement stmt= con.createStatement();
			PreparedStatement stmt = con.prepareStatement(sql);
			System.out.println("stmt찍기: "+ stmt); //실제 동작 객체: OracleStatementWrapper
			
			// SQL을 서버에 전송하고, 수신한 결과집합. rs에 결과가 들어있는 것. 
			ResultSet rs= stmt.executeQuery(sql); 
			System.out.println("rs 찍기: "+ rs);
			
			// #4. 결과집합에서 원하는 데이터 추출
			while(rs.next()) {
				// 컬럼의 순서로 데이터 읽기. 
//				int employeeId = rs.getInt(1); // 컬럼의 순서
				//컬럼 이름으로 데이터 읽기(확실함).
				int employeeId= rs.getInt("employee_id"); //getInt 오라클 Number를 int로 형변환 메소드.
				String lastName = rs.getString("last_name");
				System.out.println(employeeId + ", " + lastName);
			}
			
			// #5. connection 종료 (소켓 통신 종료)
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
