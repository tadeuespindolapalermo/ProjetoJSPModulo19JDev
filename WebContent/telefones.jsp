<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Cadastro de Telefone</title>
		<link rel="stylesheet" href="resources/css/cadastro.css"/>		
	</head>
	<a href="acessoliberado.jsp"><img alt="Início" title="Início" src="resources/img/home.png" width="30px" height="30px"></a>
	<a href="index.jsp"><img alt="Sair" title="Sair" src="resources/img/exit.png" width="30px" height="30px"></a>
	<body>
		<div style="text-align: center;">
			<h1>Cadastro de Telefone</h1>
			<div style="color: red;">
				<h3>${msg}</h3>
			</div>
			<div style="color: green;">
				<h3>${msgSucesso}</h3>
			</div>
		</div>
		<!-- <form action="salvarTelefone" method="post" id="formFone" onsubmit="return validarCampos() ? true : false;">-->
		<form action="salvarTelefone" method="post" id="formFone">
			<ul class="form-style-1">
				<li>
				
					<table>				
						
						<tr>
							<td>Usuário ID</td>
							<td><input class="field-long" type="text" readonly="readonly" id="id" name="id" value="${userEscolhido.id}"/></td>									
						</tr>
						
						<tr>						
							<td>Usuário Nome</td>
							<td><input class="field-long" type="text" readonly="readonly" id="nome" name="nome" value="${userEscolhido.nome}"/></td>											
						</tr>
						
						<tr>
							<td>Número</td>
							<td><input class="field-long" type="text" id="numero" name="numero" value=""/></td>																				
						</tr>												
						
						<tr>						
							<td>Tipo</td>
							<td>
								<select class="field-long" id="tipo" name=tipo>
									<option>Casa</option>
									<option>Contato</option>
									<option>Celular</option>
								</select>
							</td>																				
						</tr>							
						
						<tr>						
							<td></td>
							<td>
								<input type="submit" value="Salvar" style="width: 87px;">
								<input type="submit" value="Voltar" style="width: 87px;" onclick="document.getElementById('formFone').action = 'salvarTelefone?acao=voltar'">
							</td>							
						</tr>
					</table>
									
				</li>
			</ul>
		</form>
		<div class="container">		
		<table class="responsive-table" border="1">		
		<caption>Lista de Telefones</caption>	
			<tr>
				<th>ID</th>
				<th>Número</th>		
				<th>Tipo</th>		
				<th>Excluir</th>	
			</tr>
			<c:forEach items="${telefones}" var="fone">
				<tr style="text-align: center">
				
					<td><c:out value="${fone.id}"></c:out></td>
					<td><c:out value="${fone.numero}"></c:out></td>		
					<td><c:out value="${fone.tipo}"></c:out></td>				
					
					<td>
						<a href="salvarTelefone?acao=deletarFone&foneId=${fone.id}" onclick="return confirm('Tem certeza que deseja excluir?');">
							<img src="resources/img/excluir.png" alt="Excluir" title="Excluir">
						</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		</div>
		<script type="text/javascript">
			function validarCampos() {
				if(document.getElementById("numero").value == '') {
					alert('Informe o Número!');
					return false;
				} else if(document.getElementById("tipo").value == '') {
					alert('Informe o Tipo!');
					return false;
				} 
				return true;
			}		
		</script>
	</body>
</html>