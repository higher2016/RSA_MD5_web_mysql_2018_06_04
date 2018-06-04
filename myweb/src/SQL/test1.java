package SQL;

import java.sql.ResultSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class test1 {

	String sql = "";

	public model getmodel(String username) {
		baseSQL BaseSQL = new baseSQL();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		model Model = null;
		try {
			conn = (Connection) BaseSQL.getConnection();
			stmt = (Statement) conn.createStatement();

			String sql = "select email from user where username='" + username + "'";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Model = new model();
				Model.setUsername(rs.getString(username));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseSQL.closeAll(conn, stmt, rs);
		}
		return Model;
	}
}
