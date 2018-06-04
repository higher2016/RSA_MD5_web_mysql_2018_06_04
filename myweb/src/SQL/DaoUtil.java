package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoUtil {
	private static Connection conn;

	static {
		try {
			String driver = "com.mysql.jdbc.Driver";// ���ݿ������ַ���
			Class.forName(driver);
			String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8";// ����url�ַ���
			String user = "root";// ���ݿ��û���
			String password = "1234";// ���ݿ�����
//			String url = "jdbc:mysql://localhost:3306;DatabaseName=mymy";// ����url�ַ���
//			String user = "root";// ���ݿ��û���
//			String password = "mengyuan";// ���ݿ�����
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean execPreStatement(PreparedStatement pre) throws SQLException {
		return pre.execute();
	}

	public static ResultSet select(String sql) {
		Statement statement = null;
		try {
			statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean insert(String sql) {
		Statement statement;
		try {
			statement = conn.createStatement();
			return statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public static boolean delete(String sql) {
		Statement statement;
		try {
			statement = conn.createStatement();
			return statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public static boolean update(String sql) {
		Statement statement;
		try {
			statement = conn.createStatement();
			boolean flag = statement.execute(sql);
			conn.commit();
			return flag;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}
}
