package br.com.projetojsp.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.projetojsp.bean.UsuarioBean;
import br.com.projetojsp.service.UsuarioService;
import br.com.projetojsp.util.LogUtil;

@WebServlet("/pesquisaServlet")
public class PesquisaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final UsuarioService usuarioService = new UsuarioService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			String descricaoPesquisa = request.getParameter("descricaoConsulta");
			
			if (descricaoPesquisa != null && !descricaoPesquisa.trim().isEmpty()) {
				listarTodosUsuarios(request, response, usuarioService.listarPorNome(descricaoPesquisa));
				return;
			}
			listarTodosUsuarios(request, response, usuarioService.listarTodos());
		} catch (Exception e) {
			LogUtil.getLogger(PesquisaServlet.class).error(e.getCause().toString());
		}	
	}

	private void listarTodosUsuarios(HttpServletRequest request, HttpServletResponse response,
			List<UsuarioBean> usuarios) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
		request.setAttribute("usuarios", usuarios);
		view.forward(request, response);
	}

}
