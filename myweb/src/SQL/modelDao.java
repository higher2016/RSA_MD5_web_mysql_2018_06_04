package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class modelDao {
	public static boolean addmodel(model model) {
		String sql = "insert into user values('" + model.getUsername() + "','" + model.getPassword() + "','" + model.getEmail() + "','" + model.getDongtai() + "'," + model.getTime() + ")";
		return DaoUtil.insert(sql);
	}

	public static boolean deletemodel(model model) {
		String sql = "delete user where username='" + model.getUsername() + "'";
		return DaoUtil.delete(sql);
	}

	public static boolean modifymodel(model model) {
		String sql = "update user set username='" + model.getUsername() + "',password='" + model.getPassword() + "',email=" + model.getEmail() + ",dongtai='" + model.getDongtai() + "' "
				+ "where username='" + model.getUsername() + "'";
		return DaoUtil.update(sql);
	}

	public static model getmodelByName(String name) {
		String sql = "select * from user where username='" + name + "'";
		ResultSet resultSet = DaoUtil.select(sql);
		model model = new model();
		try {
			if (resultSet.next()) {
				model.setUsername(resultSet.getString("username"));
				model.setPassword(resultSet.getString("password"));
				model.setEmail(resultSet.getString("email"));
				model.setDongtai(resultSet.getString("dongtai"));
				model.setTime(resultSet.getLong("time"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return model;
	}
}
