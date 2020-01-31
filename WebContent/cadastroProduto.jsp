<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Cadastro de Produto</title>
		<link rel="stylesheet" href="resources/css/cadastro.css"/>
		<script src="resources/js/jquery.min.js" type="text/javascript"></script>
  		<script src="resources/js/jquery.maskMoney.min.js" type="text/javascript"></script>
	</head>
	<a href="acessoliberado.jsp"><img alt="Início" title="Início" src="resources/img/home.png" width="30px" height="30px"></a>
	<a href="index.jsp"><img alt="Sair" title="Sair" src="resources/img/exit.png" width="30px" height="30px"></a>
	<body>
		<div style="text-align: center;">
			<h1>Cadastro de Produto</h1>
			<div style="color: red;">
				<h3>${msg}</h3>
			</div>
		</div>
		<!-- <form action="salvarProduto" method="post" id="formProd" onsubmit="return validarCampos() ? true : false;">-->
		<form action="salvarProduto" method="post" id="formProd">
			<ul class="form-style-1">
				<li>
				
					<table>
						<tr>
							<td>Código</td>
							<td><input class="field-long" type="text" readonly="readonly" id="id" name="id" value="${prod.id}"/></td>	
						</tr>
						
						<tr>
							<td>Nome</td>
							<td>
								<input type="text" id="nome" name="nome" value="${prod.nome}" maxlength="100"/>
							</td>	
						</tr>
						
						<tr>
							<td>Quantidade</td>
							<td>
								<input type="text" id="quantidade" name="quantidade" value="${prod.quantidade}" maxlength="7"/>
							</td>	
						</tr>
						
						<tr>
							<td>Valor</td>
							<td>
								<input type="text" id="valor" name="valor" value="${prod.valorTexto}" maxlength="10"
									data-thousands="." data-decimal="," data-prefix="R$"/>
							</td>	
						</tr>		
						
						<tr>
							<td>Categoria</td>
							<td>
								<select id="categorias" name="categoria">
									<option value="nao_informado">
										Selecione
									</option> 
									<c:forEach items="${categorias}" var="cat">
										<option value="${cat.id}" id="${cat.id}" 
											<c:if test="${cat.id == prod.categoria}">
												<c:out value="selected=selected"/>
											</c:if>>
											${cat.nome}
										</option>
									</c:forEach>
								</select>
							</td>
						</tr>			
						
						<tr>
							<td></td>
							<td>
								<input type="submit" value="Salvar" style="width: 87px;">
								<input type="submit" value="Cancelar" style="width: 87px;" 
									onclick="document.getElementById('formProd').action = 'salvarProduto?acao=reset'">
							</td>
						</tr>
					</table>
									
				</li>
			</ul>
		</form>
		<div class="container">		
		<table class="responsive-table" border="1">		
		<caption>Lista de Produtos</caption>	
			<tr>
				<th>ID</th>
				<th>Nome</th>
				<th>Quantidade</th>
				<th>Valor</th>
				<th>Excluir</th>
				<th>Editar</th>
			</tr>
			<c:forEach items="${produtos}" var="prod">
				<tr style="text-align: center">
					<td><c:out value="${prod.id}"></c:out></td>
					<td><c:out value="${prod.nome}"></c:out></td>
					<td><c:out value="${prod.quantidade}"></c:out></td>
					<td><fmt:formatNumber type="number" maxFractionDigits="6"
						value="${prod.valor}"/></td>
					
					<td>
						<a href="salvarProduto?acao=deletar&prod=${prod.id}" onclick="return confirm('Tem certeza que deseja excluir?');">
							<img src="resources/img/excluir.png" alt="Excluir" title="Excluir">
						</a>
					</td>
					
					<td>
						<a href="salvarProduto?acao=editar&prod=${prod.id}">
							<img src="resources/img/editar.png" alt="Editar" title="Editar">
						</a>
					</td>				
				</tr>
			</c:forEach>
		</table>
		</div>
		<script type="text/javascript">
			function validarCampos() {
				if(document.getElementById("nome").value == '') {
					alert('Informe o Nome!');
					return false;
				} else if(document.getElementById("quantidade").value == '') {
					alert('Informe a Quantidade');
					return false;
				} else if(document.getElementById("valor").value == '') {
					alert('Informe o Valor!');
					return false;
				}
				return true;
			}
		</script>
	</body>
	<script>
	  $(function() {
	    $('#valor').maskMoney();
	  })
	  
	  $(document).ready(function() {
		  $("#quantidade").keyup(function() {
			  $("#quantidade").val(this.value.match(/[0-9]*/));
		  });
	  });
	</script>
</html>