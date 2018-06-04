package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class baseSQL {

	private String driver = "com.mysql.jdbc.Driver";// ���ݿ������ַ���
	private String url = "jdbc:mysql://localhost:3306;DatabaseName=mymy";// ����url�ַ���
	private String user = "root";// ���ݿ��û���
	private String password = "mengyuan";// ���ݿ�����
	Connection conn = null; // �������Ӷ���
	/*
	 * ��ȡ���ݿ����Ӷ���
	 */

	public Connection getConnection() {
		if (conn == null) {
			// ��ȡ���Ӳ������쳣
			try {
				Class.forName(driver);
				conn = DriverManager.getConnection(url, user, password);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return conn; // �������Ӷ���
	}

	public void closeAll(Connection conn, Statement stmt, ResultSet rs) {
		// �����������Ϊ�գ���ر�
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// ��Statement����Ϊ�գ���ر�
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// ���������Ӷ���Ϊ����ر�
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public int executeUPdate(String preparedSql, Object[] param) {
		PreparedStatement pstmt = null;
		int num = 0;
		conn = getConnection();
		try {
			pstmt = conn.prepareStatement(preparedSql);
			if (param != null) {
				for (int i = 0; i < param.length; i++) {

					pstmt.setObject(i + 1, param[i]);
				}
			}
			num = pstmt.executeUpdate();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, null);
		}
		return num;
	}
}
