package br.com.projetojsp.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.projetojsp.dao.LoginDAO;
import br.com.projetojsp.util.LogUtil;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final LoginDAO loginDAO = new LoginDAO();

	public LoginServlet() {
		super();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");

			if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
				if (loginDAO.validarLogin(login, senha)) {
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("acessoliberado.jsp");
					requestDispatcher.forward(request, response);
				} else {
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("acessonegado.jsp");
					requestDispatcher.forward(request, response);
				}
			} else {
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
				requestDispatcher.forward(request, response);
			}
		} catch (Exception e) {
			LogUtil.getLogger(LoginServlet.class).error(e.getCause().toString());
		}
	}

}
