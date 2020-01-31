package br.com.projetojsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.projetojsp.connection.SingleConnection;

public class LoginDAO {
	
	private Connection connection;
	
	public LoginDAO() {
		connection = SingleConnection.getConnection();
	}
	
	public boolean validarLogin(String login, String senha) throws SQLException {		
		String sql = "SELECT * FROM usuario WHERE login = '" + login + "' AND senha = '" + senha + "'";
		try (PreparedStatement statement = connection.prepareStatement(sql);) {
			try (ResultSet result = statement.executeQuery()) {
				return result.next() ? Boolean.TRUE : Boolean.FALSE;
			}			
		}			
	}	
	
}
