<%@page import="br.com.projetojsp.bean.UsuarioBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Cadastro de Usuário</title>
		<link rel="stylesheet" href="resources/css/cadastro.css"/>
		<script src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
	</head>
	<a href="acessoliberado.jsp"><img alt="Início" title="Início" src="resources/img/home.png" width="30px" height="30px"></a>
	<a href="index.jsp"><img alt="Sair" title="Sair" src="resources/img/exit.png" width="30px" height="30px"></a>
	<body>
		<div style="text-align: center;">
			<h1>Cadastro de Usuário</h1>
			<div style="color: red;">
				<h3>${msg}</h3>
			</div>
			<div style="color: green;">
				<h3>${msgSalvarAtualizar}</h3>
			</div>
		</div>
		<!-- <form action="salvarUsuario" method="post" id="formUser" enctype="multipart/form-data" onsubmit="return validarCampos() ? true : false;">-->
		<form action="salvarUsuario" method="post" id="formUser" enctype="multipart/form-data">
			<ul class="form-style-1">
				<li>				
					<table>
						<tr>
							<td>Código</td>
							<td><input type="text" readonly="readonly" id="id" name="id" value="${user.id}"/></td>	
							
							<td>Cep</td>
							<td><input type="text" id="cep" name="cep" value="${user.cep}" onblur="consultarCep()" maxlength="9"/></td>	
						</tr>						
						<tr>
							<td>Login</td>
							<td><input type="text" id="login" name="login" value="${user.login}" maxlength="10"/></td>	
							
							<td>Rua</td>
							<td><input type="text" id="rua" name="rua" value="${user.rua}" maxlength="30" /></td>	
						</tr>						
						<tr>
							<td>Senha</td>
							<td><input type="password" id="senha" name="senha" value="${user.senha}" maxlength="10"/></td>	
							
							<td>Bairro</td>
							<td><input type="text" id="bairro" name="bairro" value="${user.bairro}" maxlength="10" /></td>	
						</tr>						
						<tr>
							<td>Nome</td>
							<td><input placeholder="Informe o nome..." type="text" id="nome" name="nome" value="${user.nome}" maxlength="50"/></td>	
							
							<td>Cidade</td>
							<td><input type="text" id="cidade" name="cidade" value="${user.cidade}" maxlength="20"/></td>	
						</tr>						
						<tr>							
							<td>Estado</td>
							<td><input type="text" id="estado" name="estado" value="${user.estado}" maxlength="10"/></td>	
							
							<td>IBGE</td>
							<td><input type="text" id="ibge" name="ibge" value="${user.ibge}" maxlength="20" /></td>
						</tr>						
						<tr>							
							<td>Perfil</td>
							<td>
								<select id="perfil" name="perfil" style="width: 185px;">
									<option value="nao_informado" 
										${user.perfil eq 'nao_informado' ? 'selected' : ''}>Selecione</option>
										
									<option value="administrador" 
										${user.perfil eq 'administrador' ? 'selected' : ''}>Administrador</option>
										
									<option value="secretario" 
										${user.perfil eq 'secretario' ? 'selected' : ''}>Secretário(a)</option>
										
									<option value="gerente" 
										${user.perfil eq 'gerente' ? 'selected' : ''}>Gerente</option>
										
									<option value="funcionario" 
										${user.perfil eq 'funcionario' ? 'selected' : ''}>Funcionário</option>
								</select>
							</td>							
							<td>Sexo</td>
							<td>									
								Masculino
								<input type="radio" ${user.sexo eq 'masculino' ? 'checked' : ''} name="sexo" value="masculino">														
								
								Feminino
								<input type="radio" ${user.sexo eq 'feminino' ? 'checked' : ''} name="sexo" value="feminino">
							</td>
						</tr>						
						<tr>
							<td>Ativo</td>
							<td>
								<input type="checkbox" id="ativo" name="ativo" ${user.ativo ? 'checked' : ''} />															
							</td>
						</tr>						
						<tr>
							<td>Foto</td>
							<td>
								<input type="file" name="foto" value="Foto"/>
							</td>	
						</tr>						
						<tr>
							<td>Currículo</td>
							<td>
								<input type="file" name="curriculo" value="Currículo" />
							</td>	
						</tr>						
						<tr>
							<td></td>
							<td>
								<input type="submit" value="Salvar" style="width: 87px;">
								<input type="submit" value="Cancelar" style="width: 87px;" onclick="document.getElementById('formUser').action = 'salvarUsuario?acao=reset'">
							</td>
						</tr>
					</table>									
				</li>
			</ul>
		</form>
		
		<form method="post" action="pesquisaServlet">
			<ul class="form-style-1">
				<li>
					<table>
						<tr>
							<td>Nome</td>
							<td><input type="text" id="descricaoConsulta" name="descricaoConsulta"></td>
							<td><input type="submit" value="Pesquisar"></td>
						</tr>
					</table>
				</li>
			</ul>
		</form>
		
		<div class="container">		
		<table class="responsive-table" border="1">		
		<caption>Lista de Usuários</caption>	
			<tr>
				<th>ID</th>
				<th>Login</th>
				<th>Nome</th>				
				<th>Foto</th>
				<th>Currículo</th>
				<th>Cep</th>
				<th>Rua</th>
				<th>Bairro</th>
				<th>Cidade</th>
				<th>Estado</th>
				<th>IBGE</th>
				<th>Telefone</th>
				<th>Excluir</th>
				<th>Editar</th>			
			</tr>
			<c:forEach items="${usuarios}" var="user">
				<tr style="text-align: center">
				
					<td><c:out value="${user.id}"></c:out></td>
					<td><c:out value="${user.login}"></c:out></td>
					<td><c:out value="${user.nome}"></c:out></td>					
					
					<c:if test="${!user.fotoBase64Miniatura.isEmpty() && user.fotoBase64Miniatura != null}">
						<td>
							<a href="salvarUsuario?acao=download&tipo=foto&user=${user.id}">
								<img src='<c:out value="${user.fotoBase64Miniatura}"/>' alt="Foto" title="Foto" width="32px" height="32px">
							</a>
						</td>
					</c:if>
					
					<c:if test="${user.fotoBase64Miniatura.isEmpty() || user.fotoBase64Miniatura == null}">
						<td>
							<img src="resources/img/user-padrao.png" alt="Foto" title="Foto" onclick="alert('O usuário não possui foto!')"/>
						</td>
					</c:if>
					
					<c:if test="${!user.curriculoBase64.isEmpty() && user.curriculoBase64 != null}">
						<td>
							<a href="salvarUsuario?acao=download&tipo=curriculo&user=${user.id}">
								<img alt="Currículo" src="resources/img/icon-pdf.png" title="Currículo">
							</a>
						</td>
					</c:if>
					
					<c:if test="${user.curriculoBase64.isEmpty() || user.curriculoBase64 == null}">
						<td>
							<img src="resources/img/ausencia-pdf.png" alt="Currículo" title="Currículo" onclick="alert('O usuário não possui currículo!')"/>
						</td>
					</c:if>
					
					<td><c:out value="${user.cep}"></c:out></td>
					<td><c:out value="${user.rua}"></c:out></td>
					<td><c:out value="${user.bairro}"></c:out></td>
					<td><c:out value="${user.cidade}"></c:out></td>
					<td><c:out value="${user.estado}"></c:out></td>
					<td><c:out value="${user.ibge}"></c:out></td>
					
					<td>
						<a href="salvarTelefone?acao=addFone&user=${user.id}">
							<img src="resources/img/fone.png" alt="Telefones" title="Telefones">
						</a>
					</td>
					
					<td>
						<a href="salvarUsuario?acao=deletar&user=${user.id}" onclick="return confirm('Tem certeza que deseja excluir?');">
							<img src="resources/img/excluir.png" alt="Excluir" title="Excluir">
						</a>
					</td>
					
					<td>
						<a href="salvarUsuario?acao=editar&user=${user.id}">
							<img src="resources/img/editar.png" alt="Editar" title="Editar">
						</a>
					</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		<script type="text/javascript">
			function validarCampos() {
				if(document.getElementById("login").value == '') {
					alert('Informe o Login!');
					return false;
				} else if(document.getElementById("senha").value == '') {
					alert('Informe a Senha');
					return false;
				} else if(document.getElementById("nome").value == '') {
					alert('Informe o Nome!');
					return false;
				} else if(document.getElementById("fone").value == '') {
					alert('Informe o Fone!');
					return false;
				} 
				return true;
			}
			
			function consultarCep() {
				var cep = $("#cep").val();
				
				//Consulta o webservice viacep.com.br/
                $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {
                	console.log(dados);

                	 if (!("erro" in dados)) {
                        //Atualiza os campos com os valores da consulta.
                        $("#rua").val(dados.logradouro);
                        $("#bairro").val(dados.bairro);
                        $("#cidade").val(dados.localidade);
                        $("#estado").val(dados.uf);
                        $("#ibge").val(dados.ibge);
                    } else {     
                    	$("#cep").val("");
                    	$("#rua").val("");
                        $("#bairro").val("");
                        $("#cidade").val("");
                        $("#estado").val("");
                        $("#ibge").val("");
                        alert("CEP não encontrado.");
                    }
                });			
			}
		</script>
	</body>
</html>