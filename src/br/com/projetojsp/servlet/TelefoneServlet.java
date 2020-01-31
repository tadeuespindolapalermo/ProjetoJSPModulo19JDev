package br.com.projetojsp.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.projetojsp.bean.TelefoneBean;
import br.com.projetojsp.bean.UsuarioBean;
import br.com.projetojsp.dao.TelefoneDAO;
import br.com.projetojsp.service.UsuarioService;
import br.com.projetojsp.util.LogUtil;

@WebServlet("/salvarTelefone")
public class TelefoneServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;      
	
	private static final UsuarioService usuarioService = new UsuarioService();
	
	private final TelefoneDAO telefoneDAO = new TelefoneDAO();    
	
	private static final String USER_ESCOLHIDO = "userEscolhido";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String user = request.getParameter("user");	
		String acao = request.getParameter("acao");		
		
		try {
			UsuarioBean usuario = acao.equalsIgnoreCase("deletarFone") 
				? (UsuarioBean) request.getSession().getAttribute(USER_ESCOLHIDO) 
				: usuarioService.consultarPorId(user);
			redirecionarUsuario(acao, usuario, request, response);	
		} catch (Exception e) {
			try {
				listarTodosUsuarios(request, response);
			} catch (Exception e1) {				
				LogUtil.getLogger(TelefoneServlet.class).error(e1.getCause().toString());
			} 
		}	
	}	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String numero = request.getParameter("numero");
			String tipo = request.getParameter("tipo");	
			UsuarioBean usuario = (UsuarioBean) request.getSession().getAttribute(USER_ESCOLHIDO);		
			String acao = request.getParameter("acao");
			
			if (acao != null && acao.equalsIgnoreCase("voltar")) {
				listarTodosUsuarios(request, response);
				return;
			}
			
			if (numero == null || numero.isEmpty()) {
				request.setAttribute("msg", "O campo telefone � de preenchimento obrigat�rio!"); 
				listarTodosTelefonesUsuario(usuario, request, response);
				return;
			}
			
			TelefoneBean telefone = criarTelefone(numero, tipo, usuario.getId());
			
			telefoneDAO.salvar(telefone);
			request.setAttribute("msgSucesso", "Telefone registrado com sucesso!!!"); 		
			
			listarTodosTelefonesUsuario(usuario, request, response);
		} catch (Exception e) {
			LogUtil.getLogger(TelefoneServlet.class).error(e.getCause().toString());
		} 				
	}
	
	private void listarTodosUsuarios(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
		request.setAttribute("usuarios", usuarioService.listarTodos());
		view.forward(request, response);		
	}
	
	private TelefoneBean criarTelefone(String numero, String tipo, Long usuario) {
		TelefoneBean telefone = new TelefoneBean();		
		telefone.setNumero(numero);
		telefone.setTipo(tipo);
		telefone.setUsuario(usuario);
		return telefone;
	}
	
	private void redirecionarUsuario(String acao, UsuarioBean usuario, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {	
		
		if (acao != null) {
			
			if (acao.equalsIgnoreCase("addFone")) {
				listarTodosTelefonesUsuario(usuario, request, response);
			} 
			
			if (acao.equalsIgnoreCase("deletarFone")) {
				deletarTelefone(usuario, request, response);
			} 
		}
		
		listarTodosTelefonesUsuario(usuario, request, response);			
	}
	
	private void deletarTelefone(UsuarioBean usuario, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {	
		String foneId = request.getParameter("foneId");
		telefoneDAO.deletar(foneId);
		request.setAttribute("msgSucesso", "Telefone exclu�do com sucesso!"); 
		listarTodosTelefonesUsuario(usuario, request, response);
	}	
	
	private void listarTodosTelefonesUsuario(UsuarioBean usuario, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
		request.getSession().setAttribute(USER_ESCOLHIDO, usuario);	
		request.setAttribute(USER_ESCOLHIDO, usuario);
		request.setAttribute("telefones", telefoneDAO.listarTelefonesUsuario(usuario.getId()));
		view.forward(request, response);		
	}	

}
