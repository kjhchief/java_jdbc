package ezen.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectExample {
	// DB 연결정보 상수
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:xe"; // Oracle
	private static final String user = "hr";
	private static final String password = "hr";

	public static void findAll() throws ClassNotFoundException {
		Class.forName(driver);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(url, user, password);
			StringBuilder sb = new StringBuilder(); // 자체편집 가능해서 이거 쓴다고 함. 뭐지? 왜??
			sb.append(" SELECT department_id, department_name, manager_id, location_id")
       		  .append(" FROM departments") 
			  .append(" ORDER BY department_id"); 
			pstmt = con.prepareStatement(sb.toString());
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int departmentId = rs.getInt("department_id");
				String departmentName= rs.getString("department_name");
				int managerId= rs.getInt("manager_id");
				int locationId = rs.getInt("location_id");
				System.out.println(departmentId + "\t" + departmentName + "\t" + managerId + "\t" + locationId);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();	
				if(pstmt != null) pstmt.close();	
				if(con != null) con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// 부서 이름으로 검색
	public static void findByName(String name) throws ClassNotFoundException {
		Class.forName(driver);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(url, user, password);
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT department_id, department_name, manager_id, location_id")
       		  .append(" FROM departments") 
			  .append(" WHERE department_name LIKE ?") 
			  .append(" ORDER BY department_id"); 
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, "%"+name+"%");
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int departmentId = rs.getInt("department_id");
				String departmentName= rs.getString("department_name");
				int managerId= rs.getInt("manager_id");
				int locationId = rs.getInt("location_id");
				System.out.println(departmentId + "\t" + departmentName + "\t" + managerId + "\t" + locationId);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();	
				if(pstmt != null) pstmt.close();	
				if(con != null) con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// 사원번호를 입력받아 해당사원의 사원번호, 성, 이름, 급여, 입사일자, 부서명 조회
	public static void findEmployee(int employeeId) throws ClassNotFoundException {
		Class.forName(driver);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(url, user, password);
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT e.employee_id id, e.last_name lname, e.first_name fname, e.salary salary, e.hire_date hiredate, d.department_name dname")
       		  .append(" FROM employees e JOIN departments d") 
			  .append(" ON e.employee_id = d.department_id") 
			  .append(" WHERE e.employee_id = ?"); 
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setInt(1, employeeId);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int empId = rs.getInt("id");
				String lastName= rs.getString("lname");
				String firstName= rs.getString("fname");
				int salary = rs.getInt("salary");
				Date hireDate = rs.getDate("hiredate");
				String departmentName = rs.getString("dname");
				
				System.out.println(empId + "\t" + lastName + "\t" + firstName + "\t" + salary + "\t" + hireDate + "\t" + departmentName);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();	
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
			// 부서 전체 목록 출력
//			findAll(); 
			findEmployee(100);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
