package br.com.projetojsp.connection;

import java.sql.Connection;
import java.sql.DriverManager;

import br.com.projetojsp.util.LogUtil;

public final class SingleConnection {

	private static String banco = "jdbc:postgresql://localhost:5432/projeto-jsp?autoReconnect=true";
	private static String password = getPassword();
	private static String user = "postgres";
	private static Connection connection = null;

	static {
		conectar();
	}
	
	private static String getPassword() {
		return "postgres" + "1985";
	}

	private SingleConnection() { }

	private static void conectar() {
		try {
			if (connection == null) {	
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(banco, user, password);
				connection.setAutoCommit(false);
			}
		} catch (Exception e) {
			LogUtil.getLogger(SingleConnection.class).error(e.getCause().toString());			
		}
	}

	public static Connection getConnection() {
		return connection;
	}

}
