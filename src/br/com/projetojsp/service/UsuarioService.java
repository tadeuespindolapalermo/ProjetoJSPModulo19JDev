package br.com.projetojsp.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import br.com.projetojsp.bean.UsuarioBean;
import br.com.projetojsp.dao.UsuarioDAO;
import br.com.projetojsp.exception.OrphanRemovalException;
import br.com.projetojsp.util.LogUtil;

public class UsuarioService {

	private static final UsuarioDAO usuarioDAO = new UsuarioDAO();

	private static final String STRING_VAZIA = "";

	private static final String USUARIOS_ATTRIBUTE = "usuarios";
	
	private static final String PAGINA_CADASTRO_USUARIO = "cadastroUsuario.jsp";

	public void salvar(UsuarioBean usuario) throws SQLException {
		usuarioDAO.salvar(usuario);		
	}

	private Boolean deletar(String id) throws OrphanRemovalException, SQLException {
		return usuarioDAO.deletar(id);		
	}
	
	private void validarUsuario(String user, HttpServletRequest request) {
		UsuarioBean usuario = consultarPorId(user);
		if (usuario != null) {
			request.setAttribute("msg", "Existe telefones cadastrados para o usu�rio " + 
				usuario.getNome());
		}
	}		
	
	private void editarUsuarioRedirecionar(String user, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		String[] attributes = {USUARIOS_ATTRIBUTE, "user"};
		UsuarioBean usuario = consultarPorId(user);
		redirecionarUsuario(attributes, request, response, STRING_VAZIA, usuario, PAGINA_CADASTRO_USUARIO);						
	}
	
	public void listarTodosUsuariosRedirecionar(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		String[] attributes = {USUARIOS_ATTRIBUTE};
		redirecionarUsuario(attributes, request, response, STRING_VAZIA, new UsuarioBean(), PAGINA_CADASTRO_USUARIO);		
	}

	public void atualizar(UsuarioBean usuario) throws SQLException {
		usuarioDAO.atualizar(usuario);
	}
	
	public UsuarioBean criarUsuario(HttpServletRequest request, String[] dadosUsuario) {		
		UsuarioBean usuario = new UsuarioBean();			
		usuario.setId(!getValue(request, dadosUsuario[0]).isEmpty() 
			? Long.parseLong(getValue(request, dadosUsuario[0])) : null);
		usuario.setLogin(getValue(request, dadosUsuario[1]));
		usuario.setSenha(getValue(request, dadosUsuario[2]));
		usuario.setNome(getValue(request, dadosUsuario[3]));
		usuario.setCep(getValue(request, dadosUsuario[4]));
		usuario.setRua(getValue(request, dadosUsuario[5]));
		usuario.setBairro(getValue(request, dadosUsuario[6]));
		usuario.setCidade(getValue(request, dadosUsuario[7]));
		usuario.setEstado(getValue(request, dadosUsuario[8]));
		usuario.setIbge(getValue(request, dadosUsuario[9]));		
		usuario.setAtivo(verificarStatus(getValue(request, dadosUsuario[10])));
		usuario.setSexo(getValue(request, dadosUsuario[11]));
		usuario.setPerfil(getValue(request, dadosUsuario[12]));	
		return usuario;		
	}

	public UsuarioBean consultarPorId(String id) {
		UsuarioBean usuario = null;
		try {
			usuario = usuarioDAO.consultarPorId(id);
		} catch (Exception e) {
			LogUtil.getLogger(UsuarioService.class).error(e.getCause().toString());
		}
		return usuario;
	}
	
	public List<UsuarioBean> listarPorNome(String descricaoConsulta) {
		List<UsuarioBean> usuarios = null;
		try {
			usuarios = usuarioDAO.listarPorNome(descricaoConsulta);
		} catch (Exception e) {
			LogUtil.getLogger(UsuarioService.class).error(e.getCause().toString());
		}
		return usuarios;
	}

	public List<UsuarioBean> listarTodos() {
		List<UsuarioBean> usuarios = null;
		try {
			usuarios = usuarioDAO.listarTodos();
		} catch (Exception e) {
			LogUtil.getLogger(UsuarioService.class).error(e.getCause().toString());
		}
		return usuarios;
	}
	
	private void redirecionarUsuarioValidarCamposFormulario(UsuarioBean usuario, String campo, 
			HttpServletRequest request, HttpServletResponse response) {	
		String[] attributes = {USUARIOS_ATTRIBUTE, "msg", "user"};
		try {
			redirecionarUsuario(attributes, request, response, campo, usuario, PAGINA_CADASTRO_USUARIO);			
		} catch (Exception e) {
			LogUtil.getLogger(UsuarioService.class).error(e.getCause().toString());
		}					
	}
	
	public String getValue(HttpServletRequest request, String valor) {
		return request.getParameter(valor);
	}
	
	public void validarCamposFormulario(String[] dadosUsuario,
			HttpServletRequest request, HttpServletResponse response, UsuarioBean usuario) {		
		if(getValue(request, dadosUsuario[1]) == null || getValue(request, dadosUsuario[1]).isEmpty()) {
			redirecionarUsuarioValidarCamposFormulario(usuario, "login", request, response);
			return;
		} else if(getValue(request, dadosUsuario[2]) == null || getValue(request, dadosUsuario[2]).isEmpty()) {
			redirecionarUsuarioValidarCamposFormulario(usuario, "senha", request, response);
			return;
		} else if(getValue(request, dadosUsuario[3]) == null || getValue(request, dadosUsuario[3]).isEmpty()) {
			redirecionarUsuarioValidarCamposFormulario(usuario, "nome", request, response);
			return;
		} 
	}	

	public void enviarImagem(HttpServletRequest request, UsuarioBean usuario) throws IOException, ServletException {
		if (ServletFileUpload.isMultipartContent(request)) {

			Part imagemFoto = request.getPart("foto");

			if (imagemFoto != null && imagemFoto.getInputStream().available() > 0) {

				String fotoBase64 = Base64.encodeBase64String(converteStremParaByte(imagemFoto.getInputStream()));

				usuario.setFotoBase64(fotoBase64);
				usuario.setContentType(imagemFoto.getContentType());
				usuario.setFotoBase64Miniatura(criarMiniaturaImagem(fotoBase64));
			} else {
				usuario.setAtualizarImagem(Boolean.FALSE);
			}
		}
	}

	// Converte a entrada de fluxo de dados da imagem para byte[]
	private byte[] converteStremParaByte(InputStream imagem) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = imagem.read();
		while (reads != -1) {
			baos.write(reads);
			reads = imagem.read();
		}
		return baos.toByteArray();
	}

	private String processarCriacaoMiniaturaImagem(String fotoBase64) throws IOException {
		String miniaturaBase64 = "";

		byte[] imageByteDecode = Base64.decodeBase64(fotoBase64);

		BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode));

		int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

		BufferedImage resizedImage = new BufferedImage(100, 100, type);

		Graphics2D g = resizedImage.createGraphics();

		g.drawImage(bufferedImage, 0, 0, 100, 100, null);

		g.dispose();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(resizedImage, "png", baos);

		miniaturaBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(baos.toByteArray());

		return miniaturaBase64;
	}

	private String criarMiniaturaImagem(String fotoBase64) {
		try {
			return processarCriacaoMiniaturaImagem(fotoBase64);
		} catch (IOException e) {
			LogUtil.getLogger(UsuarioService.class).error(e.getCause().toString());
		}
		return STRING_VAZIA;
	}

	public void enviarCurriculo(HttpServletRequest request, UsuarioBean usuario) throws IOException, ServletException {
		if (ServletFileUpload.isMultipartContent(request)) {

			Part curriculo = request.getPart("curriculo");

			if (curriculo != null && curriculo.getInputStream().available() > 0) {
				String curriculoBase64 = Base64.encodeBase64String(converteStremParaByte(curriculo.getInputStream()));

				usuario.setCurriculoBase64(curriculoBase64);
				usuario.setContentTypeCurriculo(curriculo.getContentType());
			} else {
				usuario.setAtualizarCurriculo(Boolean.FALSE);
			}
		}
	}

	private String declararMensagemParaUsuario(String campo) {
		return "O campo " + campo.toUpperCase() + " � de preenchimento o obrigat�rio!";
	}

	public boolean validarLoginInsert(String id, String login) throws SQLException {
		return id == null || id.isEmpty() && !usuarioDAO.validarLoginInsert(login);
	}

	public boolean validarLoginInsert(String login) throws SQLException {
		return usuarioDAO.validarLoginInsert(login);
	}

	public boolean validarSenhaInsert(String id, String senha) throws SQLException {
		return id == null || id.isEmpty() && !usuarioDAO.validarSenhaInsert(senha);
	}

	public boolean validarSenhaInsert(String senha) throws SQLException {
		return usuarioDAO.validarSenhaInsert(senha);
	}

	public boolean validarLoginUpdate(String id, String login) throws SQLException {
		return !usuarioDAO.validarLoginUpdate(login, id);
	}

	public boolean validarSenhaUpdate(String id, String senha) throws SQLException {
		return !usuarioDAO.validarSenhaUpdate(senha, id);
	}

	private void redirecionarUsuario(String[] attributes, HttpServletRequest request, HttpServletResponse response,
			String campo, UsuarioBean usuario, String pagina) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("/" + pagina);
		for (String attribute : attributes) {
			if (attribute.equals(USUARIOS_ATTRIBUTE)) {
				request.setAttribute(USUARIOS_ATTRIBUTE, listarTodos());
			}
			if (attribute.equals("msg")) {
				request.setAttribute("msg", declararMensagemParaUsuario(campo));
			}
			if (attribute.equals("user")) {
				request.setAttribute("user", usuario);
			}
		}
		view.forward(request, response);
	}
	
	public void executarAcao(String acao, String user, HttpServletRequest request, 
			HttpServletResponse response) throws IOException, ServletException, SQLException {		
		
		String[] attributesDeletarUsuario = {USUARIOS_ATTRIBUTE};
		
		if (acao != null) {
			
			if (acao.equalsIgnoreCase("deletar")) {
				deletarUsuarioRedirecionar(user, request, response, attributesDeletarUsuario);
				return;
			} 
			
			if (acao.equalsIgnoreCase("editar")) {
				editarUsuarioRedirecionar(user, request, response);				
				return;
			} 
			
			if (acao.equalsIgnoreCase("listarTodos")) {
				listarTodosUsuariosRedirecionar(request, response);
				return;
			} 
			
			if (acao.equalsIgnoreCase("download")) {
				fazerDownload(user, request, response);
			}
		}		
	}
	
	private Boolean deletarUsuario(String user) throws SQLException {		
		Boolean usuarioDeletado = Boolean.FALSE;
		try {
			usuarioDeletado = deletar(user);
		} catch (OrphanRemovalException e) {
			LogUtil.getLogger(UsuarioService.class).error(e.getCause().toString());
		}		
		return usuarioDeletado;
	}
	
	private void deletarUsuarioRedirecionar(String user, HttpServletRequest request, HttpServletResponse response,
			String[] attributesDeletarUsuario) throws ServletException, IOException, SQLException {
		if (deletarUsuario(user) != null) {
			redirecionarUsuario(attributesDeletarUsuario, request, response, STRING_VAZIA, new UsuarioBean(), PAGINA_CADASTRO_USUARIO);			
		}
		validarUsuario(user, request);
	}	
	
	private Boolean verificarStatus(String ativo) {
		return ativo != null && ativo.equalsIgnoreCase("on");
	}
	
	public String definirMensagem(boolean login, boolean senha) {
		
		String msg = "";
		
		if (login && !senha) {
			msg = "O login informado j� existe na base de dados para outro usu�rio!";
		}
		
		if (!login && senha) {
			msg = "A senha informada j� existe na base de dados para outro usu�rio!";
		}
		
		if (login && senha) {
			msg = "A senha e login informados j� existem na base de dados para outro usu�rio!";
		}		
		return msg;
	}

	private void fazerDownload(String user, HttpServletRequest request, HttpServletResponse response) throws IOException {

		UsuarioBean usuario = consultarPorId(user);

		if (usuario != null) {

			String contentType = "";
			byte[] fileBytes = null;
			String tipo = request.getParameter("tipo");

			if (tipo.equalsIgnoreCase("foto")) {
				contentType = usuario.getContentType();
				fileBytes = Base64.decodeBase64(usuario.getFotoBase64());
			}

			if (tipo.equalsIgnoreCase("curriculo")) {
				contentType = usuario.getContentTypeCurriculo();
				fileBytes = Base64.decodeBase64(usuario.getCurriculoBase64());
			}

			response.setHeader("Content-Disposition", "attachment;filename=arquivo." + contentType.split("\\/")[1]);

			InputStream is = new ByteArrayInputStream(fileBytes);
			int read = 0;
			byte[] bytes = new byte[1024];

			
			OutputStream os = response.getOutputStream();
			while ((read = is.read(bytes)) != -1) {
				os.write(bytes, 0, read);
			}
			os.flush();
			os.close();			
		}
	}

}
