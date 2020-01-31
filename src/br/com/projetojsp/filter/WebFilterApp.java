package br.com.projetojsp.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import br.com.projetojsp.connection.SingleConnection;
import br.com.projetojsp.util.LogUtil;

@WebFilter(urlPatterns = {"/*"})
public class WebFilterApp implements Filter {
	
	private Connection connection;	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		try {
			chain.doFilter(request, response);
			connection.commit();
		} catch (Exception e) {
			LogUtil.getLogger(WebFilterApp.class).error(e.getCause().toString());	
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LogUtil.getLogger(WebFilterApp.class).error(e1.getCause().toString());	
			}
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		connection = SingleConnection.getConnection();
	}

}
