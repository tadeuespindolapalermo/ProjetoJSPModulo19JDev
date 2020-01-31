package br.com.projetojsp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.projetojsp.bean.UsuarioBean;
import br.com.projetojsp.service.UsuarioService;
import br.com.projetojsp.util.LogUtil;

@WebServlet("/salvarUsuario")
@MultipartConfig
public class UsuarioServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L; 
	
	private static final UsuarioService usuarioService = new UsuarioService();
   
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		String user = request.getParameter("user");
		
		executarAcao(acao, user, request, response);
	}	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		String acao = request.getParameter("acao");
		
		String[] atributosUsuario = {"id", "login", "senha", "nome", "cep", "rua", "bairro", "cidade", "estado", "ibge", "ativo", "sexo", "perfil"};
		
		if (acao != null && acao.equalsIgnoreCase("reset")) {
			listarTodosUsuariosRedirecionar(request, response);				
		} else {				
			UsuarioBean usuario = usuarioService.criarUsuario(request, atributosUsuario);			

			usuarioService.validarCamposFormulario(atributosUsuario, request, response, usuario);
			
			boolean loginExiste = false;
			boolean senhaExiste = false;
			
			loginExiste = validarLoginInsert(getValue(request, atributosUsuario[0]), 
					getValue(request, atributosUsuario[1]));
			
			senhaExiste = validarSenhaInsert(getValue(request, atributosUsuario[0]), 
					getValue(request, atributosUsuario[2]));
			
			request.setAttribute("msg", usuarioService.definirMensagem(loginExiste, senhaExiste));
			request.setAttribute("user", usuario);			
			
			if (getValue(request, atributosUsuario[0]) == null 
					|| getValue(request, atributosUsuario[0]).isEmpty() 
					&& validarLoginInsert(getValue(request, atributosUsuario[1])) 
					&& validarSenhaInsert(getValue(request, atributosUsuario[2]))) {
				
				enviarImagem(request, usuario);
				enviarCurriculo(request, usuario);
				
				salvar(usuario);
				
				request.setAttribute("user", null);
				request.setAttribute("msgSalvarAtualizar", "Usu�rio cadastrado com sucesso!");
				
			} else if (getValue(request, atributosUsuario[0]) != null 
					&& !getValue(request, atributosUsuario[0]).isEmpty()) {
				
				loginExiste = validarLoginUpdate(getValue(request, atributosUsuario[0]),
						getValue(request, atributosUsuario[1]));
				
				senhaExiste = validarSenhaUpdate(getValue(request, atributosUsuario[0]),
						getValue(request, atributosUsuario[2]));
				
				boolean atualizou = false;
				
				if (!loginExiste && !senhaExiste) {
					atualizou = true;
					enviarImagem(request, usuario);
					enviarCurriculo(request, usuario);
					atualizar(usuario);	
					request.setAttribute("msgSalvarAtualizar", "Usu�rio atualizado com sucesso!");
				}
				
				if (!atualizou) { 
					request.setAttribute("msg", usuarioService.definirMensagem(loginExiste, senhaExiste));
					request.setAttribute("user", usuario);
				} else {
					request.setAttribute("user", null);
				}
			}				
			listarTodosUsuariosRedirecionar(request, response);
		}	
	}	
	
	public void salvar(UsuarioBean usuario) {
		try {
			usuarioService.salvar(usuario);
		} catch (Exception e) {
			LogUtil.getLogger(UsuarioServlet.class).error(e.getCause().toString());
		}
	}	
	
	private void atualizar(UsuarioBean usuario) {
		try {
			usuarioService.atualizar(usuario);
		} catch (Exception e) {
			LogUtil.getLogger(UsuarioServlet.class).error(e.getCause().toString());
		}
	}	
	
	private void executarAcao(String acao, String user, HttpServletRequest request, HttpServletResponse response) {
		try {
			usuarioService.executarAcao(acao, user, request, response);	
		} catch (Exception e) {
			LogUtil.getLogger(UsuarioServlet.class).error(e.getCause().toString());
		}
	}	
	
	private void listarTodosUsuariosRedirecionar(HttpServletRequest request, HttpServletResponse response) {	
		try {
			usuarioService.listarTodosUsuariosRedirecionar(request, response);
		} catch (Exception e) {
			LogUtil.getLogger(UsuarioServlet.class).error(e.getCause().toString());
		}			
	}		
	
	private void enviarImagem(HttpServletRequest request, UsuarioBean usuario) {
		try {
			usuarioService.enviarImagem(request, usuario);
		} catch (Exception e) {
			LogUtil.getLogger(UsuarioServlet.class).error(e.getCause().toString());
		}
	}
	
	private void enviarCurriculo(HttpServletRequest request, UsuarioBean usuario) {
		try {
			usuarioService.enviarCurriculo(request, usuario);
		} catch (Exception e) {
			LogUtil.getLogger(UsuarioServlet.class).error(e.getCause().toString());
		}
	}
	
	private String getValue(HttpServletRequest request, String valor) {
		return usuarioService.getValue(request, valor);
	}
	
	private boolean validarLoginInsert(String id, String login) {
		Boolean loginValido = Boolean.FALSE;
		try {
			loginValido = usuarioService.validarLoginInsert(id, login);			
		} catch (Exception e) {
			LogUtil.getLogger(UsuarioServlet.class).error(e.getCause().toString());
		}
		return loginValido;
	}

	private boolean validarLoginInsert(String login) {		
		Boolean loginValido = Boolean.FALSE;
		try {
			loginValido = usuarioService.validarLoginInsert(login);			
		} catch (Exception e) {
			LogUtil.getLogger(UsuarioServlet.class).error(e.getCause().toString());
		}
		return loginValido;		
	}

	private boolean validarSenhaInsert(String id, String senha) {
		Boolean loginValido = Boolean.FALSE;
		try {
			loginValido = usuarioService.validarSenhaInsert(id, senha);
		} catch (Exception e) {
			LogUtil.getLogger(UsuarioServlet.class).error(e.getCause().toString());
		}
		return loginValido;		}

	private boolean validarSenhaInsert(String senha) {
		Boolean loginValido = Boolean.FALSE;
		try {
			loginValido = usuarioService.validarSenhaInsert(senha);
		} catch (Exception e) {
			LogUtil.getLogger(UsuarioServlet.class).error(e.getCause().toString());
		}
		return loginValido;	
	}

	private boolean validarLoginUpdate(String id, String login) {
		Boolean loginValido = Boolean.FALSE;
		try {
			loginValido = usuarioService.validarLoginUpdate(id, login);
		} catch (Exception e) {
			LogUtil.getLogger(UsuarioServlet.class).error(e.getCause().toString());
		}
		return loginValido;	
	}

	private boolean validarSenhaUpdate(String id, String senha) {
		Boolean loginValido = Boolean.FALSE;
		try {
			loginValido = usuarioService.validarSenhaUpdate(id, senha);			
		} catch (Exception e) {
			LogUtil.getLogger(UsuarioServlet.class).error(e.getCause().toString());
		}
		return loginValido;	
	}

}
