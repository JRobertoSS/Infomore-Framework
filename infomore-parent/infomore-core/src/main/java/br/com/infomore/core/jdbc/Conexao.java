package br.com.infomore.core.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	public static Connection getConnection() {

		try {
			Class.forName("org.gjt.mm.mysql.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/infomore", "root", "12345");
		} catch (SQLException e) {
			// relanando a exception
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
